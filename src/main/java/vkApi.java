import com.google.gson.JsonParser;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public class vkApi {
    private final UserActor actor;
    private int appId = 8001608;
    private String secretKey = "GkCCP9H5VGV9C2ci1vfV";
    private String serviceKey = "553a02c6553a02c6553a02c64855401a8e5553a553a02c634a822c6159e3adbbb97e06c";
    private HttpTransportClient transportClient;
    private VkApiClient vkapi;
    private String accessToken = "3ebeabad8fbd76b9cc2596c7223e5d1f33b6ccc3077d3e3811a17149a8f4154e94b660a1fc007c359a157";

    public vkApi() throws IOException {
        transportClient = HttpTransportClient.getInstance();
        this.vkapi = new VkApiClient(transportClient);
        this.actor = new UserActor(appId, accessToken);
    }

    private List<Integer> getGroupMembers(String group_id, int offset) throws IOException, ClientException, ApiException {

        var list = vkapi.groups().getMembers(actor).groupId(group_id).offset(offset).execute().getItems();
        return list;
    }

    public List<Student> getUsersInfoFromGroup(String group_id) throws IOException, ClientException, ApiException {
        var usersId = new ArrayList<Integer>();
        for (var ofset=0; ofset<3000; ofset+=1000)
            usersId.addAll(getGroupMembers(group_id, ofset));
        var stringsId = new ArrayList<String>();
        var studentList = new ArrayList<Student>();
        var userList = new ArrayList<GetResponse>();
        usersId.forEach(id -> stringsId.add(String.valueOf(id)));
        if (stringsId.size() <= 1000)
            userList = (ArrayList<GetResponse>) vkapi.users().get(actor).userIds(stringsId).fields(Fields.BDATE, Fields.CITY, Fields.PHOTO_200).execute();
        else
            for (var offset = 0; offset < stringsId.size(); offset += 1000) {
                var endIndex = stringsId.size() - offset > 1000 ? offset + 1000 : stringsId.size();
                var sublist = stringsId.subList(offset, endIndex);
                userList.addAll(vkapi.users().get(actor).userIds(sublist).fields(Fields.BDATE, Fields.CITY, Fields.PHOTO_MAX, Fields.SEX).execute());
            }

        for (var user : userList) {
            var json = new JsonParser().parse(user.toString()).getAsJsonObject();
            if (json.get("first_name") == null || json.get("last_name") == null)
                continue;
            GregorianCalendar bdate = null;
            if (json.get("bdate") != null) {
                var str = json.get("bdate").getAsString();
                var parsedDate = json.get("bdate").getAsString().split("\\.");
                var date = new ArrayList<Integer>();
                Arrays.stream(parsedDate).forEach(s -> date.add(Integer.parseInt(s)));
                if (date.size() == 2)
                    bdate = new GregorianCalendar(1900, date.get(1), date.get(0));
                else bdate = new GregorianCalendar(date.get(2), date.get(1), date.get(0));
            }
            String city = null;
            var flag = json.get("city");
            if (flag != null) {
                var jsonCity = new JsonParser().parse(json.get("city").toString()).getAsJsonObject().get("title");
                city = jsonCity == null ? null : jsonCity.toString();
            }
            var photoJson = json.get("photo_max");
            var photo = photoJson == null || photoJson.getAsString().equals("") ? null : photoJson.getAsString();

            String sex = null;
            if (json.has("sex")){
                if (json.get("sex").getAsInt() == 2)
                    sex = "Male";
                else if (json.get("sex").getAsInt() == 1)
                    sex = "Female";
            }

            studentList.add(new Student(Integer.parseInt(json.get("id").getAsString()), json.get("first_name").getAsString(), json.get("last_name").getAsString(), city, bdate, photo, sex));
        }
        return studentList;
    }

//    public String getUserInfo(String userId, String group_id) throws IOException, ClientException, ApiException {
//        var usersId = getGroupMembers(group_id);
//        var stringsId = new ArrayList<String>();
//        usersId.forEach(id -> stringsId.add(String.valueOf(id)));
//        var userList = vkapi.users().get(actor).userIds(stringsId).fields(Fields.BDATE, Fields.CITY, Fields.ABOUT, Fields.NICKNAME, Fields.PHOTO_200, Fields.RELATION, Fields.MUSIC, Fields.MOVIES).execute();
//        for (var user : userList) {
//            var json = new JsonParser().parse(user.toString()).getAsJsonObject();
//            if (json.get("id").getAsString().equals(userId))
//                return json.get("city").toString();
//        }
//        return new String();
//    }
}


/* https://oauth.vk.com/authorize?client_id=8001608&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=offline,groups&response_type=token&v=5.126*/