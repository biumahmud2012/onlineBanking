/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dao.UserRoleDAOImpl;
import dao.UserRoleMapDAOImpl;
import exceptions.NotFoundException;
import helpers.DBConnectionHelper;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class UserRoleMap {

    private int user_role_map_id;
    private String customer_id;
    private String user_role_id;
    private int userRoleMap_number;
    private UserRoleMap selectedRow;
    

    public int getUser_role_map_id() {
        return user_role_map_id;
    }

    public int getUserRoleMap_number() {
        return userRoleMap_number;
    }

    public void setUserRoleMap_number(int userRoleMap_number) {
        this.userRoleMap_number = userRoleMap_number;
    }

    public UserRoleMap getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(UserRoleMap selectedRow) {
        this.selectedRow = selectedRow;
    }

    
    public void setUser_role_map_id(int user_role_map_id) {
        this.user_role_map_id = user_role_map_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(String user_role_id) {
        this.user_role_id = user_role_id;
    }

   

    public List<SelectItem> addUserRoleID() {
        List<SelectItem> userRoleName = new ArrayList<SelectItem>();
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String myQuery = "select role_name from user_role";

            rs = st.executeQuery(myQuery);
            while (rs.next()) {
                userRoleName.add(new SelectItem(rs.getString("role_name")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userRoleName;
    }

    
    
    public void addUserRoleMap() throws SQLException {

        Connection con = DBConnectionHelper.getConnection();
        UserRoleMapDAOImpl urm = new UserRoleMapDAOImpl();
        urm.create(con, this);
    }
    
    public void deleteData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        UserRoleMapDAOImpl ur = new UserRoleMapDAOImpl();
        ur.delete(con, this);
    }

    public void updateData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        UserRoleMapDAOImpl urdm = new UserRoleMapDAOImpl();
        urdm.save(con, this);
    }

    private void clear() {
        setUser_role_map_id(user_role_map_id);
    }
    
    public List<UserRoleMap> getDetailUserRoleMap() {
        List<UserRoleMap> data = new ArrayList<UserRoleMap>();

        String sql = "SELECT user_role_map.user_role_map_id, concat(user_role_map.customer_id,'-', customer.username) as name,\n" +
"       concat(user_role_map.user_role_id,'-', user_role.role_name) as role\n" +
"  FROM    (   onlinebanking.user_role_map user_role_map\n" +
"           INNER JOIN\n" +
"              onlinebanking.customer customer\n" +
"           ON (user_role_map.customer_id = customer.customer_id))\n" +
"       INNER JOIN\n" +
"          onlinebanking.user_role user_role\n" +
"       ON (user_role_map.user_role_id = user_role.user_role_id)";
                
//                "SELECT * FROM user_role_map;";
        try {
            Statement st = DBConnectionHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println(st);
            while (rs.next()) {
                UserRoleMap acb = new UserRoleMap();
                acb.setUserRoleMap_number(rs.getRow());
                acb.setUser_role_map_id(rs.getInt("user_role_map_id"));
                acb.setCustomer_id(rs.getString("name"));
                acb.setUser_role_id(rs.getString("role"));
                data.add(acb);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return data;
    }

    public void doSetSelectedRow() {
        getUser_role_map_id();
        getCustomer_id();
        getUser_role_id();
    }
}
