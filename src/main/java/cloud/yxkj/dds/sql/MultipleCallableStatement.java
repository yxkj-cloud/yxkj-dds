package cloud.yxkj.dds.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

/**
 * Multiple Callable Statement
 *
 * @author wping
 */
public class MultipleCallableStatement extends MultiplePreparedStatement implements CallableStatement {

    private final Collection<CallableStatement> callableStatements;

    public MultipleCallableStatement(Collection<CallableStatement> statements, MultipleConnection multipleConnection) {
        super(new ArrayList<>(statements), multipleConnection);
        this.callableStatements = statements;
    }

    protected CallableStatement getCallableStatement() throws SQLException {
        return (CallableStatement) super.getQueryStatement();
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.registerOutParameter(parameterIndex, sqlType);
        }
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.registerOutParameter(parameterIndex, sqlType, scale);
        }
    }

    @Override
    public boolean wasNull() throws SQLException {
        return getCallableStatement().wasNull();
    }

    @Override
    public String getString(int parameterIndex) throws SQLException {
        return getCallableStatement().getString(parameterIndex);
    }

    @Override
    public boolean getBoolean(int parameterIndex) throws SQLException {
        return getCallableStatement().getBoolean(parameterIndex);
    }

    @Override
    public byte getByte(int parameterIndex) throws SQLException {
        return getCallableStatement().getByte(parameterIndex);
    }

    @Override
    public short getShort(int parameterIndex) throws SQLException {
        return getCallableStatement().getShort(parameterIndex);
    }

    @Override
    public int getInt(int parameterIndex) throws SQLException {
        return getCallableStatement().getInt(parameterIndex);
    }

    @Override
    public long getLong(int parameterIndex) throws SQLException {
        return getCallableStatement().getLong(parameterIndex);
    }

    @Override
    public float getFloat(int parameterIndex) throws SQLException {
        return getCallableStatement().getFloat(parameterIndex);
    }

    @Override
    public double getDouble(int parameterIndex) throws SQLException {
        return getCallableStatement().getDouble(parameterIndex);
    }

    @Override
    @Deprecated(since = "1.2")
    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        return getCallableStatement().getBigDecimal(parameterIndex, scale);
    }

    @Override
    public byte[] getBytes(int parameterIndex) throws SQLException {
        return getCallableStatement().getBytes(parameterIndex);
    }

    @Override
    public Date getDate(int parameterIndex) throws SQLException {
        return getCallableStatement().getDate(parameterIndex);
    }

    @Override
    public Time getTime(int parameterIndex) throws SQLException {
        return getCallableStatement().getTime(parameterIndex);
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        return getCallableStatement().getTimestamp(parameterIndex);
    }

    @Override
    public Object getObject(int parameterIndex) throws SQLException {
        return getCallableStatement().getObject(parameterIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        return getCallableStatement().getBigDecimal(parameterIndex);
    }

    @Override
    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        return getCallableStatement().getObject(parameterIndex, map);
    }

    @Override
    public Ref getRef(int parameterIndex) throws SQLException {
        return getCallableStatement().getRef(parameterIndex);
    }

    @Override
    public Blob getBlob(int parameterIndex) throws SQLException {
        return getCallableStatement().getBlob(parameterIndex);
    }

    @Override
    public Clob getClob(int parameterIndex) throws SQLException {
        return getCallableStatement().getClob(parameterIndex);
    }

    @Override
    public Array getArray(int parameterIndex) throws SQLException {
        return getCallableStatement().getArray(parameterIndex);
    }

    @Override
    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        return getCallableStatement().getDate(parameterIndex, cal);
    }

    @Override
    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        return getCallableStatement().getTime(parameterIndex, cal);
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        return getCallableStatement().getTimestamp(parameterIndex, cal);
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.registerOutParameter(parameterIndex, sqlType, typeName);
        }
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.registerOutParameter(parameterName, sqlType);
        }
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.registerOutParameter(parameterName, sqlType, scale);
        }
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.registerOutParameter(parameterName, sqlType, typeName);
        }
    }

    @Override
    public URL getURL(int parameterIndex) throws SQLException {
        return getCallableStatement().getURL(parameterIndex);
    }

    @Override
    public void setURL(String parameterName, URL val) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setURL(parameterName, val);
        }
    }

    @Override
    public void setNull(String parameterName, int sqlType) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setNull(parameterName, sqlType);
        }
    }

    @Override
    public void setBoolean(String parameterName, boolean x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setBoolean(parameterName, x);
        }
    }

    @Override
    public void setByte(String parameterName, byte x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setByte(parameterName, x);
        }
    }

    @Override
    public void setShort(String parameterName, short x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setShort(parameterName, x);
        }
    }

    @Override
    public void setInt(String parameterName, int x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setInt(parameterName, x);
        }
    }

    @Override
    public void setLong(String parameterName, long x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setLong(parameterName, x);
        }
    }

    @Override
    public void setFloat(String parameterName, float x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setFloat(parameterName, x);
        }
    }

    @Override
    public void setDouble(String parameterName, double x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setDouble(parameterName, x);
        }
    }

    @Override
    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setBigDecimal(parameterName, x);
        }
    }

    @Override
    public void setString(String parameterName, String x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setString(parameterName, x);
        }
    }

    @Override
    public void setBytes(String parameterName, byte[] x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setBytes(parameterName, x);
        }
    }

    @Override
    public void setDate(String parameterName, Date x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setDate(parameterName, x);
        }
    }

    @Override
    public void setTime(String parameterName, Time x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setTime(parameterName, x);
        }
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setTimestamp(parameterName, x);
        }
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setAsciiStream(parameterName, x, length);
        }
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setBinaryStream(parameterName, x, length);
        }
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setObject(parameterName, x, targetSqlType, scale);
        }
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setObject(parameterName, x, targetSqlType);
        }
    }

    @Override
    public void setObject(String parameterName, Object x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setObject(parameterName, x);
        }
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setCharacterStream(parameterName, reader, length);
        }
    }

    @Override
    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setDate(parameterName, x, cal);
        }
    }

    @Override
    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setTime(parameterName, x, cal);
        }
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setTimestamp(parameterName, x, cal);
        }
    }

    @Override
    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setNull(parameterName, sqlType, typeName);
        }
    }

    @Override
    public String getString(String parameterName) throws SQLException {
        return getCallableStatement().getString(parameterName);
    }

    @Override
    public boolean getBoolean(String parameterName) throws SQLException {
        return getCallableStatement().getBoolean(parameterName);
    }

    @Override
    public byte getByte(String parameterName) throws SQLException {
        return getCallableStatement().getByte(parameterName);
    }

    @Override
    public short getShort(String parameterName) throws SQLException {
        return getCallableStatement().getShort(parameterName);
    }

    @Override
    public int getInt(String parameterName) throws SQLException {
        return getCallableStatement().getInt(parameterName);
    }

    @Override
    public long getLong(String parameterName) throws SQLException {
        return getCallableStatement().getLong(parameterName);
    }

    @Override
    public float getFloat(String parameterName) throws SQLException {
        return getCallableStatement().getFloat(parameterName);
    }

    @Override
    public double getDouble(String parameterName) throws SQLException {
        return getCallableStatement().getDouble(parameterName);
    }

    @Override
    public byte[] getBytes(String parameterName) throws SQLException {
        return getCallableStatement().getBytes(parameterName);
    }

    @Override
    public Date getDate(String parameterName) throws SQLException {
        return getCallableStatement().getDate(parameterName);
    }

    @Override
    public Time getTime(String parameterName) throws SQLException {
        return getCallableStatement().getTime(parameterName);
    }

    @Override
    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return getCallableStatement().getTimestamp(parameterName);
    }

    @Override
    public Object getObject(String parameterName) throws SQLException {
        return getCallableStatement().getObject(parameterName);
    }

    @Override
    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return getCallableStatement().getBigDecimal(parameterName);
    }

    @Override
    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        return getCallableStatement().getObject(parameterName, map);
    }

    @Override
    public Ref getRef(String parameterName) throws SQLException {
        return getCallableStatement().getRef(parameterName);
    }

    @Override
    public Blob getBlob(String parameterName) throws SQLException {
        return getCallableStatement().getBlob(parameterName);
    }

    @Override
    public Clob getClob(String parameterName) throws SQLException {
        return getCallableStatement().getClob(parameterName);
    }

    @Override
    public Array getArray(String parameterName) throws SQLException {
        return getCallableStatement().getArray(parameterName);
    }

    @Override
    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return getCallableStatement().getDate(parameterName, cal);
    }

    @Override
    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return getCallableStatement().getTime(parameterName, cal);
    }

    @Override
    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return getCallableStatement().getTimestamp(parameterName, cal);
    }

    @Override
    public URL getURL(String parameterName) throws SQLException {
        return getCallableStatement().getURL(parameterName);
    }

    @Override
    public RowId getRowId(int parameterIndex) throws SQLException {
        return getCallableStatement().getRowId(parameterIndex);
    }

    @Override
    public RowId getRowId(String parameterName) throws SQLException {
        return getCallableStatement().getRowId(parameterName);
    }

    @Override
    public void setRowId(String parameterName, RowId x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setRowId(parameterName, x);
        }
    }

    @Override
    public void setNString(String parameterName, String value) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setNString(parameterName, value);
        }
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setNCharacterStream(parameterName, value, length);
        }
    }

    @Override
    public void setNClob(String parameterName, NClob value) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setNClob(parameterName, value);
        }
    }

    @Override
    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setClob(parameterName, reader, length);
        }
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setBlob(parameterName, inputStream, length);
        }
    }

    @Override
    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setNClob(parameterName, reader, length);
        }
    }

    @Override
    public NClob getNClob(int parameterIndex) throws SQLException {
        return getCallableStatement().getNClob(parameterIndex);
    }

    @Override
    public NClob getNClob(String parameterName) throws SQLException {
        return getCallableStatement().getNClob(parameterName);
    }

    @Override
    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setSQLXML(parameterName, xmlObject);
        }
    }

    @Override
    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return getCallableStatement().getSQLXML(parameterIndex);
    }

    @Override
    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return getCallableStatement().getSQLXML(parameterName);
    }

    @Override
    public String getNString(int parameterIndex) throws SQLException {
        return getCallableStatement().getNString(parameterIndex);
    }

    @Override
    public String getNString(String parameterName) throws SQLException {
        return getCallableStatement().getNString(parameterName);
    }

    @Override
    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return getCallableStatement().getNCharacterStream(parameterIndex);
    }

    @Override
    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return getCallableStatement().getNCharacterStream(parameterName);
    }

    @Override
    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return getCallableStatement().getCharacterStream(parameterIndex);
    }

    @Override
    public Reader getCharacterStream(String parameterName) throws SQLException {
        return getCallableStatement().getCharacterStream(parameterName);
    }

    @Override
    public void setBlob(String parameterName, Blob x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setBlob(parameterName, x);
        }
    }

    @Override
    public void setClob(String parameterName, Clob x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setClob(parameterName, x);
        }
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setAsciiStream(parameterName, x, length);
        }
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setBinaryStream(parameterName, x, length);
        }
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setCharacterStream(parameterName, reader, length);
        }
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setAsciiStream(parameterName, x);
        }
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setBinaryStream(parameterName, x);
        }
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setCharacterStream(parameterName, reader);
        }
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setNCharacterStream(parameterName, value);
        }
    }

    @Override
    public void setClob(String parameterName, Reader reader) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setClob(parameterName, reader);
        }
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setBlob(parameterName, inputStream);
        }
    }

    @Override
    public void setNClob(String parameterName, Reader reader) throws SQLException {
        for (CallableStatement statement : callableStatements) {
            statement.setNClob(parameterName, reader);
        }
    }

    @Override
    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        return getCallableStatement().getObject(parameterIndex, type);
    }

    @Override
    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        return getCallableStatement().getObject(parameterName, type);
    }
}
