package gj.game.entities.systems;



import gj.game.entities.components.B2dBodyComponent;
import gj.game.entities.components.BulletComponent;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class BulletSystem extends IteratingSystem{

    @SuppressWarnings("unchecked")
    public BulletSystem(){
        super(Family.all(BulletComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //get box 2d body and bullet components
        B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
        BulletComponent bullet = Mapper.bulletCom.get(entity);

        // apply bullet velocity to bullet body
        b2body.body.setLinearVelocity(bullet.xVel, bullet.yVel);

        //check if bullet is dead
        if(bullet.isDead){
            b2body.isDead = true;
        }
    }
}