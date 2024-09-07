/** Copyright (c) 2022-2024, Harry Huang
 * At GPL-3.0 License
 */

// Gap Seaming and Ouline Effect Fragment Shader for TwoColorPolygonBatch.

#version 130

varying vec2 v_texCoords;       // From VS
uniform sampler2D u_texture;    // From TCPB
uniform vec3 u_outlineColor;    // Required
uniform float u_outlineWidth;   // Required

const float c_transparentAlpha = 0.1;
const float c_seamSampleAlpha = 0.9;
const float c_seamCoef = 0.5;

vec4[8] getNeighbors(sampler2D tex, vec2 texCoords, vec2 offset) {
    vec4[8] result;
    result[0] = texture2D(tex, texCoords + vec2(offset.x, 0.0));
    result[1] = texture2D(tex, texCoords - vec2(offset.x, 0.0));
    result[2] = texture2D(tex, texCoords + vec2(0.0, offset.y));
    result[3] = texture2D(tex, texCoords - vec2(0.0, offset.y));
    result[4] = texture2D(tex, texCoords + offset);
    result[5] = texture2D(tex, texCoords - offset);
    result[6] = texture2D(tex, texCoords + vec2(offset.x, -offset.y));
    result[7] = texture2D(tex, texCoords - vec2(offset.x, -offset.y));
    return result;
}

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);
    ivec2 texSize = textureSize(u_texture, 0);

    if (texColor.a < c_transparentAlpha) {
        // Outline effect apply on transparent areas
        if (u_outlineWidth > 0.0) {
            vec2 relOutlineSize = vec2(u_outlineWidth * (1.0 / texSize.x), u_outlineWidth * (1.0 / texSize.y));
            vec4[8] neighbors = getNeighbors(u_texture, v_texCoords, relOutlineSize);
            float neighborAlpha = 0.0;
            for (int i = 0; i < neighbors.length(); i++) {
                neighborAlpha += neighbors[i].a;
            }
            if (neighborAlpha > c_transparentAlpha) {
                gl_FragColor.rgb = u_outlineColor.rgb;
                gl_FragColor.a = min(1.0, neighborAlpha);
            } else {
                gl_FragColor = texColor;
            }
        } else {
            gl_FragColor = texColor;
        }
    } else if (texColor.a < c_seamSampleAlpha) {
        // Seaming apply on gap areas
        vec2 pixelSize = vec2(1.0 / texSize.x, 1.0 / texSize.y);
        vec4[8] neighbors = getNeighbors(u_texture, v_texCoords, pixelSize);
        vec4 sampleColor = vec4(0.0);
        int sampleSize = 0;
        for (int i = 0; i < neighbors.length(); i++) {
            if (neighbors[i].a > c_seamSampleAlpha) {
                sampleColor += neighbors[i];
                sampleSize += 1;
            }
        }
        if (sampleSize > 0) {
            gl_FragColor = sampleColor / sampleSize * c_seamCoef + texColor * (1.0 - c_seamCoef);
        } else {
            gl_FragColor = texColor;
        }
    } else {
        // No effect apply on other areas
        gl_FragColor = texColor;
    }

    gl_FragColor.a = gl_FragColor.a < 0.5 ? gl_FragColor.a * 2.0 : 1.0;
}