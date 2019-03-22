package io.github.lix3nn53.guardiansofadelia.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {

    private HikariDataSource dataSource;

    private String hostname;
    private String port;
    private String database;
    private String username;
    private String password;

    private int minimumConnections;
    private int maximumConnections;
    private long connectionTimeout;
    private String testQuery;

    public ConnectionPoolManager() {
        init();
        setupPool();
    }

    private void init() {
        hostname = "localhost";
        port = "3306";
        database = "goa";
        username = "lixtrinn";
        password = "asdsadfsafsabJK19034783ayu3wl12mgkj647nsdm9sf9dsfj9dsf82h32f8h2hf82f8hfhf2k2";
        minimumConnections = 5;
        maximumConnections = 20;
        connectionTimeout = 30000;
        testQuery = "SELECT 1";
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        hostname +
                        ":" +
                        port +
                        "/" +
                        database +
                        "?useSSL=false"
        );
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(minimumConnections);
        config.setMaximumPoolSize(maximumConnections);
        config.setConnectionTimeout(connectionTimeout);
        config.setConnectionTestQuery(testQuery);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setPoolName("GoA-Hikari");

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try {
            conn.close();
        } catch (SQLException ignored) {
        }
        if (ps != null) try {
            ps.close();
        } catch (SQLException ignored) {
        }
        if (res != null) try {
            res.close();
        } catch (SQLException ignored) {
        }
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
