package app.student;

import app.entry.Entry;
import app.util.Util;
import java.time.LocalDate;
import lombok.Data;

@Data
public class Student {
  public static final float OPT_STUDENT = 0.3f;
  private int id;
  final private String name;
  private String email;
  final private Entry entry;
  final private LocalDate dob;
  private String track;

  public Student(String name, LocalDate dob, Entry entry) {
    this.name = name;
    this.email = Util.name2Email(name);
    this.dob = dob;
    this.entry = entry;
  }

}
