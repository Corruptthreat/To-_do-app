package com.example.to_do;

import android.content.Intent;
import android.os.Bundle;

import com.example.to_do.data.DBHandler;
import com.example.to_do.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
       private AlertDialog.Builder builder;
       private AlertDialog dialog;
       private Button save_button;
       private EditText enter_item;
       private EditText item_quantity;
       private EditText item_brand;
       private EditText item_size;
       private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHandler = new DBHandler(this);
        List<Item> items = dbHandler.getAllItems();
        for (Item item : items){
            Log.d("Main" , "onCreate: " + item.getItem_name());
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                createPopupDialog();
            }
        });
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
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        },1200);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
