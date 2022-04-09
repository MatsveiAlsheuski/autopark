package application.infrastructure.orm;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
