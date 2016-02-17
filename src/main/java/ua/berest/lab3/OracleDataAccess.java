package ua.berest.lab3;

import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.exception.ConnectionException;
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
                throw new ConnectionException("Problems with JNDI naming", e);
            } catch (ConnectionException connectionException) {
                connectionException.printStackTrace();
            }
        }
    }

    private OracleDataAccess() {
    }

    public static OracleDataAccess getInstance() {
        return instance;
    }

    private Connection connect() throws ConnectionException {
        Connection connection;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("Problems with getting connection", e);
        }
        return connection;
    }

    private void disconnect(Connection connection, ResultSet result, PreparedStatement statement) throws ConnectionException {
        try {
            if(statement != null)
                statement.close();
            if(connection != null)
                connection.close();
            if(result != null)
                result.close();
        } catch (SQLException e) {
            throw new ConnectionException("Can't close statement or connections", e);
        }
    }

    public List<Student> getAllStudents(String target) throws ConnectionException, DataAccessException {
        Connection connection = connect();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Student> lStudent = new ArrayList<Student>();
        try {
            statement = connection.prepareStatement("SELECT * FROM STUDENTS WHERE STUDENT_GROUP = ?");
            statement.setString(1, target);
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

    public void addStudent(Student student) throws ConnectionException, DataAccessException {
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
    public void removeStudent(Student student) throws ConnectionException, DataAccessException {
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

    public void updateStudent(Student student) throws ConnectionException, DataAccessException {
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

    public Student getStudentByID(int studentId) throws ConnectionException, DataAccessException {
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
}
