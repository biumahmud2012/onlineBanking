/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.DBConnectionHelper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.primefaces.expression.impl.ThisExpressionResolver;

/**
 *
 * @author user
 */
public class NewClass {

    public static void main(String[] args) {
        accountGen();
    }

    public static void accountGen() {
        int account = 0;
        String acc = "";
        try {
            Connection con = DBConnectionHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = null;
            String myQuery = "select bank_branch_id from bank_branch where concat(name, '-', location)='" + "Brac-Khulna" + "'";
            rs = st.executeQuery(myQuery);

            System.out.println(st);
            while (rs.next()) {
                acc = rs.getString("bank_branch_id");
            }

            Statement st2 = con.createStatement();
            ResultSet rs2 = null;
            String myQuery2 = "select max(account_id) as account_id from account;";
            rs2 = st2.executeQuery(myQuery2);
            String mxId = "";
            while (rs2.next()) {
               mxId = rs2.getString("account_id");

            }
            System.out.println("befre "+mxId);
            mxId.substring(3);
            System.out.println("after "+mxId);
            acc=acc.concat(mxId);
            account = Integer.parseInt(acc);
            System.out.println(account);
            account += 1;
            //setAccount_id(account);
            System.out.println(account);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
