import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: pc
 * Date: 19.11.13
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String args[]) throws Exception{
        Connection conn = null;

        try{
        //1. Загрузить драйвер
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:mem:test");


        //2. Создаем новую таблицу
        String sql = "CREATE TABLE posts(ID INT PRIMARY KEY, " +
                "postMessage VARCHAR(1000), postDate VARCHAR(1000));";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);

        //3. Из метаинформации достаем список доступных таблиц
        //Убеждаемся что в нем есть свежесозданная таблица.

        DatabaseMetaData meta = conn.getMetaData();
        ResultSet res = meta.getTables(null, null, null, new String[] {"TABLE"});
        System.out.println("List of tables: ");
        while (res.next()) {
            System.out.println("TABLE: " + res.getString("TABLE_NAME"));
        }
        res.close();

        //4. Вставить в БД какие либо данные

        stmt = conn.createStatement();
        sql = "INSERT into posts VALUES(1, 'hello', 'SomeDate')";
        stmt.execute(sql);


        //5. Выбрать данные из таблицы posts и показать на экране
        stmt = conn.createStatement();
        sql = "SELECT ID, postMessage, postDate FROM posts";
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            int id = rs.getInt("ID");
            String postMessage = rs.getString("postMessage");
            String postDate = rs.getString("postDate");

            //Display Values
            System.out.print("ID: " + id);
            System.out.print(", Message " + postMessage);
            System.out.print(", Date " + postDate);


        }
        rs.close();
        }finally {
            //Обязательно закрывать соединение в блоке finally!!
            conn.close();
        }



    }
}
