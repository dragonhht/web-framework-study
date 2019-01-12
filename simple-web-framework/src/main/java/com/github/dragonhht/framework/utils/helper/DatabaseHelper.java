package com.github.dragonhht.framework.utils.helper;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库助手类.
 * User: huang
 * Date: 19-1-2
 */
@Slf4j
public final class DatabaseHelper {

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static final ThreadLocal<Connection> CONNECCTION_HOLEDER;

    static {

        CONNECCTION_HOLEDER = new ThreadLocal<>();

        DRIVER = ConfigHelper.getValue("jdbc.driver");
        URL = ConfigHelper.getValue("jdbc.url");
        USERNAME = ConfigHelper.getValue("jdbc.username");
        PASSWORD = ConfigHelper.getValue("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("不能加载数据库驱动");
        }
    }

    /**
     * 获取数据库链接.
     * @return 数据库链接
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            log.error("获取数据库连接失败", e);
        }
        return connection;
    }

    /**
     * 关闭连接.
     * @param connection 数据库连接
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("关闭数据库连接失败", e);
            }
        }
    }

    /**
     * 开启事务.
     */
    public static void beginTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                log.error("关闭自动提交失败", e);
                throw new RuntimeException(e);
            } finally {
                CONNECCTION_HOLEDER.set(connection);
            }
        }
    }

    /**
     * 提交事务.
     */
    public static void commitTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                log.error("提交事务失败", e);
                throw new RuntimeException(e);
            } finally {
                CONNECCTION_HOLEDER.remove();
            }

        }
    }

    /**
     * 回滚事务.
     */
    public static void rollbackTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                log.error("回滚事务失败", e);
                throw new RuntimeException(e);
            } finally {
                CONNECCTION_HOLEDER.remove();
            }
        }
    }

}
