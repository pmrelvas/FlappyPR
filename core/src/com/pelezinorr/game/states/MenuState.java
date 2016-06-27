package com.pelezinorr.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pelezinorr.game.FlappyPR;

/**
 * Created by pedro on 27-06-2016.
 */
public class MenuState extends State {
    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, FlappyPR.WIDTH, FlappyPR.HEIGHT);
        sb.draw(playBtn, (FlappyPR.WIDTH/2)-(playBtn.getWidth()/2), FlappyPR.HEIGHT/2);
        sb.end();
    }
}