package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Note_show_adapter extends RecyclerView.Adapter<Note_show_adapter.Note_ViewHolder> {
    List<NoteModel> modelList;
    Context context ;
    public Note_show_adapter(List<NoteModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Note_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
        return new Note_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Note_ViewHolder holder, int position) {
        NoteModel item = modelList.get(position);
        holder.text_title.setText(item.getTitle());
        holder.daynote.setText(item.getDay());
        holder.timenote.setText(item.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Content_noteActivity.class);
                intent.putExtra("note_title", item.getTitle());
                intent.putExtra("note_id", String.valueOf(item.getId()));
                intent.putExtra("note_content", item.getContent());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Note_ViewHolder extends RecyclerView.ViewHolder{
        TextView text_title, daynote, timenote;
        public Note_ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            daynote = itemView.findViewById(R.id.day_note);
            timenote = itemView.findViewById(R.id.time_note);
        }
    }
}
