package app.clazz;

import app.block.Block;
import app.course.Course;
import app.professor.Professor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class Class {
  final private Course course;
  final private Professor professor;
  final private Block block;
  private int capacity;
  private int enrolled;
}
