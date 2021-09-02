package com.example.appcolornote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcolornote.Arapter.ArapterItemCheckList;
import com.example.appcolornote.InterFace.ClickIterFaceItemCheckList;
import com.example.appcolornote.Model.CheckList;
import com.example.appcolornote.Model.ItemCheckList;
import com.example.appcolornote.SqlLite.SqlLiteText;

import java.io.Serializable;
import java.util.ArrayList;

public class AddCheckList extends AppCompatActivity implements View.OnClickListener {
TextView btnAddCheckList ;
RecyclerView recyCheckList ;
LinearLayout linerAddChecKList ;
CheckList mCheckList ;
EditText edtAddCheckList;
ArrayList<CheckList> checkListArrayList ;
ArrayList<ItemCheckList> itemCheckLists = new ArrayList<>();
ArapterItemCheckList arapterItemCheckList ;
AlertDialog alertDialog;
EditText edtTitleCheckList ;
String colorOptionCheckList ;
    String colorCheckList ;
ClickIterFaceItemCheckList clickIterFaceItemCheckList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_list);
        anhxa();
        arapterItemCheckList.setClickIterFaceItemCheckList(new ClickIterFaceItemCheckList() {
            @Override
            public void ClickItemCheckList(int pos, ItemCheckList itemCheckList) {
                itemCheckLists.remove(pos);
                arapterItemCheckList.notifyDataSetChanged();
            }
        });


    }

    private void anhxa() {
        edtTitleCheckList = findViewById(R.id.edtTitleCheckList);
        btnAddCheckList = findViewById(R.id.btnAddCheckList);
        recyCheckList = findViewById(R.id.recyCheckList);
        linerAddChecKList = findViewById(R.id.linerAddCheckList);
        btnAddCheckList.setOnClickListener(this);
        arapterItemCheckList = new ArapterItemCheckList(this,itemCheckLists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyCheckList.setAdapter(arapterItemCheckList);
        recyCheckList.setLayoutManager(linearLayoutManager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu_add_text,menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(AddCheckList.this,"SAVE",Toast.LENGTH_LONG).show();
                  String titleCheckList = edtTitleCheckList.getText().toString();

                 if (colorOptionCheckList==null&&colorCheckList!=null) {
                     colorOptionCheckList = colorCheckList;
                 }
                else if (colorOptionCheckList==null) {
                    colorOptionCheckList = "#EEEEEE";
                }

                 mCheckList = new CheckList(titleCheckList,itemCheckLists,colorOptionCheckList);

                sentoMain(MainActivity.RESULT_CODE_SAVE2);

                break;
            case R.id.actionSelectColor:
                AlertDialog.Builder  builder = new AlertDialog.Builder(this);
                View alert = LayoutInflater.from(this).inflate(R.layout.custom_dialog_change_color,null);
                builder.setView(alert);
                TextView txtRed , txtYellow ,txtOrange , txtPink ;
                // tham chiếu
                txtRed = alert.findViewById(R.id.btnColorRed);
                txtOrange = alert.findViewById(R.id.btnColorOrange);
                txtYellow = alert.findViewById(R.id.btnColorYellow);
                txtPink = alert.findViewById(R.id.btnColorPink);

                txtRed.setOnClickListener(this);
                txtYellow.setOnClickListener(this);
                txtPink.setOnClickListener(this);
                txtOrange.setOnClickListener(this);
                builder.create().show();

        }
        return super.onOptionsItemSelected(item);


    }
 // gửi dữ liệu về màn hình chính
 private void sentoMain(int resultcode) {
     Intent intent = getIntent();
     intent.putExtra("objCheckList",  mCheckList);
     setResult(resultcode,intent);
     finish();
 }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnColorRed:
                linerAddChecKList.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                colorOptionCheckList = "#FF0000";
                break;
            case R.id.btnColorOrange:
                linerAddChecKList.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                colorOptionCheckList="#FF6600";
                break;
            case R.id.btnColorYellow:
                linerAddChecKList.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                colorOptionCheckList="#FFCC00";
                break;
            case R.id.btnColorPink:
                linerAddChecKList.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                colorOptionCheckList = "#FF33CC";
                break;

            case R.id.btnAddCheckList:
                addCheckList();
                break;

            case R.id.btnOke:
                setDataRecy();
                break;
        }
    }
   // khi bấm vào nút oke sẽ tiến hành set dữ liệu lên recyView ;
    private void setDataRecy() {
       String addCheckList = edtAddCheckList.getText().toString().trim();
       boolean checkList = true ;
       if (addCheckList.isEmpty()==true ||addCheckList.length()>20){
           Toast.makeText(getApplicationContext(),"Bạn không được để trống và không được nhập quá 30 kí tự !!",Toast.LENGTH_LONG).show();
           alertDialog.dismiss();
           return;
       }else
           itemCheckLists.add(new ItemCheckList(addCheckList,checkList));
           arapterItemCheckList.notifyDataSetChanged();
           alertDialog.dismiss();

    }

    private void addCheckList() {
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        View alert = LayoutInflater.from(this).inflate(R.layout.custom_dialog_add_check_list,null);
        builder.setView(alert);
         edtAddCheckList = alert.findViewById(R.id.edtAddItem);
        Button btnCancel = alert.findViewById(R.id.btnCancel);
        Button btnOke = alert.findViewById(R.id.btnOke);
        btnOke.setOnClickListener(this);
        alertDialog = builder.create();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String contentCheckList = intent.getStringExtra("contentCheckList");
        String titleCheckList = intent.getStringExtra("titleCheckList");
         colorCheckList = intent.getStringExtra("colorCheckList");

        if (contentCheckList==null||titleCheckList==null){
            return;
        }
        edtTitleCheckList.setText(titleCheckList+"");
        String [] listCheckList = contentCheckList.split("\\|");
        ArrayList<ItemCheckList> arrcheckList = new ArrayList<>();
        for (int i = 0 ; i<listCheckList.length;i++){
            arrcheckList.add(new ItemCheckList(listCheckList[i],true));

        }
        linerAddChecKList.setBackgroundColor(Color.parseColor(colorCheckList));
        itemCheckLists.clear();
        itemCheckLists.addAll(arrcheckList);

        arapterItemCheckList.notifyDataSetChanged();

    }




}