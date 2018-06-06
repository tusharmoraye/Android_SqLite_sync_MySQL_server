package com.example.android.form;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;

/**
 * Created by TUSHAR on 22-09-2017.
 */

public class ClientInfo {

    //private variables
    int _id;
    String _first_name;
    String _last_name;
    String _age;
    String _qualification;
    int _status;

    // Empty constructor
    public ClientInfo(){

    }
    // constructor
    public ClientInfo(int id, String first_name, String last_name, String age, String qualification, int status){
        this._id = id;
        this._first_name = first_name;
        this._last_name = last_name;
        this._age = age;
        this._qualification = qualification;
        this._status = status;
    }

    // constructor
    public ClientInfo(String first_name, String last_name, String age, String qualification, int status){
        this._first_name = first_name;
        this._last_name = last_name;
        this._age = age;
        this._qualification = qualification;
        this._status = status;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting first name
    public String getFirstName(){
        return this._first_name;
    }

    // setting first name
    public void setFirstName(String name){
        this._first_name = name;
    }

    // getting last name
    public String getLastName(){
        return this._last_name;
    }

    // setting last name
    public void setLastName(String last_name){
        this._last_name = last_name;
    }

    //getting age
    public String getAge(){
        return this._age;
    }

    // setting age
    public void setAge(String age){
        this._age = age;
    }

    //getting qualification
    public String getQualification(){
        return this._qualification;
    }

    // setting qualification
    public void setQualification(String qualification){
        this._qualification = qualification;
    }

    // getting status
    public int getStatus(){
        return this._status;
    }

    // setting id
    public void setStatus(int status){
        this._status = status;
    }
}