package com.example.farmerassistantcustom.Controller;

import static com.example.farmerassistantcustom.Db.SqliteDatabase.Cname;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Complainerid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Complaint;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Dt;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.PImg;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.PName;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.PfName;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Price;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Reply;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Status;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Complaint;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Product;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Tip;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Tid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Tip;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Type;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.pid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.farmerassistantcustom.Db.SqliteDatabase;
import com.example.farmerassistantcustom.Model.Complain;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.Model.Tips;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TipsController {
    SqliteDatabase sqliteDatabase;

    public TipsController(Context context) {
        this.sqliteDatabase = new SqliteDatabase(context);
    }
    public void addTips(Tips farmer) {
        ContentValues values = new ContentValues();
        values.put(Tid, farmer.getTid());
        values.put(Tip, farmer.getTip());

        SQLiteDatabase db = this.sqliteDatabase.writableDatabase();
        db.insert(Table_Tip, null, values);
    }

    public ArrayList<Tips> getTips() {

        List<Tips> discountList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor = null;
        sqLiteDatabase = sqliteDatabase.readableDatabase();
        String selection=null;
        String[] selectionArgs=null;

        try {
//            String selection = "fid = ?  ";
//            // Define the selection arguments
//            String[] selectionArgs = {fid};

//            selection = "Type LIKE '%"+type+"%' And complainerid  LIKE '%"+fid+"%' " ;
            // Define the selection arguments
//            selectionArgs = new String[]{ "%"+ type+ "%","%"+ fid+ "%"};

            cursor = sqLiteDatabase.query(Table_Tip, null,
                    null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext())
                    discountList.add(getTipsfromCursor(cursor));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }
        return (ArrayList<Tips>) discountList;

    }

    private Tips getTipsfromCursor(Cursor cursor) {
        Tips discount = new Tips();
        discount.setTid(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(Tid))));
        discount.setTip(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(Tip))));

        return discount;
    }
    public void update(Tips orders) {
        ContentValues values = new ContentValues();
        values.put(Tid,orders.getTid());
        values.put(Tip,orders.getTip());
        SQLiteDatabase db = this.sqliteDatabase.writableDatabase();
        db.update(Table_Tip, values, Tid + " = ?", new String[]{String.valueOf(orders.getTid())});

    }

    public boolean delete(long offerid) {
        int result = 0;
        int result1=0;
        int result2=0;
        SQLiteDatabase sqLiteDatabase = this.sqliteDatabase.writableDatabase();
        try {
            // Finally, delete the product from TABLE_PRODUCT
            result = sqLiteDatabase.delete(Table_Tip, Tid + " =?", new String[]{String.valueOf(offerid)});
//            result1 = sqLiteDatabase.delete(Table_Order, opid + " =?", new String[]{String.valueOf(offerid)});


        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result > -1;
    }
}
