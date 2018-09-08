package com.android.mlpj.southerninvestments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
                "loan_amount FLOAT NOT NULL,\n" +
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


    public long insertLoans (LoanDetails loan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", loan.getId());
        contentValues.put("loan_no", loan.getLoanNo());
        contentValues.put("interest_rate", loan.getInterestRate());
        contentValues.put("loan_amount",loan.getLoan_amount());
        contentValues.put("installment_amount", loan.getInstallmentAmount());
        contentValues.put("no_of_installments", loan.getNoOfInstallments());
        contentValues.put("start_date", loan.getStartDate());
        contentValues.put("end_date", loan.getEndDate());
        contentValues.put("duration", loan.getDuration());
        contentValues.put("customer_id", loan.getCustomerId());
        //contentValues.put("created_at", loan.getCreatedAt());
        //contentValues.put("updated_at", loan.getUpdatedAt());

        return db.insert("CustomerLoan", null, contentValues);
    }

    public long insertRepayments (Repayment repayment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", repayment.getId());
        contentValues.put("loan_id", repayment.getLoanId());
        contentValues.put("bank_book_id", repayment.getBankBookId());
        contentValues.put("cash_book_id", repayment.getCashBookId());
        contentValues.put("amount", repayment.getAmount());
        contentValues.put("installment_count", repayment.getInstallmentCount());
        contentValues.put("remaining_amount", repayment.getRemainingAmount());
        contentValues.put("created_at", repayment.getCreatedAt());
        contentValues.put("updated_at", repayment.getUpdatedAt());

        return db.insert("LoanRepayment", null, contentValues);
    }

    public List<CustomerDetails> getAllCustomers(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Customer", null );
        res.moveToFirst();

        List<CustomerDetails> customer_list = new ArrayList<CustomerDetails>();

        while(res.isAfterLast() == false){
            CustomerDetails newCustomer = new CustomerDetails(res.getString(2),res.getString(1),res.getString(6),res.getString(5));
            customer_list.add(newCustomer);
            res.moveToNext();
        }
        return customer_list;
    }

    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete("Customer", null, null);
        db.delete("CustomerLoan", null, null);
        db.delete("LoanRepayment", null, null);
    }

    public Cursor getCustomerByNo(int customerNo){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Customer where customer_no = " + customerNo, null );
        res.moveToFirst();
        return res;
    }

    public Cursor getRepaymentByCustomerId(int customerId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select remaining_amount, installment_count, CustomerLoan.id, no_of_installments from Customer, CustomerLoan, LoanRepayment where Customer.id = "
                + customerId + " And Customer.id = Customerloan.customer_id AND LoanRepayment.loan_id = CustomerLoan.id", null );
        res.moveToFirst();
        return res;
    }



    public List<DueLoansDetails> getDueLoans(){
        SQLiteDatabase d_Loans = this.getReadableDatabase();
        Cursor get_D_Loans = d_Loans.rawQuery("select name,NIC,loan_amount,no_of_installments,CustomerLoan.id" +
                " from Customer,CustomerLoan where Customer.id = CustomerLoan.customer_id and status = 'ongoing' ",null);

        get_D_Loans.moveToFirst();


        List<DueLoansDetails> dueLoansDetailsList = new ArrayList<>();

                while (get_D_Loans.isAfterLast()==false){
                    Cursor get_Repayments = d_Loans.rawQuery("select installment_count,remaining_amount " +
                            " from LoanRepayment where LoanRepayment.loan_id = "+ get_D_Loans.getInt(4),null);
                            get_Repayments.moveToLast();

                    DueLoansDetails newDueLoans = new DueLoansDetails(get_D_Loans.getString(0),get_D_Loans.getString(1),get_Repayments.getString(1),get_D_Loans.getString(2),get_Repayments.getString(0),get_D_Loans.getString(3));
                    dueLoansDetailsList.add(newDueLoans);
                    get_D_Loans.moveToNext();
                }
                return dueLoansDetailsList;
    }
//    public List<DailyCollectionDetails> getDailyCollection(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("",null);
//    }
}
