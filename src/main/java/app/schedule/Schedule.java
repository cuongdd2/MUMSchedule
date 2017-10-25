package app.schedule;


import app.block.Block;
import app.clazz.Class;
import app.entry.Entry;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Schedule {
  private Entry entry;
  private List<Block> blocks;
  private Map<Block, List<Class>> classes;
}
