package com.example.symphony;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "symphony.db";
    private static final int DATABASE_VERSION = 1;

    // Table Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    // Table Employees
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String COLUMN_EMPLOYEE_ID = "id";
    private static final String COLUMN_EMPLOYEE_NAME = "name";
    private static final String COLUMN_EMPLOYEE_POSITION = "position";

    // Table Attendance
    private static final String TABLE_ATTENDANCE = "attendance";
    private static final String COLUMN_ATTENDANCE_ID = "id";
    private static final String COLUMN_EMPLOYEE_ID_FK = "employee_id";
    private static final String COLUMN_ATTENDANCE_DATE = "date";
    private static final String COLUMN_ATTENDANCE_STATUS = "status";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_EMAIL + " TEXT UNIQUE, "
                + COLUMN_USER_PASSWORD + " TEXT);";

        String createEmployeeTable = "CREATE TABLE " + TABLE_EMPLOYEES + " ("
                + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMPLOYEE_NAME + " TEXT, "
                + COLUMN_EMPLOYEE_POSITION + " TEXT);";

        String createAttendanceTable = "CREATE TABLE " + TABLE_ATTENDANCE + " ("
                + COLUMN_ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMPLOYEE_ID_FK + " INTEGER, "
                + COLUMN_ATTENDANCE_DATE + " TEXT, "
                + COLUMN_ATTENDANCE_STATUS + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_EMPLOYEE_ID_FK + ") REFERENCES " + TABLE_EMPLOYEES + "(" + COLUMN_EMPLOYEE_ID + "));";

        db.execSQL(createUserTable);
        db.execSQL(createEmployeeTable);
        db.execSQL(createAttendanceTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);
    }

    // creating new user
    public boolean registerUser(String email, String password, String birthday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // login user
    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?",
                new String[]{email, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
