package ua.berest.lab3;

import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.exception.ProblemWithConnectionException;
import ua.berest.lab3.model.*;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

/**
 * Created by Oleg on 26.01.2016.
 */
public class OracleDataAccess implements ModelDataAccess {

    private Connection connection;
    private boolean isConnected = false;
    private Statement statement;
    private PreparedStatement preparedStatement;

    private ResultSet result;
    private static DataSource ds;
    private static Context ctx;
    private static Hashtable ht = new Hashtable();
    static {
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            ctx = new InitialContext(ht);
            ds = (javax.sql.DataSource) ctx.lookup("JNDI_Name0");
        } catch (NamingException e) {
            try {
                throw new ProblemWithConnectionException(e, "Problems with JNDI naming");
            } catch (ProblemWithConnectionException problemWithConnectionException) {
                problemWithConnectionException.printStackTrace();
            }
        }
    }

    private void connect() throws ProblemWithConnectionException {
        try {
            connection = ds.getConnection();
            if(!connection.isClosed()){
                isConnected = true;
            }
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e, "Problems with getting connection");
        }
    }

    private boolean disconnect() throws ProblemWithConnectionException {
        try {
            ctx.close();
        } catch (NamingException e) {
            throw new ProblemWithConnectionException(e, "Can't close context");
        }
        try {
            if(preparedStatement != null)
                preparedStatement.close();
            if(statement != null)
                statement.close();
            if(connection != null)
                connection.close();
            if(result != null)
                result.close();
            if(connection.isClosed()){
                isConnected = false;
            }
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e, "Can't close statement or connections");
        }
        return isConnected;
    }
  /*  @Override
    public List<Student> getAllStudents(String tableName) throws ProblemWithConnectionException, DataAccessException {
        List<Student> lStudent = new ArrayList<Student>();
        connect();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM ?");
            preparedStatement.setString((int)1, tableName);
            result = preparedStatement.executeQuery();
            lStudent = display(result, lStudent);
        } catch (Exception e) {
            throw new DataAccessException(e, "Can't extract necessary data");
        }
        finally {
            disconnect();
        }
        return lStudent;
    }
*/
    @Override
    public List<Student> getAllStudents() throws ProblemWithConnectionException, DataAccessException {
        List<Student> lStudent = new ArrayList<Student>();
        connect();
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM OLEG.STUDENTS");
            lStudent = display(result, lStudent);
        } catch (Exception e) {
            throw new DataAccessException(e, "Can't extract necessary data");
        }
        finally {
            disconnect();
        }
        return lStudent;
    }

    @Override
    public void addStudent(int studentId, String fio, String group, String mail, String phone, String address) throws ProblemWithConnectionException {
        connect();
        try {
            statement = connection.createStatement();
            statement.execute("INSERT INTO OLEG.STUDENTS (STUDENT_ID, STUDENT_FIO, STUDENT_GROUP, MAIL, PHONE_NUMBER, ADDRESS) " +
                        "VALUES ( " + studentId + ", '" + fio + "', '" + group + "', '" + mail + "', '" + phone + "', '" + address + "')");
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e, "Can't insert new data");
        }
        finally {
            disconnect();
        }
    }

    @Override
    public void removeStudent(Student student) throws ProblemWithConnectionException {
        connect();
        try {
            statement = connection.createStatement();
            statement.execute("DELETE FROM OLEG.STUDENTS WHERE STUDENT_ID = " + student.getStudentId());
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e, "Can't delete data");
        }
        finally {
            disconnect();
        }
    }

    @Override
    public void updateStudent(Student student) throws ProblemWithConnectionException {
        connect();
        try {
            statement = connection.createStatement();
            int res = statement.executeUpdate("UPDATE OLEG.STUDENTS SET STUDENT_FIO = '" + student.getFio() + "' WHERE STUDENT_ID = " + student.getStudentId());
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e, "Can't update data");
        }
        finally {
            disconnect();
        }
    }

    public void executeStatement(String sql, String errorMessage) throws ProblemWithConnectionException {
        connect();
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e, errorMessage);
        }
        finally {
            disconnect();
        }
    }


    public List<Student> display(ResultSet result, List list) throws ProblemWithConnectionException {
        try {
            while (result.next()) {
                int id = result.getInt("STUDENT_ID");
                String lastName = result.getString("STUDENT_FIO");
                String group = result.getString("STUDENT_GROUP");
                String mail = result.getString("MAIL");
                String phone = result.getString("PHONE_NUMBER");
                String address = result.getString("ADDRESS");
                list.add(new StudentImpl(id, lastName, group, mail, phone, address));
                System.out.println(id + " " + lastName + " " + group + " " + mail + " " + phone + " " + address);
            }
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e, "Can't get data from ResultSet");
        }
        return list;
    }

    public List<Student> getAllStudentsByCourse(CourseImpl course) {
        return null;
    }

    public List<Student> getAllStudentsByDepartment(int departmentLocationId) {
        return null;
    }

    public List<Student> getAllStudentsByCountry(int countryLocationId) {
        return null;
    }

    public List<Student> getAllStudentsByCity(int cityLocationId) {
        return null;
    }

    public List<Student> getAllStudentsByUniversity(int universityLocationId) {
        return null;
    }

    public List<CourseImpl> getAllCourses() {
        return null;
    }

    public List<Location> getAllCountries() {
        return null;
    }

    public List<Location> getAllCities() {
        return null;
    }

    public List<Location> getAllUniversities() {
        return null;
    }

    public List<Location> getAllDepartments() {
        return null;
    }

    public List<GradeImpl> getAllStudentCourseGrades(CourseImpl course, Student student) {
        return null;
    }
}
