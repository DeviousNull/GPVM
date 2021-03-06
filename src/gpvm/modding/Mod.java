///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package gpvm.modding;
//
//import gpvm.io.DataLoader;
//import gpvm.io.DataNode;
//import gpvm.io.InvalidDataFileException;
//import taiga.gpvm.map.MapGenerator;
//import taiga.gpvm.map.Universe;
//import taiga.gpvm.map.Universe.World;
//import gpvm.util.MPUClassLoader;
//import gpvm.util.StringManager;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author russell
// */
//public final class Mod {
//  public static final String NAME_FIELD = "name";
//  public static final String DEP_FIELD = "dependency";
//  public static final String WORLD_FIELD = "world";
//  public static final String WORLD_NAME_FIELD = "name";
//  public static final String WORLD_GENERATOR_FIELD = "generator";
//  public static final String CONTROLLER_FIELD = "mod-controller";
//  public static final String TILE_DEFINITION_FIELD = "tile-definition";
//  public static final String RENDERING_FILE_FIELD = "rendering-info";
//  public static final String OVERWORLD_FIELD = "overworld";
//  
//  /**
//   * Contains information that can uniquely identify a mod.
//   */
//  public static class ModIdentifier {
//    public String name;
//    public String version;
//
//    public ModIdentifier() {
//    }
//
//    public ModIdentifier(String name, String version) {
//      this.name = name;
//      this.version = version;
//    }
//  
//    @Override
//    public String toString() {
//      if(version == null) return name;
//      return String.format("%s %s", name, version);
//    }
//  }
//  
//  public boolean validate() {
//    boolean result = true;
//    
//    if(name == null) {
//      log.log(Level.WARNING, StringManager.getLocalString("warn_mod_missing_name"), location);
//      result = false;
//    }
//    
//    return result;
//  }
//  
//  public Class<?> getClass(String cname) throws ClassNotFoundException {
//    return loader.loadClass(cname);
//  }
//  
//  public URL getResource(String rname) {
//    return loader.findResource(rname);
//  }
//  
//  public InputStream getResourceStream(String rname) {
//    return loader.getResourceAsStream(rname);
//  }
//  
//  public ModIdentifier getIdentifier() {
//    return new ModIdentifier(name, version);
//  }
//  
//  public List<World> getWorlds() {
//    return Collections.unmodifiableList(worlds);
//  }
//
//  public List<String> getOverworlds() {
//    return Collections.unmodifiableList(overworlds);
//  }
//  
//  public void intialize() {
//    if(controller != null) controller.initialize();
//    
//    log.log(Level.INFO, StringManager.getLocalString("info_mod_init", name));
//  }
//  
//  public void preload() {
////    //this is where the tiles will be added by the mod controller
////    for(String file : tdfiles) {
////      try {
////        URL res = getResource(file);
////        if(res == null) {
////          log.log(Level.SEVERE, StringManager.getLocalString("err_tile_def_not_found", file));
////          continue;
////        }
////        
////        RegistryDataReader.loadTileRegistryData(getResource(file), name);
////      } catch (URISyntaxException | FileNotFoundException | InvalidDataFileException ex) {
////        log.log(Level.SEVERE, StringManager.getLocalString("err_invalid_tile_def_file", file), ex);
////      }
////    }
////    
////    //load all of the rendering data
////    for(String file : rendfiles) {
////      try {
////        URL res = getResource(file);
////        if(res == null) {
////          log.log(Level.SEVERE, StringManager.getLocalString("err_rendering_file_not_found", file));
////          continue;
////        }
////        
////        RegistryDataReader.loadRenderRegistryData(res, name, loader);
////      } catch (URISyntaxException | FileNotFoundException | InvalidDataFileException | InstantiationException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException ex) {
////        log.log(Level.SEVERE, StringManager.getLocalString("err_invalid_rendering_file", file), ex);
////      }
////    }
////    
////    if(controller != null) controller.preload();
////    
////    log.log(Level.INFO, StringManager.getLocalString("info_mod_preload", name));
//  }
//  
//  public void load() {
//    if(controller != null) controller.load();
//    
//    log.log(Level.INFO, StringManager.getLocalString("info_mod_load", name));
//  }
//  
//  public void postload() {
//    if(controller != null) controller.postload();
//    
//    log.log(Level.INFO, StringManager.getLocalString("info_mod_load", name));
//  }
//  
//  @Override
//  public String toString() {
//    if(version == null) return name;
//    return String.format("%s %s", name, version);
//  }
//  
//  /**
//   * Creates a new mod using the data file from the given {@link Path}.
//   * The data file must be a modinfo file optionally with some extension that
//   * contains at least a name, and either a dependency list or a map-generator
//   * element but not both.
//   * 
//   * @param path The {@link Path} for the data file to load the mod from.
//   * @return The new mod, or null if path was not a valid modinfo file.
//   */
//  public static Mod createMod(Path path) throws FileNotFoundException, MalformedURLException, InvalidDataFileException {
//    //first see if we can load the file.
//    DataNode root = DataLoader.loadFile(path.toFile());
//    if(root == null) {
//      log.log(Level.WARNING, StringManager.getLocalString("warn_invalid_mod"), path);
//      return null;
//    }
//    
//    //declare the various mod parameters
//    Mod result = new Mod(path.getParent().toUri().toURL());
//    
//    for(String field : root.getValues()) {
//      switch(field) {
//        //field for the name of the mod
//        case NAME_FIELD:
//          if(!result.setName(root.getValue(NAME_FIELD)))
//            log.log(Level.WARNING, StringManager.getLocalString("warn_mod_name_string", path));
//          break;
//          
//        //field for the class of the mods map generator.
//        case WORLD_FIELD:
//          if(!result.addWorld(root.getValue(WORLD_FIELD)))
//            log.log(Level.WARNING, StringManager.getLocalString("warn_invalid_map_generator", path));
//          break;
//          
//        //field for the mod controller
//        case CONTROLLER_FIELD:
//          if(!result.setController(root.getValue(CONTROLLER_FIELD)))
//            log.log(Level.WARNING, StringManager.getLocalString("warn_invalid_mod_controller", path));
//          break;
//          
//        //field for adding files of tile definitions.
//        case TILE_DEFINITION_FIELD:
//          if(!result.addTileDefinitionFiles(root.getValue(TILE_DEFINITION_FIELD)))
//            log.log(Level.WARNING, StringManager.getLocalString("warn_invalid_tile_definitions", path));
//          break;
//        
//        //field for adding rendering information files.
//        case RENDERING_FILE_FIELD:
//          if(!result.addRenderingFiles(root.getValue(RENDERING_FILE_FIELD)))
//            log.log(Level.WARNING, StringManager.getLocalString("warn_invalid_rendering_file", path));
//          break;
//          
//        case OVERWORLD_FIELD:
//          if(!result.addOverworlds(root.getValue(OVERWORLD_FIELD)))
//            log.log(Level.WARNING, StringManager.getLocalString("warn_invalid_overworld", path));
//          break;
//          
//        default:
//          log.log(Level.WARNING, StringManager.getLocalString("warn_unknown_mod_field", path, field));
//      }
//    }
//    
//    //check to make sure the mod is good.
//    if(result.validate()) {
//      log.log(Level.INFO, StringManager.getLocalString("info_mod_created", result.name, path));
//      return result;
//    } else return null;
//  }
//  
//  private static final Logger log = Logger.getLogger(Mod.class.getName());
//  
//  private URLClassLoader loader;
//  
//  private Path location;
//  private List<Universe.World> worlds;
//  private String name;
//  private String version;
//  private ModController controller;
//  private List<String> tdfiles;
//  private List<String> rendfiles;
//  private List<String> overworlds;
//  
//  private Mod(URL path) {
//    tdfiles = new ArrayList<>();
//    rendfiles = new ArrayList<>();
//    overworlds = new ArrayList<>();
//    worlds = new ArrayList<>();
//    
//    loader = new MPUClassLoader(new URL[]{path});
//  }
//  
//  private boolean setName(Object obj) {
//    if(obj instanceof String) {
//      name = (String) obj;
//      return true;
//    } else {
//      return false;
//    }
//  }
//  
//  private boolean addWorld(Object obj) {
//    if(obj instanceof DataNode) {
//      DataNode data = (DataNode) obj;
//      if(!data.isType(WORLD_GENERATOR_FIELD, String.class)) return false;
//      
//      Class<?> gen = null;
//      try {
//        gen = getClass((String) data.getValue(WORLD_GENERATOR_FIELD));
//      } catch(ClassNotFoundException ex) {
//        log.log(Level.SEVERE, StringManager.getLocalString("err_map_gen_not_found", obj), ex);
//        return false;
//      }
//      
//      //check to make sure this is a proper generator
//      if(!MapGenerator.class.isAssignableFrom(gen)) {
//        log.log(Level.SEVERE, StringManager.getLocalString("err_map_gen_wrong_super", obj));
//        return false;
//      }
//      
//      World nworld = new World();
//      nworld.generator = (Class<? extends MapGenerator>) gen;
//      
//      if(data.isType(WORLD_NAME_FIELD, String.class))
//        nworld.name = data.getValue(WORLD_NAME_FIELD);
//      else
//        //give the world an working name.
//        nworld.name = "" + worlds.size();
//      
//      worlds.add(nworld);
//      
//      return true;
//    } else {
//      return false;
//    }
//  }
//    
//  private boolean setController(Object obj) {
//    if(obj instanceof String) {
//      Class<?> cont = null;
//      try {
//        cont = getClass((String) obj);
//      } catch(ClassNotFoundException ex) {
//        log.log(Level.SEVERE, StringManager.getLocalString("err_mod_cont_not_found", obj), ex);
//        return false;
//      }
//      
//      //check to make sure this is a proper controller
//      if(!ModController.class.isAssignableFrom(cont)) {
//        log.log(Level.SEVERE, StringManager.getLocalString("err_mod_cont_wrong_super", obj));
//        return false;
//      }
//      try {
//        controller = (ModController) cont.newInstance();
//      } catch (InstantiationException | IllegalAccessException ex) {
//        Logger.getLogger(Mod.class.getName()).log(Level.SEVERE, StringManager.getLocalString("err_mod_cont_instance", obj), ex);
//      }
//      
//      return true;
//    } else {
//      return false;
//    }
//  }
//  
//  private boolean addTileDefinitionFiles(Object obj) {
//    if(obj instanceof String) {
//      tdfiles.add((String) obj);
//      return true;
//      //TODO: add code for lists of files
//    } else {
//      return false;
//    }
//  }
//  
//  private boolean addRenderingFiles(Object obj) {
//    if(obj instanceof String) {
//      rendfiles.add((String) obj);
//      return true;
//      //TODO: add code for lists of files
//    } else {
//      return false;
//    }
//  }
//  
//  private boolean addOverworlds(Object obj) {
//    if(obj instanceof String) {
//      overworlds.add((String) obj);
//      return true;
//    } else {
//      return false;
//    }
//  }
//}
