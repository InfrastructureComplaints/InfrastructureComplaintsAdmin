package com.example.infrastructurecomplaintsadmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MyAdapterDeptList extends RecyclerView.Adapter<MyAdapterDeptList.MyViewHolder> {

    ArrayList<String> cmps;
    Context context;

    public MyAdapterDeptList(Context context, ArrayList<String> cmps) {
        this.context = context;
        this.cmps = cmps;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.text_dept.setText(cmps.get(position));

    }

    @Override
    public int getItemCount() {
        return cmps.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text_dept;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_dept = (TextView) itemView.findViewById(R.id.text_dept);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(v.getContext(), "Complaint clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(),Department.class);
            intent.putExtra("Name",cmps.get(getAdapterPosition()));
            v.getContext().startActivity(intent);


        }
    }
}

