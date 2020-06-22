package com.example.facialattandance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;


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
        Employee employee = employees[position];
        holder.name.setText(employee.getName());
        holder.department.setText(employee.getDepartment());
        holder.img_profile.setImageURI(employee.getImage());
    }

    @Override
    public int getItemCount() { return employees.length; }

    // ViewHolder
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


