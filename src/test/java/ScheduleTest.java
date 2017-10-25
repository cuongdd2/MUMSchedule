import app.block.BlockDao;
import app.clazz.Class;
import app.clazz.ClassDao;
import app.course.CourseDao;
import app.entry.EntryDao;
import app.professor.ProfDao;
import app.schedule.ScheduleController;
import app.student.StudentDao;
import app.user.UserDao;
import java.util.List;
import org.junit.Test;
import org.sql2o.Sql2o;

import static app.Application.*;

import static org.junit.Assert.*;

public class ScheduleTest {

  static Sql2o sql2o = new Sql2o("jdbc:mysql://104.207.139.224:3306/cs425?relaxAutoCommit=true", "cs425", "mum");

  static void init() {
    blockDao = new BlockDao(sql2o);
    courseDao = new CourseDao(sql2o);
    entryDao = new EntryDao(sql2o);
    profDao = new ProfDao(sql2o);
    studentDao = new StudentDao(sql2o);
    userDao = new UserDao(sql2o);
    classDao = new ClassDao(sql2o);
  }

  @Test
  public void test1() {
    init();
    ScheduleController.generateSchedule();
    List<Class> list = classDao.getAll();
    assertTrue(list.size() > 0);
  }
}
