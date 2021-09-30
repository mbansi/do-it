package com.bansi.doit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bansi.doit.adapter.ToDoAdapter;

import com.bansi.doit.database.DoItDatabaseHandler;
import com.bansi.doit.model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{


    private RecyclerView rvTask;
    private ToDoAdapter taskAdapter;
    private ArrayList<ToDoModel> taskList;
    private FloatingActionButton fab;
    private DoItDatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DoItDatabaseHandler(this);
        taskList = new ArrayList<>();
        fab = findViewById(R.id.fabAdd);


        rvTask = findViewById(R.id.rvTasks);
        rvTask.setHasFixedSize(true);
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdapter(this,taskList);
        rvTask.setAdapter(taskAdapter);

        taskList.addAll(db.getAllTasks());
        taskAdapter.setTasks(taskList);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
    }



    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();
    }
}