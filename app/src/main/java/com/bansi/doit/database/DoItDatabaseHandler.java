package com.bansi.doit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bansi.doit.model.ToDoModel;

import java.util.ArrayList;

public class DoItDatabaseHandler extends MainDb{

    private static final String DO_IT_TABLE = "DoItTable";
    private static final String ID = "Id";
    private static final String TASK = "Task";
    private static final String STATUS = "Status";

    public DoItDatabaseHandler(Context context) {
        super(context);
    }

    public ArrayList<ToDoModel> getAllTasks(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ToDoModel> taskList = new ArrayList<>();
        String query = "SELECT * FROM " + DO_IT_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                taskList.add(getCreatedModelUsingCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public ToDoModel getCreatedModelUsingCursor(Cursor cursor){
        ToDoModel model = new ToDoModel();
        model.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        model.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
        model.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
        return model;
    }

    public void insertTask(ToDoModel task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK,task.getTask());
        cv.put(STATUS,0);
        db.insert(DO_IT_TABLE,null,cv);
        db.close();
    }

    public void updateStatus(int id,int status){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(STATUS,status);
        db.update(DO_IT_TABLE,cv,ID + "=?", new String[]{ String.valueOf(id)});
        db.close();
    }

    public void updateTask(int id,String task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(TASK,task);
        db.update(DO_IT_TABLE,cv,ID + "=?", new String[]{ String.valueOf(id)});
        db.close();

    }

    public void deleteTask(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DO_IT_TABLE, ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}
