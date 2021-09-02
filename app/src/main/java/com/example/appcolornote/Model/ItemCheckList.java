package com.example.appcolornote.Model;

import java.io.Serializable;

public class ItemCheckList implements Serializable {
    private int Id ;
    private String Content ;
    private boolean isCheck ;

    public ItemCheckList(String content, boolean isCheck) {
        Content = content;
        this.isCheck = isCheck;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
