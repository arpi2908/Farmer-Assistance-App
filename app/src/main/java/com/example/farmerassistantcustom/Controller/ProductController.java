package com.example.farmerassistantcustom.Controller;

import static com.example.farmerassistantcustom.Db.SqliteDatabase.Contact;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.FName;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Fcontact;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Femail;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.PImg;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.PName;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.PfName;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Price;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Product;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.pfid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.pid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.farmerassistantcustom.Db.SqliteDatabase;
import com.example.farmerassistantcustom.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private final SqliteDatabase sqliteDatabase;
    public ProductController(Context context) {
        this.sqliteDatabase = new SqliteDatabase(context);
    }
    public void addproduct(Product farmer) {
        ContentValues values = new ContentValues();
        values.put(PName, farmer.getName());
        values.put(PImg, farmer.getImg());
        values.put(Price, farmer.getPrice());
        values.put(pfid, farmer.getFid());
        values.put(PfName, farmer.getFname());
        SQLiteDatabase db = this.sqliteDatabase.writableDatabase();
        db.insert(Table_Product, null, values);
    }

    public ArrayList<Product> getProduct(String fid) {

        List<Product> discountList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor = null;
        sqLiteDatabase = sqliteDatabase.readableDatabase();
        try {
            String selection = "fid = ?  ";
            // Define the selection arguments
            String[] selectionArgs = {fid};
            cursor = sqLiteDatabase.query(Table_Product, null,
                    selection, selectionArgs, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext())
                    discountList.add(getProductFromCursor(cursor));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }
        return (ArrayList<Product>) discountList;

    }

    private Product getProductFromCursor(Cursor cursor) {
        Product discount = new Product();
        discount.setPid(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(pid))));
        discount.setFid(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(pfid))));
        discount.setName(cursor.getString(cursor.getColumnIndexOrThrow(PName)));
        discount.setFname(cursor.getString(cursor.getColumnIndexOrThrow(PfName)));
        discount.setImg(cursor.getString(cursor.getColumnIndexOrThrow(PImg)));
        discount.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(Price)));

        return discount;
    }
    private Product getProductandFarmerFromCursor(Cursor cursor) {
        Product discount = new Product();
        discount.setPid(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(pid))));
        discount.setFid(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(pfid))));
        discount.setName(cursor.getString(cursor.getColumnIndexOrThrow(PName)));
        discount.setFname(cursor.getString(1));
        discount.setImg(cursor.getString(cursor.getColumnIndexOrThrow(PImg)));
        discount.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(Price)));
        discount.setContact(cursor.getString(cursor.getColumnIndexOrThrow(Fcontact)));
        discount.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(Femail)));


        return discount;
    }

    public void update(Product orders) {
        ContentValues values = new ContentValues();
        values.put(pid,orders.getPid());
        values.put(Price,orders.getPrice());
        values.put(PName,orders.getName());
        values.put(Price,orders.getPrice());
        values.put(PfName,orders.getFname());
        values.put(PImg,orders.getImg());
        SQLiteDatabase db = this.sqliteDatabase.writableDatabase();
        db.update(Table_Product, values, pid + " = ?", new String[]{String.valueOf(orders.getPid())});

    }
    public boolean delete(long offerid) {
        int result = 0;
        int result1=0;
        int result2=0;
        SQLiteDatabase sqLiteDatabase = this.sqliteDatabase.writableDatabase();
        try {
            // Finally, delete the product from TABLE_PRODUCT
            result = sqLiteDatabase.delete(Table_Product, pid + " =?", new String[]{String.valueOf(offerid)});
//            result1 = sqLiteDatabase.delete(Table_Order, opid + " =?", new String[]{String.valueOf(offerid)});


        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result > -1;
    }

    public ArrayList<Product> getProductwithFarmer() {

        List<Product> discountList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor = null;
        sqLiteDatabase = sqliteDatabase.readableDatabase();
        try {
            String selection = "select * from Product Left Join Farmer on Farmer.fid=Product.fid";
            // Define the selection arguments

            cursor = sqLiteDatabase.rawQuery(selection,null);
            if (cursor != null) {
                while (cursor.moveToNext())
                    discountList.add(getProductandFarmerFromCursor(cursor));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }
        return (ArrayList<Product>) discountList;

    }
    public ArrayList<Product> getallProductwithFarmer(String name) {

        List<Product> discountList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor = null;
        sqLiteDatabase = sqliteDatabase.readableDatabase();
        try {
            String selection = "select * from Product where Name LIKE '"+name+"%'";
            // Define the selection arguments

            cursor = sqLiteDatabase.rawQuery(selection,null);
            if (cursor != null) {
                while (cursor.moveToNext())
                    discountList.add(getProductFromCursor(cursor));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }
        return (ArrayList<Product>) discountList;

    }
}
