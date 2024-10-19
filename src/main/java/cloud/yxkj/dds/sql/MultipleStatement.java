package cloud.yxkj.dds.sql;

import lombok.RequiredArgsConstructor;

import static cloud.yxkj.dds.constant.DdsConstant.NO_ACTIVE_DS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Collection;

@RequiredArgsConstructor
public class MultipleStatement implements Statement {
    private final Collection<Statement> statements;

    private final MultipleConnection multipleConnection;

    protected Statement getQueryStatement() throws SQLException {
        // 查询默认采用随机的方式，开扩展为其他方式，如轮询
        return statements.stream().findFirst().orElseThrow(() -> new SQLException(NO_ACTIVE_DS));
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return getQueryStatement().executeQuery(sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        int res = 0;
        for (Statement statement : statements) {
            res = statement.executeUpdate(sql);
        }
        return res;
    }

    @Override
    public void close() throws SQLException {
        for (Statement statement : statements) {
            statement.close();
        }
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        int res = 0;
        for (Statement statement : statements) {
            res = statement.getMaxFieldSize();
        }
        return res;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        for (Statement statement : statements) {
            statement.setMaxFieldSize(max);
        }
    }

    @Override
    public int getMaxRows() throws SQLException {
        return getQueryStatement().getMaxRows();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        for (Statement statement : statements) {
            statement.setMaxRows(max);
        }
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        for (Statement statement : statements) {
            statement.setEscapeProcessing(enable);
        }
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return getQueryStatement().getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        for (Statement statement : statements) {
            statement.setQueryTimeout(seconds);
        }
    }

    @Override
    public void cancel() throws SQLException {
        for (Statement statement : statements) {
            statement.cancel();
        }
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return getQueryStatement().getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        for (Statement statement : statements) {
            statement.clearWarnings();
        }
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        for (Statement statement : statements) {
            statement.setCursorName(name);
        }
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        boolean res = false;
        for (Statement statement : statements) {
            res = statement.execute(sql);
        }
        return res;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return getQueryStatement().getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return getQueryStatement().getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return getQueryStatement().getMoreResults();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        for (Statement statement : statements) {
            statement.setFetchDirection(direction);
        }
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return getQueryStatement().getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        for (Statement statement : statements) {
            statement.setFetchSize(rows);
        }
    }

    @Override
    public int getFetchSize() throws SQLException {
        return getQueryStatement().getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return getQueryStatement().getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return getQueryStatement().getResultSetType();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        for (Statement statement : statements) {
            statement.addBatch(sql);
        }
    }

    @Override
    public void clearBatch() throws SQLException {
        for (Statement statement : statements) {
            statement.clearBatch();
        }
    }

    @Override
    public int[] executeBatch() throws SQLException {
        int[] res = null;
        for (Statement statement : statements) {
            res = statement.executeBatch();
        }
        return res;
    }

    @Override
    public Connection getConnection() {
        return multipleConnection;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return getQueryStatement().getMoreResults(current);
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return getQueryStatement().getGeneratedKeys();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        int res = 0;
        for (Statement statement : statements) {
            res = statement.executeUpdate(sql, autoGeneratedKeys);
        }
        return res;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        int res = 0;
        for (Statement statement : statements) {
            res = statement.executeUpdate(sql, columnIndexes);
        }
        return res;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        int res = 0;
        for (Statement statement : statements) {
            res = statement.executeUpdate(sql, columnNames);
        }
        return res;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        boolean res = false;
        for (Statement statement : statements) {
            res = statement.execute(sql, autoGeneratedKeys);
        }
        return res;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        boolean res = false;
        for (Statement statement : statements) {
            res = statement.execute(sql, columnIndexes);
        }
        return res;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        boolean res = false;
        for (Statement statement : statements) {
            res = statement.execute(sql, columnNames);
        }
        return res;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return getQueryStatement().getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return getQueryStatement().isClosed();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        for (Statement statement : statements) {
            statement.setPoolable(poolable);
        }
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return getQueryStatement().isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        for (Statement statement : statements) {
            statement.closeOnCompletion();
        }
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return getQueryStatement().isCloseOnCompletion();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getQueryStatement().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getQueryStatement().isWrapperFor(iface);
    }
}
