package app.clazz;

import app.block.Block;
import app.course.Course;
import app.professor.Professor;
import lombok.Data;

@Data
public class Class2 {
    private int id;
    private int course_id;
    private int prof_id;
    private int capacity;
    private int enrolled;
    private int block_id;

    public Class2(int course_id, int prof_id, int block_id) {
        this.course_id = course_id;
        this.prof_id = prof_id;
        this.block_id = block_id;
    }


}
