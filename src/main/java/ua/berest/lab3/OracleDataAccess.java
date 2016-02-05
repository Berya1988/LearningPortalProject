package ua.berest.lab3;

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
    private DataSource ds;
    private Context ctx;
    private Statement statement;

    private boolean connect() {
        Hashtable ht = new Hashtable();
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");

        try {
            ctx = new InitialContext(ht);
            ds = (javax.sql.DataSource) ctx.lookup ("JNDI_Name0");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            connection = ds.getConnection();
            if(!connection.isClosed()){
                isConnected = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isConnected;
    }

    private boolean disconnect() {

        try {
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            statement.close();
            connection.close();
            if(connection.isClosed()){
                isConnected = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isConnected;
    }
    public List<Student> getAllStudents() {
        List<Student> lStudent = new ArrayList<Student>();
        if(connect() == true) {
            try {
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM OLEG.STUDENTS");
                //int res = statement.executeUpdate("UPDATE STUDENTS SET STUDENT_FIO = 'Petrova S.B.' WHERE STUDENT_ID = 1");
                //System.out.println(res);

                while (result.next()) {
                    int id = result.getInt("STUDENT_ID");
                    String lastName = result.getString("STUDENT_FIO");
                    String group = result.getString("STUDENT_GROUP");
                    String mail = result.getString("MAIL");
                    String phone = result.getString("PHONE_NUMBER");
                    String address = result.getString("ADDRESS");
                    lStudent.add(new StudentImpl(id, lastName, group, mail, phone, address));
                    System.out.println(id + " " + lastName + " " + group + " " + mail + " " + phone + " " + address);
                }
            } catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
            finally {
                disconnect();
            }
        }
        return lStudent;
    }

    @Override
    public Boolean addStudent(int studentId, String fio, String group, String mail, String phone, String address) {
        if(connect() == true) {
            try {
                statement = connection.createStatement();
                statement.execute("INSERT INTO OLEG.STUDENTS (STUDENT_ID, STUDENT_FIO, STUDENT_GROUP, MAIL, PHONE_NUMBER, ADDRESS) " +
                        "VALUES ( " + studentId + ", '" + fio + "', '" + group + "', '" + mail + "', '" + phone + "', '" + address + "')");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Got an update exception! ");
                System.err.println(e.getMessage());
                return false;
            }
            finally {
                disconnect();
            }
        }
        return true;
    }

    @Override
    public Boolean removeStudentById(int studentId) {
        if(connect() == true) {
            try {
                statement = connection.createStatement();
                statement.execute("DELETE FROM OLEG.STUDENTS WHERE STUDENT_ID = " + studentId);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Got an remove exception!");
                System.err.println(e.getMessage());
                return false;
            }
            finally {
                disconnect();
            }
        }
        return true;
    }

    @Override
    public Boolean updateStudentNameById(int studentId, String fio) {
        if(connect() == true) {
            try {
                statement = connection.createStatement();
                int res = statement.executeUpdate("UPDATE OLEG.STUDENTS SET STUDENT_FIO = '" + fio + "' WHERE STUDENT_ID = " + studentId);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Got an update exception!");
                System.err.println(e.getMessage());
                return false;
            }
            finally {
                disconnect();
            }
        }
        return true;
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
