package app.register;

import app.clazz.Class;
import app.login.LoginController;
import app.student.Student;
import app.user.User;
import app.util.Path;
import app.util.ViewUtil;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Application.registrationDao;
import static app.Application.studentDao;
import static app.util.JsonUtil.jsonData;
import static spark.Spark.halt;

public class RegistrationController {

    public static Route list = (request, response) -> {
        Map<String, Object> model = new HashMap<>();

        String email = request.session().attribute("currentUser");
        if (email == null) halt(401, "Un-authorized request");
        Student student = studentDao.getUserByUsername(email);

        List<Class> sections = registrationDao.getScheduleByEntry(student.getEntry());
        model.put("sections", sections);


        return ViewUtil.render(request, model, Path.Template.REGISTERS);
    };

    public static Route registerSection = (request, response) -> {

        int cid = Integer.parseInt(request.queryParams("class_id"));

        int sid = 26;

        int res = registrationDao.registerToCourse(cid, sid);

        if(res !=0)
            return jsonData(true,res);
        else
            return jsonData(false,res);

    };
}
