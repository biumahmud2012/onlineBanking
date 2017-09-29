package models;

import dao.LoginDAOImpl;
import helpers.DBConnectionHelper;
import helpers.LoginUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    private String uname, roleName, name;
    private String password;
    private boolean showLogin = true;
    private boolean showLogout;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowLogin() {
        return showLogin;
    }

    public void setShowLogin(boolean showLogin) {
        this.showLogin = showLogin;
    }

    public boolean isShowLogout() {
        return showLogout;
    }

    public void setShowLogout(boolean showLogout) {
        this.showLogout = showLogout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String loginProject() throws SQLException {
        Connection con = DBConnectionHelper.getConnection();
        LoginDAOImpl lg = new LoginDAOImpl();
        String result = lg.loginMethod(con, this);
        return result;
    }
    public void logOut() {
    HttpSession session = LoginUtil.getSession();
    session.invalidate();
    setShowLogin(true);
    setShowLogout(false);
   // return "logout";
  }

  void clear() {
    setUname(null);
    setPassword(null);
  }
}
