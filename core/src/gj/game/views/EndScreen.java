package gj.game.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import gj.game.Orchestrator;
import gj.game.Utils;


public class EndScreen implements Screen {

    private Orchestrator parent;
    private Skin skin;
    private Stage stage;
    private TextureAtlas atlas;
    private TextureAtlas newAtlas;
    private AtlasRegion background;

    public EndScreen(Orchestrator Orchestrator){
        parent = Orchestrator;
    }

    @Override
    public void show() {
        // get skin
        skin = parent.assMan.manager.get("skin/glassy-ui.json");
        atlas = parent.assMan.manager.get("images/loading.atlas");
        newAtlas = parent.assMan.manager.get("images/ends.atlas");

        if(parent.lastScore > 50){
            background = newAtlas.findRegion("youwin_final");
        }
        else{
            background = newAtlas.findRegion("gameover_final");
        }


        // create button to go back to manu
        TextButton menuButton = new TextButton("Back", skin, "small");

        // create button listener
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Utils.log("To the MENU");
                parent.changeScreen(Orchestrator.MENU);
            }
        });

        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // create table to layout iutems we will add
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        table.setBackground(new TiledDrawable(background));


        /**
        //create a Labels showing the score and some credits
        Label labelScore = new Label("Your score was "+parent.lastScore+" Meters", skin);
        Label labelCredits = new Label("Credits:", skin);
        Label labelCredits1 = new Label("Game Design by", skin);
        Label labelCredits2 = new Label("Alex", skin);
        Label labelCredits3 = new Label("Art Design by", skin);
        Label labelCredits4 = new Label("Christina", skin);

        // add items to table
        table.add(labelScore).colspan(2);
        table.row().padTop(10);
        table.add(labelCredits).colspan(2);
        table.row().padTop(10);
        table.add(labelCredits1).uniformX().align(Align.left);
        table.add(labelCredits2).uniformX().align(Align.left);
        table.row().padTop(10);
        table.add(labelCredits3).uniformX().align(Align.left);
        table.add(labelCredits4).uniformX().align(Align.left);

         **/
        table.row().padTop(450).padLeft(450);
        table.add(menuButton).colspan(2);

        //add table to stage
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(parent.lastScore > 50){
            background = newAtlas.findRegion("youwin_final");
        }

        stage.act();
        stage.draw();
        //parent.changeScreen(Box2DTutorial.MENU);
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

}
