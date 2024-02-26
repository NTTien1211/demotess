package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Content_noteActivity extends AppCompatActivity {
    private TextView title , main;
    Database database;
    String noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_note);
        title = findViewById(R.id.titile_content);
        main = findViewById(R.id.main_content);
        Toolbar actionBar = findViewById(R.id.content_toolbar);
        setToolbar(actionBar, "PTrainer Information");
        database = new Database(this, "note.sqlite", null, 2);

        database.QueryData("CREATE TABLE IF NOT EXISTS NOTE(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR(200), Content VARCHAR(200), DayNote VARCHAR(200) ,  TimeNote VARCHAR(200) ); ");
        Intent intent = getIntent();
        String noteTitle = intent.getStringExtra("note_title");
        noteId = intent.getStringExtra("note_id");
        Toast.makeText(this, ""+noteId, Toast.LENGTH_SHORT).show();
        String noteContent = intent.getStringExtra("note_content");
        if (noteTitle == null && noteContent == null) {
            title.setText("");
            main.setText("");
        } else if (noteTitle.equals("") && noteContent.equals("")) {
            title.setText("");
            main.setText("");
        } else {
            title.setText(noteTitle);
            main.setText(noteContent);
        }

    }
    private void setToolbar(Toolbar toolbar, String name){
        setSupportActionBar(toolbar);
        SpannableString spannableString = new SpannableString(name);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(spannableString);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_undo) {
            // Xử lý khi nhấn Undo
            return true;
        } else if (id == R.id.action_save) {
            String Datatitle = title.getText().toString();
            String Datacontent = main.getText().toString();
            Calendar calendar = Calendar.getInstance();
            // Lấy ngày, tháng, năm
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, nên cần cộng thêm 1
            int year = calendar.get(Calendar.YEAR);

            // Lấy giờ, phút, giây
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            String Day =  day + "/" + month + "/" + year;
            String Time =  hour + ":" + minute ;
            if (noteId != null && !noteId.isEmpty()) {
                database.QueryData("UPDATE NOTE SET Title='" + Datatitle + "', Content='" + Datacontent + "', DayNote='" + Day + "', TimeNote='" + Time + "' WHERE Id=" + noteId);
            } else {
                if(Datatitle.equals("") && Datacontent.equals("") ){
                    Datatitle = "Untitled";
                }
                database.QueryData("INSERT INTO NOTE (Id, Title, Content, DayNote, TimeNote ) VALUES (null, '" + Datatitle + "','" + Datacontent + "','"+Day+"','"+Time+"');");
            }


            return true;
        } else if (id == R.id.action_settings) {
            // Xử lý khi nhấn Settings
            return true;
        } else if (id == R.id.action_help) {
            // Xử lý khi nhấn Help
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}