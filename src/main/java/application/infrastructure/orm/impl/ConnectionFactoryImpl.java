package application.infrastructure.orm.impl;

import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.core.annotations.Property;
import application.infrastructure.orm.ConnectionFactory;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Property("url")
    private String url;
    @Property("username")
    private String userName;
    @Property("password")
    private String password;
    private Connection connection;

    public ConnectionFactoryImpl() {
    }
    @SneakyThrows
    @InitMethod
    public void initConnection(){
        connection = DriverManager.getConnection(url,userName,password);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
