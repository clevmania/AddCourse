package com.example.lawrence.addcourse;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Lawrence on 6/29/2017.
 */
public class WelcomeActivity extends ActionBarActivity {
    int pStatus = 0;
    private Handler handler = new Handler();
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String whoIsLoggedIn = getIntent().getStringExtra("Username");
        TextView userView = (TextView) findViewById(R.id.welcomeheader);
        userView.setText("Hi, "+ whoIsLoggedIn);

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);


      /*  ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 100);
        animation.setDuration(50000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/

        tv = (TextView) findViewById(R.id.tv);
        animateTextView(0.0f,(float)computeGrade());

        new Thread(new Runnable() {

            @Override
            public void run() {
                //final String cgp = "4.70";
                // TODO Auto-generated method stub
                while (pStatus < computeProgress()) {// indicates the max, set from method
                    pStatus += 1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress(pStatus);
                            //tv.setText(cgp);// set from a gp method

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(80); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void newCourse(View view) {
        Intent courseIntent = new Intent(this,MainActivity.class);
        startActivity(courseIntent);
    }

    public void viewAllCourses(View view) {
        Intent viewIntent = new Intent(this,CourseDisplayActivity.class);
        startActivity(viewIntent);
    }

    public void shareCourse(View view) {
        Toast.makeText(this, "Generating Report!!", Toast.LENGTH_SHORT).show();
        Document doc = new Document();
        String output = Environment.getExternalStorageDirectory()+"/report.pdf";
        try{
            PdfWriter.getInstance(doc, new FileOutputStream(output));
            doc.open();
            Paragraph p1 = new Paragraph("University Of Nigeria, Nsukka");
            Font paraFont= new Font(Font.FontFamily.COURIER, Color.GREEN);
            paraFont.setSize(24.0f);
            paraFont.setStyle(Font.BOLD);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            doc.add(p1);

            DBHelper helper = new DBHelper(this);
            Cursor cursor;
            cursor = helper.pullDetails();
            if(cursor.moveToFirst()){
                do {
                    int fn = cursor.getColumnIndex(helper.Column_Fname);
                    int ln = cursor.getColumnIndex(helper.Column_Lname);
                    int fa = cursor.getColumnIndex(helper.Column_Fac);
                    int de = cursor.getColumnIndex(helper.Column_Dept);
                    String fname = cursor.getString(fn);
                    String lname = cursor.getString(ln);
                    String fac = cursor.getString(fa);
                    String dep = cursor.getString(de);

                    Paragraph p2 = new Paragraph("Faculty Of " + fac);
                    Font paraFont2= new Font(Font.FontFamily.COURIER);
                    p2.setAlignment(Paragraph.ALIGN_CENTER);
                    p2.setFont(paraFont2);

                    doc.add(p2);

                    Paragraph p3 = new Paragraph("Department of " + dep);
                    Font paraFont3= new Font(Font.FontFamily.COURIER);
                    p3.setAlignment(Paragraph.ALIGN_CENTER);
                    p3.setFont(paraFont3);

                    doc.add(p3);

                    Paragraph p4 = new Paragraph("Gradebook Report for " + lname + " " + fname);
                    Font paraFont4= new Font(Font.FontFamily.COURIER);
                    p4.setAlignment(Paragraph.ALIGN_CENTER);
                    p4.setFont(paraFont4);

                    doc.add(p4);

                }while (cursor.moveToNext());
            }

            // First Table begins
            Paragraph p5 = new Paragraph("First Year First Semester");
            Font paraFont5= new Font(Font.FontFamily.HELVETICA, 14.0f, Color.GREEN);
            p5.setPaddingTop(2.0f);
            p5.setAlignment(Paragraph.ALIGN_CENTER);
            p5.setFont(paraFont5);

            doc.add(p5);

            PdfPTable table = new PdfPTable(6);
            table.setPaddingTop(3.0f);
            table.addCell("Course Title");
            table.addCell("Course Code");
            table.addCell("CA Score");
            table.addCell("Exam Score");
            table.addCell("Total");
            table.addCell("Grade");

            //First semester first year
            cursor = helper.getAllRows(1,1);
            if(cursor.moveToFirst()){
                do {
                    int ti = cursor.getColumnIndex(helper.COLUMN_TITLE);
                    int co = cursor.getColumnIndex(helper.COLUMN_COS_CODE);
                    int caz = cursor.getColumnIndex(helper.COLUMN_CA_SCORE);
                    int xaz = cursor.getColumnIndex(helper.COLUMN_EXAM_SCORE);
                    String title = cursor.getString(ti);
                    String code = cursor.getString(co);
                    int ca = cursor.getInt(caz);
                    int xam = cursor.getInt(xaz);
                    int tot = ca + xam;

                    table.addCell(title);
                    table.addCell(code);
                    table.addCell(String.valueOf(ca));
                    table.addCell(String.valueOf(xam));
                    table.addCell(String.valueOf(tot));
                    table.addCell(computeGrade(ca,xam));

                }while (cursor.moveToNext());
            }

            doc.add(table);

            // second table goes here
            Paragraph p6 = new Paragraph("First Year Second Semester");
            Font paraFont6= new Font(Font.FontFamily.HELVETICA);
            p6.setPaddingTop(2.0f);
            p6.setAlignment(Paragraph.ALIGN_CENTER);
            p6.setFont(paraFont6);

            doc.add(p6);

            PdfPTable table2 = new PdfPTable(6);
            table2.addCell("Course Title");
            table2.addCell("Course Code");
            table2.addCell("CA Score");
            table2.addCell("Exam Score");
            table2.addCell("Total");
            table2.addCell("Grade");

            //second semester first year
            cursor = helper.getAllRows(2,1);
            if(cursor.moveToFirst()){
                do {
                    int ti = cursor.getColumnIndex(helper.COLUMN_TITLE);
                    int co = cursor.getColumnIndex(helper.COLUMN_COS_CODE);
                    int caz = cursor.getColumnIndex(helper.COLUMN_CA_SCORE);
                    int xaz = cursor.getColumnIndex(helper.COLUMN_EXAM_SCORE);
                    String title = cursor.getString(ti);
                    String code = cursor.getString(co);
                    int ca = cursor.getInt(caz);
                    int xam = cursor.getInt(xaz);
                    int tot = ca + xam;

                    table2.addCell(title);
                    table2.addCell(code);
                    table2.addCell(String.valueOf(ca));
                    table2.addCell(String.valueOf(xam));
                    table2.addCell(String.valueOf(tot));
                    table2.addCell(computeGrade(ca,xam));

                }while (cursor.moveToNext());
            }

            doc.add(table2);

            // third table goes here
            Paragraph p7 = new Paragraph("Second Year First Semester");
            Font paraFont7= new Font(Font.FontFamily.HELVETICA);
            p7.setPaddingTop(2.0f);
            p7.setAlignment(Paragraph.ALIGN_CENTER);
            p7.setFont(paraFont7);
            doc.add(p7);

            PdfPTable table3 = new PdfPTable(6);
            table3.addCell("Course Title");
            table3.addCell("Course Code");
            table3.addCell("CA Score");
            table3.addCell("Exam Score");
            table3.addCell("Total");
            table3.addCell("Grade");

            cursor = helper.getAllRows(1,2);
            if(cursor.moveToFirst()){
                do {
                    int ti = cursor.getColumnIndex(helper.COLUMN_TITLE);
                    int co = cursor.getColumnIndex(helper.COLUMN_COS_CODE);
                    int caz = cursor.getColumnIndex(helper.COLUMN_CA_SCORE);
                    int xaz = cursor.getColumnIndex(helper.COLUMN_EXAM_SCORE);
                    String title = cursor.getString(ti);
                    String code = cursor.getString(co);
                    int ca = cursor.getInt(caz);
                    int xam = cursor.getInt(xaz);
                    int tot = ca + xam;

                    table3.addCell(title);
                    table3.addCell(code);
                    table3.addCell(String.valueOf(ca));
                    table3.addCell(String.valueOf(xam));
                    table3.addCell(String.valueOf(tot));
                    table3.addCell(computeGrade(ca,xam));

                }while (cursor.moveToNext());
            }

            doc.add(table3);

            // fourth table goes here
            Paragraph p8 = new Paragraph("Second Year second Semester");
            Font paraFont8= new Font(Font.FontFamily.HELVETICA);
            p8.setPaddingTop(2.0f);
            p8.setAlignment(Paragraph.ALIGN_CENTER);
            p8.setFont(paraFont8);
            doc.add(p8);

            PdfPTable table4 = new PdfPTable(6);
            table4.addCell("Course Title");
            table4.addCell("Course Code");
            table4.addCell("CA Score");
            table4.addCell("Exam Score");
            table4.addCell("Total");
            table4.addCell("Grade");

            cursor = helper.getAllRows(2,2);
            if(cursor.moveToFirst()){
                do {
                    int ti = cursor.getColumnIndex(helper.COLUMN_TITLE);
                    int co = cursor.getColumnIndex(helper.COLUMN_COS_CODE);
                    int caz = cursor.getColumnIndex(helper.COLUMN_CA_SCORE);
                    int xaz = cursor.getColumnIndex(helper.COLUMN_EXAM_SCORE);
                    String title = cursor.getString(ti);
                    String code = cursor.getString(co);
                    int ca = cursor.getInt(caz);
                    int xam = cursor.getInt(xaz);
                    int tot = ca + xam;

                    table4.addCell(title);
                    table4.addCell(code);
                    table4.addCell(String.valueOf(ca));
                    table4.addCell(String.valueOf(xam));
                    table4.addCell(String.valueOf(tot));
                    table4.addCell(computeGrade(ca,xam));

                }while (cursor.moveToNext());
            }

            doc.add(table4);

            // fifth table goes here
            Paragraph p9 = new Paragraph("Third Year first Semester");
            Font paraFont9= new Font(Font.FontFamily.HELVETICA);
            p9.setPaddingTop(2.0f);
            p9.setAlignment(Paragraph.ALIGN_CENTER);
            p9.setFont(paraFont9);
            doc.add(p9);

            PdfPTable table5 = new PdfPTable(6);
            table5.addCell("Course Title");
            table5.addCell("Course Code");
            table5.addCell("CA Score");
            table5.addCell("Exam Score");
            table5.addCell("Total");
            table5.addCell("Grade");

            cursor = helper.getAllRows(1,3);
            if(cursor.moveToFirst()){
                do {
                    int ti = cursor.getColumnIndex(helper.COLUMN_TITLE);
                    int co = cursor.getColumnIndex(helper.COLUMN_COS_CODE);
                    int caz = cursor.getColumnIndex(helper.COLUMN_CA_SCORE);
                    int xaz = cursor.getColumnIndex(helper.COLUMN_EXAM_SCORE);
                    String title = cursor.getString(ti);
                    String code = cursor.getString(co);
                    int ca = cursor.getInt(caz);
                    int xam = cursor.getInt(xaz);
                    int tot = ca + xam;

                    table5.addCell(title);
                    table5.addCell(code);
                    table5.addCell(String.valueOf(ca));
                    table5.addCell(String.valueOf(xam));
                    table5.addCell(String.valueOf(tot));
                    table5.addCell(computeGrade(ca,xam));

                }while (cursor.moveToNext());
            }

            doc.add(table5);

            // sixth table goes here
            Paragraph p10 = new Paragraph("Third Year second Semester");
            Font paraFont10= new Font(Font.FontFamily.HELVETICA);
            p10.setPaddingTop(2.0f);
            p10.setAlignment(Paragraph.ALIGN_CENTER);
            p10.setFont(paraFont10);
            doc.add(p10);

            PdfPTable table6 = new PdfPTable(6);
            table6.setPaddingTop(20f);
            table6.addCell("Course Title");
            table6.addCell("Course Code");
            table6.addCell("CA Score");
            table6.addCell("Exam Score");
            table6.addCell("Total");
            table6.addCell("Grade");

            cursor = helper.getAllRows(2,3);
            if(cursor.moveToFirst()){
                do {
                    int ti = cursor.getColumnIndex(helper.COLUMN_TITLE);
                    int co = cursor.getColumnIndex(helper.COLUMN_COS_CODE);
                    int caz = cursor.getColumnIndex(helper.COLUMN_CA_SCORE);
                    int xaz = cursor.getColumnIndex(helper.COLUMN_EXAM_SCORE);
                    String title = cursor.getString(ti);
                    String code = cursor.getString(co);
                    int ca = cursor.getInt(caz);
                    int xam = cursor.getInt(xaz);
                    int tot = ca + xam;

                    table6.addCell(title);
                    table6.addCell(code);
                    table6.addCell(String.valueOf(ca));
                    table6.addCell(String.valueOf(xam));
                    table6.addCell(String.valueOf(tot));
                    table6.addCell(computeGrade(ca,xam));

                }while (cursor.moveToNext());
            }

            doc.add(table6);

            // seventh table goes here
            Paragraph pa = new Paragraph("Fourth Year first Semester");
            Font paraFonta= new Font(Font.FontFamily.HELVETICA);
            pa.setPaddingTop(2.0f);
            pa.setAlignment(Paragraph.ALIGN_CENTER);
            pa.setFont(paraFonta);
            doc.add(pa);

            PdfPTable table7 = new PdfPTable(6);
            table7.setPaddingTop(20f);
            table7.addCell("Course Title");
            table7.addCell("Course Code");
            table7.addCell("CA Score");
            table7.addCell("Exam Score");
            table7.addCell("Total");
            table7.addCell("Grade");

            cursor = helper.getAllRows(1,4);
            if(cursor.moveToFirst()){
                do {
                    int ti = cursor.getColumnIndex(helper.COLUMN_TITLE);
                    int co = cursor.getColumnIndex(helper.COLUMN_COS_CODE);
                    int caz = cursor.getColumnIndex(helper.COLUMN_CA_SCORE);
                    int xaz = cursor.getColumnIndex(helper.COLUMN_EXAM_SCORE);
                    String title = cursor.getString(ti);
                    String code = cursor.getString(co);
                    int ca = cursor.getInt(caz);
                    int xam = cursor.getInt(xaz);
                    int tot = ca + xam;

                    table7.addCell(title);
                    table7.addCell(code);
                    table7.addCell(String.valueOf(ca));
                    table7.addCell(String.valueOf(xam));
                    table7.addCell(String.valueOf(tot));
                    table7.addCell(computeGrade(ca,xam));

                }while (cursor.moveToNext());
            }

            doc.add(table7);

            // eight table goes here
            Paragraph pb = new Paragraph("Fourth Year second Semester");
            Font paraFontb= new Font(Font.FontFamily.HELVETICA);
            pb.setPaddingTop(2.0f);
            pb.setAlignment(Paragraph.ALIGN_CENTER);
            pb.setFont(paraFontb);
            doc.add(pb);

            PdfPTable table8 = new PdfPTable(6);
            table8.setPaddingTop(20f);
            table8.addCell("Course Title");
            table8.addCell("Course Code");
            table8.addCell("CA Score");
            table8.addCell("Exam Score");
            table8.addCell("Total");
            table8.addCell("Grade");

            cursor = helper.getAllRows(2,4);
            if(cursor.moveToFirst()){
                do {
                    int ti = cursor.getColumnIndex(helper.COLUMN_TITLE);
                    int co = cursor.getColumnIndex(helper.COLUMN_COS_CODE);
                    int caz = cursor.getColumnIndex(helper.COLUMN_CA_SCORE);
                    int xaz = cursor.getColumnIndex(helper.COLUMN_EXAM_SCORE);
                    String title = cursor.getString(ti);
                    String code = cursor.getString(co);
                    int ca = cursor.getInt(caz);
                    int xam = cursor.getInt(xaz);
                    int tot = ca + xam;

                    table8.addCell(title);
                    table8.addCell(code);
                    table8.addCell(String.valueOf(ca));
                    table8.addCell(String.valueOf(xam));
                    table8.addCell(String.valueOf(tot));
                    table8.addCell(computeGrade(ca,xam));

                }while (cursor.moveToNext());
            }

            doc.add(table8);
            Toast.makeText(this, "Report Generated successfully", Toast.LENGTH_SHORT).show();

            doc.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        sendEmail();
    }

    public void animateTextView(float iv, float fv){
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(iv,fv);
        valueAnimator.setDuration(7500);
        //valueAnimator.setObjectValues(0.0,4.7);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv.setText(String.format("%.2f",animation.getAnimatedValue()));
            }
        });
        valueAnimator.start();
    }

    public double computeGrade(){
        DBHelper helper = new DBHelper(this);
        Cursor mCursor;
        mCursor = helper.getAllScore();
        double sumFX, sumX,mean= 0;
        int ca, xam, unit, grade, gradepoint;

        sumX = 0;
        sumFX = 0;
        gradepoint = 0;
        if(mCursor.moveToNext()){
            do {
                int co = mCursor.getColumnIndex(helper.COLUMN_COS_UNIT);
                int caz = mCursor.getColumnIndex(helper.COLUMN_CA_SCORE);
                int xaz = mCursor.getColumnIndex(helper.COLUMN_EXAM_SCORE);
                unit = mCursor.getInt(co);
                ca = mCursor.getInt(caz);
                xam = mCursor.getInt(xaz);

                grade = ca + xam;
                if(grade >= 0 && grade <= 44)
                    gradepoint = 0;
                if(grade >= 45 && grade <= 49)
                    gradepoint = 2;
                if(grade >= 50 && grade <= 59)
                    gradepoint = 3;
                if(grade >= 60 && grade <= 69)
                    gradepoint = 4;
                if(grade >= 70 && grade <= 100)
                    gradepoint = 5;

                sumFX += unit * gradepoint;
                sumX += unit;


            }while(mCursor.moveToNext());
            mean = sumFX / sumX;

        }
        //Toast.makeText(this,sumFX +" "+ sumX +" "+ String.format("%.2f",mean),Toast.LENGTH_LONG).show();
        return mean;
    }

    public int computeProgress(){
        int progress = (int)((computeGrade() / 5) * 100);

        //Toast.makeText(this,String.valueOf(progress),Toast.LENGTH_LONG).show();
        return progress;

    }

    public String computeGrade(int ca, int xa){
        int grade;
        String gpt = null;
        grade = ca + xa;
        if(grade >= 0 && grade <= 44)
            gpt = "F";
        if(grade >= 45 && grade <= 49)
            gpt = "D";
        if(grade >= 50 && grade <= 59)
            gpt = "C";
        if(grade >= 60 && grade <= 69)
            gpt = "B";
        if(grade >= 70 && grade <= 100)
            gpt = "A";

        return gpt;
    }

    public void sendEmail(){
        String filename = "report.pdf";
        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
        Uri path = Uri.fromFile(filelocation);
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setData(Uri.parse("mailto:"));
        String[] to = {"ernestcleverest@gmail.com"};
        mailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        mailIntent .putExtra(Intent.EXTRA_STREAM, path);
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Gradebook Report: Sent from app");
        mailIntent.putExtra(Intent.EXTRA_TEXT, "Hi this mail contains a GradeReport attached to it, " +
                "Kindly Evaluate this Student");
        mailIntent.setType("application/pdf");
        Intent chooser = Intent.createChooser(mailIntent, "Send GradeReport");
        startActivity(chooser);
    }

}
