package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import models.Nominee;
import exceptions.NotFoundException;

public interface NomineeDAO{
	public Nominee createValueObject();
    public Nominee getObject(Connection conn, int nominee_id) throws NotFoundException, SQLException;
    public void load(Connection conn, Nominee valueObject) throws NotFoundException, SQLException;
    public List loadAll(Connection conn) throws SQLException;
    public boolean create(Connection conn, Nominee valueObject) throws SQLException;
    public void save(Connection conn, Nominee valueObject) throws NotFoundException, SQLException;
    public void delete(Connection conn, Nominee valueObject) throws NotFoundException, SQLException;
    public void deleteAll(Connection conn) throws SQLException;
    public int countAll(Connection conn) throws SQLException;
    public List searchMatching(Connection conn, Nominee valueObject) throws SQLException;
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException;
    public void singleQuery(Connection conn, PreparedStatement stmt, Nominee valueObject) throws NotFoundException, SQLException;
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException;
}

