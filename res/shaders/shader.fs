#version 120

uniform sampler2D sampler;
uniform float red = 1;
uniform float green = 1;
uniform float blue = 1;
uniform float alpha = 1;

varying vec2 tex_coords;

void main(){
	gl_FragColor = vec4(red,green,blue,alpha) * texture2D(sampler, tex_coords);
	
}