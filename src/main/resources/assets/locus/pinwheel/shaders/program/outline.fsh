uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;
in vec2 texCoord;
out vec4 fragColor;

const mat3 kernelX = mat3(
-1.0, 0.0, 1.0,
-2.0, 0.0, 2.0,
-1.0, 0.0, 1.0
);

const mat3 kernelY = mat3(
-1.0, -2.0, -1.0,
0.0,  0.0,  0.0,
1.0,  2.0,  1.0
);

float sobelEdge(sampler2D sampler) {
    vec2 texelSize = 1.0 / textureSize(sampler, 0).xy;

    float edgeX = 0.0;
    float edgeY = 0.0;

    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            vec2 offset = vec2(float(i), float(j)) * texelSize;
            vec3 tex = vec3(texture(sampler, texCoord + offset).r*25.);
            float gray = dot(tex, vec3(0.299, 0.587, 0.114));
            edgeX += gray * kernelX[(i + 1)][(j + 1)];
            edgeY += gray * kernelY[(i + 1)][(j + 1)];
        }
    }

    float edge = sqrt(edgeX * edgeX + edgeY * edgeY);

    return edge;
}

void main() {
    vec4 col = texture(DiffuseSampler0, texCoord);

    float edge = sobelEdge(DiffuseDepthSampler);

    if (edge > 0.1) {
        fragColor = vec4(col * 1.5); // White color for edges
    } else {
        fragColor = col;
    }
}