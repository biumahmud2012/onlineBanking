package dao;

import java.sql.*;
import java.util.*;
import java.math.*;

import exceptions.NotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import models.UserRole;

import models.UserRoleMap;

public class UserRoleMapDAOImpl implements UserRoleMapDAO {

    @Override
    public UserRoleMap createValueObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserRoleMap getObject(Connection conn, int user_role_map_id) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(Connection conn, UserRoleMap valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List loadAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Connection conn, UserRoleMap valueObject) throws SQLException {
        String sql = "";
        PreparedStatement pstm = null;
        ResultSet result = null;
        int count = 0;
        try {

            ResultSet rs;
            PreparedStatement ps = conn.prepareStatement("SELECT user_role_map.customer_id\n"
                    + "  FROM    onlinebanking.user_role_map user_role_map\n"
                    + "       INNER JOIN\n"
                    + "          onlinebanking.customer customer\n"
                    + "       ON (user_role_map.customer_id = customer.customer_id)\n"
                    + "where concat(customer.customer_id, '-', customer.username)=?");

            ps.setString(1, valueObject.getCustomer_id());
            System.out.println(ps);

            rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }

            if (count > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Duplicate User Role Name not allowed here");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {

                sql = "INSERT INTO user_role_map ( customer_id, "
                        + "user_role_id) VALUES ( (select customer_id from customer where concat(customer_id,'-', username)=?), (select user_role_id from user_role where role_name=?)) ";
                pstm = conn.prepareStatement(sql);

                pstm.setString(1, valueObject.getCustomer_id());
                pstm.setString(2, valueObject.getUser_role_id());

                System.out.println(pstm);
                int rowcount = pstm.executeUpdate();
                if (rowcount >= 1) {
                    System.out.println("User Role Map inserted...");
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "User Role Map Successfully Inserted...!",
                                    "Welcome.....!"));

                } else {

                    //System.out.println("PrimaryKey Error when updating DB!");
                    throw new SQLException("PrimaryKey Error when updating DB!");
                }
            }
        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }

    }

    @Override
    public void save(Connection conn, UserRoleMap valueObject) throws NotFoundException, SQLException {
        int count = 0;
        try {
                String sql = "UPDATE user_role_map SET user_role_id=(select user_role_id from user_role where role_name=?) where user_role_map_id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, valueObject.getUser_role_id());
                ps.setInt(2, valueObject.getUser_role_map_id());

                int i = ps.executeUpdate();
                System.out.println(ps);
                if (i > 0) {
//                    clear();
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "Data update successfully");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to update data");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserRoleMap.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Connection conn, UserRoleMap valueObject) throws NotFoundException, SQLException {
        String sql = "DELETE FROM user_role_map WHERE user_role_map_id=?;";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, valueObject.getUser_role_map_id());
            int i = pst.executeUpdate();
            if (i > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "One row deleted");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to delete data");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRoleMap.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List searchMatching(Connection conn, UserRoleMap valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void singleQuery(Connection conn, PreparedStatement stmt, UserRoleMap valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
