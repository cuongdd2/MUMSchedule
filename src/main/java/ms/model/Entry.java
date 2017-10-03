package ms.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Entry {
  private String name;
  private LocalDate startDate;
  private int numOfStudent;
}
