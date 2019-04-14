package gj.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gj.game.Orchestrator;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Shiny Tasty Burgers";
		config.useGL30 = false;
		config.width = 1280;
		config.height = 1024;


		new LwjglApplication(new Orchestrator(), config);
	}
}
