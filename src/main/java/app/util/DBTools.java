package app.util;

import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomStringUtils.*;

import app.block.Block;
import app.block.BlockDao;
import app.entry.Entry;
import app.student.Student;
import app.student.StudentDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DBTools {

  private static Sql2o sql2o = new Sql2o(
      "jdbc:mysql://104.207.139.224:3306/cs425?relaxAutoCommit=true", "cs425", "mum");
  private static BlockDao blockDao = new BlockDao(sql2o);
  private static StudentDao studentDao = new StudentDao(sql2o);

  public static void main(String[] args) {
    createStudent();
  }

  private static void createStudent() {
    try (Connection conn = sql2o.open()) {
      for (int i = 0; i < 100; i++) {
        String name = randomName();
        conn.createQuery(
            "insert into student(name, email, dob, entry_id, track) values(:n, :e, :d, :eid, :t)")
            .addParameter("n", name)
            .addParameter("e", Util.name2Email(name))
            .addParameter("d", rndDob())
            .addParameter("eid", 1)
            .addParameter("t", rndTrack())
            .executeUpdate();
      }
      conn.commit();
    }
  }

  private static String randomName() {
    return randomAlphabetic(1).toUpperCase() + randomAlphabetic(nextInt(5) + 2) + " " + randomAlphabetic(1).toUpperCase() + randomAlphabetic(nextInt(5) + 2);
  }

  private static LocalDate rndDob() {
    return LocalDate.of(1970 + nextInt(25), nextInt(12) + 1, nextInt(28) + 1);
  }

  private static String rndTrack() {
    return nextInt(100) % 2 == 0 ? "MPP" : "FPP";
  }

  private static void insertProfCourse(Sql2o sql2o) {
    try (Connection conn = sql2o.open()) {
      for (int pid = 1; pid <= 26; pid++) {
        int n = (int) (1.3 * Math.random());
        ArrayList<Integer> courses = new ArrayList<>(
            IntStream.range(1, 34).boxed().collect(Collectors.toList()));
        for (int i = 0; i < n; i++) {
          Integer cid = (int) (courses.size() * Math.random() + 1);
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
        int n = (int) (7 * Math.random() + 1);
        ArrayList<Integer> blocks = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        for (int i = 0; i < n; i++) {
          Integer bid = blocks.get((int) (blocks.size() * Math.random()));
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
        int n = (int) (7 * Math.random() + 1);
        ArrayList<Integer> blocks = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        for (int i = 0; i < n; i++) {
          Integer bid = blocks.get((int) (blocks.size() * Math.random()));
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
