package gj.game.views;


import gj.game.Orchestrator;

import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen {
    private Orchestrator parent;

    public LoadingScreen(Orchestrator engine){
        parent = engine;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.assMan.queueAddImages();
        parent.assMan.manager.finishLoading();

        parent.changeScreen(Orchestrator.MENU);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
