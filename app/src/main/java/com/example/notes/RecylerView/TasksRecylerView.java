package com.example.notes.RecylerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Modules.Task;
import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;

public class TasksRecylerView extends RecyclerView.Adapter<TasksRecylerView.MyViewHoler> {

    private List<Task> tasks = new ArrayList<>();
    private Context context;

    public TasksRecylerView(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.task_layout, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {

        holder.completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxStateChange(v, holder, position);
            }
        });

        if ( tasks.get(position).getActivate() == 1 ) {
            holder.completed.setChecked(false);
            Activate(holder, position);
        }
        else {
            holder.completed.setChecked(true);
            Deactivate(holder, position);
        }


    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder {

        CheckBox completed;
        TextView task;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);

            completed = itemView.findViewById(R.id.taskCompleted);
            task = itemView.findViewById(R.id.task);



        }
    }

    public void onCheckBoxStateChange(View v, MyViewHoler holder, int position) {
        CheckBox checkBox = (CheckBox)v;
        if ( checkBox.isChecked() ) {
            Deactivate(holder, position);
            tasks.get(position).setActivate(1);
        }
        else {
            Activate(holder, position);
            tasks.get(position).setActivate(0);
        }
    }

    public void Activate(MyViewHoler holder, int position) {

        holder.task.setPaintFlags(holder.task.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        holder.task.setTextColor(Color.rgb(255, 255, 255));

    }

    public void Deactivate(MyViewHoler holder, int position) {

        holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.task.setTextColor(Color.rgb(166, 166, 166));

    }
}
