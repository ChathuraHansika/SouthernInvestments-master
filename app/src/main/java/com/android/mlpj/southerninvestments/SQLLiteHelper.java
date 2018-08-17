package com.android.mlpj.southerninvestments;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLLiteHelper extends SQLiteOpenHelper{
    private static String dbName = "LocalDb.db";
    private Context context;
    public SQLLiteHelper(Context context) {
        super(context, dbName, null, 1);
        this.context = context;
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Customer (\n" +
                "id INTEGER NOT NULL,\n" +
                "customer_no INTTEGER NOT NULL,\n" +
                "name VARCHAR(60) NOT NULL,\n" +
                "email VARCHAR(60),\n" +
                "NIC VARCHAR(50) NOT NULL,\n" +
                "contact_no VARCHAR(60),\n" +
                "status VARCHAR(60),\n" +
                "addLine1 VARCHAR(60),\n" +
                "addLine2 VARCHAR(60),\n" +
                "city VARCHAR(60),\n" +
                "salesRep_id INTTEGER,\n" +
                "created_at TIMESTAMP,\n" +
                "updated_at TIMESTAMP\n" +
                ")");

        db.execSQL("CREATE TABLE CustomerLoan (\n" +
                "id INTEGER NOT NULL,\n" +
                "loan_no INTTEGER NOT NULL,\n" +
                "interest_rate FLOAT NOT NULL,\n" +
                "installment_amount FLOAT NOT NULL,\n" +
                "no_of_installments INTEGER NOT NULL,\n" +
                "start_date TIMESTAMP,\n" +
                "end_date TIMESTAMP,\n" +
                "duration INTEGER,\n" +
                "customer_id INTEGER NOT NULL,\n" +
                "created_at TIMESTAMP,\n" +
                "updated_at TIMESTAMP\n" +
                ")");

        db.execSQL("CREATE TABLE LoanRepayment (\n" +
                "id INTEGER NOT NULL,\n" +
                "loan_id INTTEGER NOT NULL,\n" +
                "bank_book_id INTEGER,\n" +
                "cash_book_id INTEGER,\n" +
                "amount FLOAT,\n" +
                "installment_count INTEGER,\n" +
                "remaining_amount FLOAT,\n" +
                "created_at TIMESTAMP,\n" +
                "updated_at TIMESTAMP\n" +
                ")");

        Toast.makeText(context, "onCreate", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
        Toast.makeText(context, "onUpgrade", Toast.LENGTH_LONG).show();

    }

    public long insertCustomer (Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", customer.getId());
        contentValues.put("customer_no", customer.getCustomer_no());
        contentValues.put("name", customer.getName());
        contentValues.put("email", customer.getEmail());
        contentValues.put("NIC", customer.getNIC());
        contentValues.put("contact_no", customer.getContact_no());
        contentValues.put("status", customer.getStatus());
        contentValues.put("addLine1", customer.getAddLine1());
        contentValues.put("addLine2", customer.getAddLine2());
        contentValues.put("city", customer.getCity());
        contentValues.put("salesRep_id", customer.getSalesRep_id());
        //contentValues.put("created_at", customer.getCreated_at());
        //contentValues.put("updated_at", customer.getUpdated_at());

        return db.insert("Customer", null, contentValues);
    }

}
