
uniform sampler2D DiffuseSampler0;

in vec2 texCoord;

out vec4 fragColor;

#define THRESHOLD 0.01
// Default is 0.05
#define INTENSITY 1.0
// Default is 1.0

vec4 blend(in vec2 Coord, in sampler2D Tex, in float MipBias)
{
    vec2 TexelSize = 1.0 / textureSize(DiffuseSampler0, 0).xy;

    vec4 Color = texture(Tex, Coord);

    for (float i = 1.; i <= 6.; i += 1.)
    {
        float inv = 1./i;
        Color += texture(Tex, Coord + vec2( TexelSize.x, TexelSize.y)*inv);
        Color += texture(Tex, Coord + vec2(-TexelSize.x, TexelSize.y)*inv);
        Color += texture(Tex, Coord + vec2( TexelSize.x,-TexelSize.y)*inv);
        Color += texture(Tex, Coord + vec2(-TexelSize.x,-TexelSize.y)*inv);
    }

    return Color/24.0;
}

void main() {
    vec2 uv = (texCoord.xy);

    vec4 Color = texture(DiffuseSampler0, uv);

    vec4 Highlight = clamp(blend(uv, DiffuseSampler0, 4.0)-THRESHOLD,0.0,1.0)*1.0/(1.0-THRESHOLD);

    fragColor = 1.0-(1.0-Color)*(1.0-Highlight*INTENSITY);
}
