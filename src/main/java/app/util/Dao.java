package app.util;

import java.sql.Timestamp;
import java.time.LocalDate;

public interface Dao {

  default Timestamp toTimestamp(LocalDate d) {
    return Timestamp.valueOf(d.atStartOfDay());
  }
}
