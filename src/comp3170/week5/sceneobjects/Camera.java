package comp3170.week5.sceneobjects;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;
import comp3170.InputManager;

public class Camera extends SceneObject {

	private float zoom = 1000.0f; // You'll need this when setting up your projection matrix...
	private Vector2f position = new Vector2f(0f,0f);
	private float rotation = 0f;
	
	
	public Matrix4f projectionMatrix = new Matrix4f();
	public Matrix4f viewMatrix = new Matrix4f();
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private Shader shader;
//	
	private int width = 800;
	private int height = 800;
	
	public Camera() {
		
		System.out.println("camera start");
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);	
		projectionMatrix.identity();
		
		viewMatrix.identity();
		viewMatrix.m30(-0f);
		viewMatrix.m31(-0f);
							
	}
	
	private void updateViewMatrix() {
		
		viewMatrix.identity();
		viewMatrix.m30(-position.x);
		viewMatrix.m31(-position.y);
		
		viewMatrix.m00((float) Math.cos(rotation));
		viewMatrix.m10((float) -Math.sin(rotation));
		viewMatrix.m01((float) Math.sin(rotation));
		viewMatrix.m11((float) Math.cos(rotation));
		viewMatrix.invert();
		
		
	}
	
	private void updateProjectionMatrix() {
		
		projectionMatrix.identity();
		projectionMatrix.m00(( width) / zoom);
		projectionMatrix.m11((	height) / zoom);
		projectionMatrix.invert();
		
	}
	
	public void resize(int w, int h) {
		
		width = w;
		height = h;
		updateProjectionMatrix();
		//TODO: Change the projection matrix when the window is resized. (TASK 2)
	}
	
	
	
	public Matrix4f GetViewMatrix(Matrix4f dest) {
		viewMatrix = getMatrix();
		return viewMatrix.invert(dest);
	}
	
	public Matrix4f GetProjectionMatrix(Matrix4f dest) {
		return projectionMatrix.invert(dest);
	}
	
// TODO: Make the camera zoom in-and-out based on user input. (TASK 4)
// You'll need to move some code around!
	
	public void update(InputManager input, float deltaTime) {
		
		
		//System.out.println("updating camera");
		
		
		if (input.isKeyDown(GLFW_KEY_UP)) {
			// TODO: Zoom the camera in
			zoom *= 1.01f;
		}
			
		if (input.isKeyDown(GLFW_KEY_DOWN)) {
			// TODO: Zoom the camera out
			zoom *= 0.99f;
		}
		
		if (input.isKeyDown(GLFW_KEY_LEFT)) {
			rotation -= deltaTime;
		}
		
		if (input.isKeyDown(GLFW_KEY_RIGHT)) {
			rotation += deltaTime;
		}
		
		updateProjectionMatrix();
		updateViewMatrix();
		
	}
	
	@Override
	public void drawSelf(Matrix4f mvpMatrix) {
		//System.out.println("hello");
		shader.enable();
		
		
		//System.out.println(viewMatrix);
		shader.setUniform("viewMatrix", viewMatrix);
		shader.setUniform("projectionMatrix", projectionMatrix);
	}
}