/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;

import java.io.IOException;
import java.util.ArrayList;

import client.Client;
import gui.AudioManager;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.bomb.NormalBomb;
import gui.entity.player.EnemyPlayer;
import gui.entity.player.MainPlayer;
import gui.entity.player.Player;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import inputHandling.InputHandler;
import server.Server;

/**
 *
 * @author qubasa
 */
public class MainPlayerHud extends Screens implements Screen
{
    /**
     * How to use tables: https://github.com/libgdx/libgdx/wiki/Table#quickstart
     * How to use elements in table: https://github.com/libgdx/libgdx/wiki/Scene2d.ui#stack
     */
    //General Objects
    public Stage stage;
    private Stack stack;
    private MainPlayer mainPlayer;
    private EnemyPlayer currentSpectatedPlayer;
    private EntityManager entityManager;

    //Debug
    private static ShapeRenderer debugRenderer = new ShapeRenderer();
    private OrthographicCamera camera;

    //Font import
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    
    //Life & Counter Hud
    private float scaleCounterHud = 3;
    private Image uiCounterImage;
    private Label bombCounterLabel;
    private Label coinCounterLabel;
    private Label rangeCounterLabel;
    private Label speedCounterLabel;
    private int live = -1;
    
    // Print to screen
    private float scaleTextMessage = 5;
    private static Label textMessageLabel;
    private static ArrayList<String> textMessageArray;
    private static float printToScreenTimer = 0;
    private float printToScreenTimerEnd = 3f;
    
    // Death message
    private static Label deathMessageLabel;
    private static ArrayList<String> deathMessageArray;
    private static float deathMessageTimer = 0;
    private float deathMessageTimerEnd = 5f;
    
    // Bomb Inventory Hud
    private float scaleInventoryImg = 1.5f;
    private int addToPositionX = -2;
    private WidgetGroup pricesLabelGroup = new WidgetGroup();
    private Image uiInventoryImage;
    private int choosenBomb = -1;
    private Label bombPrice1;
    private Label bombPrice2;
    private Label bombPrice3;
    private Label bombPrice4;
    private Label bombPrice5;
    private Label bombPrice6;
    private Label bombPrice7;
    private Label bombPrice8;
    private Label bombPrice9;

    // Exit dialog
    private Dialog exitDialog;
    
    // Touchpad for phone
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;

    // Touchcoordinates
    private ThinGridCoordinates goToCoordiantes = null;
    private Table touchTable = new Table();

    // Action touchpad
    private Image placeBombButton = new Image(new Texture("hud/placeBombButton.png"));
    private Image triggerRemoteButton = new Image(new Texture("hud/triggerRemoteButton.png"));
    private Image teleportButton = new Image(new Texture("hud/teleportButton.png"));

    // Go to menu button
    private Image goToMenuButton = new Image(new Texture("hud/goToMenuButton.png"));

    /*-------------------------CONSTRUCTOR--------------------------*/
    public MainPlayerHud(final EntityManager entityManager, Game game, Server server, Client client, final MapLoader map, OrthographicCamera camera)
    {
        super(game, client, server);
        
        deathMessageArray = new ArrayList<>();
        textMessageArray = new ArrayList<>();
        
        //Initialise Objects
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        this.stack = new Stack(); //A container in wich you can place multiple widgets to "stack" them
        this.touchTable.setSize(stage.getWidth(), stage.getHeight());
        this.camera = camera;
        touchTable.setTouchable(Touchable.enabled);
        stage.addActor(touchTable);
        this.entityManager = entityManager;

        //Initialise Font
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/lunchtime-doubly-so/lunchds.ttf"));
        this.parameter = new FreeTypeFontParameter();
        parameter.size = 15;
        
        //Table settings
        Table backgroundTable = new Table();
        backgroundTable.top();
        backgroundTable.setFillParent(true);
        
        //Table settings 2
        Table foregroundTable = new Table();
        foregroundTable.top();
        foregroundTable.setFillParent(true);
        
        /**------------------------EXIT DIALOG------------------------**/
        exitDialog = new Dialog("Confirm exit", TextureManager.skin)
        {
            @Override
            public void result(Object obj) 
            {
                boolean result = (boolean) obj;
                                
                if(result == true)
                {
                    //Stop music
                    AudioManager.getCurrentMusic().stop();
                    
                    quitGame();
                }else
                {
                    Gdx.input.setCursorCatched(true);
                }
            }
        };
        exitDialog.text("Are you sure you want to quit?");
        exitDialog.button("Yes", true); //sends "true" as the result
        exitDialog.button("No", false);  //sends "false" as the result

        if(Constants.ISRUNNINGONSMARTPHONE && Constants.TOUCHSCREEN)
        {
            touchTable.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttonCode)
                {

                    Player player = entityManager.getPlayerManager().getCurrentPlayerObject();

                    if(player != null)
                    {
                        // Calculate correct world position
                        ThinGridCoordinates aim = new ThinGridCoordinates(player.getPosition().getX() + (x - touchTable.getWidth() / 2f), player.getPosition().getY() + (y - touchTable.getHeight()  / 2f));
                        aim = new ThinGridCoordinates(aim.getX() - (x - touchTable.getWidth() / 2f) / 3.5f, aim.getY() - (y - touchTable.getHeight() / 2f) / 3.5f);

                        // Change to block center
                        ThinGridCoordinates blockCenter = new ThinGridCoordinates(new MapCellCoordinates(aim));
                        aim.setX(blockCenter.getX() + (Constants.MAPTEXTUREWIDTH - Constants.PLAYERWIDTH) / 2);
                        aim.setY(blockCenter.getY() + (Constants.MAPTEXTUREHEIGHT - Constants.PLAYERHEIGHT / 4 )/ 2);

                        System.out.println("X: " + x + " Y: " + y);

                        goToCoordiantes = aim;
                    }

                    return true;
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer)
                {
                    Player player = entityManager.getPlayerManager().getCurrentPlayerObject();

                    if(player != null)
                    {
                        // Calculate correct world position
                        ThinGridCoordinates aim = new ThinGridCoordinates(player.getPosition().getX() + (x - touchTable.getWidth() / 2f), player.getPosition().getY() + (y - touchTable.getHeight()  / 2f));
                        aim = new ThinGridCoordinates(aim.getX() - (x - touchTable.getWidth() / 2f) / 3.5f, aim.getY() - (y - touchTable.getHeight() / 2f) / 3.5f);



                        // Change to block center
                        ThinGridCoordinates blockCenter = new ThinGridCoordinates(new MapCellCoordinates(aim));
                        aim.setX(blockCenter.getX() + (Constants.MAPTEXTUREWIDTH - Constants.PLAYERWIDTH) / 2);
                        aim.setY(blockCenter.getY() + (Constants.MAPTEXTUREHEIGHT - Constants.PLAYERHEIGHT / 4 )/ 2);

                        goToCoordiantes = aim;
                    }

                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button)
                {

                }

            });
        }



        /*-------------------------MOVEMENT TOUCHPAD--------------------------*/
        if(Constants.ISRUNNINGONSMARTPHONE && Constants.TOUCHPAD) {
            //Create a touchpad skin
            touchpadSkin = new Skin();
            //Set background image
            touchpadSkin.add("touchBackground", new Texture("hud/touchBackground.png"));
            //Set knob image
            touchpadSkin.add("touchKnob", new Texture("hud/touchKnob.png"));
            //Create TouchPad Style
            touchpadStyle = new Touchpad.TouchpadStyle();
            //Create Drawable's from TouchPad skin
            touchBackground = touchpadSkin.getDrawable("touchBackground");
            touchKnob = touchpadSkin.getDrawable("touchKnob");
            //Apply the Drawables to the TouchPad Style
            touchpadStyle.background = touchBackground;
            touchpadStyle.knob = touchKnob;
            //Create new TouchPad with the created style
            touchpad = new Touchpad(10, touchpadStyle);
            //setBounds(x,y,width,height)
            touchpad.setBounds(15, 15, 150, 150);

            stage.addActor(touchpad);

            touchpad.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float screenX, float screenY, int pointer, int buttonCode) {

                    return true;
                }

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer)
                {
                    ThinGridCoordinates aim = null;

                    Player player = entityManager.getPlayerManager().getCurrentPlayerObject();

                    if(player != null) {

                        // Left
                        if (touchpad.getKnobPercentX() < -0.5)
                        {
                            float x = 370;
                            float y = 250;

                            // Calculate correct world position
                            aim = new ThinGridCoordinates(player.getPosition().getX() + (x - touchTable.getWidth() / 2f), player.getPosition().getY() + (y - touchTable.getHeight() / 2f));
                            aim = new ThinGridCoordinates(aim.getX() - (x - touchTable.getWidth() / 2f) / 3.5f, aim.getY() - (y - touchTable.getHeight() / 2f) / 3.5f);

                            //Right
                        } else if (touchpad.getKnobPercentX() > 0.5)
                        {
                            float x = 460;
                            float y = 250;

                            // Calculate correct world position
                            aim = new ThinGridCoordinates(player.getPosition().getX() + (x - touchTable.getWidth() / 2f), player.getPosition().getY() + (y - touchTable.getHeight() / 2f));
                            aim = new ThinGridCoordinates(aim.getX() - (x - touchTable.getWidth() / 2f) / 3.5f, aim.getY() - (y - touchTable.getHeight() / 2f) / 3.5f);
                        }

                        // Down
                        if (touchpad.getKnobPercentY() < -0.5)
                        {
                            float x = 415;
                            float y = 200;

                            // Calculate correct world position
                            aim = new ThinGridCoordinates(player.getPosition().getX() + (x - touchTable.getWidth() / 2f), player.getPosition().getY() + (y - touchTable.getHeight() / 2f));
                            aim = new ThinGridCoordinates(aim.getX() - (x - touchTable.getWidth() / 2f) / 3.5f, aim.getY() - (y - touchTable.getHeight() / 2f) / 3.5f);

                            // Up
                        } else if (touchpad.getKnobPercentY() > 0.5) {

                            float x = 415;
                            float y = 300;

                            // Calculate correct world position
                            aim = new ThinGridCoordinates(player.getPosition().getX() + (x - touchTable.getWidth() / 2f), player.getPosition().getY() + (y - touchTable.getHeight() / 2f));
                            aim = new ThinGridCoordinates(aim.getX() - (x - touchTable.getWidth() / 2f) / 3.5f, aim.getY() - (y - touchTable.getHeight() / 2f) / 3.5f);
                        }

                        if (aim != null) {
                            // Change to block center
                            ThinGridCoordinates blockCenter = new ThinGridCoordinates(new MapCellCoordinates(aim));
                            aim.setX(blockCenter.getX() + (Constants.MAPTEXTUREWIDTH - Constants.PLAYERWIDTH) / 2);
                            aim.setY(blockCenter.getY() + (Constants.MAPTEXTUREHEIGHT - Constants.PLAYERHEIGHT / 4) / 2);
                        }

                        goToCoordiantes = aim;
                    }
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    InputHandler.setAnyKey(false);
                    InputHandler.resetMovementKeys();

                }
            });
        }

        /*-------------------------ACTION TOUCHPAD--------------------------*/
        if(Constants.ISRUNNINGONSMARTPHONE)
        {
            Table table = new Table();
            table.setFillParent(true);
            table.right().bottom().toFront();


            /*-------------------------PLACE BOMB--------------------------*/
            placeBombButton.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float screenX, float screenY, int pointer, int buttonCode) {

                    InputHandler.setAnyKey(true);
                    InputHandler.setPlaceBombKey(true);

                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    InputHandler.setAnyKey(false);
                    InputHandler.setPlaceBombKey(false);
                }

            });

            placeBombButton.setSize(100, 100);

            table.add(placeBombButton).size(placeBombButton.getWidth(), placeBombButton.getHeight()).padBottom(10).padRight(10);

            stage.addActor(table);


            /*-------------------------TRIGGER REMOTE BUTTON--------------------------*/
            Table table1 = new Table();
            table1.right().bottom().toFront();

            triggerRemoteButton.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float screenX, float screenY, int pointer, int buttonCode) {

                    InputHandler.setAnyKey(true);
                    InputHandler.setRemoteKey(true);

                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    InputHandler.setAnyKey(false);
                    InputHandler.setRemoteKey(false);
                }

            });

            triggerRemoteButton.setSize(60, 60);

            table1.add(triggerRemoteButton).size(triggerRemoteButton.getWidth(), triggerRemoteButton.getHeight());
            table1.setPosition(685, 80);
            stage.addActor(table1);

            /*-------------------------TELEPORT BUTTON--------------------------*/
            Table table2 = new Table();
            table1.right().bottom().toFront();

            teleportButton.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float screenX, float screenY, int pointer, int buttonCode) {

                    InputHandler.setAnyKey(true);
                    InputHandler.setTeleportKey(true);

                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    InputHandler.setAnyKey(false);
                    InputHandler.setTeleportKey(false);
                }

            });

            teleportButton.setSize(60, 60);

            table2.add(teleportButton).size(teleportButton.getWidth(), teleportButton.getHeight());
            table2.setPosition(Constants.SCREENWIDTH - 65, 155);
            stage.addActor(table2);
        }

        /*-------------------------RESIZE ELEMENTS IN HUD--------------------------*/
        if(Constants.ISRUNNINGONSMARTPHONE)
        {
            scaleCounterHud = 4; // Default 3
            scaleInventoryImg = 1.8f; // Default 1.5
            addToPositionX = 20; // Default -2
            scaleTextMessage = 6; // Default 5
            Constants.DEFAULTZOOM = 0.7f; // Default 1f
        }

        /*-------------------------GO BACK TO MENU BUTTON--------------------------*/
        if(Constants.ISRUNNINGONSMARTPHONE)
        {
            Table table = new Table();


            goToMenuButton.addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float screenX, float screenY, int pointer, int buttonCode) {

                    InputHandler.setAnyKey(true);
                    InputHandler.setEscKey(true);

                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                    InputHandler.setAnyKey(false);
                    InputHandler.setEscKey(false);
                }
            });

            goToMenuButton.setSize(70, 70);

            table.add(goToMenuButton).size(goToMenuButton.getWidth(), goToMenuButton.getHeight());
            table.setSize(goToMenuButton.getWidth(), goToMenuButton.getHeight());
            table.padTop(40);
            table.padRight(40);
            table.setPosition(Constants.SCREENWIDTH - table.getWidth(), Constants.SCREENHEIGHT - table.getHeight());

            stage.addActor(table);
        }

        /*-------------------------SCREEN MESSAGE--------------------------*/
        Table messageLabel = new Table();
        messageLabel.setFillParent(true);
        messageLabel.right();


        parameter.size = 4 * (int) scaleTextMessage;
        deathMessageLabel = new Label("", new Label.LabelStyle(generator.generateFont(parameter), Color.valueOf("f7472c")));
        deathMessageLabel.setAlignment(Align.center);
        deathMessageLabel.setSize(200, 50);
        messageLabel.add(deathMessageLabel);

        textMessageLabel = new Label("", new Label.LabelStyle(generator.generateFont(parameter), Color.YELLOW));
        textMessageLabel.setAlignment(Align.center);
        textMessageLabel.setSize(200, 50);
        messageLabel.add(textMessageLabel);

        stage.addActor(messageLabel);
        
        
        /*-------------------------LIFE & COUNTER HUD--------------------------*/
        parameter.size = 5 * (int) scaleCounterHud;
        //Labels (textfields)
        bombCounterLabel = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        coinCounterLabel = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        speedCounterLabel = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        rangeCounterLabel = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        
        //Live & coins display image
        uiCounterImage = new Image(TextureManager.hudCounterFullLive);

        //Calculate image width (scaledCounterX) and image height (scaleY)
        float scaledCounterX = TextureManager.hudCounterFullLive.getWidth() * scaleCounterHud;
        float scaledCounterY = TextureManager.hudCounterFullLive.getHeight() * scaleCounterHud;

        //Add image to background messageLabel
        backgroundTable.add(uiCounterImage).width(scaledCounterX).height(scaledCounterY);
        
        //Add label to foreground messageLabel
        foregroundTable.row().expandX().padTop(8 * scaleCounterHud);
        foregroundTable.add(bombCounterLabel).padLeft(40 * scaleCounterHud);
        foregroundTable.add(coinCounterLabel).padLeft(16 * scaleCounterHud);
        foregroundTable.add(rangeCounterLabel).padLeft(13 * scaleCounterHud);
        foregroundTable.add(speedCounterLabel).padLeft(18 * scaleCounterHud);

        //Set container to the height of the image and position it on the top left
        stack.setWidth(scaledCounterX);
        stack.setHeight(scaledCounterY);
        stack.setPosition(0, 470 - scaledCounterY);

        //End messageLabel and container (stack)
        stack.add(backgroundTable);
        stack.add(foregroundTable);
        stage.addActor(stack);
        
        /*-------------------------BOMB INVENTORY HUD--------------------------*/
        
        // Inventory Image
        uiInventoryImage = new Image(TextureManager.hudInventoryHiglighted1);
        uiInventoryImage.setWidth(280*scaleInventoryImg);// 1,5x bigger
        uiInventoryImage.setHeight(32*scaleInventoryImg);
        uiInventoryImage.setPosition((Constants.SCREENWIDTH - uiInventoryImage.getWidth()) / 2 + addToPositionX, 5);
        stage.addActor(uiInventoryImage);
        
        // Price labels
        bombPrice1 = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.GOLD));
        bombPrice2 = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.GOLD));
        bombPrice3 = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.GOLD));
        bombPrice4 = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.GOLD));
        bombPrice5 = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.GOLD));
        bombPrice6 = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.GOLD));
        bombPrice7 = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.GOLD));
        bombPrice8 = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.GOLD));
        bombPrice9 = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.GOLD));
        
        Table pricesTable = new Table();
        pricesTable.setFillParent(true);
        
        //31x31
        pricesTable.add(bombPrice1).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
        pricesTable.add(bombPrice2).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
        pricesTable.add(bombPrice3).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
        pricesTable.add(bombPrice4).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
        pricesTable.add(bombPrice5).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
        pricesTable.add(bombPrice6).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
        pricesTable.add(bombPrice7).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
        pricesTable.add(bombPrice8).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
        pricesTable.add(bombPrice9).width(31*scaleInventoryImg).height(31*scaleInventoryImg);

        pricesLabelGroup.addActor(pricesTable);
        pricesLabelGroup.setPosition((Constants.SCREENWIDTH - pricesLabelGroup.getWidth()) / 2 + addToPositionX + 3, 15);
        stage.addActor(pricesLabelGroup);

        if(Constants.ISRUNNINGONSMARTPHONE)
        {
            Table touchTable = new Table();

            touchTable.add(getContainerWithBombListener(1)).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
            touchTable.add(getContainerWithBombListener(2)).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
            touchTable.add(getContainerWithBombListener(3)).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
            touchTable.add(getContainerWithBombListener(4)).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
            touchTable.add(getContainerWithBombListener(5)).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
            touchTable.add(getContainerWithBombListener(6)).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
            touchTable.add(getContainerWithBombListener(7)).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
            touchTable.add(getContainerWithBombListener(8)).width(31*scaleInventoryImg).height(31*scaleInventoryImg);
            touchTable.add(getContainerWithBombListener(9)).width(31*scaleInventoryImg).height(31*scaleInventoryImg);

            touchTable.setPosition((Constants.SCREENWIDTH - touchTable.getWidth()) / 2 + addToPositionX, 35);
            stage.addActor(touchTable);
        }

        generator.dispose();
    }
    
     /*-------------------------METHODS--------------------------*/
    @Override
    public void render(float delta)
    {
                
        //Debug
        //stage.setDebugAll(true);
        
        stage.act(Constants.DELTATIME);
        stage.draw();
       
        renderTextMessagesOnScreen();
        renderDeathMessagesOnScreen();
        
        stage.getCamera().update();
        
        if(showMainPlayer() == false)
        {
            showSpectator();
        }


        /*if(goToCoordiantes != null)
        {
            MainPlayerHud.DrawDebugLine(goToCoordiantes, goToCoordiantes, 5, Color.RED, camera.combined);
        }*/

        if(Constants.ISRUNNINGONSMARTPHONE && Constants.TOUCHSCREEN && goToCoordiantes != null)
        {
            Player player = entityManager.getPlayerManager().getCurrentPlayerObject();

            if(player != null)
            {
                if(player.goToCoordinates(goToCoordiantes))
                {
                    goToCoordiantes = null;
                }
            }
        }
    }
    
    private void renderDeathMessagesOnScreen()
    {
        if(deathMessageTimer >= deathMessageTimerEnd)
        {
            if(!deathMessageArray.isEmpty())
            {
                deathMessageArray.remove(0);
            }
            deathMessageTimer = 0;
        }else
        {
            deathMessageTimer += Constants.DELTATIME;
        }
        
        StringBuilder stringbuilder = new StringBuilder();
        for(String messageInArr: deathMessageArray)
        {
            stringbuilder.append(messageInArr + "\n");
        }

        deathMessageLabel.setText(stringbuilder.toString());
    }
    
    private void renderTextMessagesOnScreen()
    {
        if(printToScreenTimer >= printToScreenTimerEnd)
        {
            if(!textMessageArray.isEmpty())
            {
                textMessageArray.remove(0);
            }
            printToScreenTimer = 0;
        }else
        {
            printToScreenTimer += Constants.DELTATIME;
        }
        
        StringBuilder stringbuilder = new StringBuilder();
        for(String messageInArr: textMessageArray)
        {
            stringbuilder.append(messageInArr + "\n");
        }

        textMessageLabel.setText(stringbuilder.toString());
    }
    
    private boolean showSpectator()
    {
        if(currentSpectatedPlayer != null)
        {
            
            /*---------------------LIFE HUD----------------------*/
            bombCounterLabel.setText(String.format("%03d", currentSpectatedPlayer.getMaxBombPlacing()));
            coinCounterLabel.setText(String.format("%03d", currentSpectatedPlayer.getCoins()));
            speedCounterLabel.setText(String.format("%03d", currentSpectatedPlayer.getEntitySpeed()) + "%");
            rangeCounterLabel.setText(String.format("%03d", currentSpectatedPlayer.getBombRange()));
            
            //If first time live is set
            if(live == -1)
            {
                live = currentSpectatedPlayer.getLife();
                
            //If mainPlayer life changed
            }else if(live != currentSpectatedPlayer.getLife())
            {
                live = currentSpectatedPlayer.getLife();
                
                //Change heart texture
                switch (live) 
                {
                    case 6:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterSixLive)));
                        break;
                    case 5:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterFiveLive)));
                        break;
                    case 4:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterFourLive)));
                        break;
                    case 3:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterFullLive)));
                        break;
                    case 2:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterTwoThirdLive)));
                        break;
                    case 1:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterOneThirdLive)));
                        break;
                    case 0:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterNoLive)));
                        break;
                }
            }
            
            /*---------------------INVENTORY HUD----------------------*/
            bombPrice1.setText(Integer.toString(currentSpectatedPlayer.getBombPrice(1)));
            bombPrice2.setText(Integer.toString(currentSpectatedPlayer.getBombPrice(2)));
            bombPrice3.setText(Integer.toString(currentSpectatedPlayer.getBombPrice(3)));
            bombPrice4.setText(Integer.toString(currentSpectatedPlayer.getBombPrice(4)));
            bombPrice5.setText(Integer.toString(currentSpectatedPlayer.getBombPrice(5)));
            bombPrice6.setText(Integer.toString(currentSpectatedPlayer.getBombPrice(6)));
            bombPrice7.setText(Integer.toString(currentSpectatedPlayer.getBombPrice(7)));
            bombPrice8.setText(Integer.toString(currentSpectatedPlayer.getBombPrice(8)));
            bombPrice9.setText(Integer.toString(currentSpectatedPlayer.getBombPrice(9)));
            
            //If first time choosenBomb is set
            if(choosenBomb == -1)
            {
                choosenBomb = currentSpectatedPlayer.getChoosenBomb();
                
            //If mainPlayer choosenBomb changed
            }else if(choosenBomb != currentSpectatedPlayer.getChoosenBomb())
            {
                choosenBomb = currentSpectatedPlayer.getChoosenBomb();
                
                //Change inventory texture
                switch (choosenBomb) 
                {
                    case 1:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted1)));
                        break;
                        
                    case 2:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted2)));
                        break;    
                        
                    case 3:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted3)));
                        break;
                        
                    case 4:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted4)));
                        break;
                        
                    case 5:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted5)));
                        break;
                        
                    case 6:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted6)));
                        break;
                        
                    case 7:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted7)));
                        break;
                        
                    case 8:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted8)));
                        break;
                        
                    case 9:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted9)));
                        break;
                }
            }
            
        }
        
        if( entityManager.getPlayerManager().getSpectator() != null)
        {
            currentSpectatedPlayer = entityManager.getPlayerManager().getSpectator().getCurrentEnemyPlayer();

            if(currentSpectatedPlayer == null)
            {
                return false;
            }
        }else
        {
            return false;
        }
        
        
        return true;
    }
    
    
    private boolean showMainPlayer()
    {
        if(mainPlayer != null)
        {
            /*---------------------LIFE HUD----------------------*/                    
            bombCounterLabel.setText(String.format("%03d", mainPlayer.getMaxBombs()));
            coinCounterLabel.setText(String.format("%03d", mainPlayer.getCoins()));
            speedCounterLabel.setText(String.format("%03d", mainPlayer.getEntitySpeed()) + "%");
            rangeCounterLabel.setText(String.format("%03d", mainPlayer.getBombRange()));
            
            //If first time live is set
            if(live == -1)
            {
                live = mainPlayer.getLife();
                
            //If mainPlayer life changed
            }else if(live != mainPlayer.getLife())
            {
                live = mainPlayer.getLife();
                
                //Change heart texture
                switch (live) 
                {
                    case 6:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterSixLive)));
                        break;
                    case 5:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterFiveLive)));
                        break;
                    case 4:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterFourLive)));
                        break;
                    case 3:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterFullLive)));
                        break;
                    case 2:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterTwoThirdLive)));
                        break;
                    case 1:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterOneThirdLive)));
                        break;
                    case 0:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterNoLive)));
                        break;
                }
            }
            
            /*---------------------INVENTORY HUD----------------------*/
            bombPrice1.setText(Integer.toString(mainPlayer.getBombPrice(1)));
            bombPrice2.setText(Integer.toString(mainPlayer.getBombPrice(2)));
            bombPrice3.setText(Integer.toString(mainPlayer.getBombPrice(3)));
            bombPrice4.setText(Integer.toString(mainPlayer.getBombPrice(4)));
            bombPrice5.setText(Integer.toString(mainPlayer.getBombPrice(5)));
            bombPrice6.setText(Integer.toString(mainPlayer.getBombPrice(6)));
            bombPrice7.setText(Integer.toString(mainPlayer.getBombPrice(7)));
            bombPrice8.setText(Integer.toString(mainPlayer.getBombPrice(8)));
            bombPrice9.setText(Integer.toString(mainPlayer.getBombPrice(9)));
            //bombPrice10.setText(Integer.toString(mainPlayer.getBombPrice(10)));
            
            //If first time choosenBomb is set
            if(choosenBomb == -1)
            {
                choosenBomb = mainPlayer.getChoosenBomb();
                
            //If mainPlayer choosenBomb changed
            }else if(choosenBomb != mainPlayer.getChoosenBomb())
            {
                choosenBomb = mainPlayer.getChoosenBomb();
                
                //Change inventory texture
                switch (choosenBomb) 
                {
                    case 1:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted1)));
                        break;
                        
                    case 2:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted2)));
                        break;    
                        
                    case 3:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted3)));
                        break;
                        
                    case 4:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted4)));
                        break;
                        
                    case 5:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted5)));
                        break;
                        
                    case 6:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted6)));
                        break;
                        
                    case 7:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted7)));
                        break;
                        
                    case 8:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted8)));
                        break;
                        
                    case 9:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted9)));
                        break;
                        
/*                    case 10:
                        uiInventoryImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudInventoryHiglighted10)));
                        break;  */
                }
            }
        }else
        {
            mainPlayer = entityManager.getPlayerManager().getMainPlayer();
        }

        return entityManager.getPlayerManager().getMainPlayer() != null;

    }
    
    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, false);
    }
    
    @Override
    public void show() 
    {
        
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
    /*-------------------------GENERAL METHODS--------------------------*/
    public void showExitDialog()
    {
        exitDialog.show(stage);
    }
    
    private void quitGame()
    {
        try 
        {
            //Close connection to server
            client.closeConnection();
            server.stopServer();

        } catch (IOException ex) 
        {
            System.err.println("Unexpected exception on quit game in GameScreen by closing connetion with server.");
            ex.printStackTrace();
        }

        //Go to menuscreen
        game.setScreen(new MenuScreen(game, client, server));
    }

    private Image getContainerWithBombListener(final int bombId)
    {
        Image image = new Image();

        image.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float screenX, float screenY, int pointer, int buttonCode) {

                InputHandler.setAnyKey(true);

                switch(bombId)
                {
                    case 1:
                        InputHandler.setSlot1Key(true);
                        break;
                    case 2:
                        InputHandler.setSlot2Key(true);
                        break;
                    case 3:
                        InputHandler.setSlot3Key(true);
                        break;
                    case 4:
                        InputHandler.setSlot4Key(true);
                        break;
                    case 5:
                        InputHandler.setSlot5Key(true);
                        break;
                    case 6:
                        InputHandler.setSlot6Key(true);
                        break;
                    case 7:
                        InputHandler.setSlot7Key(true);
                        break;
                    case 8:
                        InputHandler.setSlot8Key(true);
                        break;
                    case 9:
                        InputHandler.setSlot9Key(true);
                        break;
                }

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                InputHandler.setAnyKey(false);

                switch(bombId)
                {
                    case 1:
                        InputHandler.setSlot1Key(false);
                        break;
                    case 2:
                        InputHandler.setSlot2Key(false);
                        break;
                    case 3:
                        InputHandler.setSlot3Key(false);
                        break;
                    case 4:
                        InputHandler.setSlot4Key(false);
                        break;
                    case 5:
                        InputHandler.setSlot5Key(false);
                        break;
                    case 6:
                        InputHandler.setSlot6Key(false);
                        break;
                    case 7:
                        InputHandler.setSlot7Key(false);
                        break;
                    case 8:
                        InputHandler.setSlot8Key(false);
                        break;
                    case 9:
                        InputHandler.setSlot9Key(false);
                        break;
                }
            }
        });

        return image;
    }

    public static void deathMessage(String message)
    {
        if(deathMessageArray.size() < 3)
        {
            if(deathMessageArray.isEmpty())
            {
                deathMessageTimer = 0;
            }
            
            deathMessageArray.add(message);
        }

    }
    
    public static void printToScreen(String message)
    {
        if(textMessageArray.size() < 3)
        {
            if(textMessageArray.isEmpty())
            {
                printToScreenTimer = 0;
            }
            
            textMessageArray.add(message);
        }
    }

    private ThinGridCoordinates hudCoordinatesToWorldCoordinates(float x, float y)
    {
        Player player = entityManager.getPlayerManager().getCurrentPlayerObject();

        if(player != null)
        {
            ThinGridCoordinates aim = new ThinGridCoordinates(player.getPosition().getX() + (x - stage.getCamera().viewportWidth / 2), player.getPosition().getY() + (y - stage.getCamera().viewportHeight / 2));

           return aim;
        }

        return null;
    }

    private static void DrawDebugLine(ThinGridCoordinates start, ThinGridCoordinates end, int lineWidth, Color color, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.line(new Vector2(start.getX(), start.getY()), new Vector2(end.getX() +5, end.getY() +1));
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

}

