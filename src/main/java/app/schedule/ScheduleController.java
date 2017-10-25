package app.schedule;

import static app.Application.blockDao;
import static app.Application.classDao;
import static app.Application.courseDao;
import static app.Application.entryDao;
import static app.Application.profDao;
import static app.Application.studentDao;

import app.block.Block;
import app.clazz.Class;
import app.course.Course;
import app.entry.Entry;
import app.professor.Professor;
import app.student.Student;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import spark.Route;
import static app.util.JsonUtil.*;
public class ScheduleController {

  static Course fppCourse;
  static Course mppCourse;


  public static Route generate = (request, response) -> {
    generateSchedule();
    return dataToJson(true);
  };
  public static Route reset = (request, response) -> {

    classDao.removeAll();
    return dataToJson(true);
  };

  public static Route list = (request, response) -> {
    List<Entry> entries = entryDao.list();
    List<Schedule> schedules = new ArrayList<>();
    for (Entry e : entries) {
      Schedule s = new Schedule();
      s.setEntry(e);
      List<Block> blocks = blockDao.getBlocksByEntry(e);
      s.setBlocks(blocks);
      Map<Block, List<Class>> classes = new HashMap<>();
      for (Block b : blocks) {
        classes.put(b, classDao.getClassByBlock(b.getId()));
      }
      schedules.add(s);
    }
    return dataToJson(schedules);
  };

  static private void generateSchedule() {

    List<Entry> entries = entryDao.list();
    fppCourse = courseDao.getId("CS390");
    mppCourse = courseDao.getId("CS401");

    for (Entry e : entries) {
      List<Student> students = studentDao.getStudentsByEntry(e);
      if (students.isEmpty()) {
        continue;
      }
      List<Block> blocks = blockDao.getBlocksByEntry(e);
      int fppTrack = (int) students.stream()
          .filter(s -> "FPP".equals(s.getTrack()))
          .count();
      int mppTrack = (int) students.stream()
          .filter(s -> "MPP".equals(s.getTrack()))
          .count();

      int blockIndex = 1;
      for (Block block : blocks) {
        if (blockIndex == 1) {
          createClass(block, fppCourse, Class.classByStudent(fppTrack));
          createClass(block, mppCourse, Class.classByStudent(mppTrack));
        } else if (blockIndex == 2) {
          createClass(block, mppCourse, Class.classByStudent(fppTrack));
          createPreClass(block, Class.classByStudent(mppTrack));
        } else if (blockIndex == 3) {
          createPreClass(block, Class.classByStudent(fppTrack));
          createRandomClass(block, Class.classByStudent(mppTrack));
        } else {
          createRandomClass(block, Class.classByStudent(fppTrack + mppTrack));
        }
        blockIndex++;
        if (blockIndex == 6) {
          mppTrack *= Student.OPT_STUDENT;
        }
        if (blockIndex == 7) {
          fppTrack *= Student.OPT_STUDENT;
        }
      }

    }
  }

  static private void createClass(Block b, Course c, int no) {
    List<Professor> profs = profDao.getProfByBlockAndCourse(b.getId(), c.getId());
    while (no-- > 0) {
      int pid = -1;// in case we run out of professor, we can hire more later to fill in where pid = -1
      if (!profs.isEmpty()) {
        pid = profs.remove(rnd(profs)).getId();
      }
      classDao.create(c.getId(), pid, b.getId());
    }
  }

  // offer pre-requisite course
  static private void createPreClass(Block b, int no) {
    create(courseDao.getPre(b), b, no);
  }

  static private void createRandomClass(Block b, int no) {
    create(courseDao.getRandom(b), b, no);
  }

  static private void create500Class(Block b, int no) {
    create(courseDao.get500(), b, no);
  }

  static private void create(List<Course> courses, Block b, int no) {
    List<Integer> profId = new ArrayList<>();
    Map<Course, List<Professor>> map = new LinkedHashMap<>();
    for (Course c : courses) {
      map.put(c, profDao.getProfByBlockAndCourse(b.getId(), c.getId()));
    }
    // we sort course by number of prof can teach
    List<Map.Entry<Course, List<Professor>>> entries = map.entrySet().stream()
        .sorted(Comparator.comparingInt(o -> o.getValue().size())).collect(Collectors.toList());
    for (Map.Entry<Course, List<Professor>> entry : entries) {
      if (no <= 0) break;
      Course c = entry.getKey();
      List<Professor> ps = entry.getValue().stream()
          .filter(p -> !profId.contains(p.getId()))
          .collect(Collectors.toList());
      if (!ps.isEmpty()) {
        int pid = ps.get(rnd(ps)).getId();
        profId.add(pid);
        classDao.create(c.getId(), pid, b.getId());
        no--;
      }
    }
  }

  static private int rnd(List list) {
    return (int) (Math.random() * list.size());
  }
}