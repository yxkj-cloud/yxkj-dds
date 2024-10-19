package cloud.yxkj.dds.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Multiple Prepared Statement
 *
 * @author wping
 * @since 2024-02-16
 */
public class MultiplePreparedStatement extends MultipleStatement implements PreparedStatement {
    private final Collection<PreparedStatement> preparedStatement;

    public MultiplePreparedStatement(Collection<PreparedStatement> statements, MultipleConnection multipleConnection) {
        super(new ArrayList<>(statements), multipleConnection);
        this.preparedStatement = statements;
    }

    protected PreparedStatement getQueryPreparedStatement() throws SQLException {
        return (PreparedStatement) super.getQueryStatement();
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return getQueryPreparedStatement().executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        int res = 0;
        for (PreparedStatement statement : preparedStatement) {
            res = statement.executeUpdate();
        }
        return res;
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setNull(parameterIndex, sqlType);
        }
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setBoolean(parameterIndex, x);
        }
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setByte(parameterIndex, x);
        }
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setShort(parameterIndex, x);
        }
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setInt(parameterIndex, x);
        }
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setLong(parameterIndex, x);
        }
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setFloat(parameterIndex, x);
        }
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setDouble(parameterIndex, x);
        }
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setBigDecimal(parameterIndex, x);
        }
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setString(parameterIndex, x);
        }
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setBytes(parameterIndex, x);
        }
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setDate(parameterIndex, x);
        }
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setTime(parameterIndex, x);
        }
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setTimestamp(parameterIndex, x);
        }
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setAsciiStream(parameterIndex, x, length);
        }
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setUnicodeStream(parameterIndex, x, length);
        }
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setBinaryStream(parameterIndex, x, length);
        }
    }

    @Override
    public void clearParameters() throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.clearParameters();
        }
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setObject(parameterIndex, x, targetSqlType);
        }
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setObject(parameterIndex, x);
        }
    }

    @Override
    public boolean execute() throws SQLException {
        boolean res = false;
        for (PreparedStatement statement : preparedStatement) {
            res = statement.execute();
        }
        return res;
    }

    @Override
    public void addBatch() throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.addBatch();
        }
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setCharacterStream(parameterIndex, reader, length);
        }
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setRef(parameterIndex, x);
        }
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setBlob(parameterIndex, x);
        }
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setClob(parameterIndex, x);
        }
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setArray(parameterIndex, x);
        }
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return getQueryPreparedStatement().getMetaData();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setDate(parameterIndex, x, cal);
        }
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setTime(parameterIndex, x, cal);
        }
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setTimestamp(parameterIndex, x, cal);
        }
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setNull(parameterIndex, sqlType, typeName);
        }
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setURL(parameterIndex, x);
        }
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return getQueryPreparedStatement().getParameterMetaData();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setRowId(parameterIndex, x);
        }
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setNString(parameterIndex, value);
        }
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setNCharacterStream(parameterIndex, value, length);
        }
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setNClob(parameterIndex, value);
        }
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setClob(parameterIndex, reader, length);
        }
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setBlob(parameterIndex, inputStream, length);
        }
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setNClob(parameterIndex, reader, length);
        }
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setSQLXML(parameterIndex, xmlObject);
        }
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
        }
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setAsciiStream(parameterIndex, x, length);
        }
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setBinaryStream(parameterIndex, x, length);
        }
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setCharacterStream(parameterIndex, reader, length);
        }
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setAsciiStream(parameterIndex, x);
        }
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setBinaryStream(parameterIndex, x);
        }
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setCharacterStream(parameterIndex, reader);
        }
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setClob(parameterIndex, value);
        }
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setClob(parameterIndex, reader);
        }
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setBlob(parameterIndex, inputStream);
        }
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        for (PreparedStatement statement : preparedStatement) {
            statement.setNClob(parameterIndex, reader);
        }
    }
}
