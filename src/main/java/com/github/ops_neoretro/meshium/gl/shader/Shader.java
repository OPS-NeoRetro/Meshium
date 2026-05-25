package com.github.ops_neoretro.gl.shader;

import com.github.ops_neoretro.gl.GlObject;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GL;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Shader extends GlObject {
    private Shader(int program) {
        super(program);
    }

    public static Builder make(IShaderProcessor processor) {
        return new Builder(processor);
    }

    public static Builder make() {
        return new Builder((aa,source)->source);
    }

    public void bind() {
        glUseProgram(id);
    }

    public void delete() {
        super.free0();
        glDeleteProgram(id);
    }

    @Override
    public void free() {
        this.delete();
    }

    public static class Builder {
        private final Map<ShaderType, String> sources = new HashMap<>();
        private final IShaderProcessor processor;
        private Builder(IShaderProcessor processor) {
            this.processor = processor;
        }
        public Builder addSource(ShaderType type, String source) {
            var processed = processor.process(type, source);
            sources.put(type, applyCrossVendorExtensionAliases(processed));
            return this;
        }

        private static String applyCrossVendorExtensionAliases(String source) {
            GLCapabilities capabilities = GL.getCapabilities();
            String patched = source;

            if (!capabilities.GL_NV_mesh_shader && capabilities.GL_EXT_mesh_shader) {
                patched = patched.replace("GL_NV_mesh_shader", "GL_EXT_mesh_shader");
            }
            if (!capabilities.GL_NV_gpu_shader5 && capabilities.GL_ARB_gpu_shader5) {
                patched = patched.replace("GL_NV_gpu_shader5", "GL_ARB_gpu_shader5");
            }
            if (!capabilities.GL_NV_bindless_texture && capabilities.GL_ARB_bindless_texture) {
                patched = patched.replace("GL_NV_bindless_texture", "GL_ARB_bindless_texture");
            }
            if (!capabilities.GL_NV_shader_buffer_load && capabilities.GL_ARB_shader_buffer_load) {
                patched = patched.replace("GL_NV_shader_buffer_load", "GL_ARB_shader_buffer_load");
            }
            if (!capabilities.GL_NV_fragment_shader_barycentric && capabilities.GL_AMD_shader_explicit_vertex_parameter) {
                patched = patched.replace("GL_NV_fragment_shader_barycentric", "GL_AMD_shader_explicit_vertex_parameter");
            }

            return patched;
        }

        public Shader compile() {
            int program = GL20C.glCreateProgram();
            int[] shaders = sources.entrySet().stream().mapToInt(a->createShader(a.getKey(), a.getValue())).toArray();

            for (int i : shaders) {
                GL20C.glAttachShader(program, i);
            }
            GL20C.glLinkProgram(program);
            for (int i : shaders) {
                GL20C.glDetachShader(program, i);
                GL20C.glDeleteShader(i);
            }
            printProgramLinkLog(program);
            verifyProgramLinked(program);
            return new Shader(program);
        }


        private static void printProgramLinkLog(int program) {
            String log = GL20C.glGetProgramInfoLog(program);

            if (!log.isEmpty()) {
                System.err.println(log);
            }
        }

        private static void verifyProgramLinked(int program) {
            int result = GL20C.glGetProgrami(program, GL20C.GL_LINK_STATUS);

            if (result != GL20C.GL_TRUE) {
                throw new RuntimeException("Shader program linking failed, see log for details");
            }
        }

        private static int createShader(ShaderType type, String src) {
            int shader = GL20C.glCreateShader(type.gl);
            GL20C.glShaderSource(shader, src);
            GL20C.glCompileShader(shader);
            String log = GL20C.glGetShaderInfoLog(shader);

            if (!log.isEmpty()) {
                System.err.println(log);
            }

            int result = GL20C.glGetShaderi(shader, GL20C.GL_COMPILE_STATUS);

            if (result != GL20C.GL_TRUE) {
                GL20C.glDeleteShader(shader);

                throw new RuntimeException("Shader compilation failed, see log for details");
            }

            return shader;
        }
    }

}
