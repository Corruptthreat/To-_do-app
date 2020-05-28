package com.example.to_do;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.to_do.data.DBHandler;
import com.example.to_do.model.Item;
import com.example.to_do.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DBHandler dbHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button save_button;
    private EditText enter_item;
    private EditText item_quantity;
    private EditText item_brand;
    private EditText item_size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dbHandler = new DBHandler(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        itemList = dbHandler.getAllItems();
        fab = findViewById(R.id.fab);
        for(Item item: itemList){
            Log.d("Data" , "oncreate : " + item.getItem_name());
        }
        recyclerViewAdapter = new RecyclerViewAdapter(this , itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopupDialog();
            }
        });

    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view  = getLayoutInflater().inflate(R.layout.popup,null);
        enter_item = view.findViewById(R.id.todoitem);
        item_quantity = view.findViewById(R.id.item_quantity);
        item_brand = view.findViewById(R.id.item_brand);
        item_size = view.findViewById(R.id.item_size);
        save_button = view.findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enter_item.getText().toString().isEmpty()
                        &&!item_quantity.getText().toString().isEmpty()
                        &&!item_brand.getText().toString().isEmpty()
                        &&!item_size.getText().toString().isEmpty())
                    saveItem(v);
                else{
                    Snackbar.make(v , "Empty fields not allowed" , Snackbar.LENGTH_SHORT).show();
                }
            }

        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
    private void saveItem(View view) {
        //save each item
        Item item = new Item();
        String newItem = enter_item.getText().toString().trim();
        int itemQuantity = Integer.parseInt(item_quantity.getText().toString().trim());
        String itemBrand = item_brand.getText().toString().trim();
        String itemSize = (item_size.getText().toString().trim());
        item.setItem_name(newItem);
        item.setItem_quantity(itemQuantity);
        item.setItem_brand(itemBrand);
        item.setItem_size(itemSize);
        dbHandler.addItem(item);
        Snackbar.make(view ,"Item saved", Snackbar.LENGTH_SHORT).show();

        //dialog.dismiss();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                //move to next screen
                startActivity(new Intent(ListActivity.this, ListActivity.class));
            }
        },1200);
    }
}
