package app.profile;

import app.student.Student;
import app.util.Path;
import app.util.ViewUtil;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static app.Application.studentDao;

public class ProfileController {

    public static Route profilePage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();

        int userid = 1;//Integer.parseInt(request.session().attribute("currentUser"));
        Student student = studentDao.getStudentById(userid);


        model.put("student", student);


        return ViewUtil.render(request, model, Path.Template.PROFILE);
    };
}
