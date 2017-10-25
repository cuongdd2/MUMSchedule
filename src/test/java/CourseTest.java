import app.course.Course;
import app.course.CourseDao;
import java.util.List;
import org.junit.Test;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class CourseTest {

  Sql2o sql2o = new Sql2o("jdbc:mysql://104.207.139.224:3306/cs425?relaxAutoCommit=true", "cs425", "mum");
  CourseDao courseDao = new CourseDao(sql2o);

  @Test
  public void test1() {
    List<Course> courses = courseDao.get500();
    courses.forEach(c -> assertEquals(c.getLevel(), "L500"));
  }
}
