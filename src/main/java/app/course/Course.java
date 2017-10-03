package app.course;

import app.model.Level;
import java.util.List;
import lombok.Data;

public @Data class Course {
  private String name;
  private String code;
  private Level level;
  private String description;
  private List<Course> prerequisite;

}
