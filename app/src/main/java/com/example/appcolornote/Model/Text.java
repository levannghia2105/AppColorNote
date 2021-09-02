package com.example.appcolornote.Model;

import android.graphics.Color;

import java.io.Serializable;

public class Text implements Serializable {

    private int id;
   private String titleText ;
    private String contentText;
    private String colorText;
    private int isCheck ;

    public int getIdText() {
        return id;
    }

    public void setIdText(int idText) {
        this.id = idText;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public Text(int idText, String titleText, String contentText, String colorText) {
        this.id = idText;
        this.titleText = titleText;
        this.contentText = contentText;
        this.colorText = colorText;
    }


    public Text(String titleText, String contentText, String colorText) {
        this.titleText = titleText;
        this.contentText = contentText;
        this.colorText = colorText;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        if (titleText.isEmpty()==true &&titleText.length()>20){
            return;
        }else
            this.titleText=titleText;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public Text(int idText, String titleText, String contentText, String colorText, int isCheck) {
        this.id = idText;
        this.titleText = titleText;
        this.contentText = contentText;
        this.colorText = colorText;
        this.isCheck = isCheck;
    }
}
