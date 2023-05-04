package orm;

import anotations.Column;
import anotations.Entity;
import anotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    public void doCreate(Class<E> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        List<String> fieldsWithTypes = getAllFieldsAndDataTypes(entityClass);

        String query = String.format("CREATE TABLE %s ( id INT PRIMARY KEY AUTO_INCREMENT, %s);"
                , tableName, String.join(",", fieldsWithTypes));

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public void doAlter(Class<E> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        List<String> newFieldsWithTypes = getStatementsForNewFields(entityClass);

        String query = String.format("ALTER TABLE %s %s;"
                , tableName, String.join(",", newFieldsWithTypes));

        connection.prepareStatement(query).executeUpdate();
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field idColumn = getID(entity.getClass());
        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);

        if (idValue == null || (long) idValue <= 0) {
            return doInsert(entity);
        }
        return doUpdate(entity, (long) idValue);
    }

    @Override
    public Iterable<E> find(Class<E> clazz) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return find(clazz, null);
    }

    @Override
    public Iterable<E> find(Class<E> clazz, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Statement statement = connection.createStatement();
        String tableName = getTableName(clazz);

        String query = String.format("SELECT * FROM %s %s;"
                , tableName, where != null ? "WHERE " + where : "");

        ResultSet resultSet = statement.executeQuery(query);

        List<E> entities = new ArrayList<>();

        while (resultSet.next()) {
            E entity = clazz.getDeclaredConstructor().newInstance();
            fillEntity(clazz, resultSet, entity);
            entities.add(entity);
        }

        return entities;
    }

    @Override
    public E findFirst(Class<E> clazz) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return findFirst(clazz, null);
    }

    @Override
    public E findFirst(Class<E> clazz, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String tableName = getTableName(clazz);

        String query = String.format("SELECT * FROM %s %s LIMIT 1;"
                , tableName, where != null ? "WHERE " + where : "");

        ResultSet resultSet = statement.executeQuery(query);
        E entity = clazz.getDeclaredConstructor().newInstance();

        resultSet.next();
        fillEntity(clazz, resultSet, entity);

        return entity;
    }

    @Override
    public boolean delete(E entity) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());

        Field idColumn = getID(entity.getClass());
        String idColumnName = idColumn.getAnnotationsByType(Column.class)[0].name();

        idColumn.setAccessible(true);
        Object idColumnValue = idColumn.get(entity);


        String query = String.format("DELETE FROM %s WHERE %s = %s;"
                , tableName, idColumnName, idColumnValue);

        return connection.prepareStatement(query).execute();
    }

    private void fillEntity(Class<E> clazz, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            fillField(field, resultSet, entity);
        }
    }

    private void fillField(Field field, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        field.setAccessible(true);

        if (field.getType() == int.class || field.getType() == long.class) {
            field.set(entity, resultSet.getInt(field.getName()));
        } else if (field.getType() == LocalDate.class) {
            field.set(entity, LocalDate.parse(resultSet.getString(field.getName())));
        } else {
            field.set(entity, resultSet.getString(field.getName()));
        }
    }

    private Field getID(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }

    private boolean doInsert(E entity) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());
        List<String> tableFields = getTableFields(entity.getClass());
        List<String> tableValues = getValuesToInsert(entity);

        String query = String.format("INSERT INTO %s (%s) VALUES (%s);"
                , tableName, String.join(",", tableFields), String.join(",", tableValues));
        return connection.prepareStatement(query).execute();
    }

    private boolean doUpdate(E entity, long id) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());
        List<String> tableFields = getTableFields(entity.getClass());
        List<String> tableValues = getValuesToInsert(entity);

        List<String> updateStatements = new ArrayList<>();

        for (int i = 0; i < tableFields.size(); i++) {
            String fieldName = tableFields.get(i);
            String fieldValue = tableValues.get(i);
            updateStatements.add(fieldName + " = " + fieldValue);
        }

        String query = String.format("UPDATE %s SET %s WHERE id = %d;"
                , tableName, String.join(",", updateStatements), id);
        return connection.prepareStatement(query).execute();
    }

    private String getTableName(Class<?> aClass) {
        Entity[] declaredAnnotationsByType = aClass.getDeclaredAnnotationsByType(Entity.class);

        if (declaredAnnotationsByType.length == 0) {
            throw new UnsupportedOperationException("Class must be Entity");
        }

        return declaredAnnotationsByType[0].name();
    }

    private List<String> getTableFields(Class<?> aClass) {
        return Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> f.getAnnotationsByType(Column.class))
                .map(a -> a[0].name())
                .collect(Collectors.toList());
    }

    private List<String> getValuesToInsert(E entity) throws IllegalAccessException {
        Class<?> aClass = entity.getClass();
        List<Field> fields = Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());

        List<String> values = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(entity);
            if (o instanceof String || o instanceof LocalDate) {
                values.add("'" + o + "'");
            } else {
                values.add(o.toString());
            }
        }

        return values;
    }

    private List<String> getAllFieldsAndDataTypes(Class<E> entityClass) {
        List<Field> fields = Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());

        List<String> fieldsWithTypes = new ArrayList<>();

        for (Field field : fields) {
            String fieldName = field.getAnnotationsByType(Column.class)[0].name();
            String fieldType = "";
            if (field.getType() == int.class || field.getType() == long.class || field.getType() == Integer.class) {
                fieldType = "INT";
            } else if (field.getType() == LocalDate.class) {
                fieldType = "DATE";
            } else if (field.getType() == String.class) {
                fieldType = "VARCHAR(50)";
            }
            fieldsWithTypes.add(fieldName + " " + fieldType);
//            fieldsWithTypes.add(getNameAndTypeOfField(field));
        }

        return fieldsWithTypes;
    }

    private List<String> getStatementsForNewFields(Class<E> entityClass) throws SQLException {
        Set<String> fields = getAllFieldsFromTable();

        List<String> newFields = new ArrayList<>();
        Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .forEach(f -> {
                    String name = f.getAnnotationsByType(Column.class)[0].name();
                    if (!fields.contains(name)) {
                        newFields.add("ADD COLUMN " + getNameAndTypeOfField(f));
                    }
                });
        return newFields;
    }

    private String getNameAndTypeOfField(Field field) {
        String fieldName = field.getAnnotationsByType(Column.class)[0].name();

        String fieldType = "";
        if (field.getType() == int.class || field.getType() == long.class || field.getType() == Integer.class) {
            fieldType = "INT";
        } else if (field.getType() == LocalDate.class) {
            fieldType = "DATE";
        } else if (field.getType() == String.class) {
            fieldType = "VARCHAR(50)";
        }
        return fieldName + " " + fieldType;
    }

    private Set<String> getAllFieldsFromTable() throws SQLException {
        Set<String> fields = new HashSet<>();
        PreparedStatement statement = connection
                .prepareStatement("SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` " +
                                  " WHERE `TABLE_SCHEMA` = 'custom_orm' AND `COLUMN_NAME` != 'id' " +
                                  " AND `TABLE_NAME` = 'users';");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            fields.add(resultSet.getString("COLUMN_NAME"));
        }
        return fields;
    }
}
