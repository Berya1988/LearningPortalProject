package ua.berest.lab3;

import java.util.List;

/**
 * Created by Oleg on 24.01.2016.
 */
public interface ModelDataAccess {
    List<Student> getAllStudents();
    Boolean addStudent(int studentId, String fio, String group, String mail, String phone, String address);
    Boolean removeStudentById(int studentId);
    Boolean updateStudentNameById(int studentId, String fio);
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
