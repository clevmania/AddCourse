package com.example.lawrence.addcourse;

/**
 * Created by Lawrence on 6/27/2017.
 */
public class DataProvider {

    private String courseTitle;
    private String courseCode;
    private int assessment;
    private int exam;

    public DataProvider(String courseTitle, String courseCode, int assessment, int exam){

        this.assessment = assessment;
        this.courseCode =  courseCode;
        this.courseTitle = courseTitle;
        this.exam = exam;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getAssessmeent() {
        return assessment;
    }

    public void setAssessmeent(int assessment) {
        this.assessment = assessment;
    }

    public int getExam() {
        return exam;
    }

    public void setExam(int exam) {
        this.exam = exam;
    }
}
