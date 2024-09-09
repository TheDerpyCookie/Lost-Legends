#version 150

vec2 center = vec2(0.5, 0.5);
float speed = 0.035;

out vec4 fragColor;

uniform vec2 OutSize;
uniform float STime;
uniform float Intensity;

in vec2 texCoord;
uniform sampler2D DiffuseSampler;

vec2 noise(vec2 p) {
    return fract(1234.1234 * sin(1234.1234 * (fract(1234.1234 * p) + p.yx)));
}

float heart(vec2 p, float s) {
    p /= s;
    vec2 q = p;
    q.x *= 0.5 + .5 * q.y;
    q.y -= abs(p.x) * .63;
    return (length(q) - .7) * s;
}

vec3 hearts(vec2 polar, float time, float fft) {
    float l = clamp(polar.y, 0., 1.);
    float tiling = 1./3.14159 * 5.;
    polar.y -= time;
    vec2 polarID = (floor(polar * tiling));

    polar.x = polar.x + polarID.y * .03;
    polar.x = mod(polar.x + 3.14159 * 2., 3.14159 * 2.);
    polarID = floor(polar * tiling);

    polar = fract(polar * tiling);

    polar = polar * 2. - 1.;
    vec2 n = noise(polarID + .1) * .75 + .25;
    vec2 n2 = 2. * noise(polarID) - 1.;
    vec2 offset = (1. - n.y) * n2;
    float heartDist = heart(polar + offset, n.y * .6);
    float a = smoothstep(.0, .25, n.x*n.x);
    float heartGlow = smoothstep(0., -.05, heartDist) * .5 * a + smoothstep(0.3, -.4, heartDist) * .75;
    vec3 heartCol = vec3(smoothstep(0., -.05, heartDist), 0., 0.) * a + heartGlow * vec3(.9, .5, .7);
    vec3 bgCol = vec3(0.15 + l / 2., .0, 0.);
    return bgCol * (.5 + fft) + heartCol;
    //    return vec3(heartCol);
}

void main() {
    vec2 uv = (2. * gl_FragCoord.xy - OutSize.xy) / OutSize.y;
    vec2 polar = vec2(atan(uv.y, uv.x), log(length(uv)));
    //float x = mod(polar.x + STime * .3, 3.14159 * 2.) - 3.14159;
    //float m = smoothstep(0.01, 0., abs(x) - .2);
    float speed = 2.5;
    float fft = abs(sin(STime));
    vec3 h = max(max(hearts(polar, STime * speed, fft),
    hearts(polar, STime * speed * 1.2 + 12., fft)),
    hearts(polar, STime * speed * .8 + 23., fft));
    //uv = 2. * fragCoord / OutSize.xy - 1.;
    //fragColor = vec4(mix(vec3(1. - length(uv) * .075), h, m), 1.);
    fragColor = vec4(mix(texture(DiffuseSampler, texCoord).rgb, h, (Intensity*length(h))), 1.0);
}