package gpvm.map;

/**
 * Represents a single unit of the game map.  Each tile has some tile type
 * that corresponds to a list of available tiles, along with an optional set of 
 * data fields.  A damage value is also stored in each tile.
 * 
 * @author russell
 */
public final class Tile {
  public long type;
  public long damage;
  public TileData data;
}