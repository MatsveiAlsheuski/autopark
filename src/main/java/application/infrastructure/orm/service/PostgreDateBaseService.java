package application.infrastructure.orm.service;

import application.infrastructure.core.Context;
import application.infrastructure.core.Scanner;
import application.infrastructure.core.annotations.Autowired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.core.annotations.Property;
import application.infrastructure.core.impl.ScannerImpl;
import application.infrastructure.orm.ConnectionFactory;
import application.infrastructure.orm.annotations.Column;
import application.infrastructure.orm.annotations.ID;
import application.infrastructure.orm.annotations.Table;
import application.infrastructure.orm.enums.SqlFieldType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostgreDateBaseService {
    @Autowired
    private ConnectionFactory connectionFactory;
    private Map<String, String> classToSql;
    private Map<String, String> insertPatternByClass;
    @Autowired
    private Context context;
    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "SELECT FROM information_schema.sequences \n" +
                    "WHERE  sequence_schema = 'public'\n" +
                    "AND    sequence_name = '%s');";
    private static final String CREATE_ID_SEQ_PATTERN =
            "CREATE SEQUENCE %S\n" +
                    "INCREMENT 1\n" +
                    "START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "SELECT FROM information_schema.tables\n" +
                    "WHERE  table_schema = 'public' \n" +
                    "AND    table_name = '%s')";
    private static final String CREATE_TABLE_SQL_PATTERN =
            "CREATE TABLE %s (\n" +
                    "%s integer PRIMARY KEY DEFAULT nextval('%s')" +
                    "%S);";
    private static final String INSERT_SQL_PATTERN =
            "INSERT INTO %s(%s)\n" +
                    "VALUES (%s)\n" +
                    "RETURNING %s;";
    private Map<String, String> insertByClassPattern;

    public PostgreDateBaseService() {
    }

    @InitMethod
    public void init() {
        classToSql = Stream.of(SqlFieldType.values()).collect(Collectors
                .toMap(fieldType -> fieldType.getType().getName(), SqlFieldType::getSqlType));
        insertPatternByClass = Stream.of(SqlFieldType.values()).collect(Collectors
                .toMap(fieldType -> fieldType.getType().getName(), fieldType -> fieldType.getInsertPattern()));
        insertByClassPattern = new HashMap<>();
        if (!checkConnectionFactory()) createConnectionFactory();

        Set<Class<?>> entities = context.getConfig().getScanner().getReflections().getTypesAnnotatedWith(Table.class);
        checkEntitiesTables(entities);

        entities.stream().forEach(entity -> insertPatternClass(entity));

    }

    private void createConnectionFactory() {
        String sql = String.format(CREATE_ID_SEQ_PATTERN, SEQ_NAME);
        try {
            Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            boolean isRetrieved = statement.execute(sql);
            statement.close();
          //  connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean checkConnectionFactory() {
        String sql = String.format(CHECK_SEQ_SQL_PATTERN, SEQ_NAME);
        boolean result = false;
        try {
            Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            result = resultSet.getBoolean("exists");
            resultSet.close();
            statement.close();
           // connection.close();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private void checkEntitiesTables(Set<Class<?>> entities) {
        for (Class<?> entity : entities) {
            try {
                checkEntitiesTableID(entity);
                checkEntitiesTableColumn(entity);
                if (!checkEntitiesTable(entity)) createEntitiesTable(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkEntitiesTableID(Class<?> entity) throws Exception {
        int countClassID = 0;
        for (Field field : entity.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                countClassID++;
                if (!(field.getType() == Long.class))
                    throw new Exception("@ID's type is not valid!");
            }
        }
        if (countClassID == 0) throw new Exception("Class don't have annotation @ID!");
    }

    private void checkEntitiesTableColumn(Class<?> entity) throws Exception {
        Set<String> setNames = new HashSet<>();
        for (Field field : entity.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                if (!(setNames.add(field.getAnnotation(Column.class).name())))
                    throw new Exception("Fields have similar names in annotations for table!");
                if (!((field.getType() == String.class) || (field.getType() == Integer.class)
                        || (field.getType() == Double.class) || (field.getType() == Date.class)))
                    throw new Exception("@Column's type is not valid!");
            } else if (!(field.isAnnotationPresent(ID.class))) throw new Exception("Field don't have annotation!");
        }
    }

    private boolean checkEntitiesTable(Class<?> entity) throws Exception {
        String tableName = entity.getAnnotation(Table.class).name();
        String sql = String.format(CHECK_TABLE_SQL_PATTERN, tableName);
        boolean result = false;
        Connection connection = connectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        result = resultSet.getBoolean("exists");
        resultSet.close();
        statement.close();
       // connection.close();
        return result;
    }

    private void createEntitiesTable(Class<?> type) throws Exception {
        String tableName = type.getAnnotation(Table.class).name();
        //String idField1 = Stream.of(type.getDeclaredFields()).map(field -> field.getAnnotation(ID.class).name()).filter(Objects::nonNull).findFirst().toString();
        String idField = null;
        StringBuilder columnField = new StringBuilder();
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class))
                idField = field.getAnnotation(ID.class).name();
            if (field.isAnnotationPresent(Column.class)) {
                columnField.append(",\n " + field.getAnnotation(Column.class).name());
                columnField.append(" " + classToSql.get(field.getType().getName()));
               if (field.getAnnotation(Column.class).unique()) columnField.append(" UNIQUE");
               if (field.getAnnotation(Column.class).nullable()) columnField.append(" NOT NULL");
            }
        }
        String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, columnField);
        Connection connection = connectionFactory.getConnection();
        Statement statement = connection.createStatement();
        boolean isRetrieved = statement.execute(sql);
        statement.close();
       // connection.close();
    }

    private void insertPatternClass(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String idFieldName = null;
        StringBuilder insertFields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                idFieldName = field.getAnnotation(ID.class).name(); /** Вопросы  по этому полю*/
            }
            if (field.isAnnotationPresent(Column.class)) {
                if (insertFields.length() != 0) insertFields.append(", ");
                if (values.length() != 0) values.append(", ");
                insertFields.append(field.getAnnotation(Column.class).name());
                values.append(insertPatternByClass.get(field.getType().getName()));
            }
        }
        String sql = String.format(INSERT_SQL_PATTERN, tableName, insertFields, values, idFieldName);
        insertByClassPattern.put(clazz.getName(), sql);
    }

    public Long save(Object obj) {
        Object[] values = createObject(obj);
        String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), values);
        Connection connection = connectionFactory.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        Long id = null;
        String idFieldName = null;
        Field fieldId = null;
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                idFieldName = field.getAnnotation(ID.class).name();                                 /** Вопросы  по этому полю*/
                fieldId = field;
            }
        }
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
           /* id = resultSet.getLong(idFieldName);    */                                                          /****/
            resultSet.close();
            statement.close();
          //  connection.close();
            fieldId.setAccessible(true);
            fieldId.set(obj, id);
        } catch (SQLException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    private Object[] createObject(Object object) {
        List<Object> listValues = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                try {
                    listValues.add(field.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        Object[] values = new Object[listValues.size()];
        for (int i = 0; i < listValues.size(); i++) {
            values[i] = listValues.get(i);
        }
        return values;
    }

    public <T> Optional<T> get(Long id, Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            try {
                throw new Exception("Class don't have annotation @Table");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String idFieldName = null;
        for (Field field : clazz.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                idFieldName = field.getAnnotation(ID.class).name();                                 /** Вопросы  по этому полю*/
            }
        }
        String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name() + " WHERE " + idFieldName + " = " + id;
        Connection connection = connectionFactory.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            resultSet.close();
            statement.close();
          //  connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(makeObject(resultSet, clazz));
    }

    @SneakyThrows
    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) {
        Object object = clazz.newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            if (Long.class == field.getType()) {
                Long value = resultSet.getLong(field.getName());
                field.setAccessible(true);
                field.set(object, value);
            }
            if (String.class == field.getType()) {
                String value = resultSet.getString(field.getName());
                field.setAccessible(true);
                field.set(object, value);
            }
            if (Integer.class == field.getType()) {
                Integer value = resultSet.getInt(field.getName());
                field.setAccessible(true);
                field.set(object, value);
            }
            if (Double.class == field.getType()) {
                Double value = resultSet.getDouble(field.getName());
                field.setAccessible(true);
                field.set(object, value);
            }
            if (Date.class == field.getType()) {
                Date value = resultSet.getDate(field.getName());
                field.setAccessible(true);
                field.set(object, value);
            }
        }
        return (T) object;
    }

    public <T> List<T> getAll(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (!clazz.isAnnotationPresent(Table.class)) {
            try {
                throw new Exception("Class don't have annotation @Table");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name();
        Connection connection = connectionFactory.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(makeObject(resultSet, clazz));
            }
            resultSet.close();
            statement.close();
           // connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
