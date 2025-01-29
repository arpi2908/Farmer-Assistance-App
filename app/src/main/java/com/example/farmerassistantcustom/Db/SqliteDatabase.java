package com.example.farmerassistantcustom.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;


import com.example.farmerassistantcustom.Model.Farmer;
import com.example.farmerassistantcustom.Model.Orders;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.Model.User;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {
    private static final int DataBase_Version = 10;
    private static final String DataBase_Name = "Farmer Assistant";
    Context context;


    //    user
    public static final String TAble_USer = "USER";
    public static String Uid = "Uid";
    public static String Name = "Name";
    public static String Email = "Email";
    public static String Contact = "Contact";
    public static String Pass = "Pass";

    //Farmer
    public static final String Table_Farmer = "Farmer";
    public static String fid = "fid";
    public static String FName = "Name";
    public static String Femail = "Email";
    public static String Fcontact = "Contact";
    public static String Fpass = "Pass";


    // product
    public static final String Table_Product = "Product";
    public static String pid = "pid";
    public static String PName = "Name";
    public static String PImg = "Img";
    public static String Price = "Price";
    public static String pfid = "fid";
    public static String PfName = "FarmerName";

//complaint

    public static final String Table_Complaint = "Complaint";
    public static String cid = "cid";
    public static String Type = "Type";
    public static String Cname = "Name";
    public static String Complainerid = "complainerid";
    public static String Complaint = "Complain";
    public static String Dt = "dt";
    public static String Reply = "Reply";
    public static String Status = "status";


    //Tip
    public static final String Table_Tip = "Tip";
    public static String Tid = "tid";
    public static String Tip = "tip";

    //Order

    public static final String Table_Orders = "Orders";
    public static String oid = "oid";
    public static String opid = "pid";
    public static String ofid = "fid";
    public static String opname = "Name";
    public static String oqauntity = "qauntity";
    public static String oamount = "Amount";
    public static String odt = "dt";
    public static String ocname = "custerName";
    public static String ofname = "FarmerName";
    public static String ouid = "uid";
    public static String ostatus = "status";

    public static String opreviousblock = "previousblock";
    public static String block = "block";


    //Cart
    public static final String Table_Cart = "Cart";
    public static String cartid = "cid";
    public static String cpid = "pid";
    public static String cuid = "uid";
    public static String cfid = "fid";
    public static String cpname = "Name";
    public static String cfname = "FName";
    public static String cimg = "img";
    public static String cuname = "UName";
    public static String ctotal = "total";
    public static String cprice = "price";
    public static String cqunatity = "Qauntity";


    public SqliteDatabase(Context context) {
        super(context, DataBase_Name, null, DataBase_Version);
        this.context = context;
    }

    public SQLiteDatabase writableDatabase() {

        return getWritableDatabase();
    }

    public SQLiteDatabase readableDatabase() {

        return getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_User = "CREATE TABLE "
                + TAble_USer + "(" + Uid
                + " INTEGER PRIMARY KEY,"
                + Name + " TEXT,"
                + Email + " TEXT,"
                + Contact + " TEXT,"
                + Pass + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_User);

        String CREATE_Farmer = "CREATE TABLE "
                + Table_Farmer + "(" + fid
                + " INTEGER PRIMARY KEY,"
                + FName + " TEXT,"
                + Femail + " TEXT,"
                + Fcontact + " TEXT,"
                + Fpass + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_Farmer);

        String CREATE_Product = "CREATE TABLE "
                + Table_Product + "(" + pid
                + " INTEGER PRIMARY KEY,"
                + PName + " TEXT,"
                + PImg + " TEXT,"
                + Price + " TEXT,"
                + pfid + " TEXT,"
                + PfName + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE_Product);

        String CREATE_Complaint = "CREATE TABLE "
                + Table_Complaint + "(" + cid
                + " INTEGER PRIMARY KEY,"
                + Type + " TEXT,"
                + Cname + " TEXT,"
                + Complaint + " TEXT,"
                + Complainerid + " TEXT,"
                + Dt + " TEXT,"
                + Reply + " TEXT,"
                + Status + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_Complaint);

        String CREATE_Orders = "CREATE TABLE "
                + Table_Orders + "(" + oid
                + " INTEGER PRIMARY KEY,"
                + opid + " TEXT,"
                + ofid + " TEXT,"
                + opname + " TEXT,"
                + oqauntity + " TEXT,"
                + oamount + " TEXT,"
                + ocname + " TEXT,"
                + odt + " TEXT,"
                + ofname + " TEXT,"
                + ouid + " TEXT,"
                + opreviousblock + " TEXT,"
                + block + " TEXT,"
                + ostatus + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_Orders);

        String CREATE_Tips = "CREATE TABLE "
                + Table_Tip + "(" + Tid
                + " INTEGER PRIMARY KEY,"
                + Tip + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_Tips);

        String CREATE_Cart = "CREATE TABLE "
                + Table_Cart + "(" + cartid
                + " INTEGER PRIMARY KEY,"
                + cuid + " TEXT,"
                + cpid + " TEXT,"
                + cfid + " TEXT,"
                + cpname + " TEXT,"
                + cfname + " TEXT,"
                + cuname + " TEXT,"
                + ctotal + " TEXT,"
                + cprice + " TEXT,"
                + cimg + " TEXT,"
                + cqunatity + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_Cart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TAble_USer);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Farmer);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Product);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Complaint);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Orders);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Tip);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Cart);

        onCreate(sqLiteDatabase);

    }

    public void registerUser(User user) {
        ContentValues values = new ContentValues();
        values.put(Name, user.getName());
        values.put(Email, user.getEmail());
        values.put(Contact, user.getContact());
        values.put(Pass, user.getPass());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TAble_USer, null, values);
    }

    public void registerFarmer(Farmer farmer) {
        ContentValues values = new ContentValues();
        values.put(FName, farmer.getName());
        values.put(Femail, farmer.getEmail());
        values.put(Fcontact, farmer.getContact());
        values.put(Fpass, farmer.getPass());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(Table_Farmer, null, values);
    }

    public boolean Ucheckemail(String email) {
        // array of columns to fetch
        String[] columns = {
                Uid
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Email + " = ?";
        // selection arguments
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TAble_USer, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public Cursor Ugetdata(String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from " + TAble_USer + " where " + Email + " = '" + email + "'";

        Cursor cursor = db.rawQuery(qry, null);
        return cursor;

    }

    public Cursor getdataProfile(String uid) {

        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from " + TAble_USer + " where " + Uid + " = '" + uid + "'";

        Cursor cursor = db.rawQuery(qry, null);
        return cursor;

    }

    public boolean Fcheckemail(String email) {
        // array of columns to fetch
        String[] columns = {
                fid
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Femail + " = ?";
        // selection arguments
        String[] selectionArgs = {email};

        Cursor cursor = db.query(Table_Farmer, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public Cursor Fgetdata(String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from " + Table_Farmer + " where " + Femail + " = '" + email + "'";

        Cursor cursor = db.rawQuery(qry, null);
        return cursor;

    }

    public Cursor getdataFProfile(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from " + Table_Farmer + " where " + fid + " = '" + uid + "'";

        Cursor cursor = db.rawQuery(qry, null);
        return cursor;

    }

    public void update(Product orders) {
        ContentValues values = new ContentValues();
        values.put(pid, orders.getPid());
        values.put(Price, orders.getPrice());
        values.put(PName, orders.getName());
        values.put(Price, orders.getPrice());
        values.put(PfName, orders.getFname());
        values.put(PImg, orders.getImg());
        SQLiteDatabase db = this.writableDatabase();
        db.update(Table_Product, values, pid + " = ?", new String[]{String.valueOf(orders.getPid())});

    }

}
