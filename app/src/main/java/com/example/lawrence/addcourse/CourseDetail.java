package com.example.lawrence.addcourse;

/**
 * Created by Lawrence on 5/3/2017.
 */
public class CourseDetail {
    private int id;
    private String CosTitle;
    private String CosCode;
    private String CosUnit;
    private String LecName;
    private String CosDesc;
    private int caScore;
    private int examScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCosTitle() {
        return CosTitle;
    }

    public void setCosTitle(String cosTitle) {
        CosTitle = cosTitle;
    }

    public String getCosCode() {
        return CosCode;
    }

    public void setCosCode(String cosCode) {
        CosCode = cosCode;
    }

    public String getCosUnit() {
        return CosUnit;
    }

    public void setCosUnit(String cosUnit) {
        CosUnit = cosUnit;
    }

    public String getLecName() {
        return LecName;
    }

    public void setLecName(String lecName) {
        LecName = lecName;
    }

    public String getCosDesc() {
        return CosDesc;
    }

    public void setCosDesc(String cosDesc) {
        CosDesc = cosDesc;
    }

    public int getCaScore() {
        return caScore;
    }

    public void setCaScore(int caScore) {
        this.caScore = caScore;
    }

    public int getExamScore() {
        return examScore;
    }

    public void setExamScore(int examScore) {
        this.examScore = examScore;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private int semester;
    private int year;


}
