package gj.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class EnemyComponent implements Component, Poolable{
    public boolean isDead = false;
    public float xPosCenter = -1;
    public boolean isGoingLeft = false;
    @Override
    public void reset() {
        isDead = false;
        xPosCenter = -1;
        isGoingLeft = false;
    }

}
