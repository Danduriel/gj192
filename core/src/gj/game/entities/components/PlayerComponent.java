package gj.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class PlayerComponent implements Component{
    public OrthographicCamera cam;   /// ToDO Remove

    int edibility;
    int shinyness;
    int toxicity;
}