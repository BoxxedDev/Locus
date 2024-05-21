uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);

    fragColor = color;
    //fragColor = vec4(1.0-originalCol.x, 1.0-originalCol.y, 1.0-originalCol.z, originalCol.w);
}