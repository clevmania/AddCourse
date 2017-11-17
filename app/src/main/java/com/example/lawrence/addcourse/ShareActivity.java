package com.example.lawrence.addcourse;

import android.app.Activity;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Lawrence on 8/8/2017.
 */
public class ShareActivity extends Activity{

    public void getReport(){
        Toast.makeText(this, "u clicked me", Toast.LENGTH_SHORT).show();
        Document doc = new Document();
        String output = Environment.getExternalStorageDirectory()+"/report.pdf";
        try{
            PdfWriter.getInstance(doc, new FileOutputStream(output));
            doc.open();
            //doc.add(new Paragraph(txt.getText().toString()));
            Paragraph p1 = new Paragraph("University Of Nigeria, Nsukka");
            Font paraFont= new Font(Font.FontFamily.COURIER);
            paraFont.setSize(24f);
            paraFont.setStyle(Font.BOLD);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            doc.add(p1);

            Paragraph p2 = new Paragraph("Faculty Of Physical Sciences");
            Font paraFont2= new Font(Font.FontFamily.COURIER);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont2);

            doc.add(p2);

            Paragraph p3 = new Paragraph("Department of Computer Science");
            Font paraFont3= new Font(Font.FontFamily.COURIER);
            p3.setAlignment(Paragraph.ALIGN_CENTER);
            p3.setFont(paraFont3);

            doc.add(p3);

            Paragraph p4 = new Paragraph("Gradebook Report for Lawrence Dominic");
            Font paraFont4= new Font(Font.FontFamily.COURIER);
            p4.setAlignment(Paragraph.ALIGN_CENTER);
            p4.setFont(paraFont4);

            doc.add(p4);

            // First Table begins
            Paragraph p5 = new Paragraph("First Year First Semester");
            Font paraFont5= new Font(Font.FontFamily.HELVETICA);
            p5.setPaddingTop(2f);
            p5.setAlignment(Paragraph.ALIGN_CENTER);
            p5.setFont(paraFont5);

            doc.add(p5);

            PdfPTable table = new PdfPTable(6);
            table.addCell("Course Title");
            table.addCell("Course Code");
            table.addCell("CA Score");
            table.addCell("Exam Score");
            table.addCell("Total");
            table.addCell("Grade");

            DBHelper helper = new DBHelper(this);
            Cursor cursor;
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
            p6.setPaddingTop(2f);
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

                    table2.addCell(title);
                    table2.addCell(code);
                    table2.addCell(String.valueOf(ca));
                    table2.addCell(String.valueOf(xam));
                    table2.addCell(String.valueOf(tot));
                    table2.addCell(computeGrade(ca,xam));

                }while (cursor.moveToNext());
            }

            doc.add(table2);

            doc.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
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
}


