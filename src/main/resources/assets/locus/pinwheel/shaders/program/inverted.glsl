#version 120

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 originalCol = texture(DiffuseSampler, texCoord);

    fragColor = vec4(1-originalCol.x, 1-originalCol.y, 1-originalCol.z, originalCol.w);
}
