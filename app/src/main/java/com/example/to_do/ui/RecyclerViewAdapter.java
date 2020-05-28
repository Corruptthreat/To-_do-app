package com.example.to_do.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.R;
import com.example.to_do.model.Item;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Item> itemList;
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
        switch(v.getId()){
            case R.id.editButton :
                //edit item
                break;
            case R.id.deleteButton:
                //delete button
                break;
        }
        }
    }
}
