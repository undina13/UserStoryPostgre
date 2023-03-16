package userStory;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserStory {
    private static final Logger logger = Logger.getLogger(UserStory.class.getName());
    private final String serverName;
    private final String bdName;
    private final String login;
    private final String password;

    public UserStory(String serverName, String bdName, String login, String password) {
        this.serverName = serverName;
        this.bdName = bdName;
        this.login = login;
        this.password = password;
    }

    public Connection setConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String datasourceUrl = "jdbc:postgresql://" + serverName + ":5432/" + bdName;
            connection = DriverManager.getConnection(datasourceUrl, login, password);
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Соединение с базой данных не может быть установлено, проверьте настройки подключения");
            logger.log(Level.SEVERE, e.getMessage());
        }
        return connection;
    }

    public ResultSet createQuery(String sqlQuery) {
        ResultSet resultSet = null;
        if (!(sqlQuery.startsWith("SELECT") || sqlQuery.startsWith("select"))) {
            logger.log(Level.WARNING, "Программа выполняет только запросы SELECT. Проверьте правильность запроса");
            throw new SQLQueryException("Программа выполняет только запросы SELECT. Проверьте правильность запроса");
        }

        try {
            Connection connection = setConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Что-то пошло не так, проверьте правильность написания запроса");
            e.printStackTrace();
        }
        return resultSet;
    }
}
