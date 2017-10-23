package app.clazz;

import app.block.Block;
import app.course.Course;
import app.professor.Professor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class Class {
  public static final float STUDENT_PER_CLASS = 24.0f;

  final private Course course;
  final private Professor professor;
  final private Block block;
  private int capacity;
  private int enrolled;
  private int id;

  public static int classByStudent(int student) {
    return Math.max(1, Math.round(student/STUDENT_PER_CLASS));
  }


  public int getAvailable(){
      return getCapacity() - getEnrolled();
  }
}
