package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {
    private NavigationView navigation_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Khởi tạo FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Bắt đầu giao dịch Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Thêm Fragment mới vào Activity
        NoteFragment fragment = new NoteFragment();
        fragmentTransaction.add(R.id.navigation_main, fragment); // R.id.fragment_container là ID của ViewGroup trong layout của Activity để đặt Fragment vào

        // Hoàn thành giao dịch
        fragmentTransaction.commit();
        anhxa();
    }

    private void anhxa() {
        navigation_main = findViewById(R.id.navigation_main);
    }


}