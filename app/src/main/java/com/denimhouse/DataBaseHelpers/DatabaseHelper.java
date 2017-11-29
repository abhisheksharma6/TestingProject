package com.denimhouse.DataBaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.denimhouse.Models.AllProductsModel;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ShoppingItemList";
    public static final String TABLE_ITEM = "item";
    private static final String KEY_ID = "id";
    private static final String KEY_PARENT_ID = "parent_id";
    private static final String KEY_PRODUCT_TITLE = "titleName";
    private static final String KEY_PRODUCT_TYPE = "type";
    private static final String KEY_PRODUCT_CODE = "code";
    private static final String KEY_PRODUCT_PRICE = "price";
    private static final String KEY_PRODUCT_SUBTOTAL = "subtotal";
    private static final String KEY_COUNT = "count";
    private static final String KEY_PRODUCT_IMAGE = "image";
    private static final String KEY_PRODUCT_SIZE = "size";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ITEM + "(" +
                KEY_ID + " INTEGER , " +
                KEY_PARENT_ID + " TEXT, " +
                KEY_PRODUCT_TITLE + " TEXT, " +
                KEY_PRODUCT_TYPE + " TEXT, " +
                KEY_PRODUCT_CODE + " TEXT, " +
                KEY_PRODUCT_PRICE + " TEXT, " +
                KEY_PRODUCT_SUBTOTAL + " TEXT, " +
                KEY_COUNT + " TEXT, " +
                KEY_PRODUCT_IMAGE + " blob, " +
                KEY_PRODUCT_SIZE + " TEXT )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        onCreate(db);
    }

    public void insert(AllProductsModel allProductsModel, String parent_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(KEY_ID, allProductsModel.getId());
        contentValue.put(KEY_PARENT_ID, parent_ID);
        contentValue.put(KEY_PRODUCT_TITLE, allProductsModel.getProductName());
        contentValue.put(KEY_PRODUCT_TYPE, allProductsModel.getCategoryId());
        contentValue.put(KEY_PRODUCT_CODE, allProductsModel.getId());
        contentValue.put(KEY_PRODUCT_PRICE, String.valueOf(allProductsModel.getProductPrice()));
        contentValue.put(KEY_PRODUCT_SUBTOTAL, String.valueOf(allProductsModel.getSubTotal()));
        contentValue.put(KEY_COUNT, String.valueOf(allProductsModel.getItemCount()));
        contentValue.put(KEY_PRODUCT_IMAGE, allProductsModel.getProductImage());
        contentValue.put(KEY_PRODUCT_SIZE, allProductsModel.getSize());
        long rowInserted = db.insert(TABLE_ITEM, null, contentValue);
        /*if (rowInserted != -1)
            Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();*/
        db.close();
    }

    public void updateRecord(AllProductsModel allProductsModel) {

        SQLiteDatabase db = this.getWritableDatabase();

/*        db.execSQL("Update " + TABLE_ITEM +
                " Set " + KEY_COUNT + " = " + allProductsModel.getItemCount() +
                " , " + KEY_PRODUCT_SUBTOTAL + " = " + allProductsModel.getSubTotal()+
                " where " + KEY_PARENT_ID + " = " + allProductsModel.getParent_id() +
                " AND " + KEY_ID + " = " + allProductsModel.getId()+
                " AND " + KEY_PRODUCT_SIZE + " = " + allProductsModel.getSize());*/

      //  db.close();
        ContentValues cv = new ContentValues();
        cv.put(KEY_COUNT,allProductsModel.getItemCount()); //These Fields should be your String values of actual column names
        cv.put(KEY_PRODUCT_SUBTOTAL,allProductsModel.getSubTotal());


        db.update(TABLE_ITEM, cv,KEY_PARENT_ID + " = " + allProductsModel.getParent_id()+" AND "+KEY_ID + " = " + allProductsModel.getId()+" AND " + KEY_PRODUCT_SIZE + " = " + "'"+allProductsModel.getSize()+"'", null);

    /*ContentValues contentValue = new ContentValues();
     contentValue.put(KEY_ID, allProductsModel.getId());
        //contentValue.put(KEY_PARENT_ID, parent_ID);
        contentValue.put(KEY_PRODUCT_TITLE, allProductsModel.getProductName());
        contentValue.put(KEY_PRODUCT_TYPE, allProductsModel.getStatus());
        contentValue.put(KEY_PRODUCT_CODE, allProductsModel.getId());
        contentValue.put(KEY_PRODUCT_PRICE, String.valueOf(allProductsModel.getProductPrice()));
        contentValue.put(KEY_PRODUCT_SUBTOTAL, String.valueOf(allProductsModel.getSubTotal()));
        contentValue.put(KEY_COUNT, String.valueOf(allProductsModel.getItemCount()));
        contentValue.put(KEY_PRODUCT_IMAGE, allProductsModel.getProductImage());
        String whereClause = "id=?";*/
        //String whereArgs[] = {String.valueOf(allProductsModel.getId())};
      /*  return db.update(TABLE_ITEM, contentValue,
                whereClause, null);*/
    }

    public int getTotalItems(){
        int sumOFItem=0;
        String selectQuery = "SELECT * FROM "+TABLE_ITEM;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {

                sumOFItem +=Integer.parseInt(cursor.getString(7));
            } while (cursor.moveToNext());


        }
        return sumOFItem;
    }
    public List<AllProductsModel> getData() {
        List<AllProductsModel> productsModels = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                AllProductsModel allProductsModel = new AllProductsModel();
                allProductsModel.setId(cursor.getString(0));
                allProductsModel.setParent_id(cursor.getString(1));
                allProductsModel.setProductName(cursor.getString(2));
                allProductsModel.setCategoryId(cursor.getString(3));
                allProductsModel.setId(cursor.getString(4));
                allProductsModel.setProductPrice(cursor.getString(5));
                allProductsModel.setSubTotal(Double.parseDouble(cursor.getString(6)));
                allProductsModel.setItemCount(Integer.parseInt(cursor.getString(7)));
                allProductsModel.setProductImage(cursor.getString(8));
                allProductsModel.setSize(cursor.getString(9));
                productsModels.add(allProductsModel);
            } while (cursor.moveToNext());


        }
        return productsModels;
    }

   /* public void getID(String id, String parentID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEM + "WHERE" + KEY_PARENT_ID  + parentID +"AND"  + KEY_ID + id, null);
        if(id==KEY_ID && parentID==KEY_PARENT_ID){

        }
    }*/

    public void delete(AllProductsModel allProductsModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEM, KEY_ID + " = ? ", new String[]{allProductsModel.getId()});
        db.close();
    }

    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_ITEM, null, null);
        db.close();

    }

    public long getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt = DatabaseUtils.queryNumEntries(db, TABLE_ITEM);
        db.close();
        return cnt;
    }
}
