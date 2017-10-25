package app.register;

import spark.Route;

import static app.Application.registrationDao;
import static app.util.JsonUtil.jsonData;

public class RegistrationController {


    public static Route registerCourse = (request, response) -> {

        int cid = Integer.parseInt(request.queryParams("courseid"));
        int sid = Integer.parseInt(request.queryParams("studentid")); // get student id from session

        int res = registrationDao.registerToCourse(cid, sid);

        if(res !=0)
            return jsonData(true,res);
        else
            return jsonData(false,res);

    };
}
