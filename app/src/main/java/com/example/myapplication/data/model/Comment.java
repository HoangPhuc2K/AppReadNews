package com.example.myapplication.data.model;

public class Comment {
    int IdCom;
    int IdNews_FK;
    String Name;
    String Comment;

    public int getIdCom() {
        return IdCom;
    }

    public void setIdCom(int idCom) {
        IdCom = idCom;
    }

    public int getIdNews_FK() {
        return IdNews_FK;
    }

    public void setIdNews_FK(int idNews_FK) {
        IdNews_FK = idNews_FK;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Comment(int idCom, int idNews_FK, String name, String comment) {
        IdCom = idCom;
        IdNews_FK = idNews_FK;
        Name = name;
        Comment = comment;
    }
}
