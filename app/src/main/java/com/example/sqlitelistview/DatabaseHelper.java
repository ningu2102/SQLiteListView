package com.example.sqlitelistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    public static final String TABLE_NAME = "Contacts";
    public static final String COL1 = "id";
    public static final String COL2 = "name";
    public static final String COL3 = "img";
    public static final String COL4 = "phone_no";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //sqLiteDatabase.delete(TABLE_NAME, null, null);
        String query = "create table " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, img TEXT, phone_no TEXT)";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, String img, String phone) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, img);
        contentValues.put(COL4, phone);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public ArrayList<Contacts> getAllData() {
        ArrayList<Contacts> contactsArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String img = cursor.getString(2);
            String phone = cursor.getString(3);
            Contacts contacts = new Contacts(name, img, phone);
            contactsArrayList.add(contacts);
        }
        return contactsArrayList;
    }
    public void deleteAllData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+TABLE_NAME);
    }

    public String getNamee(String phone)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String name = null;
        //sqLiteDatabase.execSQL("select name from "+TABLE_NAME+"where "+COL4+"="+phone);rp
        String q = "select name from Contacts where phone_no = '"+phone+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(q, null);
        while (cursor.moveToNext())
        {
           name = cursor.getString(0);
        }
        return  name;
        //return "45454";
    }
    public void deleteItem(String phone)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String res  = null;
        String q = "DELETE FROM Contacts WHERE phone_no  = '"+phone+"'";
        sqLiteDatabase.execSQL(q);
    }
}


