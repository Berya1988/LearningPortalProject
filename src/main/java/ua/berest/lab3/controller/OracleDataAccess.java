package ua.berest.lab3.controller;

import org.apache.log4j.Logger;
import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

/**
 * Created by Oleg on 26.01.2016.
 */
public class OracleDataAccess implements ModelDataAccess {
    static final Logger logger = Logger.getLogger(OracleDataAccess.class);
    private static final OracleDataAccess instance = new OracleDataAccess();
    private DataSource ds;
    private Context ctx;
    private Hashtable ht = new Hashtable();

    private OracleDataAccess() {
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            ctx = new InitialContext(ht);
            ds = (javax.sql.DataSource) ctx.lookup("JNDI_Name0");
        } catch (NamingException e) {
            logger.error("Threw a NamingException in OracleDataAccess class::" + e.getMessage(), e);
        }
    }

    public static OracleDataAccess getInstance() {
        return instance;
    }

    private Connection connect() throws DataAccessException {
        Connection connection;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("Problems with getting connection", e);
        }
        return connection;
    }

    private void disconnect(Connection connection, ResultSet result, Statement statement) throws DataAccessException {
        try {
            if(statement != null)
                statement.close();
            if(connection != null)
                connection.close();
            if(result != null)
                result.close();
        } catch (SQLException e) {
            throw new DataAccessException("Can't close statement or connections or resultset", e);
        }
    }

    public List<Student> getAllStudents() throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Student> lStudent = new ArrayList<Student>();
        try {
            statement = connection.prepareStatement("SELECT * FROM STUDENTS ORDER BY STUDENT_FIO");
            result = statement.executeQuery();
            while(result.next()){
                lStudent.add(parseStudent(result));
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lStudent;
    }

    public List<Student> getAllStudentsInCourse(int courseId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Student> lStudent = new ArrayList<Student>();
        try {
            statement = connection.prepareStatement("SELECT * FROM STUDENTS, ENROLLMENT WHERE ENROLLMENT.STUDENT_ID = STUDENTS.STUDENT_ID AND ENROLLMENT.COURSE_ID = ? ORDER BY STUDENT_FIO");
            statement.setInt(1, courseId);
            result = statement.executeQuery();
            while(result.next()){
                lStudent.add(parseStudent(result));
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lStudent;
    }

    public List<Student> getAllStudentsOutOfCourse(int courseId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Student> lStudent = new ArrayList<Student>();
        try {
            statement = connection.prepareStatement("(SELECT s.STUDENT_ID, s.STUDENT_FIO, s.STUDENT_GROUP, s.MAIL, s.PHONE_NUMBER, s.ADDRESS FROM STUDENTS s) MINUS (SELECT s.STUDENT_ID, s.STUDENT_FIO, s.STUDENT_GROUP, s.MAIL, s.PHONE_NUMBER, s.ADDRESS FROM STUDENTS s, ENROLLMENT WHERE ENROLLMENT.STUDENT_ID = s.STUDENT_ID AND ENROLLMENT.COURSE_ID = ?) ORDER BY STUDENT_FIO");
            statement.setInt(1, courseId);
            result = statement.executeQuery();
            while(result.next()){
                lStudent.add(parseStudent(result));
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lStudent;
    }

    public Map<Integer,String> getAllLocations() throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        Map<Integer,String> lMap = new HashMap<Integer, String>();
        try {
            statement = connection.prepareStatement("SELECT LOCATION_ID, NAME FROM LOCATIONS WHERE IS_LAST_LEVEL = 'false' ORDER BY NAME");
            result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("LOCATION_ID");
                String name = result.getString("NAME");
                logger.info("id = " + id + ", name  = " + name);
                lMap.put(id, name);
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lMap;
    }

    public List<Grade> getAllGradesByCourseAndStudent(int courseId, int studentId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Grade> lGrade = new ArrayList<Grade>();
        try {
            statement = connection.prepareStatement("SELECT * FROM GRADES WHERE STUDENT_ID = ? AND COURSE_ID = ?");
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            result = statement.executeQuery();
            while(result.next()){
                lGrade.add(parseGrade(result));
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lGrade;
    }

    public void addStudent(Student student) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO STUDENTS (STUDENT_ID, STUDENT_FIO, STUDENT_GROUP, MAIL, PHONE_NUMBER, ADDRESS) VALUES (NULL, ?, ?, ?, ?, ?)");
            statement.setString(1, student.getFio());
            statement.setString(2, student.getGroup());
            statement.setString(3, student.getMail());
            statement.setString(4, student.getPhone());
            statement.setString(5, student.getAddress());
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't insert new data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void addCourse(Course course) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO COURSES(COURSE_ID, LOCATION_ID, NAME, DESCRIPTION, TEACHER) VALUES (NULL, ?, ?, ?, ?)");
            statement.setInt(1, course.getLocationId());
            statement.setString(2, course.getName());
            statement.setString(3, course.getDescription());
            statement.setString(4, course.getTeacher());
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't insert new data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void addGrade(Grade grade) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO GRADES(GRADE_ID, STUDENT_ID, COURSE_ID, DATE_OF_EVALUATION, CREDITS, DESCRIPTION) VALUES (NULL, ?, ?, ?, ?, ?)");
            statement.setInt(1, grade.getStudentId());
            statement.setInt(2, grade.getCourseId());
            statement.setDate(3, grade.getDate());
            statement.setInt(4, grade.getCredits());
            statement.setString(5, grade.getDescription());
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't insert new data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void addLocation(Location location) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            if (location.getParentId() == 0) {
                statement = connection.prepareStatement("INSERT INTO LOCATIONS(LOCATION_ID, NAME, PARENT_ID, IS_LAST_LEVEL, DESCRIPTION) VALUES (NULL, ?, NULL, ?, ?)");
                statement.setString(2, String.valueOf(location.getCourse()));
                statement.setString(3, location.getDescription());
            } else {
                statement = connection.prepareStatement("INSERT INTO LOCATIONS(LOCATION_ID, NAME, PARENT_ID, IS_LAST_LEVEL, DESCRIPTION) VALUES (NULL, ?, ?, ?, ?)");
                statement.setInt(2, location.getParentId());
                statement.setString(3, String.valueOf(location.getCourse()));
                statement.setString(4, location.getDescription());
            }
            statement.setString(1, location.getName());

            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't insert new data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void removeStudent(int studentId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM STUDENTS WHERE STUDENT_ID = ?");
            statement.setInt(1, studentId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't delete data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void removeLocation(int locationId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM LOCATIONS WHERE LOCATION_ID = ?");
            statement.setInt(1, locationId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't delete data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void removeCourse(int courseId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM COURSES WHERE COURSE_ID = ?");
            statement.setInt(1, courseId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't delete data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void removeGrade(int gradeId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM GRADES WHERE GRADE_ID = ?");
            statement.setInt(1, gradeId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't delete data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void updateStudent(Student student) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE STUDENTS SET STUDENT_FIO = ?, STUDENT_GROUP = ?, MAIL = ?, PHONE_NUMBER = ?, ADDRESS = ? WHERE STUDENT_ID = ?");
            statement.setString(1, student.getFio());
            statement.setString(2, student.getGroup());
            statement.setString(3, student.getMail());
            statement.setString(4, student.getPhone());
            statement.setString(5, student.getAddress());
            statement.setInt(6, student.getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException ("Can't update data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void updateLocation(Location location) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            if(location.getParentId() == 0) {
                statement = connection.prepareStatement("UPDATE LOCATIONS SET NAME = ?, PARENT_ID = NULL, IS_LAST_LEVEL = ?, DESCRIPTION = ? WHERE LOCATION_ID = ?");
                statement.setString(2, String.valueOf(location.getCourse()));
                statement.setString(3, location.getDescription());
                statement.setInt(4, location.getLocationId());
            } else {
                statement = connection.prepareStatement("UPDATE LOCATIONS SET NAME = ?, PARENT_ID = ?, IS_LAST_LEVEL = ?, DESCRIPTION = ? WHERE LOCATION_ID = ?");
                statement.setInt(2, location.getParentId());
                statement.setString(3, String.valueOf(location.getCourse()));
                statement.setString(4, location.getDescription());
                statement.setInt(5, location.getLocationId());
            }
            statement.setString(1, location.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException ("Can't update data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void updateCourse(Course course) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE COURSES SET LOCATION_ID = ?, NAME = ?, DESCRIPTION = ?, TEACHER = ? WHERE COURSE_ID = ?");
            statement.setInt(1, course.getLocationId());
            statement.setString(2, course.getName());
            statement.setString(3, course.getDescription());
            statement.setString(4, course.getTeacher());
            statement.setInt(5, course.getCourseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException ("Can't update data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void updateGrade(Grade grade) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE GRADES SET STUDENT_ID = ?, COURSE_ID = ?, DATE_OF_EVALUATION = ?, CREDITS = ?, DESCRIPTION =? WHERE GRADE_ID = ?");
            statement.setInt(1, grade.getStudentId());
            statement.setInt(2, grade.getCourseId());
            statement.setDate(3, grade.getDate());
            statement.setInt(4, grade.getCredits());
            statement.setString(5, grade.getDescription());
            statement.setInt(6, grade.getGradeId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException ("Can't update data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public Student getStudentById(int studentId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        Student student = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM STUDENTS WHERE STUDENT_ID = ?");
            statement.setInt(1, studentId);
            result = statement.executeQuery();
            if(result.next())
                student = parseStudent(result);
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return student;
    }

    public Course getCourseById(int courseId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        Course course = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM COURSES WHERE COURSE_ID = ?");
            statement.setInt(1, courseId);
            result = statement.executeQuery();
            if(result.next())
                course = parseCourse(result);
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return course;
    }

    public Location getLocationById(int locationId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        Location location = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM LOCATIONS WHERE LOCATION_ID = ?");
            statement.setInt(1, locationId);
            result = statement.executeQuery();
            if(result.next())
                location = parseLocation(result);
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return location;
    }

    public Grade getGradeById(int gradeId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        Grade grade = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM GRADES WHERE GRADE_ID = ?");
            statement.setInt(1, gradeId);
            result = statement.executeQuery();
            if(result.next())
                grade = parseGrade(result);
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return grade;
    }

    public Location parseLocation(ResultSet result) throws DataAccessException {
        Location location;
        try {
            int id = result.getInt("LOCATION_ID");
            String name = result.getString("NAME");
            int parent_id = result.getInt("PARENT_ID");
            String is_last_level = result.getString("IS_LAST_LEVEL");
            String description = result.getString("DESCRIPTION");
            location = new LocationImpl(id, name, parent_id, Boolean.parseBoolean(is_last_level), description);
        } catch (SQLException e) {
            throw new DataAccessException("Can't get data from ResultSet", e);
        }
        return location;
    }

    public Course parseCourse(ResultSet result) throws DataAccessException {
        Course course;
        try {
            int id = result.getInt("COURSE_ID");
            Integer location_id = result.getInt("LOCATION_ID");
            String name = result.getString("NAME");
            String description = result.getString("DESCRIPTION");
            String teacher = result.getString("TEACHER");
            course = new CourseImpl(id, location_id, name, description, teacher);
        } catch (SQLException e) {
            throw new DataAccessException("Can't get data from ResultSet", e);
        }
        return course;
    }

    public Grade parseGrade(ResultSet result) throws DataAccessException {
        Grade grade;
        try {
            int id = result.getInt("GRADE_ID");
            int student_id = result.getInt("STUDENT_ID");
            int course_id = result.getInt("COURSE_ID");
            logger.info("student id = " + student_id + ", course id  = " + course_id);
            Date date = result.getDate("DATE_OF_EVALUATION");
            int credits = result.getInt("CREDITS");
            String description = result.getString("DESCRIPTION");
            grade = new GradeImpl(id, student_id, course_id, date, credits, description);
        } catch (SQLException e) {
            throw new DataAccessException("Can't get data from ResultSet", e);
        }
        return grade;
    }

    public Student parseStudent(ResultSet result) throws DataAccessException {
        Student student;
        try {
            int id = result.getInt("STUDENT_ID");
            String lastName = result.getString("STUDENT_FIO");
            String group = result.getString("STUDENT_GROUP");
            String mail = result.getString("MAIL");
            String phone = result.getString("PHONE_NUMBER");
            String address = result.getString("ADDRESS");
            student = new StudentImpl(id, lastName, group, mail, phone, address);
        } catch (SQLException e) {
            throw new DataAccessException("Can't get data from ResultSet", e);
        }
        return student;
    }

    public List<Course> getCoursesByStudentId(int studentId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Course> lCourses = new ArrayList<Course>();
        Course course;
        try {
            statement = connection.prepareStatement("SELECT STUDENTS.STUDENT_FIO, COURSES.NAME, COURSES.COURSE_ID FROM STUDENTS, COURSES, ENROLLMENT WHERE COURSES.COURSE_ID = ENROLLMENT.COURSE_ID AND STUDENTS.STUDENT_ID = ENROLLMENT.STUDENT_ID AND STUDENTS.STUDENT_ID = ?");
            statement.setInt(1, studentId);
            result = statement.executeQuery();
            while(result.next()){
                String name = result.getString("NAME");
                int id = result.getInt("COURSE_ID");
                course = new CourseImpl(id, 1, name, null, null);
                lCourses.add(course);
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lCourses;
    }

    public List<Course> getCoursesByLocationId(int locationId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Course> lCourses = new ArrayList<Course>();
        try {
            if(locationId == 0){
                statement = connection.prepareStatement("SELECT * FROM COURSES WHERE LOCATION_ID IS NULL");
            }
            else{
                statement = connection.prepareStatement("SELECT * FROM COURSES WHERE LOCATION_ID = ?");
                statement.setInt(1, locationId);
            }
            result = statement.executeQuery();
            while(result.next()){
                lCourses.add(parseCourse(result));
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lCourses;
    }

    public List<Location> getAllLocationsByParentId(int locationId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Location> lLocations = new ArrayList<Location>();
        try {
            if(locationId == 0) {
                statement = connection.prepareStatement("SELECT * FROM LOCATIONS WHERE PARENT_ID IS NULL ORDER BY NAME");
            }
            else {
                statement = connection.prepareStatement("SELECT * FROM LOCATIONS WHERE PARENT_ID = ? ORDER BY NAME");
                statement.setInt(1, locationId);
            }
            result = statement.executeQuery();
            while(result.next()){
                lLocations.add(parseLocation(result));
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lLocations;
    }

    public Map<Integer,String> getLocationHierarchy(int locationId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        Map<Integer,String> lMap = new HashMap<Integer, String>();
        try {
            statement = connection.prepareStatement("SELECT LOCATION_ID, NAME FROM LOCATIONS START WITH LOCATION_ID = ? CONNECT BY LOCATION_ID = PRIOR PARENT_ID");
            statement.setInt(1, locationId);
            result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("LOCATION_ID");
                String name = result.getString("NAME");
                lMap.put(id, name);
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lMap;
    }

    public void enrollStudent(int studentId, int courseId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO ENROLLMENT (STUDENT_ID, COURSE_ID, FINALGRADE) VALUES (?, ?, NULL)");
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't insert new data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void removeEnrollment(int studentId, int courseId) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM ENROLLMENT WHERE STUDENT_ID = ? AND COURSE_ID = ?");
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't delete data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public int getTotalCountOfStudents() throws DataAccessException{
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        int number = 0;
        try {
            statement = connection.prepareStatement("SELECT count(STUDENT_FIO) AS COUNT  FROM STUDENTS");
            result = statement.executeQuery();
            result.next();
            number = result.getInt("COUNT");
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return number;
    }
    public List<Student> getAllStudentsByPage(int page, int range) throws DataAccessException{
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Student> lStudent = new ArrayList<Student>();
        try {
            statement = connection.prepareStatement("SELECT * FROM ( SELECT p.*, ROWNUM rn FROM (SELECT STUDENTS.* FROM STUDENTS ORDER BY STUDENT_FIO)p WHERE ROWNUM < 10000)WHERE rn BETWEEN ? AND ?");
            statement.setInt(1, ((page - 1) * range + 1));
            statement.setInt(2, (page * range));
            logger.info("From " + ((page - 1) * range + 1) + " to " + (page * range));
            result = statement.executeQuery();
            while(result.next()){
                lStudent.add(parseStudent(result));

            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lStudent;
    }
}
