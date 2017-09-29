package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import models.Login;
import exceptions.NotFoundException;

public interface LoginDAO{
    public String loginMethod(Connection conn, Login valueObject) throws SQLException;
    public int getLastInsertedRowID(Connection conn) throws SQLException;
    public Login createValueObject();
    public Login getObject(Connection conn, int account_id) throws NotFoundException, SQLException;
    public void load(Connection conn, Login valueObject) throws NotFoundException, SQLException;
    public List loadAll(Connection conn) throws SQLException;
    public boolean create(Connection conn, Login valueObject) throws SQLException;
    public void save(Connection conn, Login valueObject) throws NotFoundException, SQLException;
    public void delete(Connection conn, Login valueObject) throws NotFoundException, SQLException;
    public void deleteAll(Connection conn) throws SQLException;
    public int countAll(Connection conn) throws SQLException;
    public List searchMatching(Connection conn, Login valueObject) throws SQLException;
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException;
    public void singleQuery(Connection conn, PreparedStatement stmt, Login valueObject) throws NotFoundException, SQLException;
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException;
}
