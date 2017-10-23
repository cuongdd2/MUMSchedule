package app.schedule;

import app.block.Block;
import app.clazz.Class;
import app.course.Course;
import app.entry.Entry;
import app.student.Student;
import app.student.Track;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static app.Application.*;

public class ScheduleController {


  public void generateSchedule() {

    List<Entry> entries = new ArrayList<>();
    Course fppCourse = courseDao.getId("CS390");
    Course mppCourse = courseDao.getId("CS401");

    for (Entry e : entries) {

      List<Student> students = studentDao.getStudentsByEntry(e);
      List<Block> blocks = blockDao.getBlocksByEntry(e);
      List<Student> fppTrack = students.stream()
          .filter(s -> s.getTrack() == Track.FPP)
          .collect(Collectors.toList());
      List<Student> mppTrack = students.stream()
          .filter(s -> s.getTrack() == Track.MPP)
          .collect(Collectors.toList());

      int blockIndex = 1;
      int totalStudent = 0;
      for (Block block : blocks) {
        List<Course> coursesInBlock = courseDao.getCoursesByProfList(profDao.getProfByBlock(block));
        // we sort course with least number of prerequisite first
        coursesInBlock.sort(Comparator.comparingInt(Course::getPreNo).reversed());
        if (blockIndex == 1) {
          int fppClass = Class.classByStudent(fppTrack.size());
          Class clazz = new Class(fppCourse, null, block);
          int mppClass = Class.classByStudent(mppTrack.size());
        } else if (blockIndex == 2) {
          int mppClass = Class.classByStudent(fppTrack.size());
          totalStudent = mppTrack.size();
        } else {
          totalStudent = fppTrack.size() + mppTrack.size();
        }
        for(Course course : coursesInBlock) {

        }
        blockIndex++;
      }

    }
  }

  private List<Block> getBlocksByEntry(Entry e) {
    return null;
  }
}
