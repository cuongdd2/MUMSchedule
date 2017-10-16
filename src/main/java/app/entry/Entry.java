package app.entry;

import java.sql.Date;
import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class Entry {
  private int id;
  final private String name;
  final private LocalDate startDate;
  private int numOfStudent;
}
