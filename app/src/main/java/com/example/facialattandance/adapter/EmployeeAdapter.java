package com.example.facialattandance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.facialattandance.Activity.ProfileActivity;
import com.example.facialattandance.Model.Employee;
import com.example.facialattandance.R;
import com.example.facialattandance.utils.UtilKt;
import com.google.gson.Gson;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private static String TAG = "EmployeeAdapter";
    private Employee[] employees;
    public EmployeeAdapter(Employee[] employees) {
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.view_holder_employee, parent, false);
        EmployeeViewHolder viewHolder = new EmployeeViewHolder(itemView);
        return viewHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final EmployeeViewHolder holder, int position) {
        final Employee employee = employees[position];
        holder.name.setText(UtilKt.capitalize(employee.getName()));
        holder.department.setText(employee.getDepartment());
        holder.position.setText(employee.getPosition());
//        holder.img_profile.setImageURI(employee.getImageUrl());
        Log.d(TAG, "onBindViewHolder: ");
        try{
            Glide.with(holder.itemView).load(Uri.parse(employee.getProfile_url())).into(holder.img_profile);
        }catch (NullPointerException e){
            holder.img_profile.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.profile));
        }

        // Apply on click listener on each view holder
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();
                Employee employee = employees[index];

                // Serialize mail object to json string
                Gson gson = new Gson();
                String employeeJson = gson.toJson(employee);
                // Start MailDetailActivity
                Context context = view.getContext();
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_EMPLOYEE.getCODE(), employeeJson);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return employees.length; }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView department;
//        private SimpleDraweeView img_profile;
        private ImageView img_profile;
        private TextView position;
        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            department = itemView.findViewById(R.id.department);
            img_profile = itemView.findViewById(R.id.img_profile);
            position = itemView.findViewById(R.id.position_);
        }
    }
}


