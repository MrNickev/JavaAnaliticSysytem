import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBStarter {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:jas-db.s3db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("create table if not exists students\n" +
                "(\n" +
                "    student_id integer not null\n" +
                "        constraint students_pk\n" +
                "            primary key autoincrement,\n" +
                "    name       text    not null,\n" +
                "    surname    text    not null,\n" +
                "    birthdate  date,\n" +
                "    city       text,\n" +
                "    photoUrl   integer\n" +
                ");");

        statmt.execute("create table if not exists \"group\"\n" +
                "(\n" +
                "    group_id integer not null\n" +
                "        constraint group_pk\n" +
                "            primary key autoincrement,\n" +
                "    name     text    not null\n" +
                ");");

        statmt.execute("create table if not exists student_group\n" +
                "(\n" +
                "    group_id   integer not null\n" +
                "        constraint group_id_fk\n" +
                "            references \"group\",\n" +
                "    student_id integer not null\n" +
                "        constraint student_id___fk\n" +
                "            references students\n" +
                ");");

        statmt.execute("create table if not exists courses\n" +
                "(\n" +
                "    course_id integer not null\n" +
                "        constraint courses_pk\n" +
                "            primary key autoincrement,\n" +
                "    name      text    not null\n" +
                ");");

        statmt.execute("create table if not exists course_group\n" +
                "(\n" +
                "    course_id integer not null\n" +
                "        constraint course_id___fk\n" +
                "            references courses,\n" +
                "    group_id  integer not null\n" +
                "        constraint group_id___fk\n" +
                "            references \"group\"\n" +
                ");");

        statmt.execute("create table if not exists themes\n" +
                "(\n" +
                "    theme_id    integer not null\n" +
                "        constraint themes_pk\n" +
                "            primary key autoincrement,\n" +
                "    name        text    not null,\n" +
                "    total_score integer\n" +
                ");");

        statmt.execute("create table if not exists course_theme\n" +
                "(\n" +
                "    course_id integer not null\n" +
                "        constraint course_theme_course_id___fk\n" +
                "            references courses,\n" +
                "    theme_id  integer not null\n" +
                "        constraint course_theme_theme_id___fk\n" +
                "            references themes\n" +
                ");");

        statmt.execute("create table task\n" +
                "(\n" +
                "    task_id integer not null\n" +
                "        constraint task_pk\n" +
                "            primary key autoincrement,\n" +
                "    score   integer\n" +
                ");");

        statmt.execute("create table if not exists theme_task\n" +
                "(\n" +
                "    theme_id integer not null\n" +
                "        constraint theme_task_theme_id___fk\n" +
                "            references themes,\n" +
                "    task_id  integer not null\n" +
                "        constraint theme_task_task_id___fk\n" +
                "            references task\n" +
                ");");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB(Student st) throws SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("INSERT INTO students ('name', 'surname', 'birthdate', 'city', 'photoUrl', 'vk_id', 'male') VALUES ('"+ st.getName() +"', '"+ st.getSurname() +"', '"+ st.getStringBirthdate() +"', '"+ st.getCity() +"', '"+ st.getPhotoUrl() +"', "+ st.getId() +", '"+ st.getSex() +"');");
        st.getSubject().forEach(subject -> {
            try {
                statmt.execute("INSERT OR IGNORE INTO courses ('name') VALUES ('"+ subject.getName() +"');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statmt.execute("INSERT OR IGNORE INTO \"group\" ('name') VALUES ('"+ subject.getGroupName() +"');");
            } catch (SQLException e) {
            e.printStackTrace();
            }
            try {
                resSet = statmt.executeQuery("SELECT student_id FROM students WHERE name='"+ st.getName() +"' AND surname='"+ st.getSurname() +"' AND vk_id='"+ st.getId() +"';");
                resSet.next();
                var studId = resSet.getInt("student_id");

                resSet = statmt.executeQuery("SELECT group_id FROM \"group\" WHERE name='"+ subject.getGroupName() +"';");
                resSet.next();
                var groupId = resSet.getInt("group_id");

                resSet = statmt.executeQuery("SELECT course_id FROM courses WHERE name='"+ subject.getName() +"';");
                resSet.next();
                var courseId = resSet.getInt("course_id");

                statmt.execute("INSERT INTO student_group ('student_id', 'group_id') VALUES ("+ studId+", "+ groupId +");");
                statmt.execute("INSERT INTO course_group ('course_id', 'group_id') VALUES ("+ courseId+", "+ groupId +");");

                for (var theme : subject.getThemes()){
                    try{
                        statmt.execute("INSERT OR IGNORE INTO themes ('name') VALUES ('"+ theme.getName() +"');");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    resSet = statmt.executeQuery("SELECT theme_id FROM themes WHERE name='"+ theme.getName() +"';");
                    resSet.next();
                    var themeId = resSet.getInt("theme_id");
                    statmt.execute("INSERT INTO course_theme ('course_id', 'theme_id') VALUES ("+ courseId +", "+ themeId +");");
                    var scores = theme.getScores();
                    var taskNames = theme.getTaskNames();
                    for (var i = 0; i< theme.getScores().length; i++) {
                        statmt.execute("INSERT INTO task ('student_id', 'theme_id', 'score', 'task_name') VALUES ("+ studId +", "+ themeId +", "+ scores[i] +", '"+ taskNames[i] +"');");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void WriteDB(ArrayList<Student> students) throws SQLException {
        var count = 1;
        for (var student : students) {
            WriteDB(student);
            System.out.println("Обработано: " + count + "/" + students.size());
            count++;
        }
        System.out.println("Данные внесены");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM students");

        while(resSet.next())
        {
            int id = resSet.getInt("student_id");
            String  name = resSet.getString("name");
            String  surname = resSet.getString("surname");
            System.out.println( "ID = " + id );
            System.out.println( "name = " + name );
            System.out.println( "surname = " + surname );
            System.out.println();
        }

        System.out.println("Таблица выведена");
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
