package com.example.appcolornote.Arapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcolornote.InterFace.ClickIterFaceItemCheckList;
import com.example.appcolornote.Model.CheckList;
import com.example.appcolornote.Model.ItemCheckList;
import com.example.appcolornote.R;

import java.util.ArrayList;

public class ArapterItemCheckList extends RecyclerView.Adapter<ArapterItemCheckList.holer> {
    private static ClickIterFaceItemCheckList clickIterFaceItemCheckList;
    Context mcontext ;
    ArrayList<ItemCheckList> itemCheckLists ;




    public ArapterItemCheckList(Context mcontext, ArrayList<ItemCheckList> itemCheckLists) {
        this.mcontext = mcontext;
        this.itemCheckLists = itemCheckLists;
    }

    public  void setClickIterFaceItemCheckList(ClickIterFaceItemCheckList clickIterFaceItemCheckList) {
        ArapterItemCheckList.clickIterFaceItemCheckList = clickIterFaceItemCheckList;
    }

    @NonNull
    @Override

    public ArapterItemCheckList.holer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.custom_row_add_check_list,parent,false);
        return new holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArapterItemCheckList.holer holder, int position) {
        ItemCheckList itemCheckList= itemCheckLists.get(position);
        holder.txtCheckList.setText(itemCheckList.getContent());
    }

    @Override
    public int getItemCount() {
        return itemCheckLists.size();
    }

    public class holer extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtCheckList ;
        ImageButton imgDelete ;
        ItemCheckList itemCheckList ;
        public holer(@NonNull View itemView) {
            super(itemView);
            txtCheckList = itemView.findViewById(R.id.txtCheckList);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemCheckList = itemCheckLists.get(getAdapterPosition());
            clickIterFaceItemCheckList.ClickItemCheckList(getAdapterPosition(),itemCheckList);
        }
    }

}
