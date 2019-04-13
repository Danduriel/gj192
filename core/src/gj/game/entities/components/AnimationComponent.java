package gj.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AnimationComponent implements Component, Poolable {
    //public IntMap<Animation> animations = new IntMap<Animation>();     // old

     public IntMap<Animation<TextureRegion>> animations = new IntMap<Animation<TextureRegion>>();


    @Override
    public void reset() {
        //animations = new IntMap<Animation>(); // old
        animations = new IntMap<Animation<TextureRegion>>();
    }
}