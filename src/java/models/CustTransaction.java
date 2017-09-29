package models;

import dao.CustTransactionDAOImpl;
import exceptions.NotFoundException;
import helpers.DBConnectionHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class CustTransaction implements Cloneable, Serializable {

    private int transaction_id;
    private int account_id;
    private int account_id_to;
    private String transaction_type;
    private double transaction_amount;
    private Date transaction_time;
    private double currentBallance;
    private int transaction_number;
    private CustTransaction selectedRow;

    private int acCustID;
    private String acCustName;

    public CustTransaction() {

    }

    public CustTransaction(int transaction_idIn) {

        this.transaction_id = transaction_idIn;
    }

    public int getTransaction_number() {
        return transaction_number;
    }

    public void setTransaction_number(int transaction_number) {
        this.transaction_number = transaction_number;
    }

    public CustTransaction getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(CustTransaction selectedRow) {
        this.selectedRow = selectedRow;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getAccount_id_to() {
        return account_id_to;
    }

    public void setAccount_id_to(int account_id_to) {
        this.account_id_to = account_id_to;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public double getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(double transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public Date getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(Date transaction_time) {
        this.transaction_time = transaction_time;
    }

    public double getCurrentBallance() {
        return currentBallance;
    }

    public void setCurrentBallance(double currentBallance) {
        this.currentBallance = currentBallance;
    }

    public int getAcCustID() {
        return acCustID;
    }

    public void setAcCustID(int acCustID) {
        this.acCustID = acCustID;
    }

    public String getAcCustName() {
        return acCustName;
    }

    public void setAcCustName(String acCustName) {
        this.acCustName = acCustName;
    }

    public synchronized void addTransaction() throws SQLException {
        Connection con = DBConnectionHelper.getConnection();
        CustTransactionDAOImpl ct = new CustTransactionDAOImpl();
        if (!transaction_type.equals("deposite")) {
            transaction_amount *= -1;
        }
        ct.create(con, this);
    }

    public synchronized void currentBalance() throws SQLException {
        Connection con = DBConnectionHelper.getConnection();
        CustTransactionDAOImpl ct = new CustTransactionDAOImpl();
        currentBallance = ct.curBal(con, this);
    }

    public void deleteData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        CustTransactionDAOImpl ctd = new CustTransactionDAOImpl();
        ctd.delete(con, this);
    }

    public void updateData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        CustTransactionDAOImpl urdm = new CustTransactionDAOImpl();
        if (!transaction_type.equals("deposite")) {
            transaction_amount *= -1;
        }
        urdm.save(con, this);
    }

    public List<CustTransaction> getDetailCustTransaction() {
        List<CustTransaction> data = new ArrayList<CustTransaction>();

        String sql = "SELECT * FROM customer_transaction";
        try {
            Statement st = DBConnectionHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                CustTransaction custtr = new CustTransaction();
                custtr.setTransaction_number(rs.getRow());
                custtr.setTransaction_id(rs.getInt("transaction_id"));
                custtr.setAccount_id(rs.getInt("account_id"));
                custtr.setAccount_id_to(rs.getInt("account_id_to"));
                custtr.setTransaction_type(rs.getString("transaction_type"));
                custtr.setTransaction_amount(rs.getDouble("transaction_amount"));
                custtr.setTransaction_time(rs.getDate("transaction_time"));
                data.add(custtr);
            }
        } catch (SQLException ex) {
            ex.getMessage();
            ex.printStackTrace();
        }
        return data;
    }

    public void doSetSelectedRow() {
        getTransaction_id();
        getAccount_id();
        getAccount_id_to();
        getTransaction_amount();
        getTransaction_type();

        getTransaction_time();

    }

    public List<SelectItem> addCustomerAc() {
        List<SelectItem> customerAc = new ArrayList<SelectItem>();
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String sql = "select account_id from map_account order by account_id";
            rs = st.executeQuery(sql);
            System.out.println(st);
            while (rs.next()) {
                customerAc.add(new SelectItem(rs.getString("account_id")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customerAc;
    }

    public void addCustName() {
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String myQuery = "SELECT map_account.customer_id, customer.username\n"
                    + "  FROM    onlinebanking.map_account map_account\n"
                    + "       INNER JOIN\n"
                    + "          onlinebanking.customer customer\n"
                    + "       ON (map_account.customer_id = customer.customer_id) where account_id='" + getAccount_id() + "'";

            rs = st.executeQuery(myQuery);
            System.out.println(st);
            while (rs.next()) {
                setAcCustID(rs.getInt("customer_id"));
                setAcCustName(rs.getString("username"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
