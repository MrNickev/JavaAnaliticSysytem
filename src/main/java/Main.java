
import com.google.gson.JsonParser;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClientException, ApiException {
        var filename = "D:\\projects\\JavaAnaliticSysytem\\java.csv";
        var students = Parser.ParseCSVFile(filename);
//        for (var student: students) {
//            System.out.println(student.toString(InfoType.AllInfo));
//        }

        var studentsFromVk = new vkApi().getUsersInfoFromGroup("208866240");
        for (var student : students) {
            for (var studentVk : studentsFromVk)
                if (studentVk.getName().equals(student.getName()) && studentVk.getSurname().equals(student.getSurname()))
                    student.addInfo(studentVk);
        }

        for (var student : students)
            System.out.println(student.toString(InfoType.AllInfo));

//        var stud = vkapi.getUserInfo("3228491", "198188261");
//        var stud = vkapi.getUserInfo("185118702", "208866240"); //my id
//        System.out.println(stud);
//        for (var stud : vkapi.getUsersInfoFromGroup("208866240"))
//            System.out.println(stud.toString(InfoType.PrivateInfo));
//        var user = "{\"bdate\":\"27.1.2003\",\"city\":{\"id\":1253,\"title\":\"Трехгорный\"},\"first_name\":\"Никита\",\"id\":185118702,\"last_name\":\"Зайцев\",\"can_access_closed\":true,\"is_closed\":false}";
//        System.out.println(new JsonParser().parse(user).getAsJsonObject().get("bdate").getAsString());
//        System.out.println(stud.getName() + " " + stud.getSurname() + " " + stud.getBirthdate().toString());
    }

}
