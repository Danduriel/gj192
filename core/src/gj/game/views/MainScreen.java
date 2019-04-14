package gj.game.views;

import gj.game.Orchestrator;
import gj.game.Utils;
import gj.game.LevelFactory;
import gj.game.controller.KeyboardController;
import gj.game.entities.components.Mapper;
import gj.game.entities.components.PlayerComponent;
import gj.game.entities.components.TextureComponent;
import gj.game.entities.systems.AnimationSystem;
import gj.game.entities.systems.BulletSystem;
import gj.game.entities.systems.CollisionSystem;
import gj.game.entities.systems.EnemySystem;
import gj.game.entities.systems.LevelGenerationSystem;
import gj.game.entities.systems.ParticleEffectSystem;
import gj.game.entities.systems.PhysicsDebugSystem;
import gj.game.entities.systems.PhysicsSystem;
import gj.game.entities.systems.PlayerControlSystem;
import gj.game.entities.systems.RenderingSystem;
import gj.game.entities.systems.SteeringSystem;
import gj.game.entities.systems.WallSystem;
import gj.game.entities.systems.FloorSystem;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class MainScreen implements Screen {
    private Orchestrator parent;
    private OrthographicCamera cam;
    private KeyboardController controller;
    private SpriteBatch sb;
    private PooledEngine engine;
    private LevelFactory lvlFactory;

    //private Sound ping;
    //private Sound boing;
    private Entity player;


    /**
     * @param Orchestrator
     */
    public MainScreen(Orchestrator Orchestrator) {
        parent = Orchestrator;
        //parent.assMan.queueAddSounds();
        //parent.assMan.manager.finishLoading();
        //ping = parent.assMan.manager.get("sounds/ping.wav",Sound.class);
        //boing = parent.assMan.manager.get("sounds/boing.wav",Sound.class);
        controller = new KeyboardController();
        engine = new PooledEngine();
        lvlFactory = new LevelFactory(engine,parent.assMan);


        sb = new SpriteBatch();
        RenderingSystem renderingSystem = new RenderingSystem(sb);
        cam = renderingSystem.getCamera();
        ParticleEffectSystem particleSystem = new ParticleEffectSystem(sb,cam);
        sb.setProjectionMatrix(cam.combined);

        engine.addSystem(new AnimationSystem());
        engine.addSystem(new PhysicsSystem(lvlFactory.world));
        engine.addSystem(renderingSystem);
        // not a fan of splitting batch into rendering and particles but I like the separation of the systems
        engine.addSystem(particleSystem); // particle get drawns on top so should be placed after normal rendering
        engine.addSystem(new PhysicsDebugSystem(lvlFactory.world, renderingSystem.getCamera()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new SteeringSystem());
        engine.addSystem(new PlayerControlSystem(controller,lvlFactory));
        player = lvlFactory.createPlayer(cam);
        engine.addSystem(new EnemySystem(lvlFactory));
        engine.addSystem(new WallSystem(lvlFactory));
        engine.addSystem(new FloorSystem(lvlFactory));
        engine.addSystem(new BulletSystem(lvlFactory));
        engine.addSystem(new LevelGenerationSystem(lvlFactory));


        lvlFactory.createFloor();
        lvlFactory.createWaterFloor();
        lvlFactory.createBackground();
        //lvlFactory.createSeeker(Mapper.sCom.get(player),20,15);


        int wallWidth = (int) (1*RenderingSystem.PPM);
        int wallHeight = (int) (60*RenderingSystem.PPM);
        TextureRegion wallRegion = Utils.makeTextureRegion(wallWidth, wallHeight, "222222FF");
        lvlFactory.createWalls(wallRegion); //TODO make image
    }

    // reset world or start world again
    public void resetWorld(){
        System.out.println("Resetting world");
        engine.removeAllEntities();
        lvlFactory.resetWorld();

        player = lvlFactory.createPlayer(cam);
        lvlFactory.createFloor();
        lvlFactory.createWaterFloor();

        int wallWidth = (int) (1*RenderingSystem.PPM);
        int wallHeight = (int) (60*RenderingSystem.PPM);
        TextureRegion wallRegion = Utils.makeTextureRegion(wallWidth, wallHeight, "222222FF");
        lvlFactory.createWalls(wallRegion); //TODO make images

        // reset controller controls (fixes bug where controller stuck on directrion if died in that position)
        controller.left = false;
        controller.right = false;
        controller.up = false;
        controller.down = false;
        controller.isMouse1Down = false;
        controller.isMouse2Down = false;
        controller.isMouse3Down = false;

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);

        //check if player is dead. if so show end screen
        PlayerComponent pc = (player.getComponent(PlayerComponent.class));
        if(pc.isDead){
            Utils.log("YOU DIED : back to menu you go!");
            parent.lastScore = (int) pc.cam.position.y;
            parent.changeScreen(Orchestrator.ENDGAME);
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        sb.dispose();
        engine.clearPools();
    }

}
