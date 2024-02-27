package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class Content_noteActivity extends AppCompatActivity {
    private TextView title , main;
    Database database;
    private static final int REQUEST_CODE_SAVE_FILE = 1;
    ImageButton btnBold,btnItalic,btnUnderline;
    String noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_note);
         btnBold = findViewById(R.id.btn_bold);
         btnItalic = findViewById(R.id.btn_italic);
         btnUnderline = findViewById(R.id.btn_underline);
        title = findViewById(R.id.titile_content);
        main = findViewById(R.id.main_content);
        Toolbar actionBar = findViewById(R.id.content_toolbar);
        setToolbar(actionBar, "PTrainer Information");
        database = new Database(this, "note.sqlite", null, 2);

//        database.QueryData("CREATE TABLE IF NOT EXISTS NOTE(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR(200), Content VARCHAR(200), DayNote VARCHAR(200) ,  TimeNote VARCHAR(200) ); ");
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
        btnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBold(); // Chuyển đổi đậm
            }
        });

        btnItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleItalic(); // Chuyển đổi in nghiêng
            }
        });

        btnUnderline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleUnderline(); // Chuyển đổi gạch chân
            }
        });

    }
    private void toggleBold() {
        int start = main.getSelectionStart();
        int end = main.getSelectionEnd();
        SpannableStringBuilder spannable = new SpannableStringBuilder(main.getText());

        // Kiểm tra xem văn bản đã được in đậm chưa
        StyleSpan[] styleSpans = spannable.getSpans(start, end, StyleSpan.class);
        boolean isBold = false;
        for (StyleSpan styleSpan : styleSpans) {
            if (styleSpan.getStyle() == android.graphics.Typeface.BOLD) {
                isBold = true;
                break;
            }
        }

        // Nếu văn bản đã được in đậm, hãy loại bỏ định dạng in đậm
        if (isBold) {
            spannable.removeSpan(new StyleSpan(android.graphics.Typeface.BOLD));
        } else {
            spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        main.setText(spannable);
    }

    private void toggleItalic() {
        int start = main.getSelectionStart();
        int end = main.getSelectionEnd();
        SpannableStringBuilder spannable = new SpannableStringBuilder(main.getText());

        // Kiểm tra xem văn bản đã được in nghiêng chưa
        StyleSpan[] styleSpans = spannable.getSpans(start, end, StyleSpan.class);
        boolean isItalic = false;
        for (StyleSpan styleSpan : styleSpans) {
            if (styleSpan.getStyle() == android.graphics.Typeface.ITALIC) {
                isItalic = true;
                break;
            }
        }

        // Nếu văn bản đã được in nghiêng, hãy loại bỏ định dạng in nghiêng
        if (isItalic) {
            spannable.removeSpan(new StyleSpan(android.graphics.Typeface.ITALIC));
        } else {
            spannable.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        main.setText(spannable);
    }

    private void toggleUnderline() {
        int start = main.getSelectionStart();
        int end = main.getSelectionEnd();
        SpannableStringBuilder spannable = new SpannableStringBuilder(main.getText());

        // Kiểm tra xem văn bản đã được gạch chân chưa
        UnderlineSpan[] underlineSpans = spannable.getSpans(start, end, UnderlineSpan.class);
        boolean isUnderlined = underlineSpans.length > 0;

        // Nếu văn bản đã được gạch chân, hãy loại bỏ định dạng gạch chân
        if (isUnderlined) {
            spannable.removeSpan(new UnderlineSpan());
        } else {
            spannable.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        main.setText(spannable);
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
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain"); // Loại tệp bạn muốn tạo hoặc mở
        intent.putExtra(Intent.EXTRA_TITLE, "filename.txt"); // Tên mặc định của tệp

        startActivityForResult(intent, REQUEST_CODE_SAVE_FILE);
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
            openFilePicker();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SAVE_FILE) {
            if (resultCode == RESULT_OK && data != null) {
                // Lấy URI của tệp đã chọn
                Uri uri = data.getData();

                // Tiếp theo, bạn có thể sử dụng URI này để ghi dữ liệu vào tệp
                // Ví dụ: Ghi dữ liệu từ TextView main vào tệp đã chọn
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    if (outputStream != null) {
                        String content = main.getText().toString();
                        outputStream.write(content.getBytes());
                        outputStream.close();
                        Toast.makeText(this, "File đã được lưu.", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Đã xảy ra lỗi khi lưu file.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Không có tệp nào được chọn hoặc xảy ra lỗi.", Toast.LENGTH_SHORT).show();
            }
        }
    }



}