package com.example.dsa_visualizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView Sorting,Searching,Stack_Array,Stack_List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sorting=findViewById(R.id.soring);
        Searching=findViewById(R.id.searching);
        Stack_Array=findViewById(R.id.stackArray);
        Stack_List=findViewById(R.id.stackList);
        Sorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Bubble_sort.class);
                startActivity(intent);
            }
        });
        Searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Search.class);
                startActivity(intent);
            }
        });
        Stack_Array.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Stack_array.class);
                startActivity(intent);
            }
        });
        Stack_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Stack_list.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.infixTopostfix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, infixTopostfix.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.infixToprefix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, infixToprefix.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.prefixTopostfix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, prefixTopostfix.class);
                startActivity(intent);
            }
        });

    }


}
