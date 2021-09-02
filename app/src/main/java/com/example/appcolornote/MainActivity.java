package com.example.appcolornote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcolornote.Arapter.ArapterItemMenu;
import com.example.appcolornote.Arapter.ArapterItemText;
import com.example.appcolornote.InterFace.ClickInterFace;
import com.example.appcolornote.Model.CheckList;
import com.example.appcolornote.Model.ItemMenu;
import com.example.appcolornote.Model.Text;
import com.example.appcolornote.SqlLite.SqlLiteText;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
DrawerLayout drawerLayout;
NavigationView navigationView ;
RecyclerView recyclerViewMenu ;
ArrayList <ItemMenu> menuArrayList  = new ArrayList<>();
ArapterItemMenu arapterItemMenu  ;
ImageButton btnAddNote ;
Text text ;
RecyclerView recyText ;
TextView txtHelp ;
String text1 ;
int posActivity ;
public static final int REQUEST_CODE_INPUT=113;
public static final int RESULT_CODE_SAVE1=115;
public static final int RESULT_CODE_SAVE2=114;

ArapterItemText arapterItemText ;
public   ArrayList<Text> arrayListText;
SqlLiteText sqlLiteText ;
public static int state = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        sqlLiteText = new SqlLiteText(this);
        arrayListText = new ArrayList<>();
        arrayListText.addAll(sqlLiteText.getAllText());
        arapterItemText = new ArapterItemText(MainActivity.this,arrayListText);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyText.setAdapter(arapterItemText);
        recyText.setLayoutManager(linearLayout);
        setItemText();
        actionbar();
        setItemMenu();
        //set nút bấm cho nút add
        btnAddNote.setOnClickListener(this);
    }

    private void setItemText() {
        if (arrayListText!=null){
            txtHelp.setVisibility(View.GONE);

        }

    }


    // set giao diện cho menuicon
    private void setItemMenu() {
        menuArrayList.add(new ItemMenu("Note",R.drawable.note));
        menuArrayList.add(new ItemMenu("Caledal",R.drawable.caledal));
        menuArrayList.add(new ItemMenu("Archiver",R.drawable.stoge));
        menuArrayList.add(new ItemMenu("Trash Can",R.drawable.trashcan));
        menuArrayList.add(new ItemMenu("Settings",R.drawable.setting));
        menuArrayList.add(new ItemMenu("Share",R.drawable.share));
        menuArrayList.add(new ItemMenu("Rate",R.drawable.rate));
        menuArrayList.add(new ItemMenu("Feedback",R.drawable.feedback));
        menuArrayList.add(new ItemMenu("Information",R.drawable.information));

        arapterItemMenu = new ArapterItemMenu( menuArrayList , MainActivity.this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerViewMenu.setAdapter(arapterItemMenu);
        recyclerViewMenu.setLayoutManager(linearLayout);
        arapterItemText.setClickInterFace(new ClickInterFace() {
            @Override
            public void OnItemClick(int pos, View v,Text mText ) {
                     if (mText.getIsCheck()==1){
                         ChangeScreenText(mText);
                         posActivity = pos ;
                         state = 0 ;
                     }
                     else if (mText.getIsCheck()==0) {
                         posActivity = pos;
                         state = 3;
                         ChangeSceenCheckList(mText);

                     }
            }




        });
    }

        // thực hiện chuyển màn hình sang CheckList để chỉnh sửa nội dung
    private void ChangeSceenCheckList(Text mText) {
        Intent intent = new Intent(MainActivity.this,AddCheckList.class);
        intent.putExtra("contentCheckList",mText.getContentText());
        intent.putExtra("titleCheckList",mText.getTitleText());
        intent.putExtra("colorCheckList",mText.getColorText());
        startActivityForResult(intent,REQUEST_CODE_INPUT);
    }

   // thực hiện chuyển màn hình sang Text để người dùng chỉnh sửa nội dung
    private void ChangeScreenText(Text mtext) {
        Intent intent = new Intent(MainActivity.this,AddText.class);
        intent.putExtra("contentText",mtext.getContentText());
        intent.putExtra("titleText",mtext.getTitleText());
        intent.putExtra("colorText",mtext.getColorText());
        startActivityForResult(intent,REQUEST_CODE_INPUT);

    }

    private void anhxa() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.naviMenu);
        recyclerViewMenu = findViewById(R.id.recyMenu);
        btnAddNote = findViewById(R.id.btnAddNote);
        recyText = findViewById(R.id.recyText);
        txtHelp = findViewById(R.id.txtHelp);


    }


    //action menu trong activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);

    }
    // set giao diện cho actionbar
    private void actionbar() {
        ActionBar actionBar  = getSupportActionBar();
        actionBar.setTitle("APP COLOR NOTE");
//        Drawable drawable = getResources().getDrawable(R.drawable.hambuger);
//        actionBar.setHomeAsUpIndicator(drawable);
        actionBar.setHomeAsUpIndicator(R.drawable.hambuger);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }
    //goi phương thức set sự kiện cho actionbar

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
           case android.R.id.home :
               drawerLayout.openDrawer(GravityCompat.START);
               break ;
            case R.id.menuSeach:
                Intent intent = new Intent(MainActivity.this ,Seach.class);
                intent.putExtra("arrayText",arrayListText);
                // nếu chưa có dữ liệu trong sql thì không chuyển màn hình
                if (arrayListText == null){
                    Toast.makeText(MainActivity.this,"bạn chưa có dữ liệu nên chưa thể tìm kiếm được ",Toast.LENGTH_SHORT).show();
                    break;
            }
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);

    }

// set sự kiện cho từng nút bấm trong mainactivity
    @Override
    public void onClick( View v) {
        switch (v.getId()){
            case R.id.btnAddNote:
                setAddNote();
                break;

        }

    }
  // set sự kiện cho nút addnote
    private void setAddNote() {

        PopupMenu popupMenu = new PopupMenu(this,btnAddNote);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_add_note,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();

    }

    //set sự kiện cho từng item khi add note
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addText:
                state = 1 ;
               Intent intent = new Intent(MainActivity.this, AddText.class);
               startActivityForResult(intent,REQUEST_CODE_INPUT);
               break;
            case R.id.addCheckList:
                state = 2 ;
                Intent intentAddCheckList = new Intent(MainActivity.this,AddCheckList.class);
                startActivityForResult(intentAddCheckList,REQUEST_CODE_INPUT);

        }
        return false;
    }
    // xử lí sự kiện khi nhận dữ liệu trả về

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_INPUT&&data!=null){
            switch (resultCode){
                // dữ liệu trả về từ màn hình add text
                case RESULT_CODE_SAVE1:
                    text = (Text) data.getSerializableExtra("objText");
                     if (state==1){
                         sqlLiteText.addText(text);
                         //hiển thị dữ liệu lên RecyView
                         displayText();}
                     if (state==0){
                         sqlLiteText.updateText(text,posActivity);
                         displayText();
                     }

                    break;
                case RESULT_CODE_SAVE2:
                    CheckList checkList = (CheckList) data.getSerializableExtra("objCheckList");
                    String conntentCheckList = taoChuoi(checkList);
                    if (state==2) {
                        sqlLiteText.addCheckList(checkList, conntentCheckList);

                        displayText();
                    }
                    if (state==3){
                        sqlLiteText.updateCheckList(checkList,posActivity,conntentCheckList);
                        displayText();
                    }
                    break;

            }

        }
    }
      // tạo ra 1 chuỗi trả về với với dấu | để ngăn cách chúng với nhau
    private String taoChuoi(CheckList checkList) {
        StringBuilder stringBuilder = null ;
        stringBuilder = new StringBuilder();
        for (int i =0 ; i<checkList.getArrayContent().size();i++){
            stringBuilder.append(checkList.getArrayContent().get(i).getContent()+"|");
        }
        String result = stringBuilder.toString();
        return  result ;

    }

    // xử lí hiện dữ liệu lên RecyView
    private void displayText() {
        arrayListText.clear();
        arrayListText.addAll(sqlLiteText.getAllText());
        arapterItemText.notifyDataSetChanged();
    }
}