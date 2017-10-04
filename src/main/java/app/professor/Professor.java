package app.professor;

import app.util.Util;
import app.course.Course;
import app.model.Block;
import java.util.List;
import lombok.Data;

public @Data class Professor {
  final String name;
  private String title;
  final String email;
  private String phone;
  private String about;
  private List<Course> preferCourses;
  private List<Block> preferBlocks;


  public Professor(String name) {
    this.name = name;
    this.email = Util.name2Email(name);;
  }
}
