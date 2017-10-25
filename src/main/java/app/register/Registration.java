package app.register;

import app.clazz.Class;
import app.student.Student;
import lombok.Data;

@Data
public class Registration {
  private int id;
  private Student student;
  private Class clazz;
}
