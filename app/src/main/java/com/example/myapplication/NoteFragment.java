package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class NoteFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclview;
    Database database;
    ArrayList<NoteModel> mlist ;
    Note_show_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        database = new Database(getActivity(), "note.sqlite", null, 2);
        mlist = new ArrayList<>();


        database.QueryData("CREATE TABLE IF NOT EXISTS NOTE(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR(200), Content VARCHAR(200), DayNote VARCHAR(200) ,  TimeNote VARCHAR(200) ); ");
        GetDataJob();
        floatingActionButton = view.findViewById(R.id.button);
        recyclview = view.findViewById(R.id.recyclview);
        adapter = new Note_show_adapter(mlist);
        recyclview.setAdapter(adapter);
        recyclview.setLayoutManager(new LinearLayoutManager(getActivity()));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Content_noteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void GetDataJob(){
        Cursor dataNote = database.GetData("SELECT * FROM NOTE");
        mlist.clear();
        if (dataNote!=null){
            while (dataNote.moveToNext()){
                int id = dataNote.getInt(0);
                String name = dataNote.getString(1);
                String note = dataNote.getString(2);
                String day = dataNote.getString(3);
                String time = dataNote.getString(4);
                mlist.add(new NoteModel(id, name, note,day, time));
            }

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        GetDataJob();
        super.onResume();
    }
}
