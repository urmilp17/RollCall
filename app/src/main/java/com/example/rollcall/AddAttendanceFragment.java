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

import com.example.rollcall.databinding.FragmentAddAttendanceListDialogItemBinding;
import com.example.rollcall.databinding.FragmentAddAttendanceListDialogBinding;

/**
 *
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     fragment_add_attendance.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class AddAttendanceFragment extends BottomSheetDialogFragment {

    private Spinner spinnerMonth,spinnerNum;
    private EditText editTextRollNumbers;
    private Button btnSubmit;

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    private FragmentAddAttendanceListDialogBinding binding;

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
        View view = inflater.inflate(R.layout.fragment_add_attendance_list_dialog, container, false);

        spinnerNum = view.findViewById(R.id.spinnerDate);
        spinnerMonth = view.findViewById(R.id.spinnerMonth);
        editTextRollNumbers = view.findViewById(R.id.editTextRollNumbers);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve inputs
                String date = spinnerNum.getSelectedItem().toString();
                String month = spinnerMonth.getSelectedItem().toString();
                String rollNumbers = editTextRollNumbers.getText().toString();
                String indate = month+"_"+date;

                // Convert the comma-separated roll numbers to an array
                String[] presentRollNumbers = rollNumbers.split(",");

                // Validate and execute the task using a function from DBHelper
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