/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.gdx.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import gui.camera.OrthoCamera;
import gui.camera.VirtualViewport;



/**
 *
 * @author pl0614
 */
public class Tiledtest implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;
    private OrthographicCamera camera;

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("assest/map.tmx");
        render = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        render.setView(camera);
        render.render();

    }

    @Override
    public void resize(int height, int width) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        render.dispose();
    }
    
}
