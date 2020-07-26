package com.example.facialattandance.adaptor;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facialattandance.Activity.EmployeeActivity;
import com.example.facialattandance.Activity.ProfileActivity;
import com.example.facialattandance.Model.Employee;
import com.example.facialattandance.Model.Meeting;
import com.example.facialattandance.R;
import com.google.gson.Gson;


public class MeetingAdaptor extends RecyclerView.Adapter<MeetingAdaptor.MeetingViewHolder> {
    private Meeting[] meetings;
    public MeetingAdaptor(Meeting[] meetings) {
        this.meetings = meetings;
    }
    @NonNull
    @Override
    public MeetingAdaptor.MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.view_holder_meeting, parent, false);
        MeetingViewHolder viewHolder = new MeetingViewHolder(itemView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MeetingAdaptor.MeetingViewHolder holder, int position) {
        final Meeting meeting = meetings[position];
        holder.title.setText(meeting.getTitle());
        holder.attendee.setText(meeting.getAttendee());
        holder.date.setText(meeting.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();
                Meeting meet = meetings[index];
                String id = meet.getId().toString();
                Context context = view.getContext();
                Intent intent = new Intent(context, EmployeeActivity.class);
                intent.putExtra("1000", id);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return meetings.length;
    }

    static class MeetingViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView attendee;
        private TextView date;
        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            attendee = itemView.findViewById(R.id.attendee);
            date = itemView.findViewById(R.id.date);

        }

    }
}