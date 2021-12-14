import java.sql.*;

public class DBGetter {
    public static Connection conn;
    public static Statement statmt;
    public static Statement statmt2;
    public static Statement statmt3;
    public static Statement statmt4;
    public static ResultSet resSet;

    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:jas-db.s3db");
        statmt = conn.createStatement();
        System.out.println("База DBGetter Подключена!");
    }

    public static void GetStudentAchievmentStat() throws ClassNotFoundException, SQLException
    {
        statmt2 = conn.createStatement();
        statmt3 = conn.createStatement();
        statmt4 = conn.createStatement();
        var studTable = statmt.executeQuery("SELECT * FROM students;");

        while(studTable.next())
        {
            int id = studTable.getInt("student_id");
            String stName = studTable.getString("name");
            String  stSurname = studTable.getString("surname");
            System.out.print(stName + " ");
            System.out.println(stSurname + " ");
            var themes = statmt2.executeQuery("SELECT * FROM themes");
            while (themes.next()){
                var theme_id = themes.getInt("theme_id");
                var tests = statmt3.executeQuery("SELECT count(*) AS total FROM task WHERE student_id="+ id +" AND theme_id="+ theme_id +" AND task_name LIKE '%Контрольный вопрос%' AND score>0;");
                var tasks = statmt4.executeQuery("SELECT count(*) AS total FROM task WHERE student_id="+ id +" AND theme_id="+ theme_id +" AND task_name NOT LIKE '%Контрольный вопрос%' AND score>0;");
                var theme = themes.getString("name");
                System.out.println(theme);
                System.out.println("\t Решенных тестов: " + tests.getInt("total"));
                System.out.println("\t Решенных задач: " + tasks.getInt("total"));

            }

            System.out.println();
            System.out.println("------------------------------------------");
        }

        System.out.println("Таблица выведена");
    }

    public static void GetCityStat() throws SQLException {
        var cities = statmt.executeQuery("SELECT city, count(city) as 'total' from students group by city");
        while (cities.next()) {
            System.out.println(cities.getString("city") + "\t" + cities.getInt("total"));
        }
    }
}
