package com.example.notes.RecylerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.EditNote;
import com.example.notes.Modules.Note;
import com.example.notes.R;

import java.util.List;

public class NotesRecylerView extends RecyclerView.Adapter<NotesRecylerView.MyViewHolder> {

    private List<Note> notes;
    private Context context;

    public NotesRecylerView(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.note_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.note.setText(this.notes.get(position).getNote());
        holder.lastupdate.setText(this.notes.get(position).getUpdatedAt());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNote.class);
                intent.putExtra("id", Integer.toString(notes.get(position).getId()));
                intent.putExtra("note", notes.get(position).getNote());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView note, lastupdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            note = itemView.findViewById(R.id.note);
            lastupdate = itemView.findViewById(R.id.lastupdate);
        }
    }
}
