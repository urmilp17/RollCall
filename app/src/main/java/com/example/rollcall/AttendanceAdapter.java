package com.example.rollcall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private List<AttendanceItem> itemList;

    public AttendanceAdapter(List<AttendanceItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_past_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceItem item = itemList.get(position);

        holder.dateTextView.setText(item.getDate());
        holder.presentTextView.setText(item.getPresentPercentage());
        holder.absentTextView.setText(item.getAbsentPercentage());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView presentTextView;
        public TextView absentTextView;

        public ViewHolder(View view) {
            super(view);
            dateTextView = view.findViewById(R.id.date);
            presentTextView = view.findViewById(R.id.present);
            absentTextView = view.findViewById(R.id.absent);
        }
    }
}

