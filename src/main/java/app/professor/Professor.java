package app.professor;

import app.util.Util;
import app.course.Course;
import app.block.Block;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data @Getter
public class Professor {
  private int id;
  final String name;
  private String title;
  final String email;
  private String phone;
  private String about;
  //private List<Course> preferCourses;
  //private List<Block> preferBlocks;


  public Professor(String name , String title , String phone , String about) {
    this.name = name;
    this.title = title;
   // this.email = email;
    this.about = about;
    this.phone = phone;
    this.email = Util.name2Email(name);;
  }
}
