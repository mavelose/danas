package com.danas.danas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "danas.db"; // Unified Database Name
    private static final int DATABASE_VERSION = 1;          // Database Version

    // Table Names
    public static final String TABLE_USER_PROFILE = "UserProfile";
    public static final String TABLE_STUDENT = "student";
    public static final String TABLE_EMPLOYEE = "employee";

    // Common Column Names
    public static final String COLUMN_ID = "id";

    // User Profile Table
    public static final String COLUMN_PROFILE_PIC = "profilePic";

    // Student Table Columns
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_UNIVERSITY = "university";
    public static final String COLUMN_PROGRAM = "program";
    public static final String COLUMN_YEAR_LEVEL = "year_level";

    // Employee Table Columns
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_NUMBER_OF_JOBS = "number_of_jobs";

    // SQL Queries to Create Tables
    private static final String CREATE_TABLE_USER_PROFILE =
            "CREATE TABLE " + TABLE_USER_PROFILE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PROFILE_PIC + " BLOB)";

    private static final String CREATE_TABLE_STUDENT =
            "CREATE TABLE " + TABLE_STUDENT + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_UNIVERSITY + " TEXT, " +
                    COLUMN_PROGRAM + " TEXT, " +
                    COLUMN_YEAR_LEVEL + " INTEGER)";

    private static final String CREATE_TABLE_EMPLOYEE =
            "CREATE TABLE " + TABLE_EMPLOYEE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COMPANY + " TEXT, " +
                    COLUMN_ROLE + " TEXT, " +
                    COLUMN_NUMBER_OF_JOBS + " INTEGER)";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create All Tables
        db.execSQL(CREATE_TABLE_USER_PROFILE);
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_EMPLOYEE);
        Log.d("DatabaseHelper", "All tables created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
        Log.d("DatabaseHelper", "Database upgraded from version " + oldVersion + " to " + newVersion);
    }

    // ===================== USER PROFILE METHODS =====================
    public byte[] getProfilePicture() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PROFILE_PIC + " FROM " + TABLE_USER_PROFILE + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {
            byte[] imageBytes = cursor.getBlob(0);
            cursor.close();
            return imageBytes;
        }
        return null; // No image found
    }

    public boolean saveProfilePicture(byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROFILE_PIC, imageBytes);
        long result = db.insert(TABLE_USER_PROFILE, null, values);
        db.close();
        return result != -1;
    }

    // ===================== STUDENT METHODS =====================
    public void saveStudentData(String firstName, String lastName, String university, String program, int yearLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_UNIVERSITY, university);
        values.put(COLUMN_PROGRAM, program);
        values.put(COLUMN_YEAR_LEVEL, yearLevel);

        long id = db.insert(TABLE_STUDENT, null, values);
        db.close();

        if (id != -1) {
            Log.d("DatabaseHelper", "Student data saved successfully with ID: " + id);
        } else {
            Log.e("DatabaseHelper", "Failed to save student data");
        }
    }

    public Cursor getStudentData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENT;
        return db.rawQuery(query, null);
    }

    // ===================== EMPLOYEE METHODS =====================
    public void saveEmployeeData(String company, String role, int numberOfJobs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_COMPANY, company);
        values.put(COLUMN_ROLE, role);
        values.put(COLUMN_NUMBER_OF_JOBS, numberOfJobs);

        long id = db.insert(TABLE_EMPLOYEE, null, values);
        db.close();

        if (id != -1) {
            Log.d("DatabaseHelper", "Employee data saved successfully with ID: " + id);
        } else {
            Log.e("DatabaseHelper", "Failed to save employee data");
        }
    }

    public Cursor getEmployeeData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEE;
        return db.rawQuery(query, null);
    }

    // ===================== UTILITY METHODS =====================
    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
        Log.d("DatabaseHelper", "Database deleted successfully");
    }
}
