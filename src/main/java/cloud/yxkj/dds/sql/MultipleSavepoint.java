package cloud.yxkj.dds.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class MultipleSavepoint implements Savepoint {
    private final Map<Connection, Savepoint> savepoints;

    @Override
    public int getSavepointId() throws SQLException {
        return 0;
    }

    @Override
    public String getSavepointName() throws SQLException {
        return null;
    }
}
