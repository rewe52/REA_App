package com.example.vladislav.rea_attention;

/**
 * Created by Vladislav on 27.09.2017.
 */

public class UsersMessage {
     public String id;
     public String type;
     public String text;
     public String typeOfProblem;

    public UsersMessage(){
}
// не забыть добавить type of problem
 public UsersMessage(String id, String type, String text, String typeOfProblem){
    this.id = id;
    this.type = type;
    this.text = text;
     this.typeOfProblem = typeOfProblem;
}
}

