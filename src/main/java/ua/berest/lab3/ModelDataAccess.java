package ua.berest.lab3;

import java.util.List;

/**
 * Created by Oleg on 24.01.2016.
 */
public interface ModelDataAccess {
    List<Student> getAllStudents();
    List<Student> getAllStudentsByCourse(Course course);
    List<Student> getAllStudentsByDepartment(int departmentLocationId);
    List<Student> getAllStudentsByCountry(int countryLocationId);
    List<Student> getAllStudentsByCity(int cityLocationId);
    List<Student> getAllStudentsByUniversity(int universityLocationId);
    List<Course> getAllCourses();
    List<Location> getAllCountries();
    List<Location> getAllCities();
    List<Location> getAllUniversities();
    List<Location> getAllDepartments();
    List<Grade> getAllStudentCourseGrades(Course course, Student student);
}
