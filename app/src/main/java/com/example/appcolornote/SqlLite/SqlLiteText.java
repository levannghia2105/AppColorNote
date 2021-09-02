package com.example.appcolornote.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.appcolornote.Model.CheckList;
import com.example.appcolornote.Model.ItemCheckList;
import com.example.appcolornote.Model.Text;

import java.security.Key;
import java.util.ArrayList;

public class SqlLiteText  extends SQLiteOpenHelper {
    private  static final String DATABASE_NAME = "textManager";
    private static final int DATABASE_VERSION = 1 ;
    private static final String TABLE_NAME = "tblText";
    private static final String TABLE_NAME_ITEM_CHECKLIST = "tblItemCheckList";
    private static String KEY_ID = "id";
    private static final String KEY_ID_ITEM_CHECKLIST = "idItemChecKlist";
    private static final String KEY_IS_CHECKLIST_OR_TEXT = "isCheckOrText";
    private static final String KEY_TITLE_TEXT = "title";
    private static final String KEY_CONTENT_TEXT = "content";
    private static final String KEY_CONTENT_CHECK_LIST = "contentCheckList";
    private static final String KEY_COLOR_TEXT = "color";
    private static final String KEY_IS_CHECK_LIST = "isCheckList";
    public SqlLiteText(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE "+TABLE_NAME+"( " +
                        KEY_ID +"   INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_TITLE_TEXT +" TEXT  ," +
                        KEY_CONTENT_TEXT +" TEXT  ," +
                        KEY_COLOR_TEXT +" TEXT ," +
                        KEY_IS_CHECKLIST_OR_TEXT + " NUMBERRIC );";

        String create = "CREATE TABLE "+TABLE_NAME_ITEM_CHECKLIST+"(" +
                KEY_ID_ITEM_CHECKLIST + "INTERGER ,"+
                KEY_CONTENT_CHECK_LIST+ "TEXT ,"+
                KEY_IS_CHECK_LIST+" NUMERIC );" ;
                db.execSQL(createTable);
                db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = String.format("DROP TABLE IF EXISTS %s",TABLE_NAME);
        db.execSQL(dropTable);
         onCreate(db);
    }

    //add text
    public void addText(Text text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE_TEXT, text.getTitleText());
        values.put(KEY_CONTENT_TEXT, text.getContentText());
        values.put(KEY_COLOR_TEXT, text.getColorText());
        values.put(KEY_IS_CHECKLIST_OR_TEXT,1);
        db.insert(TABLE_NAME,null,values);

        db.close();

    }
    // thêm vào checklist
    public void addCheckList(CheckList checkList, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE_TEXT,checkList.getCheckListTitle());
        values.put(KEY_CONTENT_TEXT, content);
        values.put(KEY_COLOR_TEXT, checkList.getColorCheckList());
        values.put(KEY_IS_CHECKLIST_OR_TEXT,0);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    // additemChecklist


    // lấy ra id của màn hình activity

    public Text getText(int TextId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[] { String.valueOf(TextId) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Text text = new Text(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return text;
    }
    // lấy ra toàn bộ text
    public ArrayList<Text> getAllText() {
        ArrayList<Text>  textList = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NAME ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Text text = new Text(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4));
            textList.add(text);
            cursor.moveToNext();
        }
        return textList;
    }
    public void updateText( Text text , int keyId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE_TEXT, text.getTitleText());
        contentValues.put(KEY_CONTENT_TEXT,text.getContentText());
        contentValues.put(KEY_COLOR_TEXT , text.getColorText());
        db.update(TABLE_NAME,contentValues,KEY_ID +  " = ?" ,new String[]{String.valueOf(keyId+1)});
        db.close();


    }
    public void updateCheckList(CheckList checkList, int keyId, String content ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE_TEXT,checkList.getCheckListTitle());
        contentValues.put(KEY_CONTENT_TEXT,content);
        contentValues.put(KEY_COLOR_TEXT,checkList.getColorCheckList());
        db.update(TABLE_NAME,contentValues,KEY_ID + " =? ", new String[]{String.valueOf(keyId+1)});
    }
}
