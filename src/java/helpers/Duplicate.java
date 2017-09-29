/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.List;

/**
 *
 * @author MASHUK
 */
public class Duplicate<T> {

  public Duplicate() {
  }

  public boolean isDuplicate(T value, List<T> list) {
    boolean b = false;
    for (T object : list) {
      if (value.equals(object)) {
        b = true;
        break;
      }
    }
    return b;
  }
  
}
