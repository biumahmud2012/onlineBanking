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

public class UserRoleDAOImpl implements UserRoleDAO {

    @Override
    public UserRole createValueObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserRole getObject(Connection conn, int user_role_id) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(Connection conn, UserRole valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List loadAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Connection conn, UserRole valueObject) throws SQLException {
        PreparedStatement pstm = null;
        int count = 0;
        try {
            ResultSet rs;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_role where lower(role_name)=lower(?)");

            ps.setString(1, valueObject.getRole_name());
            System.out.println(ps);

            rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }

            if (count > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Duplicate User Role Name not allowed here");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                String sql = "INSERT INTO user_role ( role_name, role_status) VALUES (?, ?)";
                pstm = conn.prepareStatement(sql);

                pstm.setString(1, valueObject.getRole_name());
                pstm.setBoolean(2, valueObject.getRole_status());

                int rowcount = pstm.executeUpdate();
                if (rowcount >= 1) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "User Role Inserted Successfully...!!!",
                                    "Welcome...!"));
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
    public void save(Connection conn, UserRole valueObject) throws NotFoundException, SQLException {

        try{
                String sql = "UPDATE user_role SET role_status=?  where user_role_id=?;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setBoolean(1, valueObject.getRole_status());
                ps.setInt(2, valueObject.getUser_role_id());
                int i = ps.executeUpdate();
                if (i > 0) {
//                    clear();
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "Data update successfully");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to update data");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRole.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Connection conn, UserRole valueObject) throws NotFoundException, SQLException {
        String sql = "DELETE FROM user_role WHERE role_name=?;";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, valueObject.getRole_name());
            int i = pst.executeUpdate();
            if (i > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "One row deleted");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to delete data");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRole.class
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
    public List searchMatching(Connection conn, UserRole valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void singleQuery(Connection conn, PreparedStatement stmt, UserRole valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
