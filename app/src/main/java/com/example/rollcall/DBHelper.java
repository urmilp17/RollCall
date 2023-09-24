package com.example.rollcall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "kc_college.db";

    // Table name and column name
    private static final String KEY_ID = "id";
    private static final String KEY_ATTENDANCE_STATUS = "AttendanceStatus";
    private static final String TABLE_ATTENDANCE = "attendance";
    private static final String COLUMN_ROLL_NUMBERS = "Roll_Numbers";
    private static final String COLUMN_SEPTEMBER_15 = "September_15";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Attendance table with Roll No.s and September 15 column
        String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " + TABLE_ATTENDANCE + "("
                + COLUMN_ROLL_NUMBERS + " INTEGER,"
                + COLUMN_SEPTEMBER_15 + " VARCHAR(20) DEFAULT 'absent')";

        db.execSQL(CREATE_ATTENDANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);
    }

    public double calculateAttendancePercentage(String dateColumn) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to count present students for the given date
        String presentQuery = "SELECT COUNT(*) FROM " + TABLE_ATTENDANCE + " WHERE " +
                dateColumn + " = 'present'";

        // Query to count absent students for the given date
        String absentQuery = "SELECT COUNT(*) FROM " + TABLE_ATTENDANCE + " WHERE " +
                dateColumn + " = 'absent'";

        Cursor presentCursor = db.rawQuery(presentQuery, null);
        Cursor absentCursor = db.rawQuery(absentQuery, null);

        int presentCount = 0;
        int absentCount = 0;

        if (presentCursor != null && presentCursor.moveToFirst()) {
            presentCount = presentCursor.getInt(0);
            presentCursor.close();
        }

        if (absentCursor != null && absentCursor.moveToFirst()) {
            absentCount = absentCursor.getInt(0);
            absentCursor.close();
        }

        int totalStudents = presentCount + absentCount;

        double presentPercentage = (double) presentCount / totalStudents * 100.0;

        double absentPercentage = (double) absentCount / totalStudents * 100.0;

        return presentPercentage;
    }


    public List<AttendanceItem> getAttendanceData() {
        List<AttendanceItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        List<String> dateColumns = new ArrayList<>();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + TABLE_ATTENDANCE + ")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int columnIndex = cursor.getColumnIndex("name");
                if (columnIndex >= 0) {
                    String columnName = cursor.getString(columnIndex);
                    if (!columnName.equals(KEY_ID) && !columnName.matches("RollNo\\d+") && !columnName.equals(KEY_ATTENDANCE_STATUS) && !columnName.equals("Roll_Numbers")) {
                        dateColumns.add(columnName);
                    }
                }
            }
            cursor.close();
        }

        for (String dateColumn : dateColumns) {
            double presentPercentage = calculateAttendancePercentage(dateColumn);
            double absentPercentage = 100.0 - presentPercentage;

            AttendanceItem item = new AttendanceItem(dateColumn, String.format("%.2f%%", presentPercentage), String.format("%.2f%%", absentPercentage));

            itemList.add(item);
        }

        return itemList;
    }

    public void addAttendanceWithDateAndRollNumbers(String date, String[] presentRollNumbers) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the date column exists, if not, add it
        if (!isDateColumnExists(date, db)) {
            // Add the new date column with default 'absent' for all rows
            ContentValues columnValue = new ContentValues();
            columnValue.put(date, "absent");
            db.update(TABLE_ATTENDANCE, columnValue, null, null);
        }

        // Update attendance records for the provided date and present roll numbers
        ContentValues values = new ContentValues();
        values.put(date, "present");

        for (String rollNumber : presentRollNumbers) {
            String whereClause = "Roll_Numbers=?";
            String[] whereArgs = {rollNumber};
            db.update(TABLE_ATTENDANCE, values, whereClause, whereArgs);
        }


        db.close();
    }

    public void addDateColumnAndUpdateTable(String date, SQLiteDatabase db) {
        // Check if the date column already exists
        if (!isDateColumnExists(date, db)) {
            String alterTableQuery = "ALTER TABLE " + TABLE_ATTENDANCE + " ADD COLUMN " + date + " TEXT DEFAULT 'absent'";
            db.execSQL(alterTableQuery);
        }
    }



    public boolean isDateColumnExists(String date, SQLiteDatabase db) {
        boolean exists = false;
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + TABLE_ATTENDANCE + ")", null);

        if (cursor != null) {
            try {
                int nameColumnIndex = cursor.getColumnIndex("name");
                if (nameColumnIndex >= 0) {
                    while (cursor.moveToNext()) {
                        String columnName = cursor.getString(nameColumnIndex);
                        if (columnName.equals(date)) {
                            exists = true;
                            break;
                        }
                    }
                }
            } finally {
                cursor.close();
            }
        }

        return exists;
    }


}
