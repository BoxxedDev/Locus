uniform sampler2D DiffuseSampler0;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler0, texCoord);

    fragColor = vec4(1.0-color.r, 1.0-color.g, 1.0-color.b, color.a);
}