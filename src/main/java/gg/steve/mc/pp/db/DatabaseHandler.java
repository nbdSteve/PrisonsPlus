package gg.steve.mc.pp.db;

public interface DatabaseHandler {

    String query(String sql, String field);

    void update(String sql);

    void delete(String sql);

    void insert(String sql, String... values);

    void execute(String sql);

    void synchronousUpdate(String sql);

    void synchronousDelete(String sql);

    void synchronousInsert(String sql, String... values);

    void synchronousExecute(String sql);
}
