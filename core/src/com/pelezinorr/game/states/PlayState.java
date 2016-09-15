package com.pelezinorr.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.pelezinorr.game.FlappyPR;
import com.pelezinorr.game.sprites.Bird;
import com.pelezinorr.game.sprites.Tube;

/**
 * Created by pedro on 27-06-2016.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Label lblScore;
    private int score;
    private Stage stage;
    private Sound soundThrough, soundFail;
    private boolean gameOver;
    private Texture playBtn;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        stage = new Stage();
        bird = new Bird(50, 300);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        playBtn = new Texture("playbtn.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(cam.position.x - cam.viewportWidth/2 + ground.getWidth(), GROUND_Y_OFFSET);
        tubes = new Array<Tube>();
        cam.setToOrtho(false, FlappyPR.WIDTH/2, FlappyPR.HEIGHT/2);
        score = 0;
        initLabels();
        initSounds();
        gameOver = false;

        for(int i=1; i<=TUBE_COUNT; i++) {
            tubes.add(new Tube(i*(TUBE_SPACING+Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            bird.jump();
            if(gameOver) {
                gsm.set(new PlayState(gsm));
                gameOver = false;
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(!gameOver) {
            updateGround();
            bird.update(dt);
            cam.position.x = bird.getPosition().x + 80;

            for (int i = 0; i < tubes.size; i++) {
                Tube tube = tubes.get(i);

                if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) { // se o tubo estiver do lado esquerdo
                    tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                }
                //check collisions
                if (tube.collides(bird.getBounds())) {
                    soundFail.play();
                    gameOver = true;
                }

                // check if the bird went through the tubes
                if ((tube.getPosTopTube().x + tube.TUBE_WIDTH >= bird.getPosition().x - 0.8) && (tube.getPosTopTube().x + tube.TUBE_WIDTH <= bird.getPosition().x + 0.8)) {
                    soundThrough.play();
                    incrementScore();
                }
            }

            if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
                soundFail.play();
                gameOver = true;
            }
        }
        stage.act(dt);
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosBotTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        if(gameOver) {
            sb.draw(playBtn, cam.position.x - playBtn.getWidth()/2, cam.position.y);
        }
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for(Tube tube : tubes) {
            tube.dispose();
        }
        ground.dispose();
        soundFail.dispose();
        soundThrough.dispose();
    }

    private void updateGround() {
        if(cam.position.x - cam.viewportWidth/2 > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth()*2, 0);
        }
        if(cam.position.x - cam.viewportWidth/2 > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth()*2, 0);
        }
    }

    private void initLabels() {
        lblScore = new Label(score+"", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblScore.setSize(50,20);
        lblScore.setPosition(10, FlappyPR.HEIGHT-50);
        lblScore.setAlignment(Align.center);
        lblScore.setFontScale(3);

        stage.addActor(lblScore);
    }

    private void initSounds() {
        soundThrough = Gdx.audio.newSound(Gdx.files.internal("acertar.ogg"));
        soundFail    = Gdx.audio.newSound(Gdx.files.internal("falhar.ogg"));
    }

    private void incrementScore() {
        lblScore.setText(++score+"");
    }
}
