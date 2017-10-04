package app.model;

import app.student.Student;
import lombok.Data;

@Data
public class Registration {
  private Student student;
  private Class clazz;
}
