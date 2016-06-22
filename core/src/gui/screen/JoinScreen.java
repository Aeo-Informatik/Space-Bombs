/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.gdx.bomberman.Constants;


/**
 *
 * @author pl0614
 */
public class JoinScreen implements Screen{


    //Objects
    private Stage stage;
    private TextButton joinbutton;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private Game game;
    private TextField hostip;
    private  Music titleMusic;
    
    /**------------------------CONSTRUCTOR-----------------------
     * @param game-**/
    public JoinScreen(Game game)
    {
        //Initialise Objects
        this.game = game;
        stage = new Stage();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("menu/uiskin.json"));
        titleMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/click.wav"));
        Gdx.input.setInputProcessor(stage);
        
        //Load button description into memory
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
        skin.addRegions(buttonAtlas);
        
        //Add button style
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_up");
        textButtonStyle.down = skin.getDrawable("button_down");
        textButtonStyle.over = skin.getDrawable("button_checked");
        textButtonStyle.font = font;
        textButtonStyle.pressedOffsetY = -3;
        
        //Set the Textfield
        hostip = new TextField("", skin);
        hostip.setPosition(Gdx.graphics.getWidth() / 2 - (hostip.getWidth() / 2) + hostip.getWidth()/15, Gdx.graphics.getHeight() / 2- Gdx.graphics.getHeight()/10 );
        hostip.setSize(150,25);
        stage.addActor(hostip);
      
        //Add button to screen
        joinbutton = new TextButton("Join Game", skin);
        joinbutton.setPosition(Gdx.graphics.getWidth() / 2 - (joinbutton.getWidth() / 2), Gdx.graphics.getHeight() / 2); // Add to the center even after resize
        stage.addActor(joinbutton);
        
        
        //Add click listener --> Start Game
        joinbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {
                titleMusic.play();
                titleMusic.dispose();
                
                try
                {
                    if(!hostip.getText().equals(""))
                    {
                        int ip = Integer.parseInt(hostip.getText());
                        Constants.SERVERIP = Integer.toString(ip);
                    }else
                    {
                        Constants.SERVERIP = "localhost";
                    }
    
                    game.setScreen(new GameScreen(game));
                
                }catch(NumberFormatException e)
                {
                    System.err.println("ERROR: Please use only numbers int the ip address field. " + e.toString());
                }
            }
        });
    }
    
    
    /**------------------------RENDER------------------------**/
    @Override
    public void render(float f) 
    {
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        //Render stage
        stage.act(Gdx.graphics.getDeltaTime());
        
        stage.draw();
    }
    
    
    /**------------------------RESIZE------------------------**/
    @Override
    public void resize(int width, int height) 
    {
        
    }

    
    /**------------------------DISPOSE------------------------**/
    @Override
    public void dispose() 
    {
        buttonAtlas.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
    }

    
    
    /**------------------------OTHER------------------------**/
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }
}
    
