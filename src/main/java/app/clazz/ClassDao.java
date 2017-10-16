package app.clazz;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

public class ClassDao {

    private Sql2o sql2o;
    private final static String CLASSES_BY_BLOCK = "SELECT * FROM class WHERE block_id = :blockId";

    public ClassDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Class> getClassByBlock(int blockid) {
        List<Class> classes = new ArrayList<>();

        try (Connection conn = sql2o.beginTransaction()) {

            classes = conn.createQuery(CLASSES_BY_BLOCK)
                    .addParameter("blockId", blockid)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Class.class);
        }
        catch(Exception e) {
            System.out.println("Error: "+ e.getMessage() );
        }
        return classes;

    }
}