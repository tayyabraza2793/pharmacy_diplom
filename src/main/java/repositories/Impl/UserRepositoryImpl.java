package repositories.Impl;

import models.UserEntity;
import repositories.UserRepository;
import utils.ResultSetConvertService;
import utils.DBCPDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final String SQL_SELECT = "SELECT * FROM [dbo].[User]";
    private static final String SQL_SELECT_EMAIL = "SELECT Id, Name, SecondName, Role, Phone, Email, Password, Salt " +
            "FROM [dbo].[User] WHERE Email = ?";
    private static final String SQL_CREATE = "IF NOT EXISTS (SELECT * FROM [dbo].[User] WHERE Email = ?)" +
            "INSERT INTO [dbo].[User] (Name, SecondName, Role, Phone, Email, Password, Salt) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE [dbo].[User] SET Name = ?, SecondName = ?, Role = ?, Phone = ?, Email = ?, Password = ?, Salt = ? WHERE Id = ?";
    private static final String SQL_DELETE = "DELETE FROM [dbo].[User] WHERE Id = ?";
    private static ResultSetConvertService converter;

    public UserRepositoryImpl() {
        if (converter == null) {
            converter = new ResultSetConvertService<UserEntity>(UserEntity.class);
        }
    }

    @Override
    public ArrayList<UserEntity> get() throws SQLException {
        ArrayList<UserEntity> users = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();
            users = converter.convertResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
        return users;
    }

    @Override
    public UserEntity getByEmail(String email) throws SQLException {
        Connection connection = null;
        UserEntity returnedUser = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            connection = DBCPDataSource.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            List<UserEntity> returnedUsers = converter.convertResultSet(resultSet);
            if (!returnedUsers.isEmpty()) returnedUser = returnedUsers.get(0);
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return returnedUser;
    }

    @Override
    public int create(UserEntity user) throws SQLException {
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_CREATE);
            statement.setString(1, user.getEmail());
            statement = converter.convertStatement(statement, user, 1);
            result = statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
        }

        return result;
    }

    @Override
    public int update(UserEntity user) throws SQLException {
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_UPDATE);
            statement = converter.convertStatement(statement, user, 0);
            statement.setInt(8, user.getId());
            result = statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
        }

        return result;
    }

    @Override
    public int delete(int id, Connection connection, PreparedStatement statement) throws SQLException {
        int result = 0;
        statement = connection.prepareStatement(SQL_DELETE);
        statement.setInt(1, id);
        result = statement.executeUpdate();
        return result;
    }
}

//----------For delete without passing connection
//        } catch (Exception e) {
//            if (connection != null) connection.rollback();
//            e.printStackTrace();
//        } finally {
//            if (connection != null) connection.close();
//            if (statement != null) statement.close();
//        }

//----------All moved to external services
//
//    private ArrayList<UserEntity> convert(ResultSet resultSet) throws SQLException, InvocationTargetException, IllegalAccessException {
//        ArrayList<UserEntity> users = new ArrayList<>();
//        while (resultSet.next()) {
//            UserEntity user = new UserEntity();
//            ResultSetMetaData md = resultSet.getMetaData();
//            for (int i = 0; i < md.getColumnCount(); i++) {
//                if (setMethodList.get(i).getParameterTypes()[0] == int.class) {
//                    setMethodList.get(i).invoke(user, resultSet.getInt(i + 1));
//                    continue;
//                }
//                setMethodList.get(i).invoke(user, resultSet.getString(i + 1));
//            }
//            users.add(user);
//        }
//        return users;
//    }
//
//    private void CreateGetSetMethods(Object object) {
//        Method[] methods = object.getClass().getMethods();
//        List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields())
//                .filter(f -> f.getAnnotation(Order.class) != null)
//                .sorted(Comparator.comparingInt(f -> f.getAnnotation(Order.class).value()))
//                .collect(Collectors.toList());
//
//        for (Field field : fields) {
//            for (Method method : methods) {
//                if (field.getName() == "id") {
//                    if (method.getName().startsWith("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))) {
//                        setMethodList.add(method);
//                    }
//                    continue;
//                }
//                if (method.getName().startsWith("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))) {
//                    getMethodList.add(method);
//                }
//                if (method.getName().startsWith("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))) {
//                    setMethodList.add(method);
//                }
//            }
//        }
//    }
//----------Alternative to streamApi used in CreateGetSetMethods,
//----------but includes fields without order annotations
//
//    Arrays.sort(fields, (o1, o2) -> {
//            Order or1 = o1.getAnnotation(Order.class);
//            Order or2 = o2.getAnnotation(Order.class);
//            if (or1 != null && or2 != null) {
//                return or1.value() - or2.value();
//            } else
//            if (or1 != null && or2 == null) {
//                return -1;
//            } else
//            if (or1 == null && or2 != null) {
//                return 1;
//            }
//            return o1.getName().compareTo(o2.getName());
//        });