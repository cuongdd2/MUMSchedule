package app.entry;

import java.sql.Timestamp;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class Entry {
  private int id;
  final private String name;
  final private Date startDate;
  private int numOfStudent;

  public Timestamp getTimestamp() {
    return Timestamp.from(startDate.toInstant());
  }
}
