package ru.netology.web.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSQL {
    private static QueryRunner runner = new QueryRunner();

    private DataSQL() {

    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }


    public static DataHelper.VerificationCode getVerificationCode() {
        val codeSQL = "select code from auth_codes order by created DESC Limit 1";
        try (val conn = getConn()) {
            val code = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(code);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDataBase() {
        val connection = getConn();
        runner.execute(connection, "delete from auth_codes");
        runner.execute(connection, "delete from card_transactions");
        runner.execute(connection, "delete from cards");
        runner.execute(connection, "delete from users");
    }
}
