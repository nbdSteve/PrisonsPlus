package gg.steve.mc.pp.db;

import java.sql.ResultSet;

public interface DatabaseHandler {

    ResultSet query(String sql);

    boolean update(String sql);

    boolean delete(String sql);

    boolean insert(String sql);

    boolean execute(String sql);
}
