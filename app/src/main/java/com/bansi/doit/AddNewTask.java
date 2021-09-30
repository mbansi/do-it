package com.bansi.doit;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bansi.doit.database.DoItDatabaseHandler;
import com.bansi.doit.model.ToDoModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText newTask;
    private Button saveNewTask;
    private DoItDatabaseHandler db;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTask = getView().findViewById(R.id.etNewtask);
        saveNewTask = getView().findViewById(R.id.btnNewtask);


        newTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().isEmpty()){

                    saveNewTask.setEnabled(false);
                    saveNewTask.setTextColor(Color.LTGRAY);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    saveNewTask.setEnabled(false);
                    saveNewTask.setTextColor(Color.LTGRAY);
                }
                else{
                    saveNewTask.setEnabled(true);
                    saveNewTask.setTextColor(ContextCompat.getColor(getContext(),R.color.app_default_color ));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        saveNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = newTask.getText().toString();
                if(validTask(text)){
                   long newTask = new DoItDatabaseHandler(getActivity().getApplicationContext()).insertTask(text);

                   Toast.makeText(getContext(),"Added Task",Toast.LENGTH_LONG);
                }
                else{
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                }
                dismiss();
            }
        });
    }

    boolean validTask(String task){
        boolean isUpdate = false;
        if(task.length()<=0){
            return isUpdate;
        }
        else{
            isUpdate = true;
        }
        return isUpdate;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if( activity instanceof DialogCloseListener){
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
