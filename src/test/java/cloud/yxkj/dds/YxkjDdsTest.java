package cloud.yxkj.dds;

import lombok.SneakyThrows;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

class YxkjDdsTest {
    private final YxkjDds yxkjDds;

    {
        JdbcDataSource dataSourceA = new JdbcDataSource();
        dataSourceA.setUrl("jdbc:h2:./target/test-data/h2-A");
        JdbcDataSource dataSourceB = new JdbcDataSource();
        dataSourceB.setUrl("jdbc:h2:./target/test-data/h2-B");
        Map<Object, DataSource> dataSourceMap = new LinkedHashMap<>();
        dataSourceMap.put("A", dataSourceA);
        dataSourceMap.put("B", dataSourceB);
        yxkjDds = new YxkjDds(dataSourceMap);
    }

    @BeforeEach
    @SneakyThrows
    void setup() {
        try (Connection connection = yxkjDds.getConnection()) {
            // 删除表，还原到初始状态
            try (Statement statement = connection.createStatement()) {
                statement.execute("drop table if exists test_t");
            }

            // 创建表，在两个数据库中创建表成功
            try (Statement statement = connection.createStatement()) {
                int count = statement.executeUpdate("create table test_t(id int);");
                Assertions.assertEquals(0, count);
            }
        }

    }

    @SneakyThrows
    @Test
    @DisplayName("测试写数据到指定数据库")
    void test_insert__specify_the_database_then_success() {
        try (Connection connection = yxkjDds.getConnection()) {
            // 在A表中插入数据成功
            yxkjDds.setActive("A");
            try (Statement statement = connection.createStatement()) {
                int count = statement.executeUpdate("insert into test_t(id)values(1);");
                Assertions.assertEquals(1, count);
            }

            // 检查A库中存在数据
            yxkjDds.setActive("A");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(1, count, "A库中预期应该存在一条数据，但实际为：" + count);
            }

            // 检查B库中不存在数据
            yxkjDds.setActive("B");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(0, count, "B库中预期应该不存在数据，但实际为：" + count);
            }
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("测试双库双写成功")
    void test_dual_writes_then_success() {
        try (Connection connection = yxkjDds.getConnection()) {
            // 在A表中插入数据成功
            yxkjDds.setActive("A", "B");
            try (Statement statement = connection.createStatement()) {
                int count = statement.executeUpdate("insert into test_t(id)values(1);");
                Assertions.assertEquals(1, count);
            }

            // 检查A库中存在数据
            yxkjDds.setActive("A");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(1, count, "A库中预期应该存在一条数据，但实际为：" + count);
            }

            // 检查B库中不存在数据
            yxkjDds.setActive("B");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(1, count, "B库中预期应该存在一条数据，但实际为：" + count);
            }
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("测试双库双写成功，带事务但未提交")
    void test_dual_writes_with_transactional_and_un_commit_then_success() {
        try (Connection connection = yxkjDds.getConnection()) {
            connection.setAutoCommit(false);

            // 在A表中插入数据成功
            yxkjDds.setActive("A", "B");
            try (Statement statement = connection.createStatement()) {
                int count = statement.executeUpdate("insert into test_t(id)values(1);");
                Assertions.assertEquals(1, count);
            }

            // 检查A库中存在数据
            yxkjDds.setActive("A");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(1, count, "A库中预期应该存在一条数据，但实际为：" + count);
            }

            // 检查B库中不存在数据
            yxkjDds.setActive("B");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(1, count, "B库中预期应该存在一条数据，但实际为：" + count);
            }
        }

        // 上面的写入没有提交，则再次查询预期查不到数据
        try (Connection connection = yxkjDds.getConnection()) {
            // 检查A库中存在数据
            yxkjDds.setActive("A");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(0, count, "A库中预期没有数据，但实际为：" + count);
            }

            // 检查B库中不存在数据
            yxkjDds.setActive("B");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(0, count, "B库中预期没有数据，但实际为：" + count);
            }
        }
    }


    @SneakyThrows
    @Test
    @DisplayName("测试双库双写成功，带事务并且已提交")
    void test_dual_writes_with_transactional_and_commit_then_success() {
        try (Connection connection = yxkjDds.getConnection()) {
            connection.setAutoCommit(false);

            // 在A表中插入数据成功
            yxkjDds.setActive("A", "B");
            try (Statement statement = connection.createStatement()) {
                int count = statement.executeUpdate("insert into test_t(id)values(1);");
                Assertions.assertEquals(1, count);
            }
            connection.commit();

            // 检查A库中存在数据
            yxkjDds.setActive("A");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(1, count, "A库中预期应该存在一条数据，但实际为：" + count);
            }

            // 检查B库中不存在数据
            yxkjDds.setActive("B");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(1, count, "B库中预期应该存在一条数据，但实际为：" + count);
            }
        }

        // 上面的写入没有提交，则再次查询预期查不到数据
        try (Connection connection = yxkjDds.getConnection()) {
            // 检查A库中存在数据
            yxkjDds.setActive("A");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(1, count, "A库中预期有一条数据，但实际为：" + count);
            }

            // 检查B库中不存在数据
            yxkjDds.setActive("B");
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select count(1) from test_t");
                Assertions.assertTrue(resultSet.next());
                int count = resultSet.getInt(1);
                Assertions.assertEquals(1, count, "B库中预期有一条数据，但实际为：" + count);
            }
        }
    }
}