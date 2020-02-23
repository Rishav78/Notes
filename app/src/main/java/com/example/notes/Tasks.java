package com.example.notes;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.Database.DatabaseHelper;
import com.example.notes.Modules.Task;
import com.example.notes.RecylerView.TasksRecylerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Tasks extends Fragment {

    private DatabaseHelper database;
    private List<Task> tasks;
    private RecyclerView taskView;
    private TasksRecylerView adapter;
    private FloatingActionButton addTasksButton;
    private BottomSheetDialog addNewTaskDiaglog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tasks, container, false);

        database = new DatabaseHelper(getActivity());
        tasks = new ArrayList<>();
        taskView = v.findViewById(R.id.tasksRecylerView);
        addTasksButton = v.findViewById(R.id.newTask);

        taskView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new ItemTouchHelper(itemTouchHelperSimpleCallback).attachToRecyclerView(taskView);
        retrieveData();
        createBottonSheetDiaglog();
        addTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottonSheetDiaglog();
            }
        });

        return v;
    }

    public void retrieveData() {

        tasks.clear();
        Cursor res = database.getAllData_table2();

        while ( res.moveToNext() ) {

            int ID = Integer.parseInt(res.getString(0));
            String TASK = res.getString(1), UPDATEDAT = res.getString(2);
            int active = Integer.parseInt(res.getString(3));
            tasks.add(new Task(ID, TASK, active, UPDATEDAT));

        }

        if ( tasks.size() == 0 ) {

            Toast.makeText(getActivity(), "No task in the database", Toast.LENGTH_LONG).show();

        }

        adapter = new TasksRecylerView(getActivity(), tasks);
        taskView.setAdapter(adapter);

    }

    public void createBottonSheetDiaglog() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_task_layout, null);
        EditText taskDescription = view.findViewById(R.id.editTask);
        Button updateTask = view.findViewById(R.id.updateTask);

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

        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskDescription.getText().toString();
                database.insert_table2(task);
                retrieveData();
                addNewTaskDiaglog.dismiss();
            }
        });

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

        addNewTaskDiaglog = new BottomSheetDialog(getActivity());
        addNewTaskDiaglog.setContentView(view);

    }

    public void showBottonSheetDiaglog() {

        this.addNewTaskDiaglog.show();

    }

    public void activateButton( Button updateTask ) {

        updateTask.setEnabled(true);
        updateTask.setTextColor(Color.rgb(255, 165, 0));

    }

    public void deactivateButton( Button updateTask ) {

        updateTask.setEnabled(false);
        updateTask.setTextColor(Color.rgb(77, 77, 77));

    }

    ItemTouchHelper.SimpleCallback itemTouchHelperSimpleCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    database.delete_table2(Integer.toString(tasks.get(viewHolder.getAdapterPosition()).getId()));
                    tasks.remove(viewHolder.getAdapterPosition());
                    adapter.notifyDataSetChanged();
                }
            };
}
