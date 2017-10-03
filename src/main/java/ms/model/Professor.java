package ms.model;

import java.util.List;
import lombok.Data;

public @Data class Professor {
  final String name;
  private String profile;
  final String email;
  private String phone;
  private List<Course> preferCourses;
  private List<Block> preferBlocks;


  public Professor(String name, String email) {
    this.name = name;
    this.email = email;
  }
}
