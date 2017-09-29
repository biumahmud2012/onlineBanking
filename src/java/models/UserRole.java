package models;


import dao.UserRoleDAOImpl;
import exceptions.NotFoundException;
import helpers.DBConnectionHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
public class UserRole implements Cloneable, Serializable {

    private int user_role_id;
    private String role_name;
    private boolean role_status;
    private int role_number;
    private UserRole selectedRow;

    public int getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public boolean getRole_status() {
        return role_status;
    }

    public void setRole_status(boolean role_status) {
        this.role_status = role_status;
    }

    public int getRole_number() {
        return role_number;
    }

    public void setRole_number(int role_number) {
        this.role_number = role_number;
    }

    public UserRole getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(UserRole selectedRow) {
        this.selectedRow = selectedRow;
    }

    //methods.....
    public void addUserRole() throws SQLException {

        Connection con = DBConnectionHelper.getConnection();
        UserRoleDAOImpl bb = new UserRoleDAOImpl();
        bb.create(con, this);
    }

    public void deleteData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        UserRoleDAOImpl ur = new UserRoleDAOImpl();
        ur.delete(con, this);
    }

    public void updateData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        UserRoleDAOImpl urdm = new UserRoleDAOImpl();
        urdm.save(con, this);
    }

    private void clear() {
        setRole_name("");
    }
    
    public List<UserRole> getDetailUserRole() {
        List<UserRole> data = new ArrayList<UserRole>();

        String sql = "SELECT * FROM user_role;";
        try {
            Statement st = DBConnectionHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                UserRole acb = new UserRole();
                acb.setRole_number(rs.getRow());
                acb.setUser_role_id(rs.getInt("user_role_id"));
                acb.setRole_name(rs.getString("role_name"));
                acb.setRole_status(rs.getBoolean("role_status"));
                data.add(acb);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return data;
    }

    public void doSetSelectedRow() {
        getUser_role_id();
        getRole_name();
        getRole_status();
    }
}
