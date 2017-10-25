package app.util;

import app.block.Block;
import app.block.BlockDao;
import app.entry.Entry;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DBTools {

  public static void main(String[] args) {
    Sql2o sql2o = new Sql2o("jdbc:mysql://104.207.139.224:3306/cs425?relaxAutoCommit=true", "cs425", "mum");
    BlockDao blockDao = new BlockDao(sql2o);
    List<Block> blocks = blockDao.getBlocksByEntry(new Entry("August 2017", LocalDate.of(2017, 8, 3)));
    System.out.println(blocks.size());
    blocks.forEach(System.out::println);
//    insertProfCourse(sql2o);
//    insertProfBlock(sql2o);
    int i = 18;
    i *= 0.2;
    System.out.println(i);
  }
  private static void insertProfCourse(Sql2o sql2o) {
    try (Connection conn = sql2o.open()) {
      for (int pid = 1; pid <= 26; pid++) {
        int n = (int)(1.3 * Math.random());
        ArrayList<Integer> courses = new ArrayList<>(
            IntStream.range(1, 34).boxed().collect(Collectors.toList()));
        for (int i = 0; i < n; i++) {
          Integer cid = (int)(courses.size() * Math.random() + 1);
          courses.remove(cid);
          conn.createQuery("insert into prof_course values(:pid, :cid)")
              .addParameter("pid", pid)
              .addParameter("cid", cid)
              .executeUpdate();
        }
      }
      conn.commit();
    }
  }

  private static void insertProfBlock(Sql2o sql2o) {
    try (Connection conn = sql2o.open()) {
      for (int pid = 1; pid <= 26; pid++) {
        int n = (int)(7 * Math.random() + 1);
        ArrayList<Integer> blocks = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        for (int i = 0; i < n; i++) {
          Integer bid = blocks.get((int)(blocks.size() * Math.random()));
          blocks.remove(bid);
          conn.createQuery("insert into prof_block values(:pid, :bid)")
              .addParameter("pid", pid)
              .addParameter("bid", bid)
              .executeUpdate();
        }
      }
      conn.commit();
    }
  }

  private static void insertPreCourse(Sql2o sql2o) {
    try (Connection conn = sql2o.open()) {
      for (int pid = 1; pid <= 26; pid++) {
        int n = (int)(7 * Math.random() + 1);
        ArrayList<Integer> blocks = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        for (int i = 0; i < n; i++) {
          Integer bid = blocks.get((int)(blocks.size() * Math.random()));
          blocks.remove(bid);
          conn.createQuery("insert into prof_block values(:pid, :bid)")
              .addParameter("pid", pid)
              .addParameter("bid", bid)
              .executeUpdate();
        }
      }
      conn.commit();
    }
  }
}
