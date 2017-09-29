package dao;

import java.sql.*;
import java.util.*;
import java.math.*;

import exceptions.NotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import models.BankBranch;
import models.UserRole;

public class BankBranchDAOImpl implements BankBranchDAO {

    @Override
    public BankBranch createValueObject() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BankBranch getObject(Connection conn, int bank_branch_id) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void load(Connection conn, BankBranch valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List loadAll(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * create-method. This will create new row in database according to supplied
     * valueObject contents. Make sure that values for all NOT NULL columns are
     * correctly specified. Also, if this table does not use automatic
     * surrogate-keys the primary-key must be specified. After INSERT command
     * this method will read the generated primary-key back to valueObject if
     * automatic surrogate-keys were used.
     *
     * @param conn This method requires working database connection.
     * @param valueObject This parameter contains the class instance to be
     * created. If automatic surrogate-keys are not used the Primary-key field
     * must be set for this to work properly.
     */
    @Override
    public void create(Connection conn, BankBranch valueObject) throws SQLException {
        String sql = "";
        PreparedStatement pstm = null;
        ResultSet result = null;
        int count = 0;
        try {
            ResultSet rs;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM bank_branch where lower(location)=lower(?)");

            ps.setString(1, valueObject.getLocation());
            System.out.println(ps);

            rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }

            if (count > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Duplicate Bank Branch Location not allowed here");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                sql = "INSERT INTO bank_branch ( name, location, "
                        + "description) VALUES (?, ?, ?) ";
                pstm = conn.prepareStatement(sql);

                pstm.setString(1, valueObject.getName());
                pstm.setString(2, valueObject.getLocation());
                pstm.setString(3, valueObject.getDescription());

                int rowcount = pstm.executeUpdate();
                if (rowcount >= 1) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Inserted Successfully!",
                                    "Welcome...!"));
                } else {
                    //System.out.println("PrimaryKey Error when updating DB!");
                    throw new SQLException("PrimaryKey Error when updating DB!");

                }
            }
        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }
    }

    @Override
    public void save(Connection conn, BankBranch valueObject) throws NotFoundException, SQLException {
        try {
            String sql = "UPDATE bank_branch SET description=?  where bank_branch_id=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, valueObject.getDescription());
            ps.setInt(2, valueObject.getBank_branch_id());

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
            Logger.getLogger(BankBranch.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void delete(Connection conn, BankBranch valueObject) throws NotFoundException, SQLException {
        String sql = "DELETE FROM bank_branch WHERE location=?;";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, valueObject.getLocation());
            int i = pst.executeUpdate();
            if (i > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "One row deleted");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to delete data");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BankBranch.class
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
    public List searchMatching(Connection conn, BankBranch valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * databaseUpdate-method. This method is a helper method for internal use.
     * It will execute all database handling that will change the information in
     * tables. SELECT queries will not be executed here however. The return
     * value indicates how many rows were affected. This method will also make
     * sure that if cache is used, it will reset when data changes.
     *
     * @param conn This method requires working database connection.
     * @param stmt This parameter contains the SQL statement to be excuted.
     */
    @Override
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
        int result = stmt.executeUpdate();

        return result;

    }

    @Override
    public void singleQuery(Connection conn, PreparedStatement stmt, BankBranch valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
