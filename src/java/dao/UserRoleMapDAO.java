package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import models.UserRoleMap;
import exceptions.NotFoundException;

public interface UserRoleMapDAO {
    public UserRoleMap createValueObject();
    public UserRoleMap getObject(Connection conn, int user_role_map_id) throws NotFoundException, SQLException;
    public void load(Connection conn, UserRoleMap valueObject) throws NotFoundException, SQLException;
    public List loadAll(Connection conn) throws SQLException;
    public void create(Connection conn, UserRoleMap valueObject) throws SQLException;
    public void save(Connection conn, UserRoleMap valueObject) throws NotFoundException, SQLException;
    public void delete(Connection conn, UserRoleMap valueObject) throws NotFoundException, SQLException;
    public void deleteAll(Connection conn) throws SQLException;
    public int countAll(Connection conn) throws SQLException;
    public List searchMatching(Connection conn, UserRoleMap valueObject) throws SQLException;
    public int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException;
    public void singleQuery(Connection conn, PreparedStatement stmt, UserRoleMap valueObject) throws NotFoundException, SQLException;
    public List listQuery(Connection conn, PreparedStatement stmt) throws SQLException;
}

