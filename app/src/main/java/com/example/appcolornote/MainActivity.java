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
        //set n??t b???m cho n??t add
        btnAddNote.setOnClickListener(this);
    }

    private void setItemText() {
        if (arrayListText!=null){
            txtHelp.setVisibility(View.GONE);

        }

    }


    // set giao di???n cho menuicon
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

        // th???c hi???n chuy???n m??n h??nh sang CheckList ????? ch???nh s???a n???i dung
    private void ChangeSceenCheckList(Text mText) {
        Intent intent = new Intent(MainActivity.this,AddCheckList.class);
        intent.putExtra("contentCheckList",mText.getContentText());
        intent.putExtra("titleCheckList",mText.getTitleText());
        intent.putExtra("colorCheckList",mText.getColorText());
        startActivityForResult(intent,REQUEST_CODE_INPUT);
    }

   // th???c hi???n chuy???n m??n h??nh sang Text ????? ng?????i d??ng ch???nh s???a n???i dung
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
    // set giao di???n cho actionbar
    private void actionbar() {
        ActionBar actionBar  = getSupportActionBar();
        actionBar.setTitle("APP COLOR NOTE");
//        Drawable drawable = getResources().getDrawable(R.drawable.hambuger);
//        actionBar.setHomeAsUpIndicator(drawable);
        actionBar.setHomeAsUpIndicator(R.drawable.hambuger);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }
    //goi ph????ng th???c set s??? ki???n cho actionbar

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
           case android.R.id.home :
               drawerLayout.openDrawer(GravityCompat.START);
               break ;
            case R.id.menuSeach:
                Intent intent = new Intent(MainActivity.this ,Seach.class);
                intent.putExtra("arrayText",arrayListText);
                // n???u ch??a c?? d??? li???u trong sql th?? kh??ng chuy???n m??n h??nh
                if (arrayListText == null){
                    Toast.makeText(MainActivity.this,"b???n ch??a c?? d??? li???u n??n ch??a th??? t??m ki???m ???????c ",Toast.LENGTH_SHORT).show();
                    break;
            }
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);

    }

// set s??? ki???n cho t???ng n??t b???m trong mainactivity
    @Override
    public void onClick( View v) {
        switch (v.getId()){
            case R.id.btnAddNote:
                setAddNote();
                break;

        }

    }
  // set s??? ki???n cho n??t addnote
    private void setAddNote() {

        PopupMenu popupMenu = new PopupMenu(this,btnAddNote);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_add_note,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();

    }

    //set s??? ki???n cho t???ng item khi add note
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
    // x??? l?? s??? ki???n khi nh???n d??? li???u tr??? v???

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_INPUT&&data!=null){
            switch (resultCode){
                // d??? li???u tr??? v??? t??? m??n h??nh add text
                case RESULT_CODE_SAVE1:
                    text = (Text) data.getSerializableExtra("objText");
                     if (state==1){
                         sqlLiteText.addText(text);
                         //hi???n th??? d??? li???u l??n RecyView
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
      // t???o ra 1 chu???i tr??? v??? v???i v???i d???u | ????? ng??n c??ch ch??ng v???i nhau
    private String taoChuoi(CheckList checkList) {
        StringBuilder stringBuilder = null ;
        stringBuilder = new StringBuilder();
        for (int i =0 ; i<checkList.getArrayContent().size();i++){
            stringBuilder.append(checkList.getArrayContent().get(i).getContent()+"|");
        }
        String result = stringBuilder.toString();
        return  result ;

    }

    // x??? l?? hi???n d??? li???u l??n RecyView
    private void displayText() {
        arrayListText.clear();
        arrayListText.addAll(sqlLiteText.getAllText());
        arapterItemText.notifyDataSetChanged();
    }
}