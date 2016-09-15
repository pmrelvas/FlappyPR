package com.pelezinorr.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.pelezinorr.game.FlappyPR;

/**
 * Created by pedro on 27-06-2016.
 */
public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private Stage stage;
    private Label lblTitle, lblDeveloper, lblVersion;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        stage = new Stage();
        cam.setToOrtho(false, FlappyPR.WIDTH/2, FlappyPR.HEIGHT/2);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        initLabels();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth()/2, cam.position.y);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }

    private void initLabels() {
        lblTitle = new Label("Flappy Birds \n \"No Buraco\"", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblTitle.setSize(50,20);
        lblTitle.setPosition(FlappyPR.WIDTH-FlappyPR.WIDTH/2-25, FlappyPR.HEIGHT-80);
        lblTitle.setAlignment(Align.center);
        lblTitle.setFontScale(3);

        lblDeveloper = new Label("Brent Aureli\nMod: Pedro Relvas, 2016", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblDeveloper.setSize(50,20);
        lblDeveloper.setPosition(20, 35);
        lblDeveloper.setAlignment(Align.left);
        lblDeveloper.setFontScale(1.5f);

        lblVersion = new Label("V 0.1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblVersion.setSize(50,20);
        lblVersion.setPosition(FlappyPR.WIDTH-70, 20);
        lblVersion.setAlignment(Align.right);
        lblVersion.setFontScale(1.5f);

        stage.addActor(lblTitle);
        stage.addActor(lblDeveloper);
        stage.addActor(lblVersion);
    }
}
