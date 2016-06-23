/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.game;
import gui.AudioManager;
import gui.TextureManager;


/**
 *
 * @author pl0614
 */
public class JoinScreen implements Screen
{
    //Objects
    private Stage stage;
    private Stack stack;
    private Table rootTable;
    
    //Music
    private Music clickSound;
    private Music menuMusic;
    
    //Local TextureManager
    private Skin skin;
    private TextureAtlas buttonAtlas;
    
    //Buttons
    private TextField ipTextField;
    private TextButton joinButton;
    
    
    /**------------------------CONSTRUCTOR-----------------------
     * @param game-**/
    public JoinScreen(Music menuMusic)
    {
        //General Object initalisation
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        this.stack = new Stack();
        this.menuMusic = menuMusic;
        Gdx.input.setInputProcessor(stage);
        
        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontOptions.size = 11;
        BitmapFont font = TextureManager.menuFont.generateFont(fontOptions);
        
        //Audio
        clickSound = AudioManager.clickSound;

        //Load button description into memory
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
        skin.addRegions(buttonAtlas);
        
        //Set the Textfield
        ipTextField = new TextField("", skin);
        ipTextField.setPosition(50, 50);
        ipTextField.setSize(150,25);
        stage.addActor(ipTextField);
      
        //Add button to screen
        joinButton = new TextButton("Join Game", skin);
        joinButton.setPosition(100, 100);
        stage.addActor(joinButton);
        
        
        //Add click listener --> Start Game
        joinButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {
                menuMusic.dispose();
                clickSound.play();
                
                if(!ipTextField.getText().equals(""))
                {
                    Constants.SERVERIP = ipTextField.getText();
                }else
                {
                    Constants.SERVERIP = "localhost";
                }

                game.setScreen(new GameScreen());
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
    
