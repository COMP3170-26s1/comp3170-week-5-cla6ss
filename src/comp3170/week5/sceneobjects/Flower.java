package comp3170.week5.sceneobjects;

import static org.lwjgl.opengl.GL41.*;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;

public class Flower extends SceneObject {
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private Shader shader;
	
	private final float HEIGHT = 1.0f;
	private final float WIDTH = 0.1f;
	private Vector3f colour = new Vector3f(0f, 0.5f, 0f); // Dark Green

	private Vector4f[] vertices;
	private int vertexBuffer;
	private int[] indices;
	private int indexBuffer;
	
	private float time;
	private ArrayList<Flower> flowers = new ArrayList<Flower>();

	public Flower(float height) {
		
		time = colour.x * 3 + colour.y * 11 + colour.z * 19;
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);		
	
		// make the stem of the flower

		// vertices for a wxh square with origin at the end
		// 
		//  (-w/2, h)     (w/2, h)		
		//       2-----------3
		//       | \         |
		//       |   \       |
		//       |     \     |
		//       |       \   |
		//       |         \ |
		//       0-----*-----1
		//  (-w/2, 0)     (w/2, 0)	
		
		
		
		
		//@formatter:off
		vertices = new Vector4f[] {
			new Vector4f(-WIDTH / 2,           0, 0, 1),
			new Vector4f( WIDTH / 2,           0, 0, 1),
			new Vector4f(-WIDTH / 2, height, 0, 1),
			new Vector4f( WIDTH / 2, height, 0, 1),
		};
		//@formatter:on
		vertexBuffer = GLBuffers.createBuffer(vertices);
		
	    indices = new int[] {
		    	0, 1, 2,
		    	3, 2, 1,
		};
		    
		indexBuffer = GLBuffers.createIndexBuffer(indices);
	}
	
	public void drawSelf(Matrix4f mvpMatrix) {
		
//		long time = System.currentTimeMillis();
//		System.out.println(Math.sin((float) time / 100));
//		
//			
//		}
		
		shader.enable();
		shader.setUniform("u_mvpMatrix", mvpMatrix);
	    shader.setAttribute("a_position", vertexBuffer);
	    shader.setUniform("u_colour", colour);	    
	    
	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
	    glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
	    
	    //this.update(0.01f);
	}
	
	public void update(float dt) {
		float speed = 1.2f;
		time += dt * speed;
		
//		for (int i = 2; i < 4; i++) {
//			System.out.println(vertices[i]);
//			vertices[i].x += Math.sin(time);
//		}
		float waveWidth = 1f;
		float waveOffset = (float) Math.sin(time + Math.sin(getMatrix().m30() * waveWidth) + Math.cos(getMatrix().m31() * waveWidth));
		float sway = (float) Math.sin(time) * 0.03f;
		getMatrix().m10((float) (getMatrix().get(0, 1) + waveOffset * 0.05f));
		//System.out.println(Math.sin(time));
		// TODO: make the flower sway. (TASK 5)
	}
}
