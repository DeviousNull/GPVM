/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpvm;

import gpvm.modding.ModManager;
import gpvm.render.RenderingSystem;
import gpvm.util.StringManager;

/**
 * Controls the overall state of the game.  This class will initialize the
 * various system of the game and clean them up as needed.  For most cases 
 * interaction should go through the this class when possible so that all systems
 * are notified of changes.
 * 
 * @author russell
 */
public class GameManager {
  public static GameManager getInstance() {
    return instance;
  }
  
  public void startGame() {
    //first load the mods, the manager already knows what mods to load.
    ModManager.getInstance().loadMods();
    
    RenderingSystem.createSystem(StringManager.getDisplayMode());
  }
  
  public boolean isPaused() {
    return paused;
  }
  
  public void setPaused(boolean val) {
    paused = val;
    
    RenderingSystem.getInstance().setPaused(val);
  }
  
  private boolean paused;
  
  private static GameManager instance = new GameManager();
}