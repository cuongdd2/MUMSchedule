package app.schedule;

import app.block.Block;
import app.clazz.Class;
import app.course.Course;
import app.entry.Entry;
import app.professor.Professor;
import app.student.Student;
import app.student.Track;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static app.Application.*;

public class ScheduleController {
  Course fppCourse;
  Course mppCourse;
  public void generateSchedule() {

    List<Entry> entries = new ArrayList<>();
    fppCourse = courseDao.getId("CS390");
    mppCourse = courseDao.getId("CS401");

    for (Entry e : entries) {

      List<Student> students = studentDao.getStudentsByEntry(e);
      List<Block> blocks = blockDao.getBlocksByEntry(e);
      int fppTrack = (int)students.stream()
          .filter(s -> s.getTrack() == Track.FPP)
          .count();
      int mppTrack = (int)students.stream()
          .filter(s -> s.getTrack() == Track.MPP)
          .count();

      int blockIndex = 1;
      for (Block block : blocks) {
        if (blockIndex == 1) {
          createClass(block, fppCourse, Class.classByStudent(fppTrack));
          createClass(block, mppCourse, Class.classByStudent(mppTrack));
        } else if (blockIndex == 2) {
          createClass(block, mppCourse, Class.classByStudent(fppTrack));
          createPreClass(block, Class.classByStudent(mppTrack) + 1);
        } else if (blockIndex == 3) {
          createPreClass(block, Class.classByStudent(fppTrack) + 1);
          createRandomClass(block, Class.classByStudent(mppTrack) + 1);
        } else {
          createRandomClass(block, Class.classByStudent(fppTrack + mppTrack) + 1);
        }
        blockIndex++;
        if (blockIndex == 6) mppTrack *= Student.OPT_STUDENT;
        if (blockIndex == 7) fppTrack *= Student.OPT_STUDENT;
      }

    }
  }

  private void createClass(Block b, Course c, int no) {
    List<Professor> profs = profDao.getProfByBlockAndCourse(b.getId(), c.getId());
    for (int i = 0; i < no; i++) {
      classDao.create(new Class(c, profs.get(i), b));
    }
  }

  // offer pre-requisite course
  private void createPreClass(Block b, int no) {
    create(courseDao.getPre(), b, no);
  }

  private void createRandomClass(Block b, int no) {
    create(courseDao.getRandom(), b, no);
  }

  private void create500Class(Block b, int no) {
    create(courseDao.get500(), b, no);
  }

  private void create(List<Course> courses, Block b, int no) {
    List<Integer> profId = new ArrayList<>();
    while(no-- > 0 && courses.size() > 0) {
      Course c = courses.remove(rnd(courses));
      List<Professor> profs = profDao.getProfByBlockAndCourse(b.getId(), c.getId()).stream()
          .filter(p -> !profId.contains(p.getId()))
          .collect(Collectors.toList());
      Professor p = profs.get(rnd(profs));
      classDao.create(new Class(c, p, b));
      profId.add(p.getId());
    }
  }

  private int rnd(List list) {
    return (int)(Math.random() * list.size());
  }
}
