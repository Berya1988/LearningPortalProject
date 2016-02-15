package ua.berest.lab3.model;

import java.util.Date;

/**
 * Created by Oleg on 26.01.2016.
 */
public interface Grade {
    int getGradeId();
    void setGradeId(int gradeId);
    int getCourseId();
    void setCourseId(int courseId);
    int getStudentId();
    void setStudentId(int studentId);
    float getCredits();
    void setCredits(float credits);
    Date getDate();
    void setDate(Date date);
}
