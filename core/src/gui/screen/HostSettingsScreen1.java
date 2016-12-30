package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;

import client.Client;
import gui.AudioManager;
import gui.TextureManager;
import inputHandling.InputHandler;
import server.Server;

import static gui.TextureManager.backSkin;
import static gui.TextureManager.dynamiteSkin;
import static gui.TextureManager.roundSkin;


public class HostSettingsScreen1 extends Screens implements Screen
{
    // Objects
    private Stage stage;
    private Table rootTable = new Table();
    private InputHandler inputHandler = new InputHandler();
    
    //Other buttons
    private TextButton backbutton;
   
    // Start coins settings
    private Label startBombPlaceLabel;
    private WidgetGroup startBombPlaceWidget = new WidgetGroup();
    private TextButton startBombPlaceUp;
    private TextButton startBombPlaceDown;

    // Lives settings
    private Label startSpeedLabel;
    private WidgetGroup startSpeedWidget = new WidgetGroup();
    private TextButton startSpeedUp;
    private TextButton startSpeedDown;
    
    // Coin bonus settings
    private Label startRangeLabel;
    private WidgetGroup startRangeWidget = new WidgetGroup();
    private TextButton startRangeUp;
    private TextButton startRangeDown;
    
    // Slide screen button
    private TextButton slideRight;
    private TextButton slideLeft;
    
    // Variables
    private int startBombPlace = server.getStartBombPlace();
    private int startSpeed = server.getStartSpeed();
    private int startRange = server.getStartRange();
    
    /**------------------------CONSTRUCTOR------------------------**/
    public HostSettingsScreen1(final Game game, final Client client, final Server server)
    {
        super(game, client, server);
        
        
        //General Object initalisation
        stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        inputHandler.setInputSource(stage);
        
        // Set map preview and background
        rootTable.background(new TextureRegionDrawable(new TextureRegion(TextureManager.hostBackground)));

                
        //Set background
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        
        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontOptions.size = 11;
        BitmapFont font = TextureManager.menuFont.generateFont(fontOptions);
        
        
        
        /**------------------------LABEL STYLE------------------------**/
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        fontOptions.size = 14;
        labelStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        labelStyle.fontColor = Color.BLACK;
       
         
        /**------------------------DYNAMITE BUTTON STYLE ------------------------**/
        TextButton.TextButtonStyle dynamiteTextButtonStyle = new TextButton.TextButtonStyle();
        dynamiteTextButtonStyle.font = font;
        dynamiteTextButtonStyle.up   = dynamiteSkin.getDrawable("button_up");
        dynamiteTextButtonStyle.down = dynamiteSkin.getDrawable("button_down");
        dynamiteTextButtonStyle.over = dynamiteSkin.getDrawable("button_checked");
        
        
        /**------------------------ROUND BUTTON------------------------**/
        TextButton.TextButtonStyle textButtonStyleRound = new TextButton.TextButtonStyle();
        textButtonStyleRound.font = font;
        textButtonStyleRound.up   = roundSkin.getDrawable("button_up");
        textButtonStyleRound.down = roundSkin.getDrawable("button_down");
        textButtonStyleRound.over = roundSkin.getDrawable("button_checked");
        
        /**------------------------BACK BUTTON------------------------**/
        TextButton.TextButtonStyle textButtonStyleBack = new TextButton.TextButtonStyle();
        textButtonStyleBack.font = font;
        textButtonStyleBack.up   = backSkin.getDrawable("button_up");
        textButtonStyleBack.down = backSkin.getDrawable("button_down");
        textButtonStyleBack.over = backSkin.getDrawable("button_checked");

        /**------------------------START BOMB PLACE SETTINGS------------------------**/
        Table table1 = new Table();
        
        startBombPlaceLabel = new Label("Start Bombs: " + String.format("%03d",startBombPlace), labelStyle);
        table1.add(startBombPlaceLabel).width(224);
        
        
        // Coin down button
        startBombPlaceDown = new TextButton("-",textButtonStyleRound);
        table1.add(startBombPlaceDown).padLeft(100);
        
        // Coin up button
        startBombPlaceUp = new TextButton("+",textButtonStyleRound);
        table1.add(startBombPlaceUp).padLeft(35);
        
        //Add table to widget
        startBombPlaceWidget.addActor(table1);
        startBombPlaceWidget.setPosition(320, 390);
        
        // Add widget to screen
        stage.addActor(startBombPlaceWidget);
        
        
        
        /**------------------------START SPEED SETTINGS------------------------**/
        Table table3 = new Table();
        
        startSpeedLabel = new Label( "Start Speed: " + String.format("%03d", server.getStartSpeed()) + "%", labelStyle);
        table3.add(startSpeedLabel).width(224);
        
        // Coin down button
        startSpeedDown = new TextButton("-",textButtonStyleRound);
        table3.add(startSpeedDown).padLeft(100);
        
        // Coin up button
        startSpeedUp = new TextButton("+",textButtonStyleRound);
        table3.add(startSpeedUp).padLeft(35);
        
        //Add table to widget
        startSpeedWidget.addActor(table3);
        startSpeedWidget.setPosition(320, 310);
        
        // Add widget to screen
        stage.addActor(startSpeedWidget);
        
        /**------------------------START RANGE SETTINGS------------------------**/
        Table table4 = new Table();
        
        startRangeLabel = new Label("Start Range: " + String.format("%03d", startRange), labelStyle);
        table4.add(startRangeLabel).width(224);
        
        // Coin down button
        startRangeDown = new TextButton("-",textButtonStyleRound);
        table4.add(startRangeDown).padLeft(100);
        
        // Coin up button
        startRangeUp = new TextButton("+",textButtonStyleRound);
        table4.add(startRangeUp).padLeft(35);
        
        //Add table to widget
        startRangeWidget.addActor(table4);
        startRangeWidget.setPosition(320, 230);
        
        // Add widget to screen
        stage.addActor(startRangeWidget);
        
        /**------------------------OTHER BUTTONS------------------------**/
        // Back button
        backbutton = new TextButton("", textButtonStyleBack);
        backbutton.setPosition(0, Constants.SCREENHEIGHT - backbutton.getHeight() + 7);
        stage.addActor(backbutton);
        
        //Slide right button
        slideRight = new TextButton(">>>", textButtonStyleRound);
        slideRight.setPosition(420, 45);
        //stage.addActor(slideRight);
        
         //Slide right button
        slideLeft = new TextButton("<<<", textButtonStyleRound);
        slideLeft.setPosition(340, 45);
        stage.addActor(slideLeft);
        
        /**------------------------BUTTON FUNCTIONS------------------------**/
        //Add click listener --> Slide right
        slideRight.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                AudioManager.playClickSound();
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }

                
            }
        });
        
        //Add click listener --> Slide left
        slideLeft.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                AudioManager.playClickSound();
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                game.setScreen(new HostSettingsScreen(game, client, server));
            }
        });
        
        //Add click listener --> Coin bonus up  
        startRangeUp.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startRange < Constants.MAXBOMBRANGE)
               { 
                    startRange += 1;
                    startRangeLabel.setText("Start Range: " + String.format("%03d",startRange));
                    server.setStartRange(startRange);
               }
            }
        });
        
        // Coin bonus down
        startRangeDown.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startRange > 1)
               { 
                    startRange -= 1;
                    startRangeLabel.setText("Start Range: " + String.format("%03d",startRange));
                    server.setStartRange(startRange);
               }
            }
        });

        //Add click listener --> Live up  
        startSpeedUp.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startSpeed < Constants.MAXSPEED)
               { 
                    startSpeed += 10;
                   
                    startSpeedLabel.setText("Start Speed: " + String.format("%03d", startSpeed) + "%");
                    server.setStartSpeed(startSpeed);
               }
            }
        });
        
        //Add click listener --> Live down 
        startSpeedDown.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startSpeed > Constants.MINSPEED)
               { 
                    startSpeed -= 10;

                    startSpeedLabel.setText("Start Speed: " + String.format("%03d", startSpeed) + "%");
                    server.setStartSpeed(startSpeed);
               }
            }
        });
        
        //Add click listener --> Coins up  
        startBombPlaceUp.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startBombPlace < Constants.MAXBOMBPLACE)
               { 
                    startBombPlace += 1;
                    startBombPlaceLabel.setText("Start Bombs: " + String.format("%03d",startBombPlace));
                    server.setStartBombPlace(startBombPlace);
               }
            }
        });
        
        //Add click listener --> Coins down
        startBombPlaceDown.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startBombPlace > 1)
               {
                    startBombPlace -= 1;
                    startBombPlaceLabel.setText("Start Bombs: " + String.format("%03d",startBombPlace));
                    server.setStartBombPlace(startBombPlace);
               }
            }
        });
          
       
        
        //Add click listener --> Back button
        backbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                AudioManager.playClickSound();
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }

                game.setScreen(new HostScreen(game, client, server));
            }
        });
    }

   
    

    /**------------------------RENDER------------------------**/
    @Override
    public void render(float f) 
    {        
        //Debug
//        stage.setDebugAll(true);
        
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        //Draw stage
        stage.act(Constants.DELTATIME);
        stage.draw();
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        super.changeToFullScreenOnF12();
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {

            game.setScreen(new HostScreen(game, client, server));
        }
    }
    
    
    /**------------------------RESIZE------------------------**/
    @Override
    public void resize(int width, int height) 
    {
        stage.getViewport().update(width, height, false);
    }

    
    /**------------------------DISPOSE------------------------**/
    @Override
    public void dispose() 
    {
        stage.dispose();
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