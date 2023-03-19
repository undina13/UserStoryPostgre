import org.junit.Assert;
import org.junit.Test;
import userstory.SQLQueryException;
import userstory.UserStory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStoryTest {
    private final String serverName = "localhost";
    private final String bdName = "truestore";
    private final String login = "root";
    private final String password = "root";
    private final String port = "5432";

    @Test
    public void testUserStore() throws SQLException {
        String sqlQuery = "SELECT * FROM users";
        UserStory userStory = new UserStory(serverName, bdName, login, password, port);
        ResultSet resultSet = userStory.createQuery(sqlQuery);

        int size = 0;
        while (resultSet.next()) {
            size++;
        }
        Assert.assertEquals(size, 13);
    }

    @Test
    public void testWrongStringSql() {
        String sqlQuery = "INSERT INTO USERS (id, email, password, role, blocked, created) VALUES ('b4861725-8aa5-4770-8e78-c9b6694dc975', 'user1@mail.ru', '$2a$10$lTgowhKpte2llERILz/C9ermIl9Q.ICoDa0ZkkLSm9dR2OeNdtKuW', 'ROLE_USER', false, '2022-10-02 19:13:10')";
        UserStory userStory = new UserStory(serverName, bdName, login, password, port);
        Assert.assertThrows(SQLQueryException.class, () -> userStory.createQuery(sqlQuery));
    }
}
