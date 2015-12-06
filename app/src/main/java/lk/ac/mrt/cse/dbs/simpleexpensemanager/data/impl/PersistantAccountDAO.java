package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHandler.DatabaseHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
/**
 * Created by prani on 12/5/2015.
 */
public class PersistantAccountDAO implements AccountDAO {

    SQLiteDatabase db =null;
    DatabaseHandler databaseHandler=null;

    public PersistantAccountDAO(Context context){
        if(databaseHandler==null){
            databaseHandler=new DatabaseHandler(context);
        }
        db = databaseHandler.getWritableDatabase();
    }
    @Override
    public List<String> getAccountNumbersList() {
        List<String> accNoList = new ArrayList<String>();
        // Select All Query
        String query = "SELECT" + databaseHandler.account_No +  "FROM " + databaseHandler.account_Table;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            accNoList.add(cursor.getString(cursor.getColumnIndex(databaseHandler.account_No)));
        }
        return accNoList;
    }

    @Override
    public List<Account> getAccountsList() {

    List<Account> accList = new ArrayList<Account>();
    String query = "SELECT * FROM " + databaseHandler.account_Table;
    Cursor cursor = db.rawQuery(query, null);

    while(cursor.moveToNext()){
        String accountNo = cursor.getString(cursor.getColumnIndex(databaseHandler.account_No));
        String bankName = cursor.getString(cursor.getColumnIndex(databaseHandler.bank_Name));
        String accountHolderName = cursor.getString(cursor.getColumnIndex(databaseHandler.accountHolder_Name));
        double balance = cursor.getDouble(cursor.getColumnIndex(databaseHandler.balance));

        Account account =  new Account(accountNo, bankName,accountHolderName,balance);
        accList.add(account);
    }
    return accList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        //String query = "SELECT * FROM " + databaseHandler.account_Table + "WHERE "+ databaseHandler.account_No +" = ?";
        //Cursor cursor = db.rawQuery(query, new String[] {accountNo});

        String query = "SELECT * FROM " + databaseHandler.account_Table + "WHERE "+ databaseHandler.account_No +" = '" + accountNo + "'";
        Cursor cursor = db.rawQuery(query, null);

        String accNo = cursor.getString(cursor.getColumnIndex(databaseHandler.account_No));
        String bankName = cursor.getString(cursor.getColumnIndex(databaseHandler.bank_Name));
        String accountHolderName = cursor.getString(cursor.getColumnIndex(databaseHandler.accountHolder_Name));
        double balance = cursor.getDouble(cursor.getColumnIndex(databaseHandler.balance));

        Account account =  new Account(accNo, bankName,accountHolderName,balance);
        return account;
    }

    @Override
    public void addAccount(Account account) {
        ContentValues values = new ContentValues();

        values.put(databaseHandler.account_No, account.getAccountNo());
        values.put(databaseHandler.bank_Name, account.getBankName());
        values.put(databaseHandler.accountHolder_Name, account.getAccountHolderName());
        values.put(databaseHandler.balance, account.getBalance());

        // Inserting Row
        db.insert(databaseHandler.account_Table, null, values);
        db.close(); // Closing database connection
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        db.delete(databaseHandler.account_Table, databaseHandler.account_No + " = " + accountNo, null);
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        ContentValues values = new ContentValues();

        values.put(databaseHandler.account_No, accountNo);
        values.put(databaseHandler.expense_Type, String.valueOf(expenseType));
        values.put(databaseHandler.amount, amount);

        // updating row
        //return db.update(databaseHandler.account_Table, values, databaseHandler.account_No + " = ?",new String[] { String.valueOf(contact.getID()) });
        db.update(databaseHandler.account_Table, values, databaseHandler.account_No + " = " + accountNo, null);
    }
}
