package app.schedule;

import app.block.Block;
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
      for (Block b : blocks) {
        List<Course> coursesInBlock = courseDao.getCoursesByProfList(profDao.getProfByBlock(b));
        // we sort course with least number of prerequisite first
        coursesInBlock.sort(Comparator.comparingInt(Course::getPreNo).reversed());

        blockIndex++;
      }

    }
  }

  private List<Block> getBlocksByEntry(Entry e) {
    return null;
  }
}
