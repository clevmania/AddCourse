package com.example.lawrence.addcourse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lawrence on 5/3/2017.
 */
public class DBHelper extends SQLiteOpenHelper{
    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database name declaration
    private static final String DATABASE_NAME = "cos.db";
    // Table name declaration
    private static final String TABLE_NAME = "courses";
    private static final String TABLE_PROFILE = "profile";

    // Courses Table Definition
    // Table columns declaration
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COS_CODE = "code";
    public static final String COLUMN_COS_UNIT = "unit";
    private static final String COLUMN_Lect_Name = "name";
    private static final String COLUMN_COS_DESC = "desc";
    public static final String COLUMN_CA_SCORE = "ca";
    public static final String COLUMN_EXAM_SCORE = "xam";
    private static final String COLUMN_SEMESTER = "semesta";
    private static final String COLUMN_YEAR = "year";

    // Creation of table string
    private String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_TITLE + " TEXT,"
            + COLUMN_COS_CODE + " TEXT," + COLUMN_COS_UNIT + " INTEGER,"
            + COLUMN_Lect_Name + " TEXT," + COLUMN_COS_DESC + " TEXT,"
            + COLUMN_CA_SCORE + " INTEGER," + COLUMN_EXAM_SCORE + " INTEGER,"
            + COLUMN_SEMESTER + " INTEGER," + COLUMN_YEAR + " INTEGER " + ")";

    // Profile Table Definition
    // Variable declarations
    public static final String Column_Fname = "fname";
    public static final String Column_Lname = "lname";
    private static final String Column_Email = "email";
    public static final String Column_Fac = "faculty";
    public static final String Column_Dept = "dept";
    private static final String Column_Reg = "regno";
    private static final String Column_Mobile = "mobile";
    private static final String Column_User = "user";
    private static final String Column_Pass = "pass";

    //creation of profile table stud
    private String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
            + Column_Fname + " TEXT," + Column_Lname + " TEXT,"
            + Column_Email + " TEXT," + Column_Fac + " TEXT,"
            + Column_Dept + " TEXT," + Column_Reg + " TEXT,"
            + Column_Mobile + " TEXT," + Column_User + " TEXT,"
            + Column_Pass + " TEXT " + ")";

    //for pulling specific columns
    public static final String[] All_keys = new String[]{COLUMN_TITLE,COLUMN_COS_CODE,COLUMN_CA_SCORE,COLUMN_EXAM_SCORE};
    // for pulling some other specific columns
    public static final String[] All_grades = new String[]{COLUMN_CA_SCORE,COLUMN_EXAM_SCORE,COLUMN_COS_UNIT};
    // profile pullinb stud
    public static final String[] All_details = new String[]{Column_Fname,Column_Lname,Column_Fac,Column_Dept};

    // DROP table string
    private String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    private String DROP_PROFILE_TABLE = "Drop table if exists " + TABLE_PROFILE;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_PROFILE_TABLE);
        onCreate(db);
    }

//    public void addCourse(CourseDetail cd){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_TITLE, cd.getCosTitle());
//        cv.put(COLUMN_COS_CODE, cd.getCosCode());
//        cv.put(COLUMN_COS_UNIT, cd.getCosUnit());
//        cv.put(COLUMN_Lect_Name, cd.getLecName());
//        cv.put(COLUMN_CA_SCORE, cd.getCaScore());
//        cv.put(COLUMN_EXAM_SCORE, cd.getExamScore());
//        cv.put(COLUMN_SEMESTER, cd.getSemester());
//        cv.put(COLUMN_YEAR, cd.getYear());
//
//        db.insert(TABLE_NAME, null, cv);
//        db.close();
//    }

    //should i decide to show the user status of the insertion
    //that is insertion successful or not
    public long addCourse(String title, String coscode, int unit, String lecname, String cosdesc,
                          int cascore, int examscore, int sem, int studyr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_COS_CODE, coscode);
        cv.put(COLUMN_COS_UNIT, unit);
        cv.put(COLUMN_Lect_Name, lecname);
        cv.put(COLUMN_COS_DESC, cosdesc);
        cv.put(COLUMN_CA_SCORE, cascore);
        cv.put(COLUMN_EXAM_SCORE, examscore);
        cv.put(COLUMN_SEMESTER, sem);
        cv.put(COLUMN_YEAR, studyr);

        // Handle error if not inserted!!
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result;
    }

    public long createProfile(String fn, String ln, String mail, String fa, String de, String reg,
                              String mo, String us, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_Fname,fn);
        cv.put(Column_Lname,ln);
        cv.put(Column_Email,mail);
        cv.put(Column_Fac,fa);
        cv.put(Column_Dept,de);
        cv.put(Column_Reg,reg);
        cv.put(Column_Mobile,mo);
        cv.put(Column_User,us);
        cv.put(Column_Pass,pass);
        // Handle error if not inserted!!
        long result = db.insert(TABLE_PROFILE, null, cv);
        db.close();
        return result;
    }

    public String getAllInformation(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from courses";
        Cursor cursor = db.rawQuery(query,null);
        StringBuffer buffer = new StringBuffer();
        if(cursor.moveToFirst()){
            do {
                String title = cursor.getString(1);
                String code = cursor.getString(2);
                int ca = cursor.getInt(6);
                int xam = cursor.getInt(7);
                int seme = cursor.getInt(8);
                int myYr = cursor.getInt(9);
                buffer.append(title + "\t"+code+ "\t"+ca+ "\t"+xam+ "\t"+seme+ "\t"+myYr+ "\n"  );
            }while (cursor.moveToNext());
        }

//        while(cursor.moveToNext()){
//            int ti = cursor.getColumnIndex(COLUMN_TITLE);
//            int co = cursor.getColumnIndex(COLUMN_COS_CODE);
//            int caz = cursor.getColumnIndex(COLUMN_CA_SCORE);
//            int xaz = cursor.getColumnIndex(COLUMN_EXAM_SCORE);
//            String title = cursor.getString(ti);
//            String code = cursor.getString(co);
//            int ca = cursor.getInt(caz);
//            int xam = cursor.getInt(xaz);
//           buffer.append(title + "\t"+code+ "\t"+ca+ "\t"+xam+ "\n"  );
//        }



        // Should we rather use projections
        // Would try that later!! I am meant to call the
        // columns based on sessions, remember to
        // remember to enclose in a try catch block!!

       return buffer.toString();
    }

    public Cursor getAllRows(int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_YEAR + "=" + year;;
        Cursor c = db.query(TABLE_NAME, All_keys, where,null,null,null,null,null);
        return c;
    }


    // to be used for getting semester and year
    // to a particular tab
    public Cursor getAllRows(int semester, int yr) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_SEMESTER + "=" + semester + " AND " + COLUMN_YEAR + "=" + yr;
        Cursor c = db.query(TABLE_NAME, All_keys, where,null,null,null,null,null);
//        if(c != null) {
//            c.moveToFirst();
//        }
        return c;
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);
        String query = "select * from courses";
        Cursor c = db.rawQuery(query,null);
        if(c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAllScore() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, All_grades, null,null,null,null,null);
        return c;
    }

    public Cursor pullDetails(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_PROFILE, All_details, null,null,null,null,null);
        return c;
    }

    public boolean checkUser(String user, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        //String[] columns = {COLUMN_ID};
        db = this.getReadableDatabase();
        String selection = Column_User + " =?" + " AND " + Column_Pass + " =?";
        String[] selectionArgs = {user, pass};

        Cursor cursor = db.query(TABLE_PROFILE, null, selection, selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count > 0)
            return true;

        return false;
    }
}
