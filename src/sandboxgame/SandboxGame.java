/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sandboxgame;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import placeholder.map.Region;
import placeholder.render.RawBatch;
import placeholder.render.RenderBatch;
import placeholder.render.vertices.ColorVertex;
import placeholder.util.Settings;

/**
 *
 * @author russell
 */
public class SandboxGame {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws LWJGLException {
    Settings.loadStringBundle("text");
    
    //create the drawing batch
    RawBatch bat = new RawBatch();
    
    bat.rendermode = GL11.GL_LINES;
    
    //draw two faces
    ArrayList<ColorVertex> vertices = new ArrayList<>();
   
    for(int i = 0; i <= Region.REGION_SIZE; i++) {
      //bottom
      vertices.add(new ColorVertex(i, 0, 0, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(i, Region.REGION_SIZE, 0, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(0, i, 0, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(Region.REGION_SIZE, i, 0, Color.GREEN.getRGB()));
      
      //top
      vertices.add(new ColorVertex(i, 0, Region.REGION_SIZE, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(i, Region.REGION_SIZE, Region.REGION_SIZE, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(0, i, Region.REGION_SIZE, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(Region.REGION_SIZE, i, Region.REGION_SIZE, Color.GREEN.getRGB()));
      
      //front
      vertices.add(new ColorVertex(i, 0, 0, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(i, 0, Region.REGION_SIZE, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(0, 0, i, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(Region.REGION_SIZE, 0, i, Color.GREEN.getRGB()));
      
      //back
      vertices.add(new ColorVertex(i, Region.REGION_SIZE, 0, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(i, Region.REGION_SIZE, Region.REGION_SIZE, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(0, Region.REGION_SIZE, i, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(Region.REGION_SIZE, Region.REGION_SIZE, i, Color.GREEN.getRGB()));
      
      //left
      vertices.add(new ColorVertex(0, i, 0, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(0, i, Region.REGION_SIZE, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(0, 0, i, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(0, Region.REGION_SIZE, i, Color.GREEN.getRGB()));
      
      //right
      vertices.add(new ColorVertex(Region.REGION_SIZE, i, 0, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(Region.REGION_SIZE, i, Region.REGION_SIZE, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(Region.REGION_SIZE, 0, i, Color.GREEN.getRGB()));
      vertices.add(new ColorVertex(Region.REGION_SIZE, Region.REGION_SIZE, i, Color.GREEN.getRGB()));
    }
    
    bat.vertices = new ColorVertex[vertices.size()];
    vertices.toArray(bat.vertices);
    RenderBatch renderer = new RenderBatch(bat);
    
    Display.setDisplayMode(new DisplayMode(800, 600));
    Display.create();
    
    
    float x = 0;
    float y = 0;
    float z = 0;
    float delta = .005f;
    while(!Display.isCloseRequested()) {
      if(Keyboard.isKeyDown(Keyboard.KEY_Q)) x += delta;
      if(Keyboard.isKeyDown(Keyboard.KEY_A)) x -= delta;
      if(Keyboard.isKeyDown(Keyboard.KEY_W)) y += delta;
      if(Keyboard.isKeyDown(Keyboard.KEY_S)) y -= delta;
      if(Keyboard.isKeyDown(Keyboard.KEY_E)) z += delta;
      if(Keyboard.isKeyDown(Keyboard.KEY_D)) z -= delta;
      
      GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
      
      GL11.glMatrixMode(GL11.GL_PROJECTION);
      GL11.glLoadIdentity();
      GLU.gluPerspective(60, (float) Display.getWidth() / (float) Display.getHeight(), 1, 10000);
      GL11.glMatrixMode(GL11.GL_MODELVIEW);
      GL11.glLoadIdentity();
      GLU.gluLookAt(x, y, z, 8, 8, 8, 0, 0, 1);
      
      renderer.draw(null);
      
      Display.update();
    }
  }
}
