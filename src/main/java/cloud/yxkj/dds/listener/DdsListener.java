package cloud.yxkj.dds.listener;

import cloud.yxkj.dds.YxkjDds;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * 动态数据源监听器 Listener
 *
 * @author wping 2018年5月17日
 */
public interface DdsListener {
    /**
     * 获取默认监听器
     *
     * @return 默认监听器
     */
    static DdsListener instance() {
        return CombinationDdsListener.instance();
    }

    /**
     * 设置激活的数据源
     *
     * @param actives 数据源标识
     */
    default void onAfterSetActive(YxkjDds yxkjDds, Object... actives) {
    }

    /**
     * 获取连接前调用
     *
     * @param dataSource 数据源对象
     */
    default void onBeforeGetConnection(DataSource dataSource) {
    }

    /**
     * 获取连接前调用
     *
     * @param dataSource 数据源对象
     * @param connection 连接对象
     */
    default void onAfterGetConnection(DataSource dataSource, Connection connection) {
    }

    /**
     * 在创建Statement之前调用的方法
     *
     * @param connection 数据库连接对象
     */
    default void onBeforeCreateStatement(Connection connection) {
    }

    /**
     * 在创建Statement之后调用的方法
     *
     * @param connection 数据库连接对象
     * @param statement 创建的Statement对象
     */
    default void onAfterCreateStatement(Connection connection, Statement statement) {
    }

    /**
     * 在准备PreparedStatement之前调用的方法
     *
     * @param connection 数据库连接对象
     * @param sql 要执行的SQL语句
     */
    default void onBeforePreparedStatement(Connection connection, String sql) {
    }

    /**
     * 在准备PreparedStatement之后调用的方法
     *
     * @param connection 数据库连接对象
     * @param sql 要执行的SQL语句
     * @param preparedStatement 创建的PreparedStatement对象
     */
    default void onAfterPrepareStatement(Connection connection, String sql, PreparedStatement preparedStatement) {
    }

    /**
     * 在准备CallableStatement之前调用的方法
     *
     * @param connection 数据库连接对象
     * @param sql 要执行的SQL语句
     */
    default void onBeforePrepareCall(Connection connection, String sql) {
    }

    /**
     * 在准备CallableStatement之后调用的方法
     *
     * @param connection 数据库连接对象
     * @param sql 要执行的SQL语句
     * @param callableStatement 创建的CallableStatement对象
     */
    default void onAfterPrepareCall(Connection connection, String sql, CallableStatement callableStatement) {
    }

    /**
     * 在回滚到指定保存点之前调用的方法
     *
     * @param connection 数据库连接对象
     * @param savepoint 回滚的目标保存点
     */
    default void onBeforeRollback(Connection connection, Savepoint savepoint) {
    }

    /**
     * 在回滚到指定保存点之后调用的方法
     *
     * @param connection 数据库连接对象
     * @param savepoint 已回滚的保存点
     */
    default void onAfterRollback(Connection connection, Savepoint savepoint) {
    }

    /**
     * 在释放指定保存点之前调用的方法
     *
     * @param connection 数据库连接对象
     * @param savepoint 要释放的保存点
     */
    default void onBeforeReleaseSavepoint(Connection connection, Savepoint savepoint) {
    }

    /**
     * 在释放指定保存点之后调用的方法
     *
     * @param connection 数据库连接对象
     * @param savepoint 已释放的保存点
     */
    default void onAfterReleaseSavepoint(Connection connection, Savepoint savepoint) {
    }

    /**
     * 在提交事务之前调用的方法
     *
     * @param connection 数据库连接对象
     */
    default void onBeforeCommit(Connection connection) {
    }

    /**
     * 在提交事务之后调用的方法
     *
     * @param connection 数据库连接对象
     */
    default void onAfterCommit(Connection connection) {
    }

    /**
     * 在回滚事务之前调用的方法
     *
     * @param connection 数据库连接对象
     */
    default void onBeforeRollback(Connection connection) {
    }

    /**
     * 在回滚事务之后调用的方法
     *
     * @param connection 数据库连接对象
     */
    default void onAfterRollback(Connection connection) {
    }

    /**
     * 在关闭连接之前调用的方法
     *
     * @param connection 数据库连接对象
     */
    default void onBeforeClose(Connection connection) {
    }

    /**
     * 在关闭连接之后调用的方法
     *
     * @param connection 数据库连接对象
     */
    default void onAfterClose(Connection connection) {
    }
}
