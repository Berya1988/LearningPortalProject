package ua.berest.lab3.controller;

import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.*;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

/**
 * Created by Oleg on 26.01.2016.
 */
public class OracleDataAccess implements ModelDataAccess {
    private static final OracleDataAccess instance = new OracleDataAccess();

    private  DataSource ds;
    private  Context ctx;
    private  Hashtable ht = new Hashtable();

    private OracleDataAccess() {
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            ctx = new InitialContext(ht);
            ds = (javax.sql.DataSource) ctx.lookup("JNDI_Name0");
        } catch (NamingException e) {
            System.err.println (e.getMessage());
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
            statement = connection.prepareStatement("SELECT * FROM STUDENTS");
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

    public void addStudent(Student student) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO STUDENTS (STUDENT_ID, STUDENT_FIO, STUDENT_GROUP, MAIL, PHONE_NUMBER, ADDRESS) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, student.getStudentId());
            statement.setString(2, student.getFio());
            statement.setString(3, student.getGroup());
            statement.setString(4, student.getMail());
            statement.setString(5, student.getPhone());
            statement.setString(6, student.getAddress());
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Can't insert new data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public void removeStudent(Student student) throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM STUDENTS WHERE STUDENT_ID = ?");
            statement.setInt(1, student.getStudentId());
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
            statement = connection.prepareStatement("UPDATE STUDENTS SET STUDENT_FIO = ? WHERE STUDENT_ID = ?");
            statement.setString(1, student.getFio());
            statement.setInt(2, student.getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException ("Can't update data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    public Student getStudentByID(int studentId) throws DataAccessException {
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
                System.out.println(id + " " + lastName + " " + group + " " + mail + " " + phone + " " + address);
        } catch (SQLException e) {
            throw new DataAccessException("Can't get data from ResultSet", e);
        }
        return student;
    }

    public List<Location> getAllCountries() throws DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Location> lLocations = new ArrayList<Location>();
        Location location;
        try {
            statement = connection.prepareStatement("SELECT * FROM LOCATIONS WHERE PARENT_ID = 1");
            result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("LOCATION_ID");
                String name = result.getString("NAME");
                int idParent = result.getInt("PARENT_ID");
                Boolean is_last_level = result.getBoolean("IS_LAST_LEVEL");
                String description = result.getString("DESCRIPTION");
                location = new LocationImpl(id, name, description, idParent, is_last_level);
                lLocations.add(location);
            }
        } catch (Exception e) {
            throw new DataAccessException("Can't extract necessary data", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return lLocations;
    }
}
