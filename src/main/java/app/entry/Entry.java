package app.entry;

import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data @Getter @Setter @RequiredArgsConstructor
public class Entry {
  private int id;
  final private String name;
  final private LocalDate startDate;
 // private int numOfStudent;
}
