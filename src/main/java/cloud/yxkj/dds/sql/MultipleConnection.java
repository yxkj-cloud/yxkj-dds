package cloud.yxkj.dds.sql;

import cloud.yxkj.dds.listener.DdsListener;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * 多连接
 *
 * @author wping
 * @since 2024-02-15
 */
@RequiredArgsConstructor
public class MultipleConnection implements Connection {
    private final DdsListener ddsListener = DdsListener.instance();

    private final MultipleDataSource multipleDataSource;

    private final Map<DataSource, Connection> connections;

    private List<Connection> getActiveConnections() {
        return multipleDataSource.getActiveDataSources().stream()
            .map(ds -> connections.computeIfAbsent(ds, this::getConnectionWithoutException))
            .collect(Collectors.toList());
    }

    private Connection getActiveConnection() {
        return getActiveConnections().stream().findFirst()
            .orElseThrow(() -> new NoSuchElementException("No active connection found"));
    }

    @SneakyThrows
    private Connection getConnectionWithoutException(DataSource ds) {
        return ds.getConnection();
    }

    @Override
    public Statement createStatement() throws SQLException {
        ddsListener.onBeforeCreateStatement(this);
        List<Statement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.createStatement());
        }
        MultipleStatement statement = new MultipleStatement(statements, this);
        ddsListener.onAfterCreateStatement(this, statement);
        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        ddsListener.onBeforePreparedStatement(this, sql);
        List<PreparedStatement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.prepareStatement(sql));
        }
        MultiplePreparedStatement statement = new MultiplePreparedStatement(statements, this);
        ddsListener.onAfterPrepareStatement(this, sql, statement);
        return statement;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        ddsListener.onBeforePrepareCall(this, sql);
        List<CallableStatement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.prepareCall(sql));
        }
        MultipleCallableStatement statement = new MultipleCallableStatement(statements, this);
        ddsListener.onAfterPrepareCall(this, sql, statement);
        return statement;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return getActiveConnection().nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return getActiveConnection().getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        ddsListener.onBeforeCommit(this);
        for (Connection connection : getActiveConnections()) {
            connection.commit();
        }
        ddsListener.onAfterCommit(this);
    }

    @Override
    public void rollback() throws SQLException {
        ddsListener.onBeforeRollback(this);
        for (Connection connection : getActiveConnections()) {
            connection.rollback();
        }
        ddsListener.onAfterRollback(this);
    }

    @Override
    public void close() throws SQLException {
        ddsListener.onBeforeClose(this);
        for (Connection connection : getActiveConnections()) {
            connection.close();
        }
        ddsListener.onAfterClose(this);
    }

    @Override
    public boolean isClosed() throws SQLException {
        return getActiveConnection().isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return getActiveConnection().getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.setReadOnly(readOnly);
        }
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return getActiveConnection().isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.setCatalog(catalog);
        }
    }

    @Override
    public String getCatalog() throws SQLException {
        return getActiveConnection().getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.setTransactionIsolation(level);
        }
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return getActiveConnection().getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return getActiveConnection().getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.clearWarnings();
        }
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        ddsListener.onBeforeCreateStatement(this);
        List<Statement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.createStatement(resultSetType, resultSetConcurrency));
        }
        MultipleStatement statement = new MultipleStatement(statements, this);
        ddsListener.onAfterCreateStatement(this, statement);
        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        ddsListener.onBeforePreparedStatement(this, sql);
        List<PreparedStatement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.prepareStatement(sql, resultSetType, resultSetConcurrency));
        }
        MultiplePreparedStatement statement = new MultiplePreparedStatement(statements, this);
        ddsListener.onAfterPrepareStatement(this, sql, statement);
        return statement;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        ddsListener.onBeforePrepareCall(this, sql);
        List<CallableStatement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.prepareCall(sql, resultSetType, resultSetConcurrency));
        }
        MultipleCallableStatement statement = new MultipleCallableStatement(statements, this);
        ddsListener.onAfterPrepareCall(this, sql, statement);
        return statement;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return getActiveConnection().getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.setTypeMap(map);
        }
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.setHoldability(holdability);
        }
    }

    @Override
    public int getHoldability() throws SQLException {
        return getActiveConnection().getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        Map<Connection, Savepoint> savepointMap = getActiveConnections().stream()
            .collect(Collectors.toMap(conn -> conn, this::getSavepointWithoutException));
        return new MultipleSavepoint(savepointMap);
    }

    @SneakyThrows
    private Savepoint getSavepointWithoutException(Connection conn) {
        return conn.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        Map<Connection, Savepoint> savepointMap = getActiveConnections().stream()
            .collect(Collectors.toMap(conn -> conn, conn -> this.getSavepointWithoutException(conn, name)));
        return new MultipleSavepoint(savepointMap);
    }

    @SneakyThrows
    private Savepoint getSavepointWithoutException(Connection conn, String name) {
        return conn.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        ddsListener.onBeforeRollback(this, savepoint);
        MultipleSavepoint multipleSavepoint = (MultipleSavepoint) savepoint;
        for (Map.Entry<Connection, Savepoint> entry : multipleSavepoint.getSavepoints().entrySet()) {
            entry.getKey().rollback(entry.getValue());
        }
        ddsListener.onAfterRollback(this, savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        ddsListener.onBeforeReleaseSavepoint(this, savepoint);
        MultipleSavepoint multipleSavepoint = (MultipleSavepoint) savepoint;
        for (Map.Entry<Connection, Savepoint> entry : multipleSavepoint.getSavepoints().entrySet()) {
            entry.getKey().releaseSavepoint(entry.getValue());
        }
        ddsListener.onAfterReleaseSavepoint(this, savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        ddsListener.onBeforeCreateStatement(this);
        List<Statement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
        }
        MultipleStatement statement = new MultipleStatement(statements, this);
        ddsListener.onAfterCreateStatement(this, statement);
        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        ddsListener.onBeforePreparedStatement(this, sql);
        List<PreparedStatement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        }
        MultiplePreparedStatement statement = new MultiplePreparedStatement(statements, this);
        ddsListener.onAfterPrepareStatement(this, sql, statement);
        return statement;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        ddsListener.onBeforePrepareCall(this, sql);
        List<CallableStatement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        }
        MultipleCallableStatement statement = new MultipleCallableStatement(statements, this);
        ddsListener.onAfterPrepareCall(this, sql, statement);
        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        ddsListener.onBeforePreparedStatement(this, sql);
        List<PreparedStatement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.prepareStatement(sql, autoGeneratedKeys));
        }
        MultiplePreparedStatement statement = new MultiplePreparedStatement(statements, this);
        ddsListener.onAfterPrepareStatement(this, sql, statement);
        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        ddsListener.onBeforePreparedStatement(this, sql);
        List<PreparedStatement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.prepareStatement(sql, columnIndexes));
        }
        MultiplePreparedStatement statement = new MultiplePreparedStatement(statements, this);
        ddsListener.onAfterPrepareStatement(this, sql, statement);
        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        ddsListener.onBeforePreparedStatement(this, sql);
        List<PreparedStatement> statements = new ArrayList<>();
        for (Connection connection : getActiveConnections()) {
            statements.add(connection.prepareStatement(sql, columnNames));
        }
        MultiplePreparedStatement statement = new MultiplePreparedStatement(statements, this);
        ddsListener.onAfterPrepareStatement(this, sql, statement);
        return statement;
    }

    @Override
    public Clob createClob() throws SQLException {
        return getActiveConnection().createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return getActiveConnection().createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return getActiveConnection().createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return getActiveConnection().createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return getActiveConnection().isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        for (Connection connection : getActiveConnections()) {
            connection.setClientInfo(name, value);
        }
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        for (Connection connection : getActiveConnections()) {
            connection.setClientInfo(properties);
        }
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return getActiveConnection().getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return getActiveConnection().getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return getActiveConnection().createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return getActiveConnection().createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.setSchema(schema);
        }
    }

    @Override
    public String getSchema() throws SQLException {
        return getActiveConnection().getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.abort(executor);
        }
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        for (Connection connection : getActiveConnections()) {
            connection.setNetworkTimeout(executor, milliseconds);
        }
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return getActiveConnection().getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getActiveConnection().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getActiveConnection().isWrapperFor(iface);
    }
}
