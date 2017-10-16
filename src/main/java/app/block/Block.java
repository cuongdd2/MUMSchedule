package app.block;

import java.sql.Date;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data @Getter @RequiredArgsConstructor
public class Block {
  private int id;
  final private String name;
  final private LocalDate startDate;
  final private LocalDate endDate;
}
