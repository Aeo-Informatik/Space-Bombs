package gui.screen;

import client.Client;
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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import static com.gdx.bomberman.Main.game;
import gui.AudioManager;
import gui.TextureManager;
import static gui.TextureManager.backSkin;
import static gui.TextureManager.dynamiteSkin;
import static gui.TextureManager.roundSkin;
import gui.map.AvailableMaps;
import server.Server;


public class HostScreen implements Screen
{
    // Map List
    AvailableMaps maps = new AvailableMaps();

    // Objects
    private Stage stage;
    private Table rootTable = new Table();
    
    //Buttons
    private TextButton startbutton;
    private TextButton backbutton;
    
    // Ip label
    private Label ipLabel;
    
    //Slider
    private WidgetGroup sliderGroup = new WidgetGroup();
    private Container mapImage = new Container();
    private Label mapName;
    private TextButton slideLeft;
    private TextButton slideRight;
            
    //Players
    private WidgetGroup joinedPlayerGroup = new WidgetGroup();
    private int numPlayers = 0;
    private Container p1Field;
    private Container p2Field;
    private Container p3Field;
    private Container p4Field;
    
    /**------------------------CONSTRUCTOR------------------------**/
    public HostScreen()
    {
        //General Object initalisation
        stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        Gdx.input.setInputProcessor(stage);
        
        // Set map preview and background
        rootTable.background(new TextureRegionDrawable(new TextureRegion(TextureManager.hostBackground)));
        mapImage.background(new TextureRegionDrawable(new TextureRegion(TextureManager.loadTexture(maps.getCurrentMapPreviewPath()))));

                
        //Set background
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        
        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontOptions.size = 11;
        BitmapFont font = TextureManager.menuFont.generateFont(fontOptions);
        
        
        /**------------------------OPEN SERVER------------------------**/
        // Create server object
        if(Constants.OWNSERVEROBJ == null)
        {
           Constants.OWNSERVEROBJ = new Server(Constants.LISTENINGPORT, 4, new AvailableMaps().getTestMap()); 
        }
            
        System.out.println("CLIENT: Launching server with 4 players...");
        Constants.OWNSERVEROBJ.OpenLobby();
        
        try 
        {
            //Connect to server
            client = new Client("127.0.0.1", Constants.CONNECTIONPORT);
            client.connectToServer();
            
        } catch (Exception e) 
        {
            System.err.println("ERROR: Client couldnt connect to own server: " + e);
            e.printStackTrace();
        }

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
        
        /**------------------------IP LABEL------------------------**/
        ipLabel = new Label("", labelStyle);
        ipLabel.setAlignment(Align.left);
        ipLabel.setPosition(10, 10);
        try 
        {
            ipLabel.setText("Your local IP: " + client.getCurrentIp().getHostAddress());
        } catch (Exception ex) 
        {
            ipLabel.setText("Error on reading local IP!");
            ex.printStackTrace();
        }
        
        stage.addActor(ipLabel);
        
        /**------------------------JOIN PLAYER GROUP------------------------**/
        //Table options
        Table table = new Table();
        table.setFillParent(true);
        
        //P1 spawn field
        p1Field = new Container();
        p1Field.background(new TextureRegionDrawable(TextureManager.p1WalkingDownAnim.getKeyFrame(0)));
        table.add(p1Field).width(64).height(64);
        
        //P2 spawn field
        p2Field = new Container();
        p2Field.setVisible(false);
        p2Field.background(new TextureRegionDrawable(TextureManager.p2WalkingDownAnim.getKeyFrame(0)));
        table.add(p2Field).width(64).height(64).padLeft(96);
        
        //P3 spawn field
        p3Field = new Container();
        p3Field.setVisible(false);
        p3Field.background(new TextureRegionDrawable(TextureManager.p3WalkingDownAnim.getKeyFrame(0)));
        table.add(p3Field).width(64).height(64).padLeft(96);
        
        //P4 spawn field
        p4Field = new Container();
        p4Field.setVisible(false);
        p4Field.background(new TextureRegionDrawable(TextureManager.p4WalkingDownAnim.getKeyFrame(0)));
        table.add(p4Field).width(64).height(64).padLeft(96);
        
        //Stage & group options
        joinedPlayerGroup.addActor(table);
        joinedPlayerGroup.setPosition(443, 74);
        stage.addActor(joinedPlayerGroup);
        
        
        /**------------------------MAP SLIDER GROUP------------------------**/
        //Table options
        Table table1 = new Table();
        table1.setFillParent(true);
        Table table2 = new Table();

        // Map Name Label
        mapName = new Label("I like trains", labelStyle);
        mapName.setText(maps.getCurrentMapName());
        mapName.setAlignment(Align.center);
        mapName.setPosition(310, 430);
        stage.addActor(mapName);
        
        // Map Screenshot
        table2.add(mapImage).width(400).height(210);
        
        //Stage & group options
        sliderGroup.addActor(table1);
        sliderGroup.addActor(table2);
        sliderGroup.setPosition(400, 310);
        stage.addActor(sliderGroup);
        
        /**------------------------ALL BUTTONS------------------------**/
        //Start game button
        startbutton = new TextButton("Start Game!", dynamiteTextButtonStyle);
        startbutton.setPosition(305, 125);
        stage.addActor(startbutton);

        //Slide right button
        slideRight = new TextButton(">>>", textButtonStyleRound);
        slideRight.setPosition(610, 282);
        stage.addActor(slideRight);
        
         //Slide right button
        slideLeft = new TextButton("<<<", textButtonStyleRound);
        slideLeft.setPosition(140, 282);
        stage.addActor(slideLeft);
        
        // Back button
        backbutton = new TextButton("", textButtonStyleBack);
        backbutton.setPosition(0, Constants.SCREENHEIGHT - backbutton.getHeight() + 7);
        stage.addActor(backbutton);
        
        /**------------------------BUTTON FUNCTIONS------------------------**/
        //Add click listener --> Back button
        backbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }

                Constants.OWNSERVEROBJ.stopServer();
                Constants.OWNSERVEROBJ = null;
                game.setScreen(new MenuScreen());
            }
        });
        
        //Add click listener --> Start Game
        startbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                AudioManager.menuMusic.stop();
                Constants.OWNSERVEROBJ.startGame();
                game.setScreen(new GameScreen());
            }
        });
        
        //Add click listener --> Slide right
        slideRight.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                maps.nextMap();
                mapImage.background(new TextureRegionDrawable(new TextureRegion(TextureManager.loadTexture(maps.getCurrentMapPreviewPath()))));
                mapName.setText(maps.getCurrentMapName());
                Constants.OWNSERVEROBJ.setMap(maps.getCurrentMap());
            }
        });
        
        //Add click listener --> Slide left
        slideLeft.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                
                maps.previousMap();
                mapImage.background(new TextureRegionDrawable(new TextureRegion(TextureManager.loadTexture(maps.getCurrentMapPreviewPath()))));
                mapName.setText(maps.getCurrentMapName());
                Constants.OWNSERVEROBJ.setMap(maps.getCurrentMap());
            }
        });
    }
    

    /**------------------------RENDER------------------------**/
    @Override
    public void render(float f) 
    {        
        //Debug
        //stage.setDebugAll(true);
        
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        //Check for connecting and disconnecting players
        int currentNumPlayers = Server.getClientList().size();
        
        if(currentNumPlayers != numPlayers)
        {
            int difference = currentNumPlayers - numPlayers;
            int playerId = numPlayers + difference;
            
            //If positive someone joined
            if(difference > 0)
            {
                //Who joined
                switch(playerId)
                {
                    case 1:
                        System.out.println("Player 1 joined");
                        p1Field.setVisible(true);
                        break;
                    
                    case 2:
                        System.out.println("Player 2 joined");
                        p2Field.setVisible(true);
                        break;
                    
                    case 3:
                        System.out.println("Player 3 joined");
                        p3Field.setVisible(true);
                        break;
                        
                    case 4:
                        System.out.println("Player 4 joined");
                        p4Field.setVisible(true);
                        break;
                }
            }
            
            //If negative someone disconnected
            if(difference < 0)
            {
                //Who joined
                switch(numPlayers)
                {
                    case 1:
                        System.out.println("Player 1 disconnected");
                        p1Field.setVisible(false);
                        break;
                    
                    case 2:
                        System.out.println("Player 2 disconnected");
                        p2Field.setVisible(false);
                        break;
                    
                    case 3:
                        System.out.println("Player 3 disconnected");
                        p3Field.setVisible(false);
                        break;
                        
                    case 4:
                        System.out.println("Player 4 disconnected");
                        p4Field.setVisible(false);
                        break;
                }
            }
            
            //Update saved num players
            numPlayers = currentNumPlayers;
        }
        
        //Draw stage
        stage.act(Constants.DELTATIME);
        stage.draw();
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        if(Gdx.input.isKeyJustPressed(Input.Keys.F12))
        {
            if(Gdx.graphics.isFullscreen())
            {
                Gdx.graphics.setWindowedMode(Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
            }else
            {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            Constants.OWNSERVEROBJ.stopServer();
            Constants.OWNSERVEROBJ = null;
            game.setScreen(new MenuScreen());
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
