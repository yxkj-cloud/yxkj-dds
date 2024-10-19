package cloud.yxkj.dds.sql;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import static cloud.yxkj.dds.constant.DdsConstant.NO_ACTIVE_DS;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 多数据源
 *
 * @author wping
 * @since 2021-03-15
 */
@RequiredArgsConstructor
public abstract class MultipleDataSource implements DataSource {
    private final Map<Object, DataSource> dataSources;

    /**
     * 获取当前激活的数据源的Key的清单，可以为多个
     *
     * @return 当前激活状态的数据源的key的集合
     */
    protected abstract Collection<Object> getActiveKeys();

    /**
     * 获取当前激活的数据源的清单
     *
     * @return 获取当前激活的数据源的清单
     */
    protected List<DataSource> getActiveDataSources() {
        Collection<Object> keys = Optional.ofNullable(getActiveKeys()).orElse(Collections.emptyList());
        return keys.stream().map(dataSources::get).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Connection getConnection() {
        Map<DataSource, Connection> connectionMap = getActiveDataSources().stream()
            .collect(Collectors.toMap(ds -> ds, this::getConnectionWithoutException));
        return new MultipleConnection(this, connectionMap);
    }

    @Override
    public Connection getConnection(String username, String password) {
        Map<DataSource, Connection> connectionMap = getActiveDataSources().stream()
            .collect(Collectors.toMap(ds -> ds, ds -> getConnectionWithoutException(ds, username, password)));
        return new MultipleConnection(this, connectionMap);
    }

    @SneakyThrows
    private Connection getConnectionWithoutException(DataSource ds) {
        return ds.getConnection();
    }

    @SneakyThrows
    private Connection getConnectionWithoutException(DataSource ds, String username, String password) {
        return ds.getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getActiveDataSources().stream().findFirst()
            .map(this::getLogWriterWithoutException)
            .orElseThrow(this::getNoActiveDsException);
    }

    @SneakyThrows
    private PrintWriter getLogWriterWithoutException(DataSource ds) {
        return ds.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        for (DataSource activeDataSource : getActiveDataSources()) {
            activeDataSource.setLogWriter(out);
        }
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        for (DataSource activeDataSource : getActiveDataSources()) {
            activeDataSource.setLoginTimeout(seconds);
        }
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        for (DataSource activeDataSource : getActiveDataSources()) {
            return activeDataSource.getLoginTimeout();
        }
        throw getNoActiveDsException();
    }

    private SQLException getNoActiveDsException() {
        return new SQLException(NO_ACTIVE_DS);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        for (DataSource activeDataSource : getActiveDataSources()) {
            return activeDataSource.getParentLogger();
        }
        throw new SQLFeatureNotSupportedException(getNoActiveDsException());
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        for (DataSource activeDataSource : getActiveDataSources()) {
            return activeDataSource.unwrap(iface);
        }
        throw getNoActiveDsException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        for (DataSource activeDataSource : getActiveDataSources()) {
            return activeDataSource.isWrapperFor(iface);
        }
        throw getNoActiveDsException();
    }
}
