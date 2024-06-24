uniform sampler2D DiffuseSampler0;

in vec2 texCoord;

out vec4 fragColor;

float levels = 10;

void main() {
    vec4 originalCol = texture(DiffuseSampler0, texCoord);

    float greyscale = max(originalCol.r, max(originalCol.g, originalCol.b));

    float lower = floor(greyscale * levels) / levels;
    float lowerDiff = abs(greyscale - lower);

    float upper = ceil(greyscale * levels) / levels;
    float upperDiff = abs(upper - greyscale);

    float level = lowerDiff <= upperDiff ? lower : upper;
    float adjustment = level / greyscale;

    fragColor = vec4(originalCol.rgb * adjustment, originalCol.a);
}