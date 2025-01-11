package com.danas.danas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database information
    private static final String DATABASE_NAME = "danas.db"; // Database name
    private static final int DATABASE_VERSION = 1;          // Database version

    // Table names
    public static final String TABLE_STUDENT = "student";
    public static final String TABLE_EMPLOYEE = "employee";

    // Common column names
    public static final String COLUMN_ID = "id";

    // Student table column names
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_UNIVERSITY = "university";
    public static final String COLUMN_PROGRAM = "program";
    public static final String COLUMN_YEAR_LEVEL = "year_level";

    // Employee table column names
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_NUMBER_OF_JOBS = "number_of_jobs";

    // SQL statements for creating tables
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
        // Create tables
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_EMPLOYEE);
        Log.d("DatabaseHelper", "Tables created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        // Create tables again
        onCreate(db);
        Log.d("DatabaseHelper", "Database upgraded from version " + oldVersion + " to " + newVersion);
    }

    // Method to save student data
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

    // Method to save employee data
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

    // Optional: Add utility methods for database operations
    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
        Log.d("DatabaseHelper", "Database deleted successfully");
    }
}