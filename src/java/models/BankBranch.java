/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dao.BankBranchDAOImpl;
import dao.UserRoleDAOImpl;
import exceptions.NotFoundException;
import helpers.DBConnectionHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BankBranch implements Cloneable, Serializable {

    private int bank_branch_id;
    private String name;
    private String location;
    private String description;
    private int bankBranchNumber;
    private BankBranch SelectedRow;

    public int getBankBranchNumber() {
        return bankBranchNumber;
    }

    public void setBankBranchNumber(int bankBranchNumber) {
        this.bankBranchNumber = bankBranchNumber;
    }

    public BankBranch getSelectedRow() {
        return SelectedRow;
    }

    public void setSelectedRow(BankBranch SelectedRow) {
        this.SelectedRow = SelectedRow;
    }
    

    
    public int getBank_branch_id() {
        return bank_branch_id;
    }

    public void setBank_branch_id(int bank_branch_id) {
        this.bank_branch_id = bank_branch_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
     public void addUserRole() throws SQLException {

        Connection con = DBConnectionHelper.getConnection();
        BankBranchDAOImpl bb = new BankBranchDAOImpl();
        bb.create(con, this);
    }

    public void deleteData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        BankBranchDAOImpl ur = new BankBranchDAOImpl();
        ur.delete(con, this);
    }

    public void updateData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        BankBranchDAOImpl bankbr = new BankBranchDAOImpl();
        bankbr.save(con, this);
    }

    private void clear() {
        setLocation("");
    }

    public void addBankBranch() throws SQLException {

        Connection con = DBConnectionHelper.getConnection();
        BankBranchDAOImpl bb = new BankBranchDAOImpl();
        bb.create(con, this);
    }
     public List<BankBranch> getDetailBankBranch() {
        List<BankBranch> data = new ArrayList<BankBranch>();

        String sql = "SELECT * FROM bank_branch;";
        try {
            Statement st = DBConnectionHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                BankBranch sbb = new BankBranch();
                sbb.setBankBranchNumber(rs.getRow());
                sbb.setBank_branch_id(rs.getInt("bank_branch_id"));
                sbb.setName(rs.getString("name"));
                sbb.setLocation(rs.getString("location"));
                sbb.setDescription(rs.getString("description"));
                data.add(sbb);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return data;
    }
     public void doSetSelectedRow() {
        getBank_branch_id();
        getName();
        getLocation();
        getDescription();
    }
}
