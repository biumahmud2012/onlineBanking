package dao;

import java.sql.*;
import java.util.*;
import java.math.*;

import javax.sql.RowSet;

import exceptions.NotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import models.Account;
import models.UserRole;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public int getLastInsertedRowID(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Account createValueObject() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Account getObject(Connection conn, int account_id) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void load(Connection conn, Account valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List loadAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void create(Connection conn, Account valueObject) throws SQLException {
        String sql = "";
        PreparedStatement pstm = null;
        try {
            sql = "INSERT INTO account ( account_id, pin, amount, "
                    + "account_type_id, bank_branch_id) VALUES ( ?, ?, ?, (select account_type_id from account_type where account_type_name=?), (select bank_branch_id from bank_branch where concat(name,'-',location)=?)) ";
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, valueObject.getAccount_id());
            pstm.setInt(2, valueObject.getPin());
            pstm.setDouble(3, valueObject.getAmount());
            pstm.setString(4, valueObject.getAccount_type_name());
            pstm.setString(5, valueObject.bank_branch_name);

            System.out.println(pstm);
            int rowcount = pstm.executeUpdate();
            if (rowcount >= 1) {
                System.out.println("Account inserted...");
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Account Successfully Inserted...!",
                                "Welcome.....!"));
            } else {
                //System.out.println("PrimaryKey Error when updating DB!");
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Account Failed to Create...!",
                                "Try Again.....!"));
                throw new SQLException("PrimaryKey Error when updating DB!");
            }

        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }

    }

    @Override
    public void save(Connection conn, Account valueObject) throws NotFoundException, SQLException {
        try {
            String sql = "UPDATE account SET pin=?, amount=?, account_type_id=(select account_type_id from account_type where account_type_name=?), bank_branch_id=(select bank_branch_id from bank_branch where concat(name,'-',location)=?)  where account_id=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, valueObject.getPin());
            ps.setDouble(2, valueObject.getAmount());
            ps.setString(3, valueObject.getAccount_type_name());
            ps.setString(4, valueObject.getBank_branch_name());
            
            ps.setInt(5, valueObject.getAccount_id());

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
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void delete(Connection conn, Account valueObject) throws NotFoundException, SQLException {
        String sql = "DELETE FROM account WHERE account_id=?;";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, valueObject.getAccount_id());
            int i = pst.executeUpdate();
            if (i > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "One row deleted");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to delete data");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Account.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int countAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List searchMatching(Connection conn, Account valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int databaseUpdate(Connection conn, PreparedStatement pstm) throws SQLException {
        int result = pstm.executeUpdate();

        return result;

    }

    @Override
    public void singleQuery(Connection conn, PreparedStatement stmt, Account valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
