package app.student;

import app.entry.Entry;
import java.time.LocalDate;
import lombok.Data;

@Data
public class Student {
  final private String name;
  final private int id;
  final private String email;
  final private LocalDate dob;
  final private Entry entry;
  private Track track;

  public Student(String name, int id, String email, LocalDate dob, Entry entry) {
    this.name = name;
    this.id = id;
    this.email = email;
    this.dob = dob;
    this.entry = entry;
  }
}
