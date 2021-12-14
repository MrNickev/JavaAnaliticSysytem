
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.sqlite.core.DB;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ClientException, ApiException, SQLException, ClassNotFoundException {

//        LoadDataToDB(ParseAllData());
            DBGetter.Conn();
//            DBGetter.GetStudentAchievmentStat();
            DBGetter.GetCityStat();
//            DBGetter.PrintThemes();
    }


    public static void LoadDataToDB(List<Student> students) throws SQLException, ClassNotFoundException {
        DBStarter.Conn();
//        DBStarter.CreateDB();
        DBStarter.WriteDB((ArrayList<Student>) students);
//        DBStarter.WriteDB(students.get(0));
//        DBStarter.WriteDB(students.get(1));
        DBStarter.CloseDB();
    }

    public static List<Student> ParseAllData() throws IOException, ClientException, ApiException {
        var filename = "D:\\projects\\JavaAnaliticSysytem\\java.csv";
        var students = Parser.ParseCSVFile(filename);

//        for (var student: students) {
//            System.out.println(student.toString(InfoType.AllInfo));
//        }

        var studentsFromVk = new vkApi().getUsersInfoFromGroup("198188261");
        for (var student : students) {
            for (var studentVk : studentsFromVk)
                if (studentVk.getName().equals(student.getName()) && studentVk.getSurname().equals(student.getSurname()))
                    student.addInfo(studentVk);
        }

//        for (var student : students)
//            System.out.println(student.toString(InfoType.SubjectInfo));
        return studentsFromVk;
    }

}
