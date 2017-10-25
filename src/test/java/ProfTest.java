import static org.junit.Assert.assertEquals;

import app.professor.ProfDao;
import app.professor.Professor;
import org.junit.Test;
import org.sql2o.Sql2o;

public class ProfTest {

  Sql2o sql2o = new Sql2o("jdbc:mysql://104.207.139.224:3306/cs425?relaxAutoCommit=true", "cs425", "mum");
  ProfDao profDao = new ProfDao(sql2o);

  @Test
  public void test1() {
    Professor p = profDao.getProfById(10);
    assertEquals(p.getName(), "Clyde Ruby");
  }
}
