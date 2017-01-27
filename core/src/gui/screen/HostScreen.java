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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;

import java.net.SocketException;

import client.Client;
import gui.AudioManager;
import gui.TextureManager;
import gui.map.AvailableMaps;
import inputHandling.InputHandler;
import server.ClientConnection;
import server.Server;

import static gui.TextureManager.backSkin;
import static gui.TextureManager.dynamiteSkin;
import static gui.TextureManager.roundSkin;
import static gui.TextureManager.settingsSkin;


public class HostScreen extends Screens implements Screen
{
    // Map List
    private AvailableMaps maps = new AvailableMaps(server.getMaxConnections());

    // Objects
    private Stage stage;
    private Table rootTable = new Table();
    private InputHandler inputHandler = new InputHandler();
    
    //Buttons
    private TextButton startbutton;
    private TextButton backbutton;
    private TextButton settingsButton;
    
    // Ip label
    private Label ipLabel;
    private TextField ipTextField;
    
    //Slider
    private WidgetGroup sliderGroup = new WidgetGroup();
    private Container mapImage = new Container();
    private Label mapName;
    private TextButton slideLeft;
    private TextButton slideRight;
    
    // Filter box
    private SelectBox<String> selectBox;
    private Label filterDescription;
    
    //Players
    private WidgetGroup joinedPlayerGroup = new WidgetGroup();
    private Container p1Field;
    private Container p2Field;
    private Container p3Field;
    private Container p4Field;
    
    /**------------------------CONSTRUCTOR------------------------**/
    public HostScreen(final Game game,final Client client,final Server server)
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
        
        
        /**------------------------OPEN SERVER------------------------**/
        // Start slider picture
        if(server.getMap().isEmpty())
        {    
            maps.goToNextCompatibleMap();
            mapImage.background(new TextureRegionDrawable(new TextureRegion(TextureManager.loadTexture(maps.getCurrentMapPreviewPath()))));
            server.setMap(maps.getCurrentMap());
        }else
        {
            int index = maps.searchMapByName(server.getMap());
            maps.setCurrentMap(index);
            mapImage.background(new TextureRegionDrawable(new TextureRegion(TextureManager.loadTexture(maps.getCurrentMapPreviewPath()))));
        }
        
        if(server.startServer())
        {
            System.out.println("CLIENT: Launching server with "+ server.getMaxConnections() + " players...");
        }
        server.OpenLobby();
        
        if(!client.isConnectedToServer())
        {
            try 
            {
                //Connect to server
                client.setHost("127.0.0.1", Constants.CONNECTIONPORT);
                client.connectToServer();

            } catch (Exception e) 
            {
                System.err.println("ERROR: Client couldnt connect to own server: " + e);
                e.printStackTrace();
            }
        }

        /**------------------------LABEL STYLE------------------------**/
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        fontOptions.size = 14;
        labelStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        labelStyle.fontColor = Color.LIGHT_GRAY;
               
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
        
        /**------------------------BACK BUTTON------------------------**/
        TextButton.TextButtonStyle textButtonStyleSettings = new TextButton.TextButtonStyle();
        textButtonStyleSettings.font = font;
        textButtonStyleSettings.up   = settingsSkin.getDrawable("button_up");
        textButtonStyleSettings.down = settingsSkin.getDrawable("button_down");
        textButtonStyleSettings.over = settingsSkin.getDrawable("button_checked");
        
        /**------------------------IP LABEL------------------------**/
        ipLabel = new Label("Your local IP:", labelStyle);
        ipLabel.setAlignment(Align.left);
        ipLabel.setPosition(10, 9);
        
        ipTextField = new TextField("", TextureManager.skin);
        ipTextField.setPosition(17 + ipLabel.getWidth(), 3);
        ipTextField.setHeight(25);
        ipTextField.setBlinkTime(9999);
        
        stage.addActor(ipTextField);
        stage.addActor(ipLabel);
        
        /**------------------------JOIN PLAYER GROUP------------------------**/
        //Table options
        Table table = new Table();
        table.setFillParent(true);
        
        //P1 spawn field
        p1Field = new Container();
        p1Field.background(new TextureRegionDrawable((TextureRegion) TextureManager.p1WalkingDownAnim.getKeyFrame(0)));
        table.add(p1Field).width(64).height(64);
        
        //P2 spawn field
        p2Field = new Container();
        p2Field.setVisible(false);
        p2Field.background(new TextureRegionDrawable((TextureRegion) TextureManager.p2WalkingDownAnim.getKeyFrame(0)));
        table.add(p2Field).width(64).height(64).padLeft(96);
        
        //P3 spawn field
        p3Field = new Container();
        p3Field.setVisible(false);
        p3Field.background(new TextureRegionDrawable((TextureRegion) TextureManager.p3WalkingDownAnim.getKeyFrame(0)));
        table.add(p3Field).width(64).height(64).padLeft(96);
        
        //P4 spawn field
        p4Field = new Container();
        p4Field.setVisible(false);
        p4Field.background(new TextureRegionDrawable((TextureRegion) TextureManager.p4WalkingDownAnim.getKeyFrame(0)));
        table.add(p4Field).width(64).height(64).padLeft(96);
        
        //Stage & group options
        joinedPlayerGroup.addActor(table);
        joinedPlayerGroup.setPosition(443, 74);
        stage.addActor(joinedPlayerGroup);
        
        
        /**------------------------MAP SLIDER GROUP------------------------**/
        //Table options
        Table table2 = new Table();

        // Map Name Label
        mapName = new Label("I like trains", labelStyle);
        mapName.setText(maps.getCurrentMapName());
        mapName.setAlignment(Align.center);
        mapName.setColor(Color.BLACK);
        mapName.setPosition(310, 430);
        stage.addActor(mapName);
        
        
        // Map Screenshot
        table2.add(mapImage).width(400).height(210);
        
        //Stage & group options
        sliderGroup.addActor(table2);
        sliderGroup.setPosition(400, 310);
        stage.addActor(sliderGroup);
        
        
        /**------------------------FILTER BOX------------------------**/
        // Filter box
        selectBox = new SelectBox<>(TextureManager.skin);
        selectBox.setItems("4", "3", "2", "ALL");
        selectBox.setWidth(50);
        selectBox.setHeight(25);
        selectBox.setSelected("ALL");
//        selectBox.getStyle().fontColor = Color.LIGHT_GRAY;
        maps.setFilterByPlayerNumber(-1);
        selectBox.setPosition(Constants.SCREENWIDTH - selectBox.getWidth() - 10, 3);
        stage.addActor(selectBox);
        
        filterDescription = new Label("Filter by players:", labelStyle);
        filterDescription.setPosition(480, 9);
        stage.addActor(filterDescription);
        
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
        
        // Settings
        settingsButton = new TextButton("", textButtonStyleSettings);
        settingsButton.setPosition(Constants.SCREENWIDTH - settingsButton.getWidth(), Constants.SCREENHEIGHT - settingsButton.getHeight() + 7);
        stage.addActor(settingsButton);
        
        // Back button
        backbutton = new TextButton("", textButtonStyleBack);
        backbutton.setPosition(0, Constants.SCREENHEIGHT - backbutton.getHeight() + 7);
        stage.addActor(backbutton);
        
        /**------------------------BUTTON FUNCTIONS------------------------**/
        selectBox.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {
                // Check if ALL maps is selected
                if(!selectBox.getSelected().equals("ALL"))
                {
                    maps.setFilterByPlayerNumber(Integer.parseInt(selectBox.getSelected()));
                }else
                {
                    // Number to deactivate filter and show all maps
                    maps.setFilterByPlayerNumber(-1);
                }
                
                maps.goToNextCompatibleMap();
                mapImage.background(new TextureRegionDrawable(new TextureRegion(TextureManager.loadTexture(maps.getCurrentMapPreviewPath()))));
                server.setMap(maps.getCurrentMap());
            }
        });
        
        //Add click listener --> Back button
        settingsButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
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
        
        //Add click listener --> Back button
        backbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
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

                server.stopServer();
                game.setScreen(new MenuScreen(game, client, server));
            }
        });
        
        //Add click listener --> Start Game
        startbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
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
                
                if(Constants.TESTMAP)
                {
                    System.out.println("Ignoring user settings and loading test map. This is defined in CONSTANTS!");
                    server.setMap(Constants.TESTMAPPATH);
                }else
                {
                    server.setMap(maps.getCurrentMap());
                }
                
                AudioManager.getCurrentMusic().stop();
                server.startGame();
                game.setScreen(new GameScreen(game, client, server));
            }
        });
        
        //Add click listener --> Slide right
        slideRight.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
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
                
                maps.nextMap();
                checkCurrentPlayerCompatibility();
                mapImage.background(new TextureRegionDrawable(new TextureRegion(TextureManager.loadTexture(maps.getCurrentMapPreviewPath()))));
                mapName.setText(maps.getCurrentMapName());
                server.setMap(maps.getCurrentMap());
            }
        });
        
        //Add click listener --> Slide left
        slideLeft.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
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
                
                
                maps.previousMap();
                checkCurrentPlayerCompatibility();
                mapImage.background(new TextureRegionDrawable(new TextureRegion(TextureManager.loadTexture(maps.getCurrentMapPreviewPath()))));
                mapName.setText(maps.getCurrentMapName());
                server.setMap(maps.getCurrentMap());
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
        
        try 
        {
            ipTextField.setText(client.getCurrentIp().getHostAddress());
        } catch (SocketException ex) 
        {
            ipTextField.setText("ERROR");
            ex.printStackTrace();
        } catch (NullPointerException e)
        {
            ipTextField.setText("No network");
        }
        
        checkCurrentPlayerCompatibility();
        
        p1Field.setVisible(false);
        p2Field.setVisible(false);
        p3Field.setVisible(false);
        p4Field.setVisible(false);
        
        for(int i=0; i < Server.getClientConnectionArraySize(); i++)
        {
            ClientConnection clientconnection = Server.getClient(i);
            
            //Who joined
            switch(clientconnection.getPlayerId())
            {
                case 1:
                    p1Field.setVisible(true);
                    break;

                case 2:
                    p2Field.setVisible(true);
                    break;

                case 3:
                    p3Field.setVisible(true);
                    break;

                case 4:
                    p4Field.setVisible(true);
                    break;
            }
        }
            
        //Draw stage
        stage.act(Constants.DELTATIME);
        stage.draw();
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        super.changeToFullScreenOnF12();
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            server.stopServer();
            game.setScreen(new MenuScreen(game, client, server));
        }
    }
    
    
    private void checkCurrentPlayerCompatibility()
    {
        int numPlayers = Server.getClientConnectionArraySize();
        
        if(numPlayers != 1)
        {
            if(maps.isCurrentMapCompatible(numPlayers))
            {
                startbutton.setDisabled(false);
                startbutton.setText("Start Game!");
            }else
            {
                startbutton.setDisabled(true);
                startbutton.setText("Not Compatible!");
            }
        }else
        {
            startbutton.setDisabled(false);
            startbutton.setText("Play Alone!");
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