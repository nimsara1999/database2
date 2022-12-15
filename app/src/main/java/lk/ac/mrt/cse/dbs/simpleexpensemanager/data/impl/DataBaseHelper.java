package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public static final String Column_ACCOUNT = "account";
    public static final String Column_TR_ACCOUNT_NO = Column_ACCOUNT + "No";
    public static final String Column_ACCOUNT_NO = Column_TR_ACCOUNT_NO;
    public static final String Column_BANK = "bank";
    public static final String Column_ACCOUNT_HOLDER = Column_ACCOUNT + "Holder";
    public static final String Column_INIT_BALANCE = "initBalance";
    public static final String TRANSACTION_TABLE = "TRANSACTION_TABLE";
    public static final String Column_ID = "ID";
    public static final String Column_TYPE = "type";
    public static final String Column_AMOUNT = "amount";
    public static final String Column_DATE = "date";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "Bank.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement1 = "CREATE TABLE " + ACCOUNT_TABLE + "(" + Column_ACCOUNT_NO + " varchar(10) NOT NULL PRIMARY KEY, " + Column_BANK + " varchar(255), " + Column_ACCOUNT_HOLDER + " varchar(255), " + Column_INIT_BALANCE + " double(10,2))";
        String createTableStatement2 = "CREATE TABLE " + TRANSACTION_TABLE + "(" + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_ACCOUNT + " varchar(10), " + Column_TYPE + " bool, " + Column_AMOUNT + " double(10,2), " + Column_DATE + " DATE, FOREIGN KEY(account) REFERENCES ACCOUNT_TABLE(Column_ACCOUNT_NO))";

        //String createTableStatement2 = "CREATE TABLE TRANSACTION_TABLE(ID INTEGER PRIMARY KEY AUTOINCREMENT, account varchar(10), type bool, amount double(10,2), date DATE, FOREIGN KEY(account) REFERENCES ACCOUNT_TABLE(Column_ACCOUNT_NO))";

        db.execSQL(createTableStatement1);
        db.execSQL(createTableStatement2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists  ACCOUNT_TABLE");
        db.execSQL("drop Table if exists  TRANSACTION_TABLE");
    }

    public Boolean addNewAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Column_ACCOUNT_NO",account.getAccountNo());
        contentValues.put("Column_BANK",account.getBankName());
        contentValues.put("Column_ACCOUNT_HOLDER",account.getAccountHolderName());
        contentValues.put("Column_INIT_BALANCE",account.getBalance());

        long result=db.insert("ACCOUNT_TABLE",null,contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor removeAccountData(String accNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select *from ACCOUNT_TABLE where Column_ACCOUNT_NO=?", new String[]{accNo});
        if (cursor.getCount() > 0) {
            long result = db.delete("ACCOUNT_TABLE", "name=?", new String[]{accNo});
        }
        return cursor;
    }


    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select *from ACCOUNT_TABLE", null);
        return cursor;
    }
}
