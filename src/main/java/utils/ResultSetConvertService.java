package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class ResultSetConvertService<T> {
    private GetSet getSet;
    private Class<T> clazz;

    public ResultSetConvertService(Class clazz) {
        getSet = GetSetCreatorService.getGettersSetters(clazz);
        this.clazz = clazz;
    }

    public ArrayList<T> convertResultSet(ResultSet resultSet) {
        ArrayList<T> results = new ArrayList<>();

        try {
            while (resultSet.next()) {
                T result = clazz.newInstance();
                ResultSetMetaData md = resultSet.getMetaData();
                for (int i = 0; i < md.getColumnCount(); i++) {
                    if (getSet.setters.get(i).getParameterTypes()[0] == int.class) {
                        getSet.setters.get(i).invoke(result, resultSet.getInt(i + 1));
                        continue;
                    }
                    getSet.setters.get(i).invoke(result, resultSet.getString(i + 1));
                }
                results.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    public PreparedStatement convertStatement(PreparedStatement statement, T object, int start) {
        try {
            for (int i = start; i < getSet.getters.size() + start; i++) {
                if (getSet.getters.get(i - start).getReturnType() == int.class) {
                    statement.setInt(i + 1, (int) getSet.getters.get(i - start).invoke(object));
                    continue;
                }
                statement.setString(i + 1, (String) getSet.getters.get(i - start).invoke(object));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statement;
    }
}
