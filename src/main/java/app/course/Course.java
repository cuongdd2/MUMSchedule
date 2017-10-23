package app.course;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class Course {
  public static final String FPP_CODE = "CS390";
  public static final String MPP_CODE = "CS401";
  private int id;
  final private String name;
  final private String code;
  final private String level;
  private String desc;
  private int preNo;
  private List<Course> prerequisite;
}
