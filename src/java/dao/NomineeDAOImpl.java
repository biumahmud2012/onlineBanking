/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.NotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import models.Nominee;
import models.UserRole;

/**
 *
 * @author User
 */
public class NomineeDAOImpl implements NomineeDAO {

    @Override
    public Nominee createValueObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Nominee getObject(Connection conn, int nominee_id) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(Connection conn, Nominee valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List loadAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Connection conn, Nominee valueObject) throws SQLException {
        String sql = "";
        PreparedStatement pstm = null;
        ResultSet result = null;
        System.out.println("Inserting Image");
        if (valueObject.uploadPhoto != null) {

        try {
            System.out.println(valueObject.uploadPhoto.getFileName());
                InputStream streamFile = null;
                try {
                    streamFile = valueObject.uploadPhoto.getInputstream();
                } catch (IOException ex) {
                    System.out.println("Error in login() -->" + ex.getMessage());
                    Logger.getLogger(NomineeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            sql = "INSERT INTO nominee ( nric, givenname, image, address, gender, "
                    + "nationality, dob) VALUES (?, ?, ?, ?, ?, ?, ?) ";
            pstm = conn.prepareStatement(sql);

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
            String dob = sdf.format(valueObject.getDob());

            pstm.setString(1, valueObject.getNric());
            pstm.setString(2, valueObject.getGivenname());
            pstm.setBinaryStream(3, streamFile, valueObject.uploadPhoto.getSize());
            pstm.setString(4, valueObject.getAddress());
            pstm.setString(5, valueObject.getGender());
            pstm.setString(6, valueObject.getNationality());
            pstm.setString(7, dob);

            System.out.println(pstm);
            int rowcount = pstm.executeUpdate();
            if (rowcount >= 1) {
                System.out.println("Nominee inserted....");
                return true;
            } else {
                //System.out.println("PrimaryKey Error when updating DB!");
                throw new SQLException("PrimaryKey Error when updating DB!");

            }

        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }
        } else {
            FacesMessage msg = new FacesMessage("Please select image!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return true;
    }

    @Override
    public void save(Connection conn, Nominee valueObject) throws NotFoundException, SQLException {

        try {
            String sql = "UPDATE nominee SET givenname=?, address=?, gender=?, nationality=?, dob=?  where nominee_id=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
            String dob = sdf.format(valueObject.getDob());
            
            ps.setString(1, valueObject.getGivenname());
            ps.setString(2, valueObject.getAddress());
            ps.setString(3, valueObject.getGender());
            ps.setString(4, valueObject.getNationality());
            ps.setString(5, dob);
            ps.setInt(6, valueObject.getNominee_id());
            
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
            Logger.getLogger(Nominee.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void delete(Connection conn, Nominee valueObject) throws NotFoundException, SQLException {
        String sql = "DELETE FROM nomineee WHERE givenname=?;";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, valueObject.getGivenname());
            int i = pst.executeUpdate();
            if (i > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "One row deleted");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to delete data");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Nominee.class
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
    public List searchMatching(Connection conn, Nominee valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void singleQuery(Connection conn, PreparedStatement stmt, Nominee valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
