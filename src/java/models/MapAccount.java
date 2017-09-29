package models;

import dao.MapAccountDAOImpl;
import dao.UserRoleDAOImpl;
import exceptions.NotFoundException;
import helpers.DBConnectionHelper;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class MapAccount implements Cloneable, Serializable {

    private int map_id;
    private int account_id;
    private String customer_name;
    private String nominee_name;
    private int mapAccount_number;
    private MapAccount selectedRow;

    private String acStatus;

    public int getMapAccount_number() {
        return mapAccount_number;
    }

    public void setMapAccount_number(int mapAccount_number) {
        this.mapAccount_number = mapAccount_number;
    }

    public MapAccount getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(MapAccount selectedRow) {
        this.selectedRow = selectedRow;
    }

    public int getMap_id() {
        return map_id;
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getNominee_name() {
        return nominee_name;
    }

    public void setNominee_name(String nominee_name) {
        this.nominee_name = nominee_name;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public List<SelectItem> addAccountID() {
        List<SelectItem> accountPIN = new ArrayList<SelectItem>();
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String myQuery = "select account_id from account";

            rs = st.executeQuery(myQuery);
            while (rs.next()) {
                accountPIN.add(new SelectItem(rs.getString("account_id")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return accountPIN;
    }

    public List<SelectItem> addCustomerID() {
        List<SelectItem> custIdName = new ArrayList<SelectItem>();
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String myQuery = "select concat(customer_id,'-', username) as customer from customer";

            rs = st.executeQuery(myQuery);
            while (rs.next()) {
                custIdName.add(new SelectItem(rs.getString("customer")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return custIdName;
    }

    public List<SelectItem> addNomineeID() {
        List<SelectItem> nomIdName = new ArrayList<SelectItem>();
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String myQuery = "select concat(nominee_id,'-', givenname) as nominee from nominee";

            rs = st.executeQuery(myQuery);
            while (rs.next()) {
                nomIdName.add(new SelectItem(rs.getString("nominee")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nomIdName;
    }

    public void addMapAccount() throws SQLException {
        Connection con = DBConnectionHelper.getConnection();
        MapAccountDAOImpl ac = new MapAccountDAOImpl();
        ac.create(con, this);
    }

    public void deleteData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        MapAccountDAOImpl ur = new MapAccountDAOImpl();
        ur.delete(con, this);
    }

    public void updateData() throws NotFoundException, SQLException {
        Connection con = DBConnectionHelper.getConnection();
        MapAccountDAOImpl urdm = new MapAccountDAOImpl();
        urdm.save(con, this);
    }

    private void clear() {
        setAccount_id(0);
    }

    public List<MapAccount> getDetailMapAccount() {
        List<MapAccount> data = new ArrayList<MapAccount>();

        String sql = "SELECT map_account.map_id,\n"
                + "       map_account.account_id,\n"
                + "       concat(map_account.customer_id,'-', customer.username) as customer,\n"
                + "       concat(map_account.nominee_id, '-', nominee.givenname) as nominee\n"
                + "  FROM    (   (   onlinebanking.map_account map_account\n"
                + "               INNER JOIN\n"
                + "                  onlinebanking.nominee nominee\n"
                + "               ON (map_account.nominee_id = nominee.nominee_id))\n"
                + "           INNER JOIN\n"
                + "              onlinebanking.account account\n"
                + "           ON (map_account.account_id = account.account_id))\n"
                + "       INNER JOIN\n"
                + "          onlinebanking.customer customer\n"
                + "       ON (map_account.customer_id = customer.customer_id) order by map_id";
        try {
            Statement st = DBConnectionHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                MapAccount mapacc = new MapAccount();
                mapacc.setMapAccount_number(rs.getRow());
                mapacc.setMap_id(rs.getInt("map_id"));
                mapacc.setAccount_id(rs.getInt("account_id"));
                mapacc.setCustomer_name(rs.getString("customer"));
                mapacc.setNominee_name(rs.getString("nominee"));

                data.add(mapacc);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return data;
    }

    public void doSetSelectedRow() {
        getMap_id();
        getCustomer_name();
        getNominee_name();
    }

    public void seeAcStatus() {
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String myQuery = "select account_id from map_account where account_id='"+getAccount_id()+"'";

            rs = st.executeQuery(myQuery);
            if (rs.next()) {
                acStatus="This Account is Mapped.";
            } else {
                acStatus="This Account is not Mapped.";
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    public List<MapAccount> getDetailsOfAccount() {
        List<MapAccount> data = new ArrayList<MapAccount>();

        String sql = "SELECT map_account.map_id,\n"
                + "       map_account.account_id,\n"
                + "       concat(map_account.customer_id,'-', customer.username) as customer,\n"
                + "       concat(map_account.nominee_id, '-', nominee.givenname) as nominee\n"
                + "  FROM    (   (   onlinebanking.map_account map_account\n"
                + "               INNER JOIN\n"
                + "                  onlinebanking.nominee nominee\n"
                + "               ON (map_account.nominee_id = nominee.nominee_id))\n"
                + "           INNER JOIN\n"
                + "              onlinebanking.account account\n"
                + "           ON (map_account.account_id = account.account_id))\n"
                + "       INNER JOIN\n"
                + "          onlinebanking.customer customer\n"
                + "       ON (map_account.customer_id = customer.customer_id) order by map_id where account_id='"+account_id+"'";
        try {
            Statement st = DBConnectionHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                MapAccount mapacc = new MapAccount();
                mapacc.setMapAccount_number(rs.getRow());
                mapacc.setMap_id(rs.getInt("map_id"));
                mapacc.setAccount_id(rs.getInt("account_id"));
                mapacc.setCustomer_name(rs.getString("customer"));
                mapacc.setNominee_name(rs.getString("nominee"));

                data.add(mapacc);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return data;
    }
}
