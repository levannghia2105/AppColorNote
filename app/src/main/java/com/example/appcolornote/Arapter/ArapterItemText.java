package com.example.appcolornote.Arapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcolornote.InterFace.ClickInterFace;
import com.example.appcolornote.Model.Text;
import com.example.appcolornote.R;

import java.util.ArrayList;


public class ArapterItemText extends RecyclerView.Adapter<ArapterItemText.Holer> implements Filterable {
    private static ClickInterFace clickInterFace;
    @NonNull
    Context mConText;
    ArrayList<Text> arrayListText ;
    Text mText ;
    ArrayList<Text> arrayListTextOld ;



    public void setClickInterFace(ClickInterFace clickInterFace) {
        ArapterItemText.clickInterFace = clickInterFace;
    }

    public ArapterItemText(@NonNull Context mConText, ArrayList<Text> arrayListText) {
        this.mConText = mConText;
        this.arrayListText = arrayListText;
        this.arrayListTextOld = arrayListText;
    }

    @Override
    public ArapterItemText.Holer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(mConText).inflate(R.layout.custom_row_recy_text,parent,false);

        return new Holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArapterItemText.Holer holder, int position) {
        Text mText1 = arrayListText.get(position);

//        holder.txtContent.setText(mText1.getContentText());
        holder.txtTitle.setText(mText1.getTitleText());
        holder.itemView.setBackgroundColor(Color.parseColor(mText1.getColorText()));

    }

    @Override
    public int getItemCount() {
        if (arrayListText !=null ){
            return arrayListText.size();
        }
        return 0 ;

    }



    public class Holer extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle , txtContent ;

        public Holer(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtTitle = itemView.findViewById(R.id.txtTitle);
//            txtContent = itemView.findViewById(R.id.txtContent);
        }

        @Override
        public void onClick(View v) {
            mText = arrayListText.get(getAdapterPosition());
            clickInterFace.OnItemClick(getAdapterPosition(),v,mText);
        }

    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //lấy ra string lúc search
                String strSeach = constraint.toString().trim();
                if (strSeach.isEmpty()){
                    arrayListText = arrayListTextOld;
                }
                else {
                    ArrayList<Text> listText = new ArrayList<>();
                   for (int i=0 ; i<arrayListTextOld.size();i++){
                       if (arrayListTextOld.get(i).getTitleText().toLowerCase().trim().contains(strSeach.toLowerCase())==true){
                           listText.add(arrayListTextOld.get(i));
                       }
                   }
                    arrayListText = listText ;
                }
                FilterResults filterResults  = new FilterResults();
                filterResults.values = arrayListText ;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               arrayListText = (ArrayList<Text>) results.values;
               notifyDataSetChanged();
            }
        };
    }
}
