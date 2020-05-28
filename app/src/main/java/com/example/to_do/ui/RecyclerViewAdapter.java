package com.example.to_do.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.R;
import com.example.to_do.data.DBHandler;
import com.example.to_do.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view  = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row,viewGroup,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       Item item = itemList.get(position);
       ViewHolder vh = (ViewHolder) holder;
       vh.itemName.setText(MessageFormat.format("Item: {0}", item.getItem_name()));
       vh.itemQuantity.setText(MessageFormat.format("Quantity: {0}", Integer.toString((item.getItem_quantity()))));
       vh.itemBrand.setText(MessageFormat.format("Brand: {0}", item.getItem_brand()));
       vh.itemSize.setText(MessageFormat.format("Size: {0}", (item.getItem_size())));
       vh.itemDate.setText(MessageFormat.format("Date Added: {0}", item.getDateItem()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }




    public class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemName;
        public TextView itemQuantity;
        public TextView itemBrand;
        public TextView itemSize;
        public TextView itemDate;
        public Button deleteButton;
        public Button editButton;
        public int id;
        public ViewHolder(View itemView , Context ctx){
            super(itemView);
            context = ctx;

            itemName = itemView.findViewById(R.id.item_name_list);
            itemQuantity = itemView.findViewById(R.id.item_quantity_list);
            itemBrand = itemView.findViewById(R.id.item_brand_list);
            itemSize = itemView.findViewById(R.id.item_size_list);
            itemDate = itemView.findViewById(R.id.item_added_date);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Item item = itemList.get(position);
        switch(v.getId()){
            case R.id.editButton :
                //edit item
                editItem(item);
                break;
            case R.id.deleteButton:
                //delete button
                deleteItem(item.getId());
                break;
        }
        }

        private void editItem(final Item newItem) {
            //final Item item = itemList.get(getAdapterPosition());
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup,null);
             Button save_button;
             final EditText enter_item;
             final EditText item_quantity;
            final EditText item_brand;
            final EditText item_size;
             TextView title;
            enter_item = view.findViewById(R.id.todoitem);
            item_quantity = view.findViewById(R.id.item_quantity);
            item_brand = view.findViewById(R.id.item_brand);
            item_size = view.findViewById(R.id.item_size);
            save_button = view.findViewById(R.id.save_button);
            title = view.findViewById(R.id.title);
            title.setText(R.string.edit_item_des);
            enter_item.setText(newItem.getItem_name());
            item_quantity.setText(String.valueOf(newItem.getItem_quantity()));
            item_brand.setText(newItem.getItem_brand());
            item_size.setText(newItem.getItem_size());
            save_button.setText("Update");

           builder.setView(view);
           dialog = builder.create();
           dialog.show();
            save_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHandler dbHandler = new DBHandler(context);
                    newItem.setItem_name(enter_item.getText().toString());
                   newItem.setItem_quantity(Integer.parseInt(item_quantity.getText().toString()));
                   newItem.setItem_brand(item_brand.getText().toString());
                   newItem.setItem_size(item_size.getText().toString());

                   if(!enter_item.getText().toString().isEmpty()
                           &&!item_quantity.getText().toString().isEmpty()
                           &&!item_brand.getText().toString().isEmpty()
                           &&!item_size.getText().toString().isEmpty()
                   ) {
                       dbHandler.updateItem(newItem);
                       dialog.dismiss();
                       notifyItemChanged(getAdapterPosition(), newItem);
                   }
                   else {
                       Snackbar.make(v, "Failed Empty", Snackbar.LENGTH_SHORT).show();
                   }
                }
            });
        }

        private void deleteItem(final int id){
            builder = new AlertDialog.Builder(context);
            inflater  = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation,null);
            Button noButton = view.findViewById(R.id.conf_no_btn);
            Button yesButton = view.findViewById(R.id.conf_yes_btn);
            builder.setView(view);
            dialog = builder.create();
            dialog.show();
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHandler db = new DBHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();                }
            });
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });






        }
    }
}
