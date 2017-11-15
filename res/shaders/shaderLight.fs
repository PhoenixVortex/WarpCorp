#version 120

uniform sampler2D sampler;
uniform float red = 1;
uniform float green = 1;
uniform float blue = 1;
uniform float alpha = 1;
uniform float radius = 1;
uniform float str = 1;

varying vec2 tex_coords;

void main(){
	float a;
	if(radius > 0)
		a = str/radius;
	else
		a = alpha;
	gl_FragColor = vec4(red,green,blue,a) * texture2D(sampler, tex_coords);
	
}