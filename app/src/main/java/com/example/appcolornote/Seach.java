package com.example.appcolornote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.appcolornote.Arapter.ArapterItemText;
import com.example.appcolornote.Model.Text;

import java.util.ArrayList;

public class Seach extends AppCompatActivity implements View.OnClickListener {
RecyclerView recySeach ;
ImageButton btnSeachText, btnSeachCheckList;
ArapterItemText arapterItemText1 ;
TextView txtColorRed , txtColorYelow , txtColorOrange, txtColorPink ;
ArrayList<Text> arrayList  = new ArrayList<>();
    ArrayList<Text> arrayListText ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        anhxa();

        Intent intent = getIntent();
         arrayList = (ArrayList<Text>) intent.getSerializableExtra("arrayText");
          arrayListText = new ArrayList<>();
         arrayListText.addAll(arrayList);
        arapterItemText1 = new ArapterItemText(Seach.this,arrayList);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recySeach.setAdapter(arapterItemText1);
        recySeach.setLayoutManager(linearLayout);
        btnSeachText.setOnClickListener(this);
        btnSeachCheckList.setOnClickListener(this);
        txtColorYelow.setOnClickListener(this);
        txtColorOrange.setOnClickListener(this);
        txtColorRed.setOnClickListener(this);
        txtColorPink.setOnClickListener(this);

    }

    private void anhxa() {
        recySeach = findViewById(R.id.recySeach);
        btnSeachCheckList = findViewById(R.id.btnseachCheckList);
        btnSeachText = findViewById(R.id.btnseachText);
        txtColorRed = findViewById(R.id.txtColorRed);
        txtColorOrange = findViewById(R.id.txtColorOrange);
        txtColorPink = findViewById(R.id.txtColorPink);
        txtColorYelow = findViewById(R.id.txtColorYelow);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arapterItemText1.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               arapterItemText1.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(Seach.this,MainActivity.class);
                startActivity(intent);
                break;


        }


        return super.onOptionsItemSelected(item);

    }
  // hàm trả về 1 danh sách text
    private ArrayList seachText() {

        ArrayList<Text> arrayText = new ArrayList<>();
       for (int i =0 ; i < arrayListText.size();i++){
           if (arrayListText.get(i).getIsCheck()==1){
               arrayText.add(arrayListText.get(i));
           }
       }

      return  arrayText ;

    }
    private ArrayList seachCheckList() {
        ArrayList<Text> arrayListCheckList = new ArrayList<>();
        for (int i =0 ; i<arrayListText.size();i++){
            if (arrayListText.get(i).getIsCheck()==0){
                arrayListCheckList.add(arrayListText.get(i));
            }
        }
        return  arrayListCheckList ;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnseachText:
                //tìm kiếm riêng text note
                arrayList.clear();
                arrayList.addAll(seachText());
                arapterItemText1.notifyDataSetChanged();
                break;
            case R.id.btnseachCheckList:
                arrayList.clear();
                arrayList.addAll(seachCheckList());
                arapterItemText1.notifyDataSetChanged();
                break;
            case R.id.txtColorRed:
                arrayList.clear();
                arrayList.addAll(seachColor("#FF0000"));
                arapterItemText1.notifyDataSetChanged();
                break;
            case R.id.txtColorOrange:
                arrayList.clear();
                arrayList.addAll(seachColor("#FF6600"));
                arapterItemText1.notifyDataSetChanged();
                break;
            case R.id.txtColorYelow:
                arrayList.clear();
                arrayList.addAll(seachColor("#FFCC00"));
                arapterItemText1.notifyDataSetChanged();
                break;
            case R.id.txtColorPink:
                arrayList.clear();
                arrayList.addAll(seachColor("#FF33CC"));
                arapterItemText1.notifyDataSetChanged();

                break;

        }
    }

    private ArrayList seachColor(String colorText) {
        ArrayList<Text> arrayText = new ArrayList<>();
        for (int i = 0 ; i<arrayListText.size();i++){
            if (arrayListText.get(i).getColorText().equals(colorText)){
                arrayText.add(arrayListText.get(i));
            }
        }
        return arrayText ;
    }
}