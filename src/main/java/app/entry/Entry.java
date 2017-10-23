package app.entry;

import java.sql.Date;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data @Getter @RequiredArgsConstructor
public class Entry {
  private int id;
  final private String name;
  final private LocalDate start_date;
  private int numOfStudent;
}
