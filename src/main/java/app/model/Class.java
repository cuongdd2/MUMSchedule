package app.model;

import app.course.Course;
import lombok.Data;

@Data
public class Class {
  final Course course;
  final Professor professor;
  private int capacity;
  private int enrolled;
  final Block block;
  private Classroom room;


  public Class(Course course, Professor professor, Block block) {
    this.course = course;
    this.professor = professor;
    this.block = block;
  }
}
