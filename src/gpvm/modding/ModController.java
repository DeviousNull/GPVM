/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpvm.modding;

/**
 * An interface for a class that will handle the high level functions of
 * a mod.  It will handle the any loading or initialization the mod may need.
 * For most case the modinfo will be able to handle this, however this class
 * can be useful for content created at runtime or similar uses.
 * 
 * Each ModLoader is associated with a {@link Mod} that it can use to interact
 * with the game.  The {@link Mod} will go through several stages: Uninitialized,
 * initialized, loaded, destroyed.
 * 
 * A mod may be loaded multiple times by a single instance of a {@link ModLoader}.
 * The {@link ModLoader#unload()} method should ideally return the mod to state
 * it was in immediately after being initialized.
 * 
 * @author russell
 */
public class ModController {
  //protected Mod parent;
  
  /**
   * Initializes the mod.  At this point none of the assets for the mod should
   * be loaded, but all the settings should loaded and the {@link Mod} should be
   * set up with all of the information it will use to load content.  Any mods that
   * this one depends on are not guaranteed to be added to the class path at this
   * point.
   */
  public void initialize() {}
  
  /**
   * Called prior to loading the mod.
   */
  public void preload() {}
  
  /**
   * Loads the assets of the {@link Mod}.  For many resources the {@link Mod}
   * for this class can handle loading assets once it has been given set of files
   * to load assets from.
   */
  public void load() {}
  
  /**
   * Called after all active mods are loaded.
   */
  public void postload() {}
  
  /**
   * Unloads all of the assets for the mod.  At the end of this the mod should
   * not have any references or assets loaded into the game state.
   */
  public void unload() {}
  
  /**
   * This method is called when the mod is no longer going to be loaded.
   * Handles any final cleanup of the mod.
   */
  public void destroy() {}
}
