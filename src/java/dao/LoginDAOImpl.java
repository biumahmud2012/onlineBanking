/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.NotFoundException;
import helpers.LoginUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import models.Login;

/**
 *
 * @author A14
 */
public class LoginDAOImpl implements LoginDAO {

    @Override
    public String loginMethod(Connection conn, Login valueObject) throws SQLException {
        String returnPage = "index";
        String sql = "";
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            sql = "SELECT customer.customer_id, customer.givenname, customer.approval_officer, customer.approval_manager,"
                    + "       user_role.role_name,"
                    + "       user_role.role_status"
                    + "  FROM (onlinebanking.user_role_map user_role_map"
                    + "           INNER JOIN"
                    + "              onlinebanking.user_role user_role"
                    + "           ON (user_role_map.user_role_id = user_role.user_role_id))"
                    + "       INNER JOIN"
                    + "          onlinebanking.customer customer"
                    + "       ON (user_role_map.customer_id = customer.customer_id) where customer.username=? and"
                    + "       customer.password=? ";
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, valueObject.getUname());
            pstm.setString(2, valueObject.getPassword());
            System.out.println(pstm);
            rs = pstm.executeQuery();
            if (rs.next()) {
                int userid = rs.getInt("customer_id");
                String name = rs.getString("givenname");
                boolean appOff=rs.getBoolean("approval_officer");
                boolean appMan=rs.getBoolean("approval_manager");
                String userType = rs.getString("role_name");
                boolean isActive = rs.getBoolean("role_status");
                
                if (isActive && appOff && appMan) {
                    if (!userType.equalsIgnoreCase("customer")) {
                        HttpSession session = LoginUtil.getSession();
                        session.setAttribute("username", valueObject.getUname());
                        session.setAttribute("roleName", userType);
                        valueObject.setRoleName(userType);
                        valueObject.setShowLogout(true);
                        valueObject.setName(name);
                        returnPage = "admin/adminPanel?faces-redirect=true";
                    } else {
                        HttpSession session = LoginUtil.getSession();
                        session.setAttribute("username", valueObject.getUname());
                        session.setAttribute("roleName", userType);
                        valueObject.setRoleName(userType);
                        valueObject.setShowLogout(true);
                        valueObject.setName(name);
                        returnPage = "user/userPanel?faces-redirect=true";
                    }

                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "User is not active");
                    FacesContext.getCurrentInstance().addMessage("warn", msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Invalid Username or password");
                FacesContext.getCurrentInstance().addMessage("warn", msg);
            }
        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }
        return returnPage;
    }

    @Override
    public int getLastInsertedRowID(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Login createValueObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Login getObject(Connection conn, int account_id) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(Connection conn, Login valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List loadAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Connection conn, Login valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Connection conn, Login valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Connection conn, Login valueObject) throws NotFoundException, SQLException {
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
    public List searchMatching(Connection conn, Login valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void singleQuery(Connection conn, PreparedStatement stmt, Login valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
