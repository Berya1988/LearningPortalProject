package ua.berest.lab3.model;

import java.util.Date;

/**
 * Created by Oleg on 24.01.2016.
 */
public class GradeImpl implements Grade {
    private int gradeId;
    private int courseId;
    private int studentId;
    private float credits;
    private Date date;

    public GradeImpl(int gradeId, int courseId, int studentId, float credits, Date date) {
        this.gradeId = gradeId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.credits = credits;
        this.date = date;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    public Date getDate() {
        return (Date)this.date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date)date.clone();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GradeImpl other = (GradeImpl) obj;
        return !(gradeId != other.gradeId || courseId != other.courseId || studentId != other.studentId || credits != other.credits || !date.equals(other.date));
    }

    @Override
    protected Object clone() {
        try{
            GradeImpl copy = (GradeImpl)super.clone();
            copy.date = getDate();
            return copy;
        }
        catch(CloneNotSupportedException e){
            throw new AssertionError("Impossible");
        }
    }

    @Override
    public String toString() {
        return "Grade id = " + gradeId + ", course id = " + courseId + ", student id = " + studentId + ", credits = " + credits + ", date = " + date + ".";
    }
}
