package dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.math.*;

import exceptions.NotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import models.CustTransaction;
import models.UserRole;

/**
 * Transaction Data Access Object (DAO). This class contains all database
 * handling that is needed to permanently store and retrieve Transaction object
 * instances.
 */
public class CustTransactionDAOImpl implements CustTransactionDAO {

    @Override
    public CustTransaction createValueObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CustTransaction getObject(Connection conn, int transaction_id) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(Connection conn, CustTransaction valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List loadAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void create(Connection conn, CustTransaction valueObject) throws SQLException {
        String sql = "";
        PreparedStatement pstm = null;
        ResultSet result = null;

        try {
            sql = "INSERT INTO customer_transaction ( account_id, account_id_to, "
                    + "transaction_type, transaction_amount, transaction_time) VALUES ( ?, ?, ?, ?, now() ) ";
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, valueObject.getAccount_id());
            pstm.setInt(2, valueObject.getAccount_id_to());
            pstm.setString(3, valueObject.getTransaction_type());
            pstm.setDouble(4, valueObject.getTransaction_amount());

            System.out.println(pstm);
            int rowcount = pstm.executeUpdate();
            if (rowcount >= 1) {
                System.out.println("Transaction inserted...");
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Transaction Completed Successfully ..!",
                                "Welcome.....!"));

            } else {

                //System.out.println("PrimaryKey Error when updating DB!");
                throw new SQLException("PrimaryKey Error when updating DB!");
            }

        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }

    }

    public synchronized double curBal(Connection conn, CustTransaction valueObject) throws SQLException {
        String sql = "";
        PreparedStatement pstm = null;
        ResultSet result = null;

        try {
            sql = "select sum(transaction_amount) as total from customer_transaction where account_id=? ";
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, valueObject.getAccount_id());

            System.out.println(pstm);
            result = pstm.executeQuery();
            while (result.next()) {
                double total = result.getDouble("total");
                System.out.println(total);
                return total;
            }

        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }
        return 0;
    }

    @Override
    public void save(Connection conn, CustTransaction valueObject) throws NotFoundException, SQLException {
        int count = 0;
        try {
            String sql = "UPDATE customer_transaction SET account_id=?, account_id_to=?, transaction_type=?, transaction_amount=?, transaction_time=now()  where transaction_id=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, valueObject.getAccount_id());
            ps.setInt(2, valueObject.getAccount_id_to());
            ps.setString(3, valueObject.getTransaction_type());
            ps.setDouble(4, valueObject.getTransaction_amount());

            ps.setInt(5, valueObject.getTransaction_id());

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
            ex.printStackTrace();
            Logger.getLogger(CustTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Connection conn, CustTransaction valueObject) throws NotFoundException, SQLException {
        String sql = "DELETE FROM customer_transaction WHERE transaction_id=?;";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, valueObject.getTransaction_id());
            int i = pst.executeUpdate();
            if (i > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "One row deleted");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to delete data");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CustTransaction.class
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
    public List searchMatching(Connection conn, CustTransaction valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void singleQuery(Connection conn, PreparedStatement stmt, CustTransaction valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
