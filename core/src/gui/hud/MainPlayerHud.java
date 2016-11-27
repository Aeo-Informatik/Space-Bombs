/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.player.MainPlayer;

/**
 *
 * @author qubasa
 */
public class MainPlayerHud 
{
    /**
     * How to use tables: https://github.com/libgdx/libgdx/wiki/Table#quickstart
     * How to use elements in table: https://github.com/libgdx/libgdx/wiki/Scene2d.ui#stack
     */
    //General Objects
    public Stage stage;
    private Stack stack;
    private MainPlayer mainPlayer;
    private EntityManager entityManager;
    
    //Font import
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    
    //Life & Counter Hud
    private Image uiCounterImage;
    private Label bombCounterLabel;
    private Label coinCounterLabel;
    private int live = -1;
    
    // Print to screen
    private static Label deathMessageLabel;
    private float printToScreenTimer;
    private float printToScreenEndTimer = 5;
    private static boolean activated = false;
    
    // Bomb Inventory Hud
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

    //Constructor
    public MainPlayerHud(EntityManager entityManager)
    {
        //Initialise Objects
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        this.stack = new Stack(); //A container in wich you can place multiple widgets to "stack" them
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
        
        /*-------------------------DEATH MESSAGE--------------------------*/
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        
        parameter.size = 18;
        deathMessageLabel = new Label("", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        deathMessageLabel.setAlignment(Align.center);
        deathMessageLabel.setFillParent(true);
        rootTable.add(deathMessageLabel).padRight(30).padTop(800);
        stage.addActor(rootTable);
        
        /*-------------------------LIFE & COUNTER HUD--------------------------*/
        parameter.size = 15;
        //Labels (textfields)
        bombCounterLabel = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        coinCounterLabel = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));

        //Live & coins display image
        uiCounterImage = new Image(TextureManager.hudCounterFullLive);
        
        //Calculate image width (scaleX) and image height (scaleY)
        float scaleX = TextureManager.hudCounterFullLive.getWidth() * 3;
        float scaleY = TextureManager.hudCounterFullLive.getHeight() * 3;
        
        //Add image to background table
        backgroundTable.add(uiCounterImage).width(scaleX).height(scaleY);
        
        //Add label to foreground table
        foregroundTable.row().expandX().padTop(27);
        foregroundTable.add(bombCounterLabel).padLeft(114);
        foregroundTable.add(coinCounterLabel).padLeft(21);
        
        //Set container to the height of the image and position it on the top left
        stack.setWidth(scaleX);
        stack.setHeight(scaleY);
        stack.setPosition(0, 470 - scaleY);
        
        //End table and container (stack)
        stack.add(backgroundTable);
        stack.add(foregroundTable);
        stage.addActor(stack);
        
        /*-------------------------BOMB INVENTORY HUD--------------------------*/
        float scalingImage = 1.5f;
        
        // Inventory Image
        uiInventoryImage = new Image(TextureManager.hudInventoryHiglighted1);
        uiInventoryImage.setWidth(280*scalingImage);// 1,5x bigger
        uiInventoryImage.setHeight(32*scalingImage);
        uiInventoryImage.setPosition((Constants.SCREENWIDTH - uiInventoryImage.getWidth()) / 2 -2, 5);
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
        pricesTable.add(bombPrice1).width(31*scalingImage).height(31*scalingImage);
        pricesTable.add(bombPrice2).width(31*scalingImage).height(31*scalingImage);
        pricesTable.add(bombPrice3).width(31*scalingImage).height(31*scalingImage);
        pricesTable.add(bombPrice4).width(31*scalingImage).height(31*scalingImage);
        pricesTable.add(bombPrice5).width(31*scalingImage).height(31*scalingImage);
        pricesTable.add(bombPrice6).width(31*scalingImage).height(31*scalingImage);
        pricesTable.add(bombPrice7).width(31*scalingImage).height(31*scalingImage);
        pricesTable.add(bombPrice8).width(31*scalingImage).height(31*scalingImage);
        pricesTable.add(bombPrice9).width(31*scalingImage).height(31*scalingImage);
        
        pricesLabelGroup.addActor(pricesTable);
        pricesLabelGroup.setPosition((Constants.SCREENWIDTH - pricesLabelGroup.getWidth()) / 2, 15);
        stage.addActor(pricesLabelGroup);
        
        generator.dispose();
    }
    
    public void update()
    {
                
        //Debug
        //stage.setDebugAll(true);
        
        if(printToScreenTimer >= printToScreenEndTimer && activated == true)
        {
            deathMessageLabel.setVisible(false);
            printToScreenTimer = 0;
            activated = false;
            
        }else if(activated)
        {
            printToScreenTimer += Constants.DELTATIME;
        }
        
        stage.getCamera().update();
        
        if(mainPlayer != null)
        {
            /*---------------------LIFE HUD----------------------*/
            bombCounterLabel.setText(String.format("%03d", mainPlayer.getMaxBombs()));
            coinCounterLabel.setText(String.format("%03d", mainPlayer.getCoins()));
            
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
                }
            }
        }else
        {
            mainPlayer = entityManager.getPlayerManager().getMainPlayer();
        }
    }
    
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, false);
    }
    
    public static void printToScreen(String message)
    {
        deathMessageLabel.setVisible(true);
        activated = true;
        
//        String previousMessages[] = deathMessageLabel.getText().toString().split("\n");
//        String lastMessage = previousMessages[previousMessages.length -1];
//        
        deathMessageLabel.setText(message);
    }
}
