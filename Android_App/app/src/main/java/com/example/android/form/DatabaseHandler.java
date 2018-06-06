package com.example.android.form;

/**
 * Created by TUSHAR on 22-09-2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.key;
import static android.app.DownloadManager.COLUMN_STATUS;
import static android.provider.Contacts.SettingsColumns.KEY;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "clientData";

    // Contacts table name
    private static final String TABLE_CLIENTINFO = "clientInfo";

    // Contacts Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_AGE = "age";
    public static final String KEY_QUALIFICATION = "qualification";
    public static final String KEY_STATUS = "status";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CLIENTINFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_FIRST_NAME + " TEXT, " + KEY_LAST_NAME + " TEXT, " + KEY_AGE + " TEXT, "
                + KEY_QUALIFICATION + " TEXT, " + KEY_STATUS + " INTEGER);";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTINFO);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addClient(ClientInfo client_info) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, client_info.getFirstName()); // Contact Name
        values.put(KEY_LAST_NAME, client_info.getLastName()); // Contact Phone
        values.put(KEY_AGE, client_info.getAge()); // Contact Phone
        values.put(KEY_QUALIFICATION, client_info.getQualification()); // Contact Phone
        values.put(KEY_STATUS, client_info.getStatus());
        // Inserting Row
        db.insert(TABLE_CLIENTINFO, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    ClientInfo getClient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLIENTINFO, new String[] { KEY_ID,
                        KEY_FIRST_NAME, KEY_LAST_NAME, KEY_AGE, KEY_QUALIFICATION, KEY_STATUS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ClientInfo client_info = new ClientInfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)));
        // return contact
        return client_info;
    }

    // Getting All Contacts
    public List<ClientInfo> getAllContacts() {
        List<ClientInfo> contactList = new ArrayList<ClientInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTINFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ClientInfo client_info = new ClientInfo();
                client_info.setID(Integer.parseInt(cursor.getString(0)));
                client_info.setFirstName(cursor.getString(1));
                client_info.setLastName(cursor.getString(2));
                client_info.setAge(cursor.getString(3));
                client_info.setQualification(cursor.getString(4));
                client_info.setStatus(Integer.parseInt(cursor.getString(5)));

                // Adding contact to list
                contactList.add(client_info);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(ClientInfo client_info) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, client_info.getFirstName());
        values.put(KEY_LAST_NAME, client_info.getLastName());
        values.put(KEY_AGE, client_info.getAge());
        values.put(KEY_QUALIFICATION, client_info.getQualification());
        values.put(KEY_STATUS, client_info.getStatus());


        // updating row
        return db.update(TABLE_CLIENTINFO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(client_info.getID()) });
    }

    // Deleting single contact
    public void deleteContact(ClientInfo client_info) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTINFO, KEY_ID + " = ?",
                new String[] { String.valueOf(client_info.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CLIENTINFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CLIENTINFO + " WHERE " + KEY_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STATUS, status);
        db.update(TABLE_CLIENTINFO, contentValues, KEY_ID + "=" + id, null);
        db.close();
        return true;
    }
}
