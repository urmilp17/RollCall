package com.example.rollcall;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rollcall.databinding.FragmentAddAttendanceListDialogItemBinding;
import com.example.rollcall.databinding.FragmentAddAttendanceListDialogBinding;

public class AddAttendanceFragment extends BottomSheetDialogFragment {

    private Spinner spinnerMonth,spinnerNum;
    private EditText editTextRollNumbers;
    private Button btnSubmit,btnDate;

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";


    // TODO: Customize parameters
    public static AddAttendanceFragment newInstance(int itemCount) {
        final AddAttendanceFragment fragment = new AddAttendanceFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_attendance, container, false);

        spinnerNum = view.findViewById(R.id.spinnerDate);
        spinnerMonth = view.findViewById(R.id.spinnerMonth);
        editTextRollNumbers = view.findViewById(R.id.editTextRollNumbers);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnDate = view.findViewById(R.id.btnSubmit1);


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = spinnerNum.getSelectedItem().toString();
                String month = spinnerMonth.getSelectedItem().toString();
                String indate = month + "_" + date;

                DBHelper dbHelper = new DBHelper(getContext());
                dbHelper.addDateColumnAndUpdateTable(indate, dbHelper.getWritableDatabase());

                // Show a toast message indicating that the column was created
                Toast.makeText(getContext(), "Column for " + indate + " created.", Toast.LENGTH_SHORT).show();

                // Close the fragment
                dismiss();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle attendance submission here
                // Retrieve inputs, validate, and execute the task using DBHelper
                String date = spinnerNum.getSelectedItem().toString();
                String month = spinnerMonth.getSelectedItem().toString();
                String rollNumbers = editTextRollNumbers.getText().toString();
                String indate = month + "_" + date;
                String[] presentRollNumbers = rollNumbers.split(",");

                DBHelper dbHelper = new DBHelper(getContext());
                dbHelper.addAttendanceWithDateAndRollNumbers(indate, presentRollNumbers);

                // Close the fragment
                dismiss();
            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}