/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mahbub
 */
public class LoginUtil {

  public static HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }

  public static HttpServletRequest getRequest() {
    return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }

  public static String getUserName() {
    HttpSession session = getSession();
    if (session != null) {
      return session.getAttribute("uname").toString();
    } else {
      return null;
    }
    
  }

  public static String getUserId() {
    HttpSession session = getSession();
    if (session != null) {
      return (String) session.getAttribute("customer_id");
    } else {
      return null;
    }
  }
  public static String getRoleName(){
    String roleName = null;
    HttpSession session = getSession();
    if (session != null) {
      roleName = session.getAttribute("roleName").toString();
    } 
    return roleName;
  }
}
