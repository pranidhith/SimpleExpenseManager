package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHandler.DatabaseHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by prani on 12/5/2015.
 */
public class PersistantTransactionDAO implements TransactionDAO {
    SQLiteDatabase db =null;
    DatabaseHandler databaseHandler=null;

    public PersistantTransactionDAO(Context context){
        if(databaseHandler==null){
            databaseHandler=new DatabaseHandler(context);
        }
        db = databaseHandler.getWritableDatabase();
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        ContentValues values = new ContentValues();

        values.put(databaseHandler.date, String.valueOf(date));
        values.put(databaseHandler.account_No,accountNo);
        values.put(databaseHandler.expense_Type, String.valueOf(expenseType));
        values.put(databaseHandler.amount, amount);

        // Inserting Row
        db.insert(databaseHandler.trans_Table, null, values);
        db.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {

        List<Transaction> transList = new ArrayList<Transaction>();
        String query = "SELECT * FROM " + databaseHandler.account_Table;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){

            String accountNo = cursor.getString(cursor.getColumnIndex(databaseHandler.account_No));
            String exType = cursor.getString(cursor.getColumnIndex(databaseHandler.expense_Type));
            double amount = cursor.getDouble(cursor.getColumnIndex(databaseHandler.amount));
            String s_date = cursor.getString(cursor.getColumnIndex(databaseHandler.date));

            Date date = new Date(s_date); //<--------< Update this
            ExpenseType expenseType = null;
            if (exType == "EXPENSE"){expenseType = ExpenseType.EXPENSE;}
            else {expenseType = ExpenseType.INCOME;}

            Transaction transaction =  new Transaction(date,accountNo,expenseType,amount);
            transList.add(transaction);
        }
        return transList;

    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        List<Transaction> transList = new ArrayList<Transaction>();
        String query = "SELECT * FROM " + databaseHandler.account_Table;
        Cursor cursor = db.rawQuery(query, null);
        int i = 0;

        while( i < limit && cursor.moveToNext()){

            String accountNo = cursor.getString(cursor.getColumnIndex(databaseHandler.account_No));
            String exType = cursor.getString(cursor.getColumnIndex(databaseHandler.expense_Type));
            double amount = cursor.getDouble(cursor.getColumnIndex(databaseHandler.amount));
            String s_date = cursor.getString(cursor.getColumnIndex(databaseHandler.date));

            Date date = new Date(s_date); //<--------< Update this
            ExpenseType expenseType = null;
            if (exType == "EXPENSE"){expenseType = ExpenseType.EXPENSE;}
            else {expenseType = ExpenseType.INCOME;}

            Transaction transaction =  new Transaction(date,accountNo,expenseType,amount);
            transList.add(transaction);
            i++;
        }
        return transList;
    }
}
