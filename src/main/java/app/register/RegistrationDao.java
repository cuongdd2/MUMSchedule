package app.register;

import org.sql2o.Connection;
import org.sql2o.ResultSetHandler;
import org.sql2o.Sql2o;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static app.Application.classDao;
import static app.Application.studentDao;

public class RegistrationDao {

    private static String SELECT_BY_ID = "select * from registration where student_id = :Sid";
    private final static String REGISTER = "INSERT INTO registration (student_id, class_id) VALUES(:student, :class)";

    private Sql2o sql2o;

    public RegistrationDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Registration> getRegisteration(int studentid) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(SELECT_BY_ID)
                    .addParameter("Sid", studentid)
                    .executeAndFetch(new RegistrationDataTransfer());
        }
    }

    public int registerToCourse(int clss, int student) {
        int id;
        try (Connection conn = sql2o.beginTransaction()){
            id = conn.createQuery(REGISTER)
                    .addParameter("student", student)
                    .addParameter("class", clss)
                    .executeUpdate().getKey(Integer.class);
            conn.commit();
        }
        return id;
    }

}


class RegistrationDataTransfer implements ResultSetHandler<Registration> {

    @Override
    public Registration handle(ResultSet rs) throws SQLException {
        Registration registration = new Registration();
        registration.setId(rs.getInt("id"));
        registration.setClazz(classDao.getClazz(rs.getInt("class_id")));
        registration.setStudent(studentDao.getStudent(rs.getInt("student_id")));
        return registration;
    }
}