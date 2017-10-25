package app.block;

import java.sql.Date;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data @Getter @RequiredArgsConstructor
public class Block {
  public static final int MAX_BLOCK_PER_ENTRY = 1 + 1 + 4 + 1;// FPP + MPP + 4 courses + OPT
  private int id;
  final private String name;
  final private LocalDate startDate;
  final private LocalDate endDate;
}
