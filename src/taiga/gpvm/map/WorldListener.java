/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package taiga.gpvm.map;

/**
 *
 * @author russell
 */
public interface WorldListener {
  public void regionLoaded(Region reg);
  public void regionUnloaded(Region reg);
}