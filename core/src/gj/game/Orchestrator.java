package gj.game;


import gj.game.loader.gjAssetManager;
import gj.game.views.EndScreen;
import gj.game.views.LoadingScreen;
import gj.game.views.MainScreen;
import gj.game.views.MenuScreen;
import gj.game.views.PreferencesScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;


public class Orchestrator extends Game {

	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	private AppPreferences preferences;
	public gjAssetManager assMan = new gjAssetManager();
	private Music playingSong;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;

	@Override
	public void create () {
		loadingScreen = new LoadingScreen(this);
		preferences = new AppPreferences();
		setScreen(menuScreen);

		// tells our asset manger that we want to load the images set in loadImages method
		assMan.queueAddMusic();

		// tells the asset manager to load the images and wait until finished loading.
		assMan.manager.finishLoading();


		//playingSong = assMan.manager.get("test.mp3");

		//playingSong.play();

	}

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	public AppPreferences getPreferences(){
		return this.preferences;
	}

	@Override
	public void dispose(){
		playingSong.dispose();
		assMan.manager.dispose();
	}

}
