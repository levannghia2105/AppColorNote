package com.example.appcolornote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcolornote.Model.Text;

public class AddText extends AppCompatActivity implements View.OnClickListener {
 LinearLayout linearLayout ;
 EditText edtTitle , edtContent;
 ImageButton btnSave ;
 String colorOption ;
 Text mText ;
 ImageButton btnUndo , btnRedo ;
 TextViewUndoRedo textViewUndoRedo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);
        anhxa();
        btnSave.setOnClickListener(this);
        btnUndo.setOnClickListener(this);
        btnRedo.setOnClickListener(this);

    }


    private void anhxa() {
        linearLayout = findViewById(R.id.linnerAddText);
        edtContent = findViewById(R.id.edtContent);
        edtTitle = findViewById(R.id.edtTitle);
        btnSave = findViewById(R.id.btnSaveText);
        btnRedo = findViewById(R.id.btnRedo);
        btnUndo = findViewById(R.id.btnUndo);
        textViewUndoRedo = new TextViewUndoRedo(edtContent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu_add_text,menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);

    }
  // xử lí sự kiện cho actionbar màn hình addtext
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                Intent itent = new Intent(AddText.this,MainActivity.class);
                startActivity(itent);
                break;
            case R.id.actionSelectColor:
                showDialog();
                break;
            case R.id.actionDeleteText:
                showDialogDelete("Confirm","Are You Sure You Want To Delete This Note","OKE","Cancel") ;
        }
        return super.onOptionsItemSelected(item);
    }
    // show ra diolog để xóa
    private void showDialogDelete(String title ,String message ,String setPositive, String cancel) {
        AlertDialog.Builder  alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        // xử lí khi bấm nút oke
        alert.setPositiveButton(setPositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // tắt dialog xử lý xóa ghi chú
            }
        });
        //xử lí sự kiện khi tắt dialog
        alert.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    // xử lí khi bấm vào nút chage color
    private void showDialog() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnColorRed:
                linearLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                colorOption = "#FF0000";

                break;
            case R.id.btnColorOrange:
                linearLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                colorOption ="#FF6600";

                break;
            case R.id.btnColorYellow:
                linearLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                colorOption = "#FFCC00";

                break;
            case R.id.btnColorPink:
                linearLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                colorOption = "#FF33CC";
                break;
            case R.id.btnSaveText:
                saveText();
                break;
            case R.id.btnUndo:

                textViewUndoRedo.undo();
                break;
            case R.id.btnRedo:
                textViewUndoRedo.redo();
                break;
        }

    }
   // xử lí sự kiện khi bấm nút save text
    private void saveText() {
        String textTitle = edtTitle.getText().toString().trim();
        String textContent = edtContent.getText().toString().trim();
        if (textTitle.isEmpty()==true || textTitle.length()>20){
            Toast.makeText(this,"Lưu thất bại !! Tittle không được để trống và phải có độ dài dưới 20 kí tự",Toast.LENGTH_LONG).show();
            return;
        }
        //hiện dialog cho người dùng xem khi đã lưu được
        AlertDialog.Builder  alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirm");
        alert.setMessage("Bạn xác nhận sẽ lưu ??");
        // xử lí khi bấm nút oke
        alert.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (colorOption==null){
                    colorOption= "#EEEEEE";
                }
                mText = new Text(textTitle,textContent,colorOption);
                sentoMain(MainActivity.RESULT_CODE_SAVE1);

            }
        });
        //xử lí sự kiện khi tắt dialog
        alert.setNegativeButton("Quay Lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void sentoMain(int resultcode) {
        Intent intent = getIntent();
        intent.putExtra("objText",mText);
        setResult(resultcode,intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String contentText = intent.getStringExtra("contentText");
        String titleText = intent.getStringExtra("titleText");
        String colorText = intent.getStringExtra("colorText");

        if (contentText==null||titleText==null){
            return;
        }
        linearLayout.setBackgroundColor(Color.parseColor(colorText));
            edtContent.setText(contentText+"");
        edtTitle.setText(titleText+"");

    }


    // xử lí kết quả trả về



}