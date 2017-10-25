package app.student;

import static app.Application.*;
import static app.util.JsonUtil.dataToJson;

import app.block.Block;
import app.clazz.Class;
import app.course.Course;
import app.entry.Entry;
import app.professor.Professor;
import app.util.Path;
import app.util.ViewUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Route;

public class StudentController {

  public static Route add = (request, response) -> {
    return null;
  };
  public static Route list = (request, response) -> {
    return null;
  };
  public static Route change = (request, response) -> {
    return null;
  };
  public static Route remove = (request, response) -> {
    return null;
  };

  public static Route schedulePage = (request, response) -> {

      List<Class> classes = new ArrayList<>();
      int stid = 1;// get student id from login session that stores student id when student logged in.
      int eid = studentDao.getStudentEntry(stid);

      // Student entry comes here
      Entry entry = entryDao.getEntryById(eid);

      // List of block that belongs to the entry
      List<Block> blocks = blockDao.getBlocksByEntry(entry);


      // Get all available classes in the block
      for(Block b : blocks) {

          List<Class> cls = classDao.getClassByBlock(b.getId());

          for(Class c : cls) {

              Professor prof = profDao.getProfById(c.getProfessor().getId());
              Course course = courseDao.getCourse(c.getCourse().getId());

              Class newClass = new Class(course, prof, b);
              newClass.setCapacity(c.getCapacity());
              newClass.setEnrolled(c.getEnrolled());
              newClass.setId(c.getId());

              classes.add(newClass);
          }
      }

      Map<String, Object> model = new HashMap<>();
      model.put("classess",classes);
      return ViewUtil.render(request, model, Path.Template.STUDENT_SCHEDULE);
  };

}
