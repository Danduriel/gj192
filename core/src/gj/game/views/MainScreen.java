package gj.game.views;

import gj.game.gjModel;
import gj.game.controller.KeyboardController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import gj.game.Orchestrator;



public class MainScreen implements Screen{

    private Orchestrator parent;
    private gjModel gjModel;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    private KeyboardController controller;
    private AtlasRegion playerTex;
    private SpriteBatch sb;
    private TextureAtlas atlas;


    public MainScreen(Orchestrator Orchestrator) {
        parent = Orchestrator;
        cam = new OrthographicCamera(64,48);
        controller = new KeyboardController();
        gjModel = new gjModel(controller,cam,parent.assMan);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

        atlas = parent.assMan.manager.get("images/game.atlas");
        playerTex = atlas.findRegion("player");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        gjModel.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(playerTex, gjModel.player.getPosition().x -1, gjModel.player.getPosition().y -1,2,2);
        sb.end();


        debugRenderer.render(gjModel.world, cam.combined);

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
