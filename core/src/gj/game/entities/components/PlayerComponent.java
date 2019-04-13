package gj.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class PlayerComponent implements Component{
    public OrthographicCamera cam = null;
    public boolean onPlatform = false;
    public boolean onSpring = false;
    public boolean isDead = false;


    int edibility;
    int shinyness;
    int toxicity;
}