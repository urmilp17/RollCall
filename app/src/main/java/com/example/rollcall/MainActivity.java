package com.example.rollcall;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mAddFab, mAddAlarmFab, mAddAttendanceFab, mAddVenueFab;

    // These are taken to make visible and invisible along with FABs

    // to check whether sub FAB buttons are visible or not.
    Boolean isAllFabsVisible;

    private RecyclerView lectureView,attendanceView;
    private AttendanceAdapter adapter1;
    private LectureAdapter adapter;
    private List<LectureItem> lectureList;
    private List<AttendanceItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddFab = findViewById(R.id.add_fab);

        // FAB button
        mAddAlarmFab = findViewById(R.id.show_attendance_fab);
        mAddAttendanceFab = findViewById(R.id.add_attendance_fab);
        mAddVenueFab = findViewById(R.id.add_venue_fab);
        isAllFabsVisible = false;

        mAddFab.setOnClickListener(view -> {
            if (!isAllFabsVisible) {
                mAddAlarmFab.show();
                mAddAttendanceFab.show();
                mAddVenueFab.show();

                isAllFabsVisible = true;
            } else {
                mAddAlarmFab.hide();
                mAddAttendanceFab.hide();
                mAddVenueFab.hide();
                isAllFabsVisible = false;
            }
        });

        mAddAttendanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAttendanceFragment bottomFragment = new AddAttendanceFragment();
                bottomFragment.show(getSupportFragmentManager(), bottomFragment.getTag());
            }
        });





        DBHelper dbHelper = new DBHelper(this);
        attendanceView = findViewById(R.id.recyclerView1);
        attendanceView.setLayoutManager(new LinearLayoutManager(this));
        List<AttendanceItem> itemList = dbHelper.getAttendanceData(); // Replace with your data
        adapter1 = new AttendanceAdapter(itemList);
        attendanceView.setAdapter(adapter1);

        lectureView = findViewById(R.id.recyclerView);
        lectureView.setLayoutManager(new LinearLayoutManager(this));
        lectureList = new ArrayList<>();

        // Add sample lecture items (replace with your data)
        lectureList.add(new LectureItem("CS LAB", "18/9/23"));
        lectureList.add(new LectureItem("HW LAB", "19/9/23"));
        lectureList.add(new LectureItem("1 LAW", "20/9/23"));
        lectureList.add(new LectureItem("203 KC", "21/9/23"));

        adapter = new LectureAdapter(this, lectureList);
        lectureView.setAdapter(adapter);

        SQLiteDatabase dbs = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();




    }

}
