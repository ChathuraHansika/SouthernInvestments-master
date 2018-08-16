package com.android.mlpj.southerninvestments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLLiteHelper extends SQLiteOpenHelper{
    private static String dbName = "LocalDb";
    private Context context;
    public SQLLiteHelper(Context context) {
        super(context, dbName, null, 1);
        this.context = context;
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Customer (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "customer_no INTTEGER,\n" +
                "name VARCHAR(60) NOT NULL,\n" +
                "email VARCHAR(60),\n" +
                "NIC VARCHAR(50) NOT NULL,\n" +
                "contact_no VARCHAR(60),\n" +
                "status VARCHAR(60),\n" +
                "addLine1 VARCHAR(60),\n" +
                "addLine2 VARCHAR(60),\n" +
                "addLine3 VARCHAR(60),\n" +
                "city VARCHAR(60),\n" +
                "salesRepId INTTEGER,\n" +
                "created_at TIMESTAMP,\n" +
                "updated_at TIMESTAMP\n" +
                ")");

        Toast.makeText(context, "onCreate", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "onUpgrade", Toast.LENGTH_LONG).show();

    }
}
