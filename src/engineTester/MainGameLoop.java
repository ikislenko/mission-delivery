package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.omg.CORBA.OBJ_ADAPTER;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();


		RawModel model = OBJLoader.loadObjModel("stall", loader);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));

		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(0.1f);
		Entity entity = new Entity(staticModel, new Vector3f(0, -5, -25), 0, 0, 0, 1);
		Light light = new Light(new  Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested()) {
			entity.increaseRotation(0, 0.3f, 0);
			camera.move();
			renderer.processEntity(entity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();
	}

}
