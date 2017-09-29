package models;

import dao.AccountDAOImpl;
import exceptions.NotFoundException;
import helpers.DBConnectionHelper;
import java.beans.Transient;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class Account implements Cloneable, Serializable {

    private int account_id;
    private int pin;
    private double amount;
    private int account_type_id; //1 checking //2 saving
    private int bank_branch_id;
    public String bank_branch_name, account_type_name;
    private int account_number;
    private Account SelectdRow;

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public Account getSelectdRow() {
        return SelectdRow;
    }

    public void setSelectdRow(Account SelectdRow) {
        this.SelectdRow = SelectdRow;
    }

    public String getBank_branch_name() {
        return bank_branch_name;
    }

    public void setBank_branch_name(String bank_branch_name) {
        this.bank_branch_name = bank_branch_name;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getAccount_type_id() {
        return account_type_id;
    }

    public void setAccount_type_id(int account_type_id) {
        this.account_type_id = account_type_id;
    }

    public String getAccount_type_name() {
        return account_type_name;
    }

    public void setAccount_type_name(String account_type) {
        this.account_type_name = account_type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getBank_branch_id() {
        return bank_branch_id;
    }

    public void setBank_branch_id(int bank_branch_id) {
        this.bank_branch_id = bank_branch_id;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void addAccount() throws SQLException {
        Connection con = DBConnectionHelper.getConnection();
        AccountDAOImpl ac = new AccountDAOImpl();
        accountGen();
        ac.create(con, this);
    }

    public void deleteData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        AccountDAOImpl accdao = new AccountDAOImpl();
        accdao.delete(con, this);
    }

    public void updateData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        AccountDAOImpl accup = new AccountDAOImpl();
        accup.save(con, this);
    }

    public List<Account> getDetailAccount() {
        List<Account> data = new ArrayList<Account>();

        String sql = "SELECT account.account_id,\n"
                + "       account.pin,\n"
                + "       account.amount,\n"
                + "       account_type.account_type_name,\n"
                + "       concat(bank_branch.name,'-', bank_branch.location) as branch\n"
                + "  FROM    (   onlinebanking.account account\n"
                + "           INNER JOIN\n"
                + "              onlinebanking.bank_branch bank_branch\n"
                + "           ON (account.bank_branch_id = bank_branch.bank_branch_id))\n"
                + "       INNER JOIN\n"
                + "          onlinebanking.account_type account_type\n"
                + "       ON (account.account_type_id = account_type.account_type_id) order by account_id;";
        try {
            Statement st = DBConnectionHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Account acco = new Account();
                acco.setAccount_number(rs.getRow());
                acco.setAccount_id(rs.getInt("account_id"));
                acco.setPin(rs.getInt("pin"));
                acco.setAmount(rs.getDouble("amount"));
                acco.setAccount_type_name(rs.getString("account_type_name"));
                acco.setBank_branch_name(rs.getString("branch"));
                data.add(acco);
            }
        } catch (SQLException ex) {
            ex.getMessage();
            ex.printStackTrace();
        }
        return data;
    }

    public List<SelectItem> getBankBranchName() {
        List<SelectItem> bnkName = new ArrayList<SelectItem>();
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String myQuery = "select concat(name, '-', location) as name from bank_branch";

            rs = st.executeQuery(myQuery);
            while (rs.next()) {
                bnkName.add(new SelectItem(rs.getString("name")));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bnkName;
    }

    public void doSetSelectedRow() {
        getAccount_id();
        getPin();
        getAmount();
        getAccount_type_name();
        getBank_branch_name();
    }

    public void accountGen() {
        int account = 0;
        String acc = "";
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String myQuery = "select bank_branch_id from bank_branch where concat(name, '-', location)='" + bank_branch_name + "'";
            rs = st.executeQuery(myQuery);

            System.out.println(st);
            while (rs.next()) {
                acc = rs.getString("bank_branch_id");
            }

            Statement st2 = con.createStatement();
            ResultSet rs2 = null;
            String myQuery2 = "select max(account_id) as account_id from account;";
            rs2 = st2.executeQuery(myQuery2);

            while (rs2.next()) {
                acc = acc.concat(rs2.getString("account_id"));
            }
            account = Integer.parseInt(acc);
            System.out.println(account);
            account += 1;
            setAccount_id(account);
            System.out.println(account);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
