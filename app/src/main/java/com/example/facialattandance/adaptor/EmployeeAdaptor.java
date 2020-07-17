package com.example.facialattandance.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facialattandance.Activity.ProfileActivity;
import com.example.facialattandance.Model.Employee;
import com.example.facialattandance.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;


public class EmployeeAdaptor extends RecyclerView.Adapter<EmployeeAdaptor.EmployeeViewHolder> {
    private Employee[] employees;
    public EmployeeAdaptor(Employee[] employees) {
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

    @Override
    public void onBindViewHolder(@NonNull final EmployeeViewHolder holder, int position) {
        final Employee employee = employees[position];
        holder.name.setText(employee.getName());
        holder.department.setText(employee.getDepartment());
        holder.img_profile.setImageURI(employee.getImageUrl());

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
                intent.putExtra(ProfileActivity.EXTRA_EMPLOYEE.INSTANCE.getCODE(), employeeJson);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return employees.length; }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView department;
        private SimpleDraweeView img_profile;
        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            department = itemView.findViewById(R.id.department);
            img_profile = itemView.findViewById(R.id.img_profile);

        }

    }
}


