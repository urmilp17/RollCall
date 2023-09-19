package com.example.rollcall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> {

    private Context context;
    private List<LectureItem> lectureList;

    public LectureAdapter(Context context, List<LectureItem> lectureList) {
        this.context = context;
        this.lectureList = lectureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LectureItem lectureItem = lectureList.get(position);
        holder.locationNameTextView.setText(lectureItem.getLocationName());
        holder.eventDateTextView.setText(lectureItem.getEventDate());
    }

    @Override
    public int getItemCount() {
        return lectureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationNameTextView;
        TextView eventDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationNameTextView = itemView.findViewById(R.id.locationNameTextView);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView);
        }
    }
}

