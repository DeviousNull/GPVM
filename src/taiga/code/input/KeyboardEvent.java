package taiga.code.input;

/**
 * A simple class to hold the details of an event generated by the keyboard.
 * @author russell
 */
public class KeyboardEvent {
  /**
   * The index of the key that caused the event.  This value may not be accurate
   * to the current key board layout and the {@link KeyboardEvent#character}
   * field should be used to get the actual character pressed.
   */
  public final int key;
  
  /**
   * The character pressed for the event.
   */
  public final char character;
  
  /**
   * The time in nanoseconds for when this event occurred.  This value does not
   * have a defined start time and is only intended for measuring durations.
   */
  public final long nanoseconds;
  
  /**
   * Whether the key is pressed for this event.
   */
  public final boolean state;
  
  /**
   * Whether this event was generated by the operating system as a repeating
   * event from holding down a key.
   */
  public final boolean repeat;
  
  /**
   * Whether or not this event has been consumed by a listener.  Normally
   * setting this to true will prevent this event from propagating further, but
   * that is dependent on the source of the event.
   */
  public boolean consumed;

  public KeyboardEvent(int key, char character, long nanoseconds, boolean state, boolean repeat) {
    this.key = key;
    this.character = character;
    this.nanoseconds = nanoseconds;
    this.state = state;
    this.repeat = repeat;
    
    consumed = false;
  }
}
