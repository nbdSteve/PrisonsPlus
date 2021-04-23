package gg.steve.mc.pp.db;

import java.sql.ResultSet;

public interface DatabaseHandler {

    ResultSet query(String sql);

    void update(String sql);

    void delete(String sql);

    void insert(String sql);

    void execute(String sql);
}
