package me.cortex.meshium.gl.shader;

public interface IShaderProcessor {
    String process(ShaderType type, String source);
}
