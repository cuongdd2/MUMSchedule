package app.block;

import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;

@Data @Getter
public class Block {
  private int id;
  private String name;
  private LocalDate startDate;
  private LocalDate endDate;
}
