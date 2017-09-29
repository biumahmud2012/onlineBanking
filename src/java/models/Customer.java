package models;

import dao.CustomerDAOImpl;
import dao.UserRoleDAOImpl;
import exceptions.NotFoundException;
import helpers.DBConnectionHelper;
import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 * Customer Value Object. This class is value object representing database table
 * customer This class is intented to be used together with associated Dao
 * object.
 */
@ManagedBean
@SessionScoped

public class Customer implements Cloneable, Serializable {

    /**
     * Persistent Instance variables. This data is directly mapped to the
     * columns of database table.
     */
    private static final long serialVersionUID = 1L;
    private int customer_id;
    private String nric;
    private String username;
    private String password;
    private String givenname;
     public long image;
    private String address;
    private String gender;
    private String nationality;
    private Date dob;
    private Date date_of_join;
    private boolean approval_officer;
    private boolean approval_manager;
    private int customer_number;
    private Customer selectedRow;
    public UploadedFile uploadPhoto;

    /**
     * Constructors. DaoGen generates two constructors by default. The first one
     * takes no arguments and provides the most simple way to create object
     * instance. The another one takes one argument, which is the primary key of
     * the corresponding table.
     */
    /**
     * Get- and Set-methods for persistent variables. The default behaviour does
     * not make any checks against malformed data, so these might require some
     * manual additions.
     */
    
    public long getImage() {
        return image;
    }

    public void setImage(long image) {
        this.image = image;
    }

    public UploadedFile getUploadPhoto() {
        return uploadPhoto;
    }

    public void setUploadPhoto(UploadedFile uploadPhoto) {
        this.uploadPhoto = uploadPhoto;
    }

    public int getCustomer_id() {
        return this.customer_id;
    }

    public void setCustomer_id(int customer_idIn) {
        this.customer_id = customer_idIn;
    }

    public String getNric() {
        return this.nric;
    }

    public void setNric(String nricIn) {
        this.nric = nricIn;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String usernameIn) {
        this.username = usernameIn;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String passwordIn) {
        this.password = passwordIn;
    }

    public String getGivenname() {
        return this.givenname;
    }

    public void setGivenname(String givennameIn) {
        this.givenname = givennameIn;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String addressIn) {
        this.address = addressIn;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String genderIn) {
        this.gender = genderIn;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationalityIn) {
        this.nationality = nationalityIn;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dobIn) {
        this.dob = dobIn;
    }

    public Date getDate_of_join() {
        return this.date_of_join;
    }

    public void setDate_of_join(Date date_of_joinIn) {
        this.date_of_join = date_of_joinIn;
    }

    public boolean isApproval_officer() {
        return approval_officer;
    }

    public void setApproval_officer(boolean approval_officer) {
        this.approval_officer = approval_officer;
    }

    public boolean isApproval_manager() {
        return approval_manager;
    }

    public void setApproval_manager(boolean approval_manager) {
        this.approval_manager = approval_manager;
    }

    

    public int getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(int customer_number) {
        this.customer_number = customer_number;
    }

    public Customer getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(Customer selectedRow) {
        this.selectedRow = selectedRow;
    }

  
   

    public void addCustomer() throws SQLException {
        Connection conn = DBConnectionHelper.getConnection();
        CustomerDAOImpl cst = new CustomerDAOImpl();
        cst.create(conn, this);
    }

    public void deleteData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        CustomerDAOImpl ur = new CustomerDAOImpl();
        ur.delete(con, this);
    }

    public void updateData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        CustomerDAOImpl urdm = new CustomerDAOImpl();
        urdm.save(con, this);
    }

    private void clear() {
        setUsername("");
    }

    public List<Customer> getDetailCustomer() {
        List<Customer> data = new ArrayList<Customer>();

        String sql = "SELECT * FROM customer;";
        try {
            Statement st = DBConnectionHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Customer acc = new Customer();
                acc.setCustomer_number(rs.getRow());
                acc.setCustomer_id(rs.getInt("customer_id"));
                acc.setNric(rs.getString("nric"));
                acc.setUsername(rs.getString("username"));
                acc.setPassword(rs.getString("password"));
                acc.setGivenname(rs.getString("givenname"));
                acc.setAddress(rs.getString("address"));
                acc.setGender(rs.getString("gender"));
                acc.setNationality(rs.getString("nationality"));
                acc.setDob(rs.getDate("dob"));
                acc.setDate_of_join(rs.getDate("date_of_join"));
                acc.setApproval_officer(rs.getBoolean("approval_officer"));
                acc.setApproval_manager(rs.getBoolean("approval_manager"));

                data.add(acc);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return data;
    }

    public void doSetSelectedRow() {
        getCustomer_id();
        getNric();
        getUsername();
        getPassword();
        getGivenname();
        getUploadPhoto();
        getAddress();
        getGender();
        getNationality();
        getDob();
        getDate_of_join();
        isApproval_officer();
        isApproval_manager();
    }
}
