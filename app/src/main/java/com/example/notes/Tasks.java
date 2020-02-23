package com.example.notes;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.Database.DatabaseHelper;
import com.example.notes.Modules.Note;
import com.example.notes.Modules.Task;
import com.example.notes.RecylerView.NotesRecylerView;
import com.example.notes.RecylerView.TasksRecylerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Tasks extends Fragment {

    private DatabaseHelper database;
    private List<Task> tasks;
    private RecyclerView taskView;
    private TasksRecylerView adapter;
    private FloatingActionButton addTasks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tasks, container, false);

        database = new DatabaseHelper(getActivity());
        tasks = new ArrayList<>();
        taskView = v.findViewById(R.id.tasksRecylerView);

        taskView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new ItemTouchHelper(itemTouchHelperSimpleCallback).attachToRecyclerView(taskView);

        retrieveData();

        return v;
    }

    public void retrieveData() {

        tasks.clear();

        StringBuffer str = new StringBuffer();
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

    ItemTouchHelper.SimpleCallback itemTouchHelperSimpleCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    tasks.remove(viewHolder.getAdapterPosition());
                    adapter.notifyDataSetChanged();
                }
            };
}
