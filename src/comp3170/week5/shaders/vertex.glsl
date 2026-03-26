#version 410

in vec4 a_position;		// MODEL
uniform mat4 u_mvpMatrix;	// MODEL->WORLD
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
	
	//vec3 p = vec3(a_position, 1);
	
	//p = u_mvpMatrix * p;
	//p = viewMatrix * p;
	//p = projectionMatrx * p;
	
	//gl_position = vec4(p.xy, 0, 1);
	mat4 matrix = projectionMatrix * viewMatrix * u_mvpMatrix;
	gl_Position = matrix * a_position;
	
	
}

