package ms.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Student {
  final private String name;
  final private int id;
  final private String email;
  final private LocalDate dob;
  final private Nationality nationality;
  final private Entry entry;
  private Track track;

  public Student(String name, int id, String email, LocalDate dob, Nationality nationality,
      Entry entry) {
    this.name = name;
    this.id = id;
    this.email = email;
    this.dob = dob;
    this.nationality = nationality;
    this.entry = entry;
  }
}
