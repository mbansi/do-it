package com.bansi.doit.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansi.doit.AddNewTask;
import com.bansi.doit.MainActivity;
import com.bansi.doit.R;
import com.bansi.doit.database.DoItDatabaseHandler;
import com.bansi.doit.model.ToDoModel;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private MainActivity activity;
    private ArrayList<ToDoModel> taskList;
    private DoItDatabaseHandler db;
    public ToDoAdapter(Activity activity,ArrayList<ToDoModel> taskList){
        this.activity = (MainActivity) activity;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoModel item = taskList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int newStatus;
                if(isChecked){
                    //newStatus = db.updateStatus(item.getId(),1);
                }
                else{
                    //newStatus = db.updateStatus(item.getId(),0);
                }
            }
        });
    }
    private boolean toBoolean(int n){
        return n!=0;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setTasks(ArrayList<ToDoModel> todoList){
        this.taskList = todoList;
        notifyDataSetChanged();
    }

    public void editItem(int position) {
        ToDoModel item = taskList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox task;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.cbTodo);
        }
    }
}
