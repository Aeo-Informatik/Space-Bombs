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


public class HostSettingsScreen extends Screens implements Screen
{
    // Objects
    private Stage stage;
    private Table rootTable = new Table();
     private InputHandler inputHandler = new InputHandler();
    
    //Other buttons
    private TextButton backbutton;
   
    // Start coins settings
    private Label startCoinsLabel;
    private WidgetGroup startCoinsWidget = new WidgetGroup();
    private TextButton startCoinsUp;
    private TextButton startCoinsDown;
    
    // Max player settings
    private Label maxPlayerLabel;
    private WidgetGroup maxPlayerWidget = new WidgetGroup();
    private TextButton maxPlayerUp;
    private TextButton maxPlayerDown;

    // Lives settings
    private Label startLivesLabel;
    private WidgetGroup startLivesWidget = new WidgetGroup();
    private TextButton startLivesUp;
    private TextButton startLivesDown;
    
    // Coin bonus settings
    private Label coinBonusLabel;
    private WidgetGroup coinBonusWidget = new WidgetGroup();
    private TextButton coinBonusUp;
    private TextButton coinBonusDown;
    
    // Slide screen button
    private TextButton slideRight;
    private TextButton slideLeft;
    
    // Variables
    private int maxPlayers = server.getMaxConnections();
    private int oldMaxPlayerValue = maxPlayers;
    private int startCoins = server.getStartCoins();
    private int startLives = server.getStartLives();
    private int coinBonus = server.getCoinBonus();
    
    /**------------------------CONSTRUCTOR------------------------**/
    public HostSettingsScreen(final Game game,final Client client,final Server server)
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

        /**------------------------START COINS SETTINGS------------------------**/
        Table table1 = new Table();
        
        startCoinsLabel = new Label("Start Coins: " + String.format("%03d",startCoins), labelStyle);
        table1.add(startCoinsLabel).width(224);
        
        
        // Coin down button
        startCoinsDown = new TextButton("-",textButtonStyleRound);
        table1.add(startCoinsDown).padLeft(100);
        
        // Coin up button
        startCoinsUp = new TextButton("+",textButtonStyleRound);
        table1.add(startCoinsUp).padLeft(35);
        
        //Add table to widget
        startCoinsWidget.addActor(table1);
        startCoinsWidget.setPosition(320, 390);
        
        // Add widget to screen
        stage.addActor(startCoinsWidget);
        

        /**------------------------MAX PLAYER SETTINGS------------------------**/
        Table table2 = new Table();
        
        maxPlayerLabel = new Label("Max Players: " + String.format("%03d", maxPlayers), labelStyle);
        table2.add(maxPlayerLabel).width(224);
        
        // Coin down button
        maxPlayerDown = new TextButton("-",textButtonStyleRound);
        table2.add(maxPlayerDown).padLeft(100);
        
        // Coin up button
        maxPlayerUp = new TextButton("+",textButtonStyleRound);
        table2.add(maxPlayerUp).padLeft(35);
        
        //Add table to widget
        maxPlayerWidget.addActor(table2);
        maxPlayerWidget.setPosition(320, 310);
        
        // Add widget to screen
        stage.addActor(maxPlayerWidget);
        
        
        /**------------------------START LIVES SETTINGS------------------------**/
        Table table3 = new Table();
        
        startLivesLabel = new Label("Start Live: " + String.format("%03d", this.startLives), labelStyle);
        table3.add(startLivesLabel).width(224);
        
        // Coin down button
        startLivesDown = new TextButton("-",textButtonStyleRound);
        table3.add(startLivesDown).padLeft(100);
        
        // Coin up button
        startLivesUp = new TextButton("+",textButtonStyleRound);
        table3.add(startLivesUp).padLeft(35);
        
        //Add table to widget
        startLivesWidget.addActor(table3);
        startLivesWidget.setPosition(320, 230);
        
        // Add widget to screen
        stage.addActor(startLivesWidget);
        
        /**------------------------COIN BONUS SETTINGS------------------------**/
        Table table4 = new Table();
        
        coinBonusLabel = new Label("Coin Bonus: " + String.format("%03d", coinBonus), labelStyle);
        table4.add(coinBonusLabel).width(224);
        
        // Coin down button
        coinBonusDown = new TextButton("-",textButtonStyleRound);
        table4.add(coinBonusDown).padLeft(100);
        
        // Coin up button
        coinBonusUp = new TextButton("+",textButtonStyleRound);
        table4.add(coinBonusUp).padLeft(35);
        
        //Add table to widget
        coinBonusWidget.addActor(table4);
        coinBonusWidget.setPosition(320, 150);
        
        // Add widget to screen
        stage.addActor(coinBonusWidget);
        
        /**------------------------OTHER BUTTONS------------------------**/
        // Back button
        backbutton = new TextButton("", textButtonStyleBack);
        backbutton.setPosition(0, Constants.SCREENHEIGHT - backbutton.getHeight() + 7);
        stage.addActor(backbutton);
        
        //Slide right button
        slideRight = new TextButton(">>>", textButtonStyleRound);
        slideRight.setPosition(420, 45);
        stage.addActor(slideRight);
        
         //Slide right button
        slideLeft = new TextButton("<<<", textButtonStyleRound);
        slideLeft.setPosition(340, 45);
        //stage.addActor(slideLeft);
        
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

                game.setScreen(new HostSettingsScreen1(game, client, server));
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
                
                
            }
        });
        
        //Add click listener --> Coin bonus up  
        coinBonusUp.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(coinBonus < 999)
               { 
                    coinBonus += 5;
                    coinBonusLabel.setText("Coin Bonus: " + String.format("%03d",coinBonus));
                    server.setCoinBonus(coinBonus);
               }
            }
        });
        
        // Coin bonus down
        coinBonusDown.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(coinBonus > 0)
               { 
                    coinBonus -= 5;
                    coinBonusLabel.setText("Coin Bonus: " + String.format("%03d",coinBonus));
                    server.setCoinBonus(coinBonus);
               }
            }
        });

        //Add click listener --> Live up  
        startLivesUp.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startLives < Constants.MAXLIFE)
               { 
                    startLives += 1;
                    startLivesLabel.setText("Start Live: " + String.format("%03d",startLives));
                    server.setStartLives(startLives);
               }
            }
        });
        
        //Add click listener --> Live down 
        startLivesDown.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startLives > 1)
               { 
                    startLives -= 1;
                    startLivesLabel.setText("Start Live: " + String.format("%03d",startLives));
                    server.setStartLives(startLives);
               }
            }
        });
        
        //Add click listener --> Coins up  
        startCoinsUp.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startCoins < 999)
               { 
                    startCoins += 50;
                    startCoinsLabel.setText("Start Coins: " + String.format("%03d",startCoins));
                    server.setStartCoins(startCoins);
               }
            }
        });
        
        //Add click listener --> Coins down
        startCoinsDown.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
               if(startCoins > 0)
               {
                    startCoins -= 50;
                    startCoinsLabel.setText("Start Coins: " + String.format("%03d",startCoins));
                    server.setStartCoins(startCoins);
               }
            }
        });
          
        //Add click listener --> Player up
        maxPlayerUp.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                if(maxPlayers < Constants.MAXPLAYERS)
                {
                    maxPlayers += 1;
                    maxPlayerLabel.setText("Max Players: " + String.format("%03d", maxPlayers));
                    server.setMaxConnections(maxPlayers);
                }
            }
        });
            
        //Add click listener --> Back button   
        maxPlayerDown.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                if(maxPlayers > 2)
                {
                    maxPlayers -= 1;
                    maxPlayerLabel.setText("Max Players: " + String.format("%03d", maxPlayers));
                    server.setMaxConnections(maxPlayers);
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

                if(oldMaxPlayerValue != maxPlayers)
                {
                    server.resetServer();
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
            if(oldMaxPlayerValue != maxPlayers)
            {
                server.resetServer();
            }
            
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