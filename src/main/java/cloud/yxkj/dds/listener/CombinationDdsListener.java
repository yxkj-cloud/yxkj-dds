package cloud.yxkj.dds.listener;

import cloud.yxkj.dds.YxkjDds;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 组合 数据源监听器
 *
 * @since 2024/10/19
 */
@RequiredArgsConstructor
public class CombinationDdsListener implements DdsListener {
    private static final List<DdsListener> DDS_LISTENERS = StreamSupport.stream(ServiceLoader.load(DdsListener.class,
            CombinationDdsListener.class.getClassLoader()).spliterator(), true)
        .collect(Collectors.toList());

    private static final DdsListener INSTANCE = new CombinationDdsListener(DDS_LISTENERS);

    private final List<DdsListener> listeners;

    /**
     * 获取组合监听器实例
     *
     * @return 组合监听器实例
     */
    static DdsListener instance() {
        return INSTANCE;
    }

    @Override
    public void onAfterSetActive(YxkjDds yxkjDds, Object... actives) {
        listeners.forEach(listener -> listener.onAfterSetActive(yxkjDds, actives));
    }

    @Override
    public void onBeforeGetConnection(DataSource dataSource) {
        listeners.forEach(listener -> listener.onBeforeGetConnection(dataSource));
    }

    @Override
    public void onAfterGetConnection(DataSource dataSource, Connection connection) {
        listeners.forEach(listener -> listener.onAfterGetConnection(dataSource, connection));
    }

    @Override
    public void onBeforeCreateStatement(Connection connection) {
        listeners.forEach(listener -> listener.onBeforeCreateStatement(connection));
    }

    @Override
    public void onAfterCreateStatement(Connection connection, Statement statement) {
        listeners.forEach(listener -> listener.onAfterCreateStatement(connection, statement));
    }

    @Override
    public void onBeforePreparedStatement(Connection connection, String sql) {
        listeners.forEach(listener -> listener.onBeforePreparedStatement(connection, sql));
    }

    @Override
    public void onAfterPrepareStatement(Connection connection, String sql, PreparedStatement preparedStatement) {
        listeners.forEach(listener -> listener.onAfterPrepareStatement(connection, sql, preparedStatement));
    }

    @Override
    public void onBeforePrepareCall(Connection connection, String sql) {
        listeners.forEach(listener -> listener.onBeforePrepareCall(connection, sql));
    }

    @Override
    public void onAfterPrepareCall(Connection connection, String sql, CallableStatement callableStatement) {
        listeners.forEach(listener -> listener.onAfterPrepareCall(connection, sql, callableStatement));
    }

    @Override
    public void onBeforeRollback(Connection connection, Savepoint savepoint) {
        listeners.forEach(listener -> listener.onBeforeRollback(connection, savepoint));
    }

    @Override
    public void onAfterRollback(Connection connection, Savepoint savepoint) {
        listeners.forEach(listener -> listener.onAfterRollback(connection, savepoint));
    }

    @Override
    public void onBeforeReleaseSavepoint(Connection connection, Savepoint savepoint) {
        listeners.forEach(listener -> listener.onBeforeReleaseSavepoint(connection, savepoint));
    }

    @Override
    public void onAfterReleaseSavepoint(Connection connection, Savepoint savepoint) {
        listeners.forEach(listener -> listener.onAfterReleaseSavepoint(connection, savepoint));
    }

    @Override
    public void onBeforeCommit(Connection connection) {
        listeners.forEach(listener -> listener.onBeforeCommit(connection));
    }

    @Override
    public void onAfterCommit(Connection connection) {
        listeners.forEach(listener -> listener.onAfterCommit(connection));
    }

    @Override
    public void onBeforeRollback(Connection connection) {
        listeners.forEach(listener -> listener.onBeforeRollback(connection));
    }

    @Override
    public void onAfterRollback(Connection connection) {
        listeners.forEach(listener -> listener.onAfterRollback(connection));
    }

    @Override
    public void onBeforeClose(Connection connection) {
        listeners.forEach(listener -> listener.onBeforeClose(connection));
    }

    @Override
    public void onAfterClose(Connection connection) {
        listeners.forEach(listener -> listener.onAfterClose(connection));
    }

}
