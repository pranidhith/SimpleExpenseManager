package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHandler;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by prani on 12/5/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String account_Table = "Account";
    public static final String trans_Table = "Transaction";

    public static final String account_No = "accountNo";
    public static final String bank_Name = "bankName";
    public static final String accountHolder_Name = "accountHolderName";
    public static final String balance = "balance";

    public static final String expense_Type = "expenseType";
    public static final String amount = "amount";
    public static final String date = "date";


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "130385K";

    public static final String Account ="CREATE TABLE IF NOT EXISTS "+account_Table+"(" +
            account_No+" varchar(20) PRIMARY KEY," +
            bank_Name+" varchar(40),"+
            accountHolder_Name+" varchar(50),"+
            balance+" double"+")";

    public static final String Transaction ="CREATE TABLE IF NOT EXISTS "+trans_Table+"(" +
            account_No+" varchar(20),"+
            expense_Type+" varchar(20),"+
            amount+" double,"+
            date+" date"+")";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Account);
        db.execSQL(Transaction);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
