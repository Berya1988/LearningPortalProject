package ua.berest.lab3.controller;

import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.model.Course;
import ua.berest.lab3.model.Location;
import ua.berest.lab3.model.Student;

import java.util.List;
import java.util.Map;

/**
 * Created by Oleg on 24.01.2016.
 */
public interface ModelDataAccess {
    List<Student> getAllStudents() throws DataAccessException;
    List<Location> getAllLocationsByParentId(int locationId) throws DataAccessException;
    void addStudent(Student student) throws DataAccessException;
    void removeStudent(int studentId) throws DataAccessException;
    void updateStudent(Student student) throws DataAccessException;
    Student getStudentById(int studentId) throws DataAccessException ;
    List<Course> getCoursesByStudentId(int studentId) throws DataAccessException;
    Map<Integer,String> getLocationHierarchy(int locationId) throws DataAccessException;
}
