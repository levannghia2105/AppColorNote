package com.example.appcolornote.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckList implements Serializable {
    private int idCheckList ;
    private String checkListTitle;
    private ArrayList<ItemCheckList> arrayContent;
    private String colorCheckList;

    public CheckList(String checkListTitle, ArrayList<ItemCheckList> arrayContent, String colorCheckList) {
        this.checkListTitle = checkListTitle;
        this.arrayContent = arrayContent;
        this.colorCheckList = colorCheckList;
    }

    public CheckList(ArrayList<ItemCheckList> arrayContent) {
        this.arrayContent = arrayContent;
    }

    public String getCheckListTitle() {
        return checkListTitle;
    }

    public void setCheckListTitle(String checkListTitle) {
        this.checkListTitle = checkListTitle;
    }

    public ArrayList<ItemCheckList> getArrayContent() {
        return arrayContent;
    }

    public void setArrayContent(ArrayList<ItemCheckList> arrayContent) {
        this.arrayContent = arrayContent;
    }

    public String getColorCheckList() {
        return colorCheckList;
    }

    public void setColorCheckList(String colorCheckList) {
        this.colorCheckList = colorCheckList;
    }

    public int getIdCheckList() {
        return idCheckList;
    }

    public void setIdCheckList(int idCheckList) {
        this.idCheckList = idCheckList;
    }
}
