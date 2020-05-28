package com.example.to_do.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.to_do.model.Item;
import com.example.to_do.util.Utils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {
    private final Context context;

    public DBHandler(Context context) {
        super(context, Utils.DB_NAME,null,Utils.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + Utils.TABLE_NAME + "("
                + Utils.KEY_ID + " INTEGER PRIMARY KEY, " + Utils.KEY_ITEM_NAME + " TEXT,"
                + Utils.KEY_ITEM_QUANTITY + " INTEGER,"
                + Utils.KEY_ITEM_BRAND + " TEXT,"
                + Utils.KEY_ITEM_SIZE + " TEXT,"
                + Utils.KEY_DATE_NAME + " LONG"
                + ")";

        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME);
         onCreate(db);
    }

    //CRUD operation
    public void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.KEY_ITEM_NAME,item.getItem_name());
        values.put(Utils.KEY_ITEM_QUANTITY,item.getItem_quantity());
        values.put(Utils.KEY_ITEM_BRAND,item.getItem_brand());
        values.put(Utils.KEY_ITEM_SIZE,item.getItem_size());
        values.put(Utils.KEY_DATE_NAME, java.lang.System.currentTimeMillis()); //timestamp

        //insert values
        db.insert(Utils.TABLE_NAME , null , values);
        Log.d("TAG" , " added item ");
    }

    public Item getItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Utils.TABLE_NAME,
                new String[]{Utils.KEY_ID,
                Utils.KEY_ITEM_NAME,
                Utils.KEY_ITEM_QUANTITY,
                Utils.KEY_ITEM_BRAND,
                Utils.KEY_ITEM_SIZE,
                Utils.KEY_DATE_NAME},
                Utils.KEY_ID + "=?",
                new String[]{
                        String.valueOf(id)}, null , null , null , null);

         if(cursor!=null){
             cursor.moveToFirst();
         }
         Item item = new Item();
         if (cursor!=null){
             item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Utils.KEY_ID))));
             item.setItem_name((cursor.getString(cursor.getColumnIndex(Utils.KEY_ITEM_NAME))));
             item.setItem_quantity((cursor.getInt(cursor.getColumnIndex(Utils.KEY_ITEM_QUANTITY))));
             item.setItem_brand((cursor.getString(cursor.getColumnIndex(Utils.KEY_ITEM_BRAND))));
             item.setItem_size((cursor.getString(cursor.getColumnIndex(Utils.KEY_ITEM_SIZE))));
             //convert timestamp to readable data
         DateFormat dateFormat = DateFormat.getDateInstance();
         String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Utils.KEY_DATE_NAME)))
                 .getTime());
         item.setDateItem(formattedDate);
         }
         return item;
    }

    public List<Item> getAllItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> itemList = new ArrayList<>();
        Cursor cursor = db.query(Utils.TABLE_NAME,
                new String[]{Utils.KEY_ID,
                        Utils.KEY_ITEM_NAME,
                        Utils.KEY_ITEM_QUANTITY,
                        Utils.KEY_ITEM_BRAND,
                        Utils.KEY_ITEM_SIZE,
                        Utils.KEY_DATE_NAME},
                        null , null , null , null , Utils.KEY_DATE_NAME + " DESC");
       if(cursor.moveToFirst()){
           do {
               Item item = new Item();
               item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Utils.KEY_ID))));
               item.setItem_name((cursor.getString(cursor.getColumnIndex(Utils.KEY_ITEM_NAME))));
               item.setItem_quantity((cursor.getInt(cursor.getColumnIndex(Utils.KEY_ITEM_QUANTITY))));
               item.setItem_brand((cursor.getString(cursor.getColumnIndex(Utils.KEY_ITEM_BRAND))));
               item.setItem_size((cursor.getString(cursor.getColumnIndex(Utils.KEY_ITEM_SIZE))));
               //convert timestamp to readable data
               DateFormat dateFormat = DateFormat.getDateInstance();
               String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Utils.KEY_DATE_NAME)))
                       .getTime());
               item.setDateItem(formattedDate);
               //add to list
               itemList.add(item);
           } while (cursor.moveToNext());
       }
       return itemList;
    }
    //updateItem
    public int updateContact(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.KEY_ITEM_NAME,item.getItem_name());
        values.put(Utils.KEY_ITEM_QUANTITY,item.getItem_quantity());
        values.put(Utils.KEY_ITEM_BRAND,item.getItem_brand());
        values.put(Utils.KEY_ITEM_SIZE,item.getItem_size());
        values.put(Utils.KEY_DATE_NAME, java.lang.System.currentTimeMillis()); //timestamp

        return db.update(Utils.TABLE_NAME , values , Utils.KEY_ID + "=?" , new String[]{String.valueOf(item.getId())});
    }
    //delete item
    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Utils.TABLE_NAME,Utils.KEY_ID + "=?" , new String[]{String.valueOf(id)});
        db.close();
    }

    // getItemCount
    public int getItemCount(){
         String countQuery = "SELECT * FROM " + Utils.TABLE_NAME;
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(countQuery,null);
         return cursor.getCount();
    }
}
