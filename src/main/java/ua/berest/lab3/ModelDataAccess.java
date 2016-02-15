package ua.berest.lab3;

import ua.berest.lab3.exception.DataAccessException;
import ua.berest.lab3.exception.ProblemWithConnectionException;
import ua.berest.lab3.model.CourseImpl;
import ua.berest.lab3.model.GradeImpl;
import ua.berest.lab3.model.Location;
import ua.berest.lab3.model.Student;

import java.util.List;

/**
 * Created by Oleg on 24.01.2016.
 */
public interface ModelDataAccess {
    List<Student> getAllStudents() throws ProblemWithConnectionException, DataAccessException;
    void addStudent(int studentId, String fio, String group, String mail, String phone, String address) throws ProblemWithConnectionException;
    void removeStudent(Student student) throws ProblemWithConnectionException;
    void updateStudent(Student student) throws ProblemWithConnectionException;

    // not yet

    List<Student> getAllStudentsByCourse(CourseImpl course);
    List<Student> getAllStudentsByDepartment(int departmentLocationId);
    List<Student> getAllStudentsByCountry(int countryLocationId);
    List<Student> getAllStudentsByCity(int cityLocationId);
    List<Student> getAllStudentsByUniversity(int universityLocationId);
    List<CourseImpl> getAllCourses();
    List<Location> getAllCountries();
    List<Location> getAllCities();
    List<Location> getAllUniversities();
    List<Location> getAllDepartments();
    List<GradeImpl> getAllStudentCourseGrades(CourseImpl course, Student student);
}
