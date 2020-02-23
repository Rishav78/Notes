package com.example.notes.RecylerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Database.DatabaseHelper;
import com.example.notes.Modules.Task;
import com.example.notes.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class TasksRecylerView extends RecyclerView.Adapter<TasksRecylerView.MyViewHoler> {

    private List<Task> tasks = new ArrayList<>();
    private BottomSheetDialog addNewTaskDiaglog;
    private Context context;
    private DatabaseHelper database;

    public TasksRecylerView(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        this.database = new DatabaseHelper(context);
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

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndShowBottonSheetDiaglog(position);
            }
        });

        holder.completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                if ( checkBox.isChecked() ) {
                    database.update_table2( Integer.toString(tasks.get(position).getId()), tasks.get(position).getTask(), 0);
                    tasks.get(position).setActivate(0);
                    Deactivate(holder, position);
                }
                else {

                    database.update_table2( Integer.toString(tasks.get(position).getId()), tasks.get(position).getTask(), 1);
                    tasks.get(position).setActivate(1);
                    Activate(holder, position);
                }
            }
        });

        holder.task.setText(tasks.get(position).getTask());

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
        View view;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            completed = itemView.findViewById(R.id.taskCompleted);
            task = itemView.findViewById(R.id.task);

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

    public void createAndShowBottonSheetDiaglog(int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.edit_task_layout, null);
        EditText taskDescription = view.findViewById(R.id.editTask);
        Button updateTask = view.findViewById(R.id.updateTask);

        taskDescription.setText(tasks.get(position).getTask());
        activateButton(updateTask);

        taskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if ( taskDescription.getText().toString().equals("") ) {

                    deactivateButton(updateTask);

                }
                else {

                    activateButton(updateTask);

                }
            }
        });

        addNewTaskDiaglog = new BottomSheetDialog(context);
        addNewTaskDiaglog.setContentView(view);

        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTask = taskDescription.getText().toString();
                tasks.get(position).setTask(updatedTask);
                database.update_table2(Integer.toString(tasks.get(position).getId()), updatedTask, tasks.get(position).getActivate());
                notifyDataSetChanged();
                addNewTaskDiaglog.hide();
            }
        });

        addNewTaskDiaglog.show();

    }

    public void deactivateButton( Button updateTask ) {

        updateTask.setEnabled(false);
        updateTask.setTextColor(Color.rgb(77, 77, 77));

    }

    public void activateButton( Button updateTask ) {

        updateTask.setEnabled(true);
        updateTask.setTextColor(Color.rgb(255, 165, 0));

    }
}
