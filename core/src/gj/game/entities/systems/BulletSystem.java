package gj.game.entities.systems;

import gj.game.LevelFactory;
import gj.game.entities.components.B2dBodyComponent;
import gj.game.entities.components.BulletComponent;
import gj.game.entities.components.Mapper;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class BulletSystem extends IteratingSystem{
    private LevelFactory lvlFactory;

    @SuppressWarnings("unchecked")
    public BulletSystem(LevelFactory lvlFactory){
        super(Family.all(BulletComponent.class).get());
        this.lvlFactory = lvlFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //get box 2d body and bullet components
        B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
        BulletComponent bullet = Mapper.bulletCom.get(entity);

        // apply bullet velocity to bullet body
        b2body.body.setLinearVelocity(bullet.xVel, bullet.yVel);

        // get player pos
        B2dBodyComponent playerBodyComp = Mapper.b2dCom.get(lvlFactory.player);
        float px = playerBodyComp.body.getPosition().x;
        float py = playerBodyComp.body.getPosition().y;

        //get bullet pos
        float bx = b2body.body.getPosition().x;
        float by = b2body.body.getPosition().y;

        // if bullet is 30 units away from player on any axis then it is probably off screen
        if(bx - px > 30 || by - py > 30){
            bullet.isDead = true;
        }

        //check if bullet is dead
        if(bullet.isDead){
            System.out.println("Bullet died");
            Mapper.peCom.get(bullet.particleEffect).isDead = true;
            b2body.isDead = true;
        }
    }
}
