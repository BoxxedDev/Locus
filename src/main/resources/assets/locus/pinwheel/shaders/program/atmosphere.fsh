#include veil:camera
#include veil:deferred_utils

uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;
uniform float GameTime;

in vec2 texCoord;

out vec4 fragColor;

uniform float planetRadius = 250.;
uniform float atmosphereRadius = 255.;
uniform float densityFalloff = 4.0;
uniform int numInScatteringPoints= 10;
uniform float scatteringStrength = 20.;
uniform int numOpticalDepthPoints = 10;
uniform float MAX = 10000.0;
uniform float PI = 3.14159265359;
uniform vec3 planetCentre  = vec3(0,250,0);

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

vec3 calculateLight(vec3 rayOrigin, vec3 rayDir, float rayLength, vec3 originalCol, vec3 scatteringCoefficients) {
    vec3 inScatterPoint = rayOrigin;
    float stepSize = rayLength / (numInScatteringPoints - 1);
    vec3 inScatteredLight = vec3(0,0,0);
    float viewRayOpticalDepth = 0;
    vec3 dirToSun = vec3(sin(GameTime*1000)*300,sin(GameTime*1000)*70,cos(GameTime*1000)*300);

    for (int i = 0; i < numInScatteringPoints; i ++) {
        vec2 intersection = raySphere(vec3(0), planetRadius, inScatterPoint, dirToSun);
        float sunRayLength = intersection.y;
        float sunRayOpticalDepth = opticalDepth(inScatterPoint, dirToSun, sunRayLength);
        viewRayOpticalDepth = opticalDepth(inScatterPoint, -rayDir, stepSize * i);
        vec3 transmittance = exp(-(sunRayOpticalDepth + viewRayOpticalDepth)) * scatteringCoefficients;
        float localDensity = densityAtPoint(inScatterPoint);

        inScatteredLight += localDensity * transmittance * scatteringCoefficients * stepSize;
        inScatterPoint += rayDir * stepSize;
    }

    float originalColTransmittance = exp(-viewRayOpticalDepth);
    return originalCol * originalColTransmittance + inScatteredLight;
}

void main() {
    vec4 originalCol = texture(DiffuseSampler0, texCoord);

    vec3 rayOrigin = VeilCamera.CameraPosition;
    rayOrigin -= planetCentre;
    vec3 rayDir =  normalize(viewDirFromUv(texCoord));

    vec2 planetHit = raySphere(vec3(0), planetRadius, rayOrigin, rayDir);
    float dstToSurface = length(viewPosFromDepthSample(texture(DiffuseDepthSampler, texCoord).r, texCoord)-planetCentre);

    vec2 hitInfo = raySphere(vec3(0), atmosphereRadius, rayOrigin, rayDir);
    float dstToAtmosphere = hitInfo.x;

    float dstThroughAtmosphere = min(hitInfo.y,  planetHit.x - dstToAtmosphere)/20.;
    float dstThroughAtmosphere2 = min(hitInfo.y, dstToSurface - dstToAtmosphere)/20.;

    float dstToSurface2 = min(hitInfo.y,  (dstToSurface)/hitInfo.x*(length(VeilCamera.CameraPosition)/1000.));
    float dstToSurfaceClamped = dstToSurface2 < 0.5 ? 0. : 1.;

    //Don't go below 400
    //Default earth is 700,530,440
    vec3 wavelengths = vec3(700,530,440);

    float scatterR = pow(400 / wavelengths.x, 4) * scatteringStrength;
    float scatterG = pow(400 / wavelengths.y, 4) * scatteringStrength;
    float scatterB = pow(400 / wavelengths.z, 4) * scatteringStrength;
    vec3 scatteringCoefficients = vec3(scatterR, scatterG, scatterB);

    if (dstToSurfaceClamped > 0) {
        const float epsilon = 0.0001;
        vec3 pointInAtmosphere = rayOrigin + rayDir * dstToAtmosphere;
        vec3 light = calculateLight(pointInAtmosphere, rayDir, dstThroughAtmosphere - epsilon * 2, originalCol.rgb, scatteringCoefficients);
        fragColor = vec4(light, 0);
    } else {
        fragColor = originalCol;
    }

}






