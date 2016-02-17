package ua.berest.lab3;

import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.exception.ConnectionException;
import ua.berest.lab3.model.Student;

import java.util.List;

/**
 * Created by Oleg on 24.01.2016.
 */
public interface ModelDataAccess {
    List<Student> getAllStudents(String target) throws ConnectionException, DataAccessException;
    void addStudent(Student student) throws ConnectionException, DataAccessException;
    void removeStudent(Student student) throws ConnectionException, DataAccessException;
    void updateStudent(Student student) throws ConnectionException, DataAccessException;
    Student getStudentByID(int studentId) throws ConnectionException, DataAccessException ;
}
