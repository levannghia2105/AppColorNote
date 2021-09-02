package com.example.appcolornote.Arapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcolornote.Model.ItemMenu;
import com.example.appcolornote.R;

import java.util.ArrayList;

public class ArapterItemMenu extends RecyclerView.Adapter<ArapterItemMenu.ViewHoler> {
 ArrayList<ItemMenu> menuArrayList ;
 Context mcontext ;

    public ArapterItemMenu(ArrayList<ItemMenu> menuArrayList, Context mcontext) {
        this.menuArrayList = menuArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.custom_rowmenu,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
             ItemMenu itemMenu = menuArrayList.get(position);
             holder.imgIcon.setImageResource(itemMenu.getIconMenu());
             holder.txtMenu.setText(itemMenu.getNameMenu());
    }

    @Override
    public int getItemCount() {
        return menuArrayList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
       final ImageView imgIcon  ;
        final TextView txtMenu ;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgRowMenu);
            txtMenu = itemView.findViewById(R.id.txtRowMenu);

        }
    }
}
