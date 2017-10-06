package app.course;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data @Getter @RequiredArgsConstructor
public class Course {
  private int id;
  final private String name;
  final private String code;
  final private Level level;
  private String desc;
  private int preNo;
  private List<Course> prerequisite;
}
