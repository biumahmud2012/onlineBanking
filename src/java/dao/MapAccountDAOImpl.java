package dao;

import java.sql.*;
import java.util.*;
import java.math.*;

import exceptions.NotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import models.MapAccount;
import models.UserRole;

/**
 * MapAccountCustomer Data Access Object (DAO). This class contains all database
 * handling that is needed to permanently store and retrieve MapAccountCustomer
 * object instances.
 */
public class MapAccountDAOImpl implements MapAccountDAO {

    @Override
    public MapAccount createValueObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MapAccount getObject(Connection conn, int map_id) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(Connection conn, MapAccount valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List loadAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Connection conn, MapAccount valueObject) throws SQLException {
        String sql = "";
        PreparedStatement pstm = null;
        ResultSet result = null;
        int count = 0;
        try {
            ResultSet rs;
            PreparedStatement ps = conn.prepareStatement("SELECT account_id FROM map_account where account_id=?");

            ps.setInt(1, valueObject.getAccount_id());
            System.out.println(ps);

            rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }

            if (count > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "This Account is Already Mapped...! ");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {

                sql = "INSERT INTO map_account ( account_id, customer_id, "
                        + "nominee_id) VALUES ( ?, (select customer_id from customer where concat(customer_id,'-', username)=?), (select nominee_id from nominee where concat(nominee_id,'-', givenname)=?)) ";
                pstm = conn.prepareStatement(sql);

                pstm.setInt(1, valueObject.getAccount_id());
                pstm.setString(2, valueObject.getCustomer_name());
                pstm.setString(3, valueObject.getNominee_name());

                System.out.println(pstm);
                int rowcount = pstm.executeUpdate();
                if (rowcount >= 1) {
                    System.out.println("Map Account inserted...");
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Account Mapped Successfully ...!",
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
    public void save(Connection conn, MapAccount valueObject) throws NotFoundException, SQLException {
        int count = 0;
        try {
            String sql = "UPDATE map_account SET nominee_id=(select nominee_id from nominee where concat(nominee_id,'-', givenname)=?)  where map_id=?;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, valueObject.getNominee_name());
                ps.setInt(2, valueObject.getMap_id());
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
            Logger.getLogger(MapAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Connection conn, MapAccount valueObject) throws NotFoundException, SQLException {
        String sql = "DELETE FROM map_account WHERE map_id=?;";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, valueObject.getMap_id());
            int i = pst.executeUpdate();
            if (i > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "One row deleted");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to delete data");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MapAccount.class
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
    public List searchMatching(Connection conn, MapAccount valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void singleQuery(Connection conn, PreparedStatement stmt, MapAccount valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
