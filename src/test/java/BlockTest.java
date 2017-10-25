import app.block.Block;
import app.block.BlockDao;
import app.entry.Entry;
import java.time.LocalDate;
import java.util.List;
import org.junit.Test;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class BlockTest {

  Sql2o sql2o = new Sql2o("jdbc:mysql://104.207.139.224:3306/cs425?relaxAutoCommit=true", "cs425", "mum");
  BlockDao blockDao = new BlockDao(sql2o);

  @Test
  public void test1() {
    List<Block> blocks = blockDao.getBlocksByEntry(new Entry("August 2017", LocalDate.of(2017, 8, 3)));
    assertEquals(blocks.size(), 7);
  }
  @Test
  public void test2() {
    List<Block> blocks = blockDao.getBlocksByEntry(new Entry("August 2017", LocalDate.of(2017, 8, 3)));
    LocalDate startDate = blocks.get(0).getStartDate();
    assertEquals(startDate.getYear(), 2017);
    assertEquals(startDate.getMonthValue(), 8);
    assertTrue(startDate.getDayOfMonth() >= 3);
  }

}
