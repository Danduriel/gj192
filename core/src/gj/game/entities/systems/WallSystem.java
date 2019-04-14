package gj.game.entities.systems;

import gj.game.LevelFactory;
import gj.game.entities.components.B2dBodyComponent;
import gj.game.entities.components.WallComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;

public class WallSystem extends IteratingSystem{
    private LevelFactory lvlFactory;
    private ComponentMapper<B2dBodyComponent> bm = ComponentMapper.getFor(B2dBodyComponent.class);

    @SuppressWarnings("unchecked")
    public WallSystem(LevelFactory lvlf) {
        super(Family.all(WallComponent.class).get());
        this.lvlFactory = lvlf;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get current y level of player entity
        B2dBodyComponent b2com = lvlFactory.player.getComponent(B2dBodyComponent.class);
        float currentyLevel = b2com.body.getPosition().y;
        // get the body component of the wall we're updating
        Body bod = bm.get(entity).body;
        //set the walls y position to match the player
        bod.setTransform(bod.getPosition().x, currentyLevel, bod.getAngle());
    }
}
