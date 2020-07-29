package com.example.facialattandance.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facialattandance.Activity.EmployeeActivity;
import com.example.facialattandance.Activity.MeetingAttendeeActivity;
import com.example.facialattandance.Model.Meeting;
import com.example.facialattandance.R;
import com.example.facialattandance.utils.UtilKt;


public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {
    private final static String TAG = "MeetingAdapter";
    private Meeting[] meetings;

    public MeetingAdapter(Meeting[] meetings) {
        this.meetings = meetings;
    }

    @NonNull
    @Override
    public MeetingAdapter.MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.view_holder_meeting, parent, false);
        MeetingViewHolder viewHolder = new MeetingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.MeetingViewHolder holder, int position) {
        final Meeting meeting = meetings[position];
        String name = meeting.getName();
        name = UtilKt.capitalize(name);
        holder.title.setText(name);
        Log.d(TAG, "onBindViewHolder: " + meeting.getAttendees().length);
        holder.attendee.setText(Integer.toString(meeting.getAttendees().length));
        holder.date.setText(meeting.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int id = meeting.getId();
                Intent intent = new Intent(view.getContext(), MeetingAttendeeActivity.class);
                intent.putExtra(MeetingAttendeeActivity.MEETING_ID_KEY, id );
                intent.putExtra("MeetingName", UtilKt.capitalize(meeting.getName()));
                view.getContext().startActivity(intent);

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