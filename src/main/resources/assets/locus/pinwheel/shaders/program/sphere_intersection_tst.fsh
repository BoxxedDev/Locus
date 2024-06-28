#include veil:camera
#include veil:deferred_utils

uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;
uniform sampler2D PlanetTexture;
uniform float GameTime;

in vec2 texCoord;

out vec4 fragColor;

uniform float MAX = 1000.0;
uniform float PI = 3.14159265359;
uniform vec3 centerLoc = vec3(0,250,0);
uniform float sphereRad = 250.;

vec2 raySphere(vec3 sphereCentre, float sphereRadius, vec3 rayOrigin, vec3 rayDir) {
    vec3 offset = rayOrigin - sphereCentre;
    float a = 1; //Set to dot(rayDir, rayDir) if rayDir might not be normalized
    float b = 2 * dot(offset, rayDir);
    float c = dot(offset, offset) - sphereRadius * sphereRadius;
    float d = b * b - 4 * a * c;

    //Number of intersections: 0 when d < 0; 1 when d = 0; 2 when d > 0
    if (d > 0) {
        float s = sqrt(d);
        float dstToSphereNear = max(0, (-b -s) / (2 * a));
        float dstToSphereFar = (-b + s) / (2*a);

        //Ignore intersections behind the ray
        if (dstToSphereFar >= 0) {
            return vec2(dstToSphereNear, dstToSphereFar - dstToSphereNear);
        }
    }

    return vec2(MAX, 0);
}

void main() {
    vec3 lightDir = vec3(sin(GameTime*1000)*300,0,cos(GameTime*1000)*300);

    vec4 originalCol = texture(DiffuseSampler0, texCoord);

    vec3 rayOrigin = VeilCamera.CameraPosition;
    rayOrigin -= centerLoc;
    vec3 rayDir =  normalize(viewDirFromUv(texCoord));

    vec2 rayHit = raySphere(vec3(0), sphereRad, rayOrigin, rayDir);

    vec3 worldPos = rayDir * rayHit.x + rayOrigin;
    vec3 worldNormal = normalize(worldPos);

    float lightDot = dot(worldNormal, lightDir);

    // -0.5 to 0.5 range
    float phi = atan(worldNormal.z, worldNormal.x) / (PI * 2.0);
    // 0.0 to 1.0 range
    float phi_frac = fract(phi);

    vec2 uv = vec2((fwidth(phi) < fwidth(phi_frac) - 0.001 ? phi : phi_frac) + (GameTime*100), acos(-worldNormal.y) / PI);

    float dstToSurface = length(viewPosFromDepthSample(texture(DiffuseDepthSampler, texCoord).r, texCoord)-centerLoc);

    float dstToAtmosphere = rayHit.x;
    float dstToSurface2 = min(rayHit.y,  (dstToSurface)/rayHit.x*(length(VeilCamera.CameraPosition)/1000.));
    float dstToSurfaceClamped = dstToSurface2 < 0.5 ? 0. : 1.;

    vec3 planetTexV = texture(PlanetTexture, -uv).rgb;
    lightDot /= 200;
    float newLightDot = sin(lightDot < 0.05 ? 0.05 : lightDot);
    vec3 lighting = planetTexV * (newLightDot);

    gl_FragDepth = texture(DiffuseDepthSampler, texCoord).r;
    if ((dstToSurfaceClamped) > 0.99) {
        fragColor = vec4(lighting, 1.);
    } else {
        fragColor = originalCol;
    }

}












