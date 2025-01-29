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
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Cart;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Complaint;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Product;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Table_Tip;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Tid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Tip;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.Type;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cartid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cfid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cfname;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cimg;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cpid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cpname;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cprice;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cqunatity;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.ctotal;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cuid;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.cuname;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.ocname;
import static com.example.farmerassistantcustom.Db.SqliteDatabase.pfid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.farmerassistantcustom.Db.SqliteDatabase;
import com.example.farmerassistantcustom.Model.Cart;
import com.example.farmerassistantcustom.Model.Complain;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.Model.Tips;

import java.util.ArrayList;
import java.util.List;

public class CartController {
    SqliteDatabase sqliteDatabase;

    public CartController(Context context) {
        this.sqliteDatabase = new SqliteDatabase(context);
    }
    public void addCart(Cart farmer) {
        ContentValues values = new ContentValues();
        values.put(cuid, farmer.getUid());
        values.put(cpid, farmer.getPid());
        values.put(cfid, farmer.getFid());
        values.put(cpname, farmer.getName());
        values.put(cuname, farmer.getUname());
        values.put(cfname, farmer.getFname());
        values.put(ctotal, farmer.getTotal());
        values.put(cprice, farmer.getPrice());
        values.put(cqunatity, farmer.getQauntity());
        values.put(cimg, farmer.getImg());
        SQLiteDatabase db = this.sqliteDatabase.writableDatabase();
        db.insert(Table_Cart, null, values);
    }

    public ArrayList<Cart> getcartbyuid(String uid) {

        List<Cart> discountList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor = null;
        sqLiteDatabase = sqliteDatabase.readableDatabase();
        try {
            String selection = "select * from Cart where  uid= " + uid;
            // Define the selection arguments
            cursor = sqLiteDatabase.rawQuery(selection, null);
            if (cursor != null) {
                while (cursor.moveToNext())
                    discountList.add(getCompalinFromCursor(cursor));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }
        return (ArrayList<Cart>) discountList;

    }

    private Cart getCompalinFromCursor(Cursor cursor) {
        Cart discount = new Cart();
        discount.setCartid(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(cartid))));
        discount.setUid(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(cuid))));
        discount.setPid(cursor.getString(cursor.getColumnIndexOrThrow(cpid)));
        discount.setFid(cursor.getString(cursor.getColumnIndexOrThrow(cfid)));
        discount.setName(cursor.getString(cursor.getColumnIndexOrThrow(cpname)));
        discount.setFname(cursor.getString(cursor.getColumnIndexOrThrow(cfname)));
        discount.setUname(cursor.getString(cursor.getColumnIndexOrThrow(cuname)));
        discount.setTotal(cursor.getString(cursor.getColumnIndexOrThrow(ctotal)));
        discount.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(cprice)));
        discount.setImg(cursor.getString(cursor.getColumnIndexOrThrow(cimg)));
        discount.setQauntity(cursor.getString(cursor.getColumnIndexOrThrow(cqunatity)));
        return discount;
    }

    public boolean delete(long offerid) {
        int result = 0;
        int result1 = 0;
        int result2 = 0;
        SQLiteDatabase sqLiteDatabase = this.sqliteDatabase.writableDatabase();
        try {
            // Finally, delete the product from TABLE_PRODUCT
            result = sqLiteDatabase.delete(Table_Cart, cartid + " =?", new String[]{String.valueOf(offerid)});
//            result1 = sqLiteDatabase.delete(Table_Order, opid + " =?", new String[]{String.valueOf(offerid)});

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result > -1;
    }

    public void update(Cart orders) {
        ContentValues values = new ContentValues();
        values.put(cartid, orders.getCartid());
        values.put(cqunatity, orders.getQauntity());
        values.put(ctotal, orders.getTotal());
        SQLiteDatabase db = this.sqliteDatabase.writableDatabase();
        db.update(Table_Cart, values, cartid + " = ?", new String[]{String.valueOf(orders.getCartid())});
    }


}
