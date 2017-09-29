package dao;

import exceptions.NotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import models.Customer;

/**
 * Customer Data Access Object (DAO). This class contains all database handling
 * that is needed to permanently store and retrieve Customer object instances.
 */
public class CustomerDAOImpl implements CustomerDAO {

    public void create(Connection conn, Customer valueObject) throws SQLException {

        String sql = "";
        PreparedStatement pstm = null;
        ResultSet result = null;
        int count = 0;
        System.out.println("Inserting Image");
        if (valueObject.uploadPhoto != null) {
        try {
            System.out.println(valueObject.uploadPhoto.getFileName());
                InputStream streamFile = null;
                try {
                    streamFile = valueObject.uploadPhoto.getInputstream();
                } catch (IOException ex) {
                    System.out.println("Error in login() -->" + ex.getMessage());
                    Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            ResultSet rs;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM customer where lower(username)=lower(?)");

            ps.setString(1, valueObject.getUsername());
            System.out.println(ps);

            rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }

            if (count > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Duplicate User Name not allowed...!!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                sql = "INSERT INTO customer ( nric, username, password, "
                        + "givenname, image, address, gender, "
                        + "nationality, dob, date_of_join) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
                pstm = conn.prepareStatement(sql);

                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                String dob = sdf.format(valueObject.getDob());
                String doj = sdf.format(valueObject.getDate_of_join());

                pstm.setString(1, valueObject.getNric());
                pstm.setString(2, valueObject.getUsername());
                pstm.setString(3, valueObject.getPassword());
                pstm.setString(4, valueObject.getGivenname());
                pstm.setBinaryStream(5, streamFile, valueObject.uploadPhoto.getSize());
                pstm.setString(6, valueObject.getAddress());
                pstm.setString(7, valueObject.getGender());
                pstm.setString(8, valueObject.getNationality());
                pstm.setString(9, dob);
                pstm.setString(10, doj);
                System.out.println(pstm);
                int rowcount = pstm.executeUpdate();
                if (rowcount >= 1) {
                    System.out.println("Customer inserted....");
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Customer Successfully Inserted...!",
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
} else {
            FacesMessage msg = new FacesMessage("Please select image!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * save-method. This method will save the current state of valueObject to
     * database. Save can not be used to create new instances in database, so
     * upper layer must make sure that the primary-key is correctly specified.
     * Primary-key will indicate which instance is going to be updated in
     * database. If save can not find matching row, NotFoundException will be
     * thrown.
     *
     * @param conn This method requires working database connection.
     * @param valueObject This parameter contains the class instance to be
     * saved. Primary-key field must be set for this to work properly.
     */
    public void save(Connection conn, Customer valueObject)
            throws NotFoundException, SQLException {

        int count = 0;
        try {
            String sql = "UPDATE customer SET password = ?, "
                    + "givenname = ?, address = ?, gender = ?, "
                    + "nationality = ?, dob = ?, date_of_join = ?, "
                    + "approval_officer = ?, approval_manager = ? WHERE customer_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            String dob = sdf.format(valueObject.getDob());
            String doj = sdf.format(valueObject.getDate_of_join());

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, valueObject.getPassword());
            stmt.setString(2, valueObject.getGivenname());
            stmt.setString(3, valueObject.getAddress());
            stmt.setString(4, valueObject.getGender());
            stmt.setString(5, valueObject.getNationality());
            stmt.setString(6, dob);
            stmt.setString(7, doj);
            stmt.setBoolean(8, valueObject.isApproval_officer());
            stmt.setBoolean(9, valueObject.isApproval_manager());

            stmt.setInt(10, valueObject.getCustomer_id());

            int i = stmt.executeUpdate();
            if (i > 0) {
//                    clear();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "Data update successfully");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to update data");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * delete-method. This method will remove the information from database as
     * identified by by primary-key in supplied valueObject. Once valueObject
     * has been deleted it can not be restored by calling save. Restoring can
     * only be done using create method but if database is using automatic
     * surrogate-keys, the resulting object will have different primary-key than
     * what it was in the deleted object. If delete can not find matching row,
     * NotFoundException will be thrown.
     *
     * @param conn This method requires working database connection.
     * @param valueObject This parameter contains the class instance to be
     * deleted. Primary-key field must be set for this to work properly.
     */
    public void delete(Connection conn, Customer valueObject)
            throws NotFoundException, SQLException {

        String sql = "DELETE FROM customer WHERE customer_id = ?";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, valueObject.getCustomer_id());

            int i = stmt.executeUpdate();
            System.out.println(stmt);
            if (i > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "One row deleted");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to delete data");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public String getAuthor() {
        return "Author A.T.M. HASAN";
    }

    @Override
    public Customer createValueObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Customer getObject(Connection conn, int customer_id) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(Connection conn, Customer valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List loadAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public List searchMatching(Connection conn, Customer valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void singleQuery(Connection conn, PreparedStatement stmt, Customer valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
