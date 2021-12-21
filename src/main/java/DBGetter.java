import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

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


    public static TreeMap<String, HashMap<String, int[]>> GetStudentAchievmentStat() throws ClassNotFoundException, SQLException
    {
        var result = new TreeMap<String, HashMap<String, int[]>>(Collections.reverseOrder());
        try{
            Conn();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        statmt2 = conn.createStatement();
        statmt3 = conn.createStatement();
        statmt4 = conn.createStatement();
        var studTable = statmt.executeQuery("SELECT * FROM students ORDER BY surname;");

        while(studTable.next())
        {
            int id = studTable.getInt("student_id");
            String stName = studTable.getString("name");
            String  stSurname = studTable.getString("surname");
//            System.out.print(stName + " ");
//            System.out.println(stSurname + " ");
            var themes = statmt2.executeQuery("SELECT * FROM themes");
            var themeMap = new HashMap<String, int[]>();
            while (themes.next()){
                var theme_id = themes.getInt("theme_id");
                var tests = statmt3.executeQuery("SELECT count(*) AS total FROM task WHERE student_id="+ id +" AND theme_id="+ theme_id +" AND (task_name LIKE '%Контрольн% вопрос%') AND score>0;");
                var tasks = statmt4.executeQuery("SELECT count(*) AS total FROM task WHERE student_id="+ id +" AND theme_id="+ theme_id +" AND (task_name NOT LIKE '%Контрольн% вопрос%') AND score>0;");
                var theme = themes.getString("name");
//                System.out.println(theme);
//                System.out.println("\t Решенных тестов: " + tests.getInt("total"));
//                System.out.println("\t Решенных задач: " + tasks.getInt("total"));
                themeMap.put(theme, new int[]{tests.getInt("total"), tasks.getInt("total")});


            }

//            System.out.println();
//            System.out.println("------------------------------------------");
            result.put(stSurname + " " + stName, themeMap);
        }

//        System.out.println("Таблица выведена");

        return result;
    }

    public static HashMap<String, Integer> GetCityStat() throws SQLException, ClassNotFoundException {
        Conn();
        var dict = new HashMap<String, Integer>();
        var cities = statmt.executeQuery("SELECT city, count(city) as 'total' from students group by city");
        while (cities.next()) {
            dict.put(cities.getString("city"), cities.getInt("total"));
        }

        return dict;
    }

    public static HashMap<String, Integer> getAgeStat() throws SQLException, ClassNotFoundException, ParseException {
        try {
            Conn();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        var dict = new HashMap<String, Integer>();
        try {
            var dates = statmt.executeQuery("SELECT birthdate FROM students WHERE birthdate is not 'null';");
            while (dates.next()) {
                var today = LocalDate.now();
                var dateInArray = dates.getString("birthdate").split("-");
                var birthdate = LocalDate.of(Integer.parseInt(dateInArray[0]), Integer.parseInt(dateInArray[1]) + 1, Integer.parseInt(dateInArray[2]));
                var age = Integer.toString(Period.between(birthdate, today).getYears());
                if (dict.containsKey(age))
                    dict.put(age, dict.get(age)+1);
                else dict.put(age, 1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dict.put("Не указан год", dict.get("121"));
        return dict;
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }
}
