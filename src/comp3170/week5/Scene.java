package comp3170.week5;

import java.util.ArrayList;
import java.util.Random;

import org.joml.Vector3f;
import org.joml.Vector4f;
import comp3170.InputManager;
import comp3170.SceneObject;


import comp3170.week5.sceneobjects.*;

public class Scene extends SceneObject {
	private Camera camera;
	private ArrayList<Flower> flowers = new ArrayList<Flower>();
	private Random rand;
	
	public Scene() {
		rand = new Random();
		camera = new Camera();
		camera.setParent(this);
		
		for (int i = 0; i < 1000; i++ ) {
			float x = (rand.nextFloat() * 10) - 5;
			float y = (rand.nextFloat() * 10) - 5;
			createFlower(new Vector4f(x,y,0.f,1.0f));		
		}
		//createFlower(new Vector4f(0.0f,0.0f,0.f,1.0f));		
	}
	
	public Camera sceneCam() {
		return camera;
	}
	
	public void createFlower(Vector4f position) {
		//System.out.println("making dah flowa");
		
		float h = rand.nextFloat() * 0.7f + 0.5f;
		Flower flower = new Flower(h);
		flower.setParent(this);	
		flower.getMatrix().translate(position.x,position.y,0.0f);
		
		
		int petals = (Math.abs(rand.nextInt()) % 15) + 4;
		float red = rand.nextFloat() * 0.8f + 0.2f;
		float green = rand.nextFloat() * 0.8f + 0.2f;
		float blue = rand.nextFloat() * 0.8f + 0.2f;
		
		
		FlowerHead fh = new FlowerHead(petals, new Vector3f(red,green,blue));
		fh.getMatrix().translate(0f,h,0).scale(0.7f);
		fh.setParent(flower);
		
		flower.getMatrix().scale(0.3f);
		
		flowers.add(flower);
		
	}
	
	

	public void update(InputManager input, float dt) {
		
		camera.update(input, dt);
		
		for (Flower f: flowers) {
			f.update(dt);
		}
		// TODO: Update the flowers when animating them. (TASK 5)
	}
	
}
