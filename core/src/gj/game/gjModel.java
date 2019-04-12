package gj.game;


import gj.game.controller.KeyboardController;
import gj.game.loader.gjAssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class gjModel {
    public World world;
    private Body bodyd;
    private Body bodys;
    private Body bodyk;
    private KeyboardController controller;
    private gjAssetManager assMan;

    public boolean isSwimming = false;
    public Body player;
    private OrthographicCamera camera;
    private Sound ping;
    private Sound boing;

    public static final int BOING_SOUND = 0;
    public static final int PING_SOUND = 1;

    public gjModel(KeyboardController cont, OrthographicCamera cam, gjAssetManager assetManager){
        this.assMan = assetManager;
        camera = cam;
        controller = cont;
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new gjContactListener(this));
        createFloor();
        //createObject();
        //createMovingObject();

        // tells our asset manger that we want to load the images set in loadImages method
        assMan.queueAddSounds();
        // tells the asset manager to load the images and wait until finsihed loading.
        assMan.manager.finishLoading();
        // loads the 2 sounds we use
        ping = assMan.manager.get("sounds/ping.wav");
        boing = assMan.manager.get("sounds/boing.wav");

        // get our body factory singleton and store it in bodyFactory
        gjBodyFactory bodyFactory = gjBodyFactory.getInstance(world);

        // add a player
        player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, gjBodyFactory.RUBBER, BodyType.DynamicBody,false);

        // add some water
        Body water =  bodyFactory.makeBoxPolyBody(1, -8, 40, 16, gjBodyFactory.RUBBER, BodyType.StaticBody,false);
        water.setUserData("IAMTHESEA");

        // make the water a sensor so it doesn't obstruct our player
        bodyFactory.makeAllFixturesSensors(water);

    }

    private void createFloor() {

        // create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -10);

        // add it to the world
        bodyd = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50, 1);

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyd.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createObject(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);


        // add it to the world
        bodys = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodys.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createMovingObject(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0,-12);


        // add it to the world
        bodyk = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyk.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();

        bodyk.setLinearVelocity(0, 0.75f);
    }

    // our game logic here
    public void logicStep(float delta){

        // check if mouse1 is down (player click) then if true check if
        if(controller.isMouse1Down && pointIntersectsBody(player,controller.mouseLocation)){
            System.out.println("Player was clicked");
        }


        if(controller.left){
            player.applyForceToCenter(-10, 0,true);
        }else if(controller.right){
            player.applyForceToCenter(10, 0,true);
        }else if(controller.up){
            player.applyForceToCenter(0, 10,true);
        }else if(controller.down){
            player.applyForceToCenter(0, -10,true);
        }

        if(isSwimming){
            player.applyForceToCenter(0, 40, true);
        }

        world.step(delta , 3, 3);
    }


    /**
     * Checks if point is in first fixture
     * Does not check all fixtures.....yet
     *
     * @param body the Box2D body to check
     * @param mouseLocation the point on the screen
     * @return
     */
    public boolean pointIntersectsBody(Body body, Vector2 mouseLocation){
        Vector3 mousePos = new Vector3(mouseLocation,0); //convert mouseLocation to 3D position
        camera.unproject(mousePos); // convert from screen potition to world position
        if(body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)){
            return true;
        }
        return false;
    }

    public void playSound(int sound){
        switch(sound){
            case BOING_SOUND:
                boing.play();
                break;
            case PING_SOUND:
                ping.play();
                break;
        }
    }
}
