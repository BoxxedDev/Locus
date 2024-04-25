#version 120
#include veil:camera
#include veil:deferred_utils

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;

in vec2 texCoord;

out vec4 fragColor;

float planetRadius = 5;
float atmosphereRadius = 6;
float densityFalloff = 4.0;
int numInScatteringPoints= 10;
float dirToSun = 1.0;
float scatteringStrength = 20;
vec3 scatteringCoefficients;
int numOpticalDepthPoints = 10;
float epsilon = 1;

void main() {
    vec4 originalCol = texture(DiffuseSampler, texCoord);

    vec3 rayOrigin = VeilCamera.CameraPosition;
    vec3 rayDir =  normalize(viewDDirFromUv(texCoord));

    vec3 dstToSurface = length(viewPosFromDepthSample(texture(DiffuseDepthSampler, texCoord).r, texCoord));

    vec2 hitInfo = boxIntersection(rayOrigin, rayDir, vec3(planetRadius));
    float dstToAtmosphere = hitInfo.x;
    float dstThroughAtmosphere = hitInfo.y;

    vec3  wavelengths = vec3(700,530,440);

    float scatterR = pow(400 / wavelengths.x, 4) * scatteringStrength;
    float scatterG = pow(400 / wavelengths.y, 4) * scatteringStrength;
    float scatterB = pow(400 / wavelengths.z, 4) * scatteringStrength;
    scatteringCoefficients = vec3(scatterR, scatterG, scatterB);

    //if (dstThroughAtmosphere > 0) {
        vec3 pointInAtmosphere = rayOrigin + rayDir * dstToAtmosphere;
        vec3 light = calculateLight(pointInAtmosphere, rayDir, dstThroughAtmosphere - epsilon * 2, originalCol);
        fragColor = vec4(light, 0);
    //}
    //fragColor = originalCol;
}

// Calcs intersection and exit distances, and normal at intersection.
// The ray must be in box/object space. If you have multiple boxes all
// aligned to the same axis, you can precompute 1/rd. If you have
// multiple boxes but they are not alligned to each other, use the
// "Generic" box intersector bellow this one.
vec2 boxIntersection( in vec3 ro, in vec3 rd, in vec3 rad)
{
    vec3 m = 1.0/rd;
    vec3 n = m*ro;
    vec3 k = abs(m)*rad;
    vec3 t1 = -n - k;
    vec3 t2 = -n + k;

    float tN = max( max( t1.x, t1.y ), t1.z );
    float tF = min( min( t2.x, t2.y ), t2.z );

    if( tN>tF || tF<0.0) return vec2(-1.0); // no intersection

    //oN = -sign(rd)*step(t1.yzx,t1.xyz)*step(t1.zxy,t1.xyz);

    return vec2( tN, tF );
}

vec3 calculateLight(vec3 rayOrigin, vec3 rayDir, float rayLength, vec3 originalCol) {
    vec3 inScatterPoint = rayOrigin;
    float stepSize = rayLength / (numInScatteringPoints - 1);
    vec3 inScatteredLight = vec3(0,0,0);

    for (int i = 0; i < numInScatteringPoints; i ++) {
        float sunRayLength = boxIntersection(inScatterPoint, dirToSun, vec3(planetRadius)).y;
        float sunRayOpticalDepth = opticalDepth(inScatterPoint, dirToSun, sunRayLength);
        float viewRayOpticalDepth = opticalDepth(inScatterPoint, -rayDir, stepSize * i);
        vec3 transmittance = exp(-(sunRayOpticalDepth + viewRayOpticalDepth)) * scatteringCoefficients;
        float localDensity = densityAtPoint(inScatterPoint);

        inScatteredLight += localDensity * transmittance * scatteringCoefficients * stepSize;
        inScatterPoint += rayDir * stepSize;
    }
    float originalColTransmittance = exp(-viewRayOpticalDepth);
    return originalCol * originalColTransmittance + inScatteredLight;
}

float densityAtPoint(vec3 densitySamplePoint) {
    float heightAboveSurface = length(densitySamplePoint) - planetRadius;
    float height01 = heightAboveSurface / (atmosphereRadius - planetRadius);
    float localDensity = exp(-height01 * densityFalloff) * (1 - height01);
    return localDensity;
}

float opticalDepth(vec3 rayOrigin, vec3 rayDir, float rayLength) {
    vec3 densitySamplePoint = rayOrigin;
    float stepSize = rayLength / (numOpticalDepthPoints - 1);
    float opticalDepth = 0;

    for (int i = 0; i < numOpticalDepthPoints; i ++) {
        float localDensity = densityAtPoint(densitySamplePoint);
        opticalDepth += localDensity * stepSize;
        densitySamplePoint += rayDir * stepSize;
    }
    return opticalDepth;
}