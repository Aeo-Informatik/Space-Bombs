/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;

/**
 *
 * @author sRechner
 */
public class Map {
    private String name;
    private int[] numberOfPlayers;
    private String path;
    private String preview;

    public Map(String name, int[] numberOfPlayers, String path, String preview) {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
        this.path = path;
        this.preview = preview;
    }

    public String getName() {
        return name;
    }

    public int[] getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getPath() {
        return path;
    }

    public String getPreview() {
        return preview;
    }



   
    
    
}
