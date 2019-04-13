package gj.game.entities.systems;

import gj.game.entities.components.CollisionComponent;
import gj.game.entities.components.PlayerComponent;
import gj.game.entities.components.TypeComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class CollisionSystem  extends IteratingSystem {
    ComponentMapper<CollisionComponent> cm;
    ComponentMapper<PlayerComponent> pm;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        // only need to worry about player collisions
        super(Family.all(CollisionComponent.class,PlayerComponent.class).get());

        cm = ComponentMapper.getFor(CollisionComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = cm.get(entity);

        Entity collidedEntity = cc.collisionEntity;
        if(collidedEntity != null){
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            if(type != null){
                switch(type.type){
                    case TypeComponent.ENEMY:
                        //do player hit enemy thing
                        System.out.println("player hit enemy");
                        PlayerComponent pl = pm.get(entity);
                        pl.isDead = true;
                        int score = (int) pl.cam.position.y;
                        System.out.println("Score = "+ score);
                        break;
                    case TypeComponent.SCENERY:
                        //do player hit scenery thing
                        pm.get(entity).onPlatform = true;
                        System.out.println("player hit scenery");
                        break;
                    case TypeComponent.SPRING:
                        //do player hit other thing
                        pm.get(entity).onSpring = true;
                        System.out.println("player hit spring: bounce up");
                        break;
                    case TypeComponent.OTHER:
                        //do player hit other thing
                        System.out.println("player hit other");
                        break;
                    default:
                        System.out.println("No matching type found");
                }
                cc.collisionEntity = null; // collision handled reset component
            }else{
                System.out.println("type == null");
            }
        }
    }
}