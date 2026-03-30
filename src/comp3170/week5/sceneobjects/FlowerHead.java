package comp3170.week5.sceneobjects;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import org.joml.Matrix4f;
import comp3170.Math;
import java.math.*;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;
public class FlowerHead extends SceneObject {
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private Shader shader;

	private Vector3f petalColour = new Vector3f(1.0f,1.0f,1.0f);

	private Vector4f[] vertices;
	private int vertexBuffer;
	
	private int[] indices;
	private int indexBuffer;
	
	private Vector3f colour;

	public FlowerHead(int nPetals, Vector3f colour) {
		
		// TODO: Create the flower head. (TASK 1)
		// Consider the best way to draw the mesh with the nPetals input. 
		// Note that this may involve moving some code OUT of this class!
		
		
		this.colour = colour;
		
		float innerRadius = 0.3f;
		float outerRadius = 0.6f;
		
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);		
		
		petalColour = colour;
		
		vertices = new Vector4f[nPetals * 2];
		
		
		float colourOffset = (colour.x * 3 + colour.y * 5 + colour.z * 7) % 1f - 0.5f;
		float phi = ((1 + org.joml.Math.sqrt(5)) / 2f);
		float offsetScale = 0.5f;
		float degIncrement = Math.TAU / (nPetals * 2);
		//System.out.println(degIncrement);
		
		
		for (int i = 0; i < nPetals * 2; i++) {
			
			
			float x = org.joml.Math.sin(degIncrement * i + (phi * offsetScale) + colourOffset);
			float y = org.joml.Math.cos(degIncrement * i + (phi * offsetScale) + colourOffset);
			if (i % 2 == 0) {
				
				x *= innerRadius;
				y *= innerRadius;
			} else {
				x *= outerRadius;
				y *= outerRadius;
			}
			
			//x *= (i % 2f * innerRadius) + (((i + 1) % 2f) * outerRadius);
			//y *= (i % 2f * innerRadius) + (((i + 1) % 2f) * outerRadius);
			
			Vector4f vertex = new Vector4f(x,y,0,1);
			vertices[i] = vertex;
			
		}
		vertexBuffer = GLBuffers.createBuffer(vertices);
		
//		for (int i = 0; i < vertices.length; i++) {
//			System.out.print("|" + Float.toString(vertices[i].x) + "," + Float.toString(vertices[i].y) + "|");
//		}
//		5
//		0, 1, 2,
//		0, 2, 3,
//		0, 3, 4,
//		0, 4, 5,
//		0, 5, 6,
//		0, 6, 7,
//		0, 7, 8,
//		0, 8, 9,
		
//		0, 1, 2,
//		0, 2, 3,
//		0, 3, 4,
//		0, 4, 5,
//		0, 5, 6,
//		0, 6, 7,
//		0, 7, 8,
//		0, 8, 9,
//		0, 9, 10,
//		0, 10, 11,
//		
		indices = new int[(((nPetals * 2) - 2)  * 3)];
		
		int v = 1;
		for (int i = 0; i < (((nPetals * 2) - 2) * 3); i += 3) {
			indices[i] = 0;
			
			indices[i + 1] = v;
			v++;
			indices[i + 2] = v;
			
		}
		
//		for (int i = 0; i < indices.length; i++) {
//			System.out.print("|" + Integer.toString(indices[i]) + "|");
//		}
		
		
		    
		indexBuffer = GLBuffers.createIndexBuffer(indices);
//				{
//				new Vector4f( 0,  0, 0, 1),
//				new Vector4f( 0, 0, 0, 1),
//				new Vector4f(0, 0, 0, 1),
//				new Vector4f( 0, 0, 0, 1),
//			};
	}

	public void update(float dt) {
		// TODO: Make the flower head rotate. (TASK 5)
	}

	public void drawSelf(Matrix4f mvpMatrix) {
		
		shader.enable();
		shader.setUniform("u_mvpMatrix", mvpMatrix);
	    shader.setAttribute("a_position", vertexBuffer);
	    shader.setUniform("u_colour", petalColour);	    
	    
	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
	    glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
		// TODO: Add any appropriate draw code. (TASK 1)
	}
}
