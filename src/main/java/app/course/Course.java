package app.course;

import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data @Getter
public class Course {
  private int id;
  private String name;
  private String code;
  private Level level;
  private String desc;
  private int preNo;
  private List<Course> prerequisite;
}
