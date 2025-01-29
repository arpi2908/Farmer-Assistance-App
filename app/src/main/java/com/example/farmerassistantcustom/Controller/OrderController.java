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
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Orders;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Product;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Type;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.block;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.oamount;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.ocname;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.odt;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.ofid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.ofname;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.oid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.opid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.opname;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.opreviousblock;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.oqauntity;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.ostatus;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.ouid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.pfid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.farmerassistantcustom.Db.SqliteDatabase;
import com.example.farmerassistantcustom.Model.Complain;
import com.example.farmerassistantcustom.Model.Orders;
import com.example.farmerassistantcustom.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    SqliteDatabase sqliteDatabase;

    public OrderController(Context context) {
        this.sqliteDatabase = new SqliteDatabase(context);
    }

    public ArrayList<Orders> getOrders(String fid) {

        List<Orders> discountList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor = null;
        sqLiteDatabase = sqliteDatabase.readableDatabase();

        String selection = "fid = ?  ";
        // Define the selection arguments
        String[] selectionArgs = {fid};

        try {
            cursor = sqLiteDatabase.query(Table_Orders, null,
                    selection, selectionArgs, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext())
                    discountList.add(getOrdersfromCursor(cursor));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }
        return (ArrayList<Orders>) discountList;

    }

    private Orders getOrdersfromCursor(Cursor cursor) {
        Orders discount = new Orders();
        discount.setOid(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(oid))));
        discount.setPid(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(opid))));
        discount.setFid(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(ofid))));
        discount.setName(cursor.getString(cursor.getColumnIndexOrThrow(opname)));
        discount.setQauntity(cursor.getString(cursor.getColumnIndexOrThrow(oqauntity)));
        discount.setAmount(cursor.getString(cursor.getColumnIndexOrThrow(oamount)));
        discount.setCustname(cursor.getString(cursor.getColumnIndexOrThrow(ocname)));
        discount.setDt(cursor.getString(cursor.getColumnIndexOrThrow(odt)));
        discount.setFarmerName(cursor.getString(cursor.getColumnIndexOrThrow(ofname)));
        discount.setUid(cursor.getString(cursor.getColumnIndexOrThrow(ouid)));
        discount.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(ostatus)));
        discount.setPreviousblock(cursor.getString(cursor.getColumnIndexOrThrow(opreviousblock)));
        discount.setBlock(cursor.getString(cursor.getColumnIndexOrThrow(block)));
        return discount;
    }

    public void update(Orders orders) {
        ContentValues values = new ContentValues();
        values.put(oid, orders.getOid());
        values.put(ostatus, orders.getStatus());
        SQLiteDatabase db = this.sqliteDatabase.writableDatabase();
        db.update(Table_Orders, values, oid + " = ?", new String[]{String.valueOf(orders.getOid())});

    }

    public ArrayList<Orders> getallOrders() {

        List<Orders> discountList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor = null;
        sqLiteDatabase = sqliteDatabase.readableDatabase();

//        String selection = "fid = ?  ";
//        // Define the selection arguments
//        String[] selectionArgs = {fid};

        try {
            cursor = sqLiteDatabase.query(Table_Orders, null,
                    null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext())
                    discountList.add(getOrdersfromCursor(cursor));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }
        return (ArrayList<Orders>) discountList;

    }

    public void addOrders(Orders farmer) {
        ContentValues values = new ContentValues();
        values.put(opid, farmer.getPid());
        values.put(ofid, farmer.getFid());
        values.put(opname, farmer.getName());
        values.put(oqauntity, farmer.getQauntity());
        values.put(oamount, farmer.getAmount());
        values.put(odt, farmer.getDt());
        values.put(ocname, farmer.getCustname());
        values.put(ofname, farmer.getFarmerName());
        values.put(ouid, farmer.getUid());
        values.put(ostatus, farmer.getStatus());
        values.put(block, farmer.getBlock());
        values.put(opreviousblock, farmer.getPreviousblock());
        SQLiteDatabase db = this.sqliteDatabase.writableDatabase();
        db.insert(Table_Orders, null, values);
    }

    public ArrayList<Orders> getOrdersbyuid(String uid) {

        List<Orders> discountList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor = null;
        sqLiteDatabase = sqliteDatabase.readableDatabase();

        String selection = "uid = ?  ";
        // Define the selection arguments
        String[] selectionArgs = {uid};

        try {
            cursor = sqLiteDatabase.query(Table_Orders, null,
                    selection, selectionArgs, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext())
                    discountList.add(getOrdersfromCursor(cursor));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }
        return (ArrayList<Orders>) discountList;

    }
}
