uniform sampler2D DiffuseSampler0;
in vec2 texCoord;
out vec4 fragColor;

float warp = 0.75; // simulate curvature of CRT monitor
float scan = 0.75; // simulate darkness between scanlines

void main() {
    // squared distance from center
    vec2 uv = texCoord;
    vec2 dc = abs(0.5-uv);
    dc *= dc;

    // warp the fragment coordinates
    uv.x -= 0.5; uv.x *= 1.0+(dc.y*(0.3*warp)); uv.x += 0.5;
    uv.y -= 0.5; uv.y *= 1.0+(dc.x*(0.4*warp)); uv.y += 0.5;

    // sample inside boundaries, otherwise set to black
    if (uv.y > 1.0 || uv.x < 0.0 || uv.x > 1.0 || uv.y < 0.0)
    fragColor = vec4(0.0,0.0,0.0,1.0);
    else
    {
        // determine if we are drawing in a scanline
        float apply = abs(sin(texCoord.y)*0.5*scan);
        // sample the texture
        fragColor = vec4(mix(texture(DiffuseSampler0,uv).rgb,vec3(0.0),apply),1.0);
    }
}
