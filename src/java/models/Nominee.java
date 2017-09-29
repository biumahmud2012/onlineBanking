/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dao.NomineeDAOImpl;
import exceptions.NotFoundException;
import helpers.DBConnectionHelper;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.math.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author C13
 */
@ManagedBean
@SessionScoped
public class Nominee implements Cloneable, Serializable {
private static final long serialVersionUID = 1L;
    private int nominee_id;
    private String nric;
    private String givenname;
     public long image;
    private String address;
    private String gender;
    private String nationality;
    private String relation;
    private Date dob;
    private int nominee_number;
    private Nominee selectedRow;
    public UploadedFile uploadPhoto;

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

    
    
    public int getNominee_number() {
        return nominee_number;
    }

    public void setNominee_number(int nominee_number) {
        this.nominee_number = nominee_number;
    }

    public Nominee getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(Nominee selectedRow) {
        this.selectedRow = selectedRow;
    }

    public int getNominee_id() {
        return nominee_id;
    }

    public void setNominee_id(int nominee_id) {
        this.nominee_id = nominee_id;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getGivenname() {
        return givenname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void addNominee() throws SQLException {

        Connection con = DBConnectionHelper.getConnection();
        NomineeDAOImpl nme = new NomineeDAOImpl();
        boolean res = nme.create(con, this);
        if (res) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Nominee Successfully Inserted...!",
                            "Welcome.....!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Nominee insert Failed....!!!",
                            "Try Again.....!"));
        }

    }

    public void deleteData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        NomineeDAOImpl nom = new NomineeDAOImpl();
        nom.delete(con, this);
    }

    public void updateData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        NomineeDAOImpl nomd = new NomineeDAOImpl();
        nomd.save(con, this);
    }

    private void clear() {
        setGivenname("");
    }

    public List<Nominee> getDetailNominee() {
        List<Nominee> data = new ArrayList<Nominee>();

        String sql = "SELECT * FROM nominee;";
        try {
            Statement st = DBConnectionHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Nominee acb = new Nominee();
                acb.setNominee_number(rs.getRow());
                acb.setNominee_id(rs.getInt("nominee_id"));
                acb.setNric(rs.getString("nric"));
                acb.setGivenname(rs.getString("givenname"));
                acb.setAddress(rs.getString("address"));
                acb.setGender(rs.getString("gender"));
                acb.setNationality(rs.getString("nationality"));
                acb.setDob(rs.getDate("dob"));

                data.add(acb);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return data;
    }

    public void doSetSelectedRow() {
        getNominee_id();
        getNric();
        getGivenname();
        getUploadPhoto();
        getAddress();
        getGender();
        getNationality();
        getDob();
    }

}
