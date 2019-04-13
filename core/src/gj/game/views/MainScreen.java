package gj.game.views;


import gj.game.Orchestrator;
import gj.game.LevelFactory;
import gj.game.controller.KeyboardController;
import gj.game.entities.systems.AnimationSystem;
import gj.game.entities.systems.CollisionSystem;
import gj.game.entities.systems.LevelGenerationSystem;
import gj.game.entities.systems.PhysicsDebugSystem;
import gj.game.entities.systems.PhysicsSystem;
import gj.game.entities.systems.PlayerControlSystem;
import gj.game.entities.systems.RenderingSystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class MainScreen implements Screen{
    private Orchestrator parent;
    private OrthographicCamera cam;
    private KeyboardController controller;
    private SpriteBatch sb;
    private PooledEngine engine;
    private LevelFactory lvlFactory;

    private Sound ping;
    private Sound boing;
    private TextureAtlas atlas;


    public MainScreen(Orchestrator Orchestrator) {
        parent = Orchestrator;
        parent.assMan.queueAddSounds();
        parent.assMan.manager.finishLoading();
        atlas = parent.assMan.manager.get("images/game.atlas", TextureAtlas.class);
        ping = parent.assMan.manager.get("sounds/ping.wav",Sound.class);
        boing = parent.assMan.manager.get("sounds/boing.wav",Sound.class);
        controller = new KeyboardController();
        engine = new PooledEngine();
        lvlFactory = new LevelFactory(engine,atlas.findRegion("player"));


        sb = new SpriteBatch();
        RenderingSystem renderingSystem = new RenderingSystem(sb);
        cam = renderingSystem.getCamera();
        sb.setProjectionMatrix(cam.combined);

        engine.addSystem(new AnimationSystem());
        engine.addSystem(new PhysicsSystem(lvlFactory.world));
        engine.addSystem(renderingSystem);
        engine.addSystem(new PhysicsDebugSystem(lvlFactory.world, renderingSystem.getCamera()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));


        engine.addSystem(new LevelGenerationSystem(lvlFactory));

        lvlFactory.createPlayer(atlas.findRegion("player"),cam);
        lvlFactory.createFloor(atlas.findRegion("player"));
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);
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
