package com.example.third_year_project.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.third_year_project.logic.Item;
import com.example.third_year_project.logic.Labourer;
import com.example.third_year_project.logic.Reciept;
import com.example.third_year_project.logic.Stock;
import com.example.third_year_project.logic.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.third_year_project.storage.databaseContractclass.*;
//the description for the database and all the saving methods

public class databaseGuy extends SQLiteOpenHelper {
    String TAG = "databaseGuy";
    private static String DATABASE_NAME = "MY_DATABASE.db";
    private static int DATABASE_VERSION = 1;
    Context context;
    /*** table definitions **/
    static String TABLE_items = "ITEMS_DEFINITION" ;
    static String TABLE_merchants = "MERCHANTS";
    static String TABLE_Receipt_overall = "RECEIPT";
    static String TABLE_transactions = "TRANSACTION";



    public databaseGuy(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(labourer.query_Oncreate_labourer);
        db.execSQL(transactionn.oncreate);
        //Log.i(TAG, "onCreate: transaction creator "+transactionn.oncreate);
        db.execSQL(item.oncreate);
        db.execSQL(receipt.oncreate);
        db.execSQL(colours.oncreate);


          //  create_table_transaction();
          //  create_table_labourer();
           // create_table_item();

        Log.i(TAG, "onCreate: Imefika hapa ");
        
    }

    public void saveworker(String name ,String otherName , String phone , int wages, String specialty ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(labourer.FIRST_NAME,name);
        cv.put(labourer.OTHER_NAME,otherName);
        cv.put(labourer.PHONE,phone);
        cv.put(labourer.SPECIALTY,specialty);
        cv.put(labourer.WEEKLY_WAGE,wages);

        db.insert(labourer.TABLE_NAME,null,cv);
        db.close();
    }

    public boolean deleteWorker(int Worker_id){
        boolean deleted = true;
        SQLiteDatabase db = this.getWritableDatabase();
       // String query_delete = "DELETE FROM "+ labourer.TABLE_NAME+" WHERE "+labourer.ID+" = '"+Worker_id+"' ;";
        String[] args = new String[] {(String.valueOf( Worker_id))};
        String selection = labourer.ID+" LIKE ? ";
        db.delete(labourer.TABLE_NAME,selection,args);

        return deleted;
    }

    public void updateWorker(Labourer change_worker){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(labourer.FIRST_NAME,change_worker.getFIRST_NAME());
        cv.put(labourer.OTHER_NAME,change_worker.getOTHER_NAME());
        cv.put(labourer.WEEKLY_WAGE,change_worker.getWEEKLY_WAGE());
        cv.put(labourer.SPECIALTY,change_worker.getSPECIALTY());
        cv.put(labourer.PHONE,change_worker.getPHONE());
        String selection = labourer.ID+" LIKE ? ";
        String[] args = new String[] {String.valueOf(change_worker.getID())};
        db.update(labourer.TABLE_NAME,cv,selection,args);
        db.close();
    }

    public void save_color(String speciaty , int color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colours.col_colour,color);
        cv.put(colours.col_specialty,speciaty.toLowerCase());
        db.insert(colours.table_colors,null,cv);
        db.close();
    }

    public Map<String, Integer > get_colors(){
        Map<String, Integer> colors = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        if(db ==null ){
            db.close();

            db = this.getReadableDatabase();
        }
        Cursor cursor= null;
        String[] coloumns = new String[] {colours.col_colour,colours.col_specialty};
        cursor = db.query(colours.table_colors,coloumns,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int colourpos = cursor.getColumnIndex(colours.col_colour);
                int specialpos= cursor.getColumnIndex(colours.col_specialty);
                colors.put(cursor.getString(specialpos), cursor.getInt(colourpos));
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return colors;
    }



    public void saveitem(Item save_item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(item.item_name,save_item.getItem_name());
        cv.put(item.item_description,save_item.getItem_description());
        cv.put(item.unit_of_measure,save_item.getUnit_of_measure());
        cv.put(item.item_description,save_item.getItem_description());
        try {

          int result = (int) db.insert(item.TABLE_items, null, cv);
            Log.i(TAG, "save_Item: "+" under id "+result+" "+save_item.toString());
        }catch (SQLException e){
            Log.i(TAG, "saveitem: Something went wrong"+e.getCause());
        }
        db.close();
    }

    public void update_item(){
        // TODO: 8/6/20 priss
    }

    public int save_reciept(String reciept_number, String vendor, long date ,double costs){
        int result  = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(receipt.receipt_number,reciept_number);
        cv.put(receipt.date,date);
        cv.put(receipt.costs,costs);
        cv.put(receipt.vendor,vendor);
        result = (int) db.insert(receipt.TABLE_RECEIPT,null,cv);
        Log.i(TAG, "save_reciept: result value is"+result);


        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void save_transactions(Reciept save_this_reciept){
        SQLiteDatabase db = this.getWritableDatabase();
        int use_this_id = save_reciept(save_this_reciept.getReceipt_number(),save_this_reciept.getVendor(),save_this_reciept.getDate(),save_this_reciept.getTotal_cost());
        ArrayList<Transaction> transactions_to_save = save_this_reciept.getTransactions();
        Log.i(TAG, "save_transactions: transactions to save size"+transactions_to_save.size());
        //Log.i(TAG, "save_transactions: "+save_this_reciept.getTransactions().get(0).toString());
        int pos = 0;
        //checker
        /**
        while (pos < transactions_to_save.size()){

            Log.i(TAG, "save_transactions: "+transactions_to_save.get(pos).toString());
            pos++;
        }**/


        while (pos < transactions_to_save.size()){

        Transaction transaction = save_this_reciept.getTransactions().get(pos);
            ContentValues cv = new ContentValues();
            cv.put(transactionn.trans_item_id,transaction.getTrans_item_id());
            cv.put(transactionn.receipt_id,use_this_id);
            cv.put(transactionn.transaction_cost,transaction.getCost());

            cv.put(transactionn.trans_unit_change,transaction.getTrans_unit_change());
            int new_trans_id = (int) db.insert(transactionn.TABLE_transactions,null,cv);
            Log.i(TAG, "save_transactions: new trans id "+new_trans_id);
            pos++;
        }

    }

    public void reduce_transactions(int reduction, int item_id){

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(transactionn.trans_item_id, item_id);
        cv.put(transactionn.trans_unit_change, (reduction)*-1);
        db.insert(transactionn.TABLE_transactions,null,cv);
        db.close();
    }


    public void delete_all_tables(){
        SQLiteDatabase db = this.getWritableDatabase();
        String delete_table_items = "DROP TABLE IF EXISTS "+TABLE_items+" ;";
        String delete_table_merchant = "DROP TABLE IF EXISTS "+TABLE_merchants+" ;";
        String delete_table_reciept = "DROP TABLE IF EXISTS "+TABLE_Receipt_overall+" ;";
        //String delete_table_transaction = "DROP TABLE IF EXISTS "+TABLE_transactions+" ;";

        try{
            db.execSQL(delete_table_items);
            db.execSQL(delete_table_merchant);
            db.execSQL(delete_table_reciept);
           // db.execSQL(delete_table_transaction);
        }catch (Exception e){
            Log.i(TAG,"Something went wrong deleting the tables : "+e.getMessage()+"==>"+e.getCause());
        }
    }

    public ArrayList<Labourer> labourerList(){
        ArrayList<Labourer> labourers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= null;
        if(db != null){
            cursor = db.rawQuery(labourer.query_fetch_data,null);
            Log.i(TAG, "labourerList: something went wrong ");
           // return null;

        }
        
        if(cursor == null){
            Toast.makeText(context, "Oya bro ", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "labourerList: Something went wrong cz there is no data in the cursosr");
        }

        int idpos = cursor.getColumnIndex(labourer.ID);
        int firstpos = cursor.getColumnIndex(labourer.FIRST_NAME);
        int otherpos = cursor.getColumnIndex(labourer.OTHER_NAME);
        int specialtypos = cursor.getColumnIndex(labourer.SPECIALTY);
        int wagepos = cursor.getColumnIndex(labourer.WEEKLY_WAGE);
        int phonepos = cursor.getColumnIndex(labourer.PHONE);

        while (cursor.moveToNext()){
            int id = cursor.getInt(idpos);
            int wages = cursor.getInt(wagepos);
            String first = cursor.getString(firstpos);
            String others = cursor.getString(otherpos);
            String phone = cursor.getString(phonepos);
            String specialty = cursor.getString(specialtypos);
            labourers.add(new Labourer(id,first,others,specialty,wages,phone));
        }


        return labourers;
    }

/**
    public void onCreated(SQLiteDatabase db) {
        String create_table_items = "CREATE TABLE '"+TABLE_items+"' " +
                "('"+item_id+"' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'"+item_name+"' VARCHAR NOT NULL ," +
                "'"+item_description+"' VARCHAR);";
        //db.execSQL(create_table_items);

        String create_table_merchant = "CREATE TABLE '"+TABLE_merchants+"' " +
                "('"+merchant_id+"' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'"+merchant_name+"' VARCHAR NOT NULL," +
                "'"+merchant_description+"' VARCHAR);";
        //db.execSQL(create_table_merchant);

        String create_reciept = "CREATE TABLE '"+TABLE_Receipt_overall+"' " +
                "('"+receipt_id+"' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'"+receipt_number+"' INTEGER NOT NULL," +
                "'"+receipt_merchant_id+"' INTEGER ," +
                "'"+receipt_date+"' VARCHAR NOT NULL," +
                "'"+receipt_total_cost+"'  INTEGER NOT NULL," +
                "'"+receipt_discount+"'  INTEGER);";
        //db.execSQL(create_reciept);

        String create_transactions = "CREATE TABLE '"+TABLE_transactions+"' " +
                "('"+trans_id+"' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'"+trans_item_id+"' INTEGER NOT NULL," +
                "'"+trans_receipt_number+"' INTEGER ," +
                "'"+trans_merchant_id+"' INTEGER ," +
                "'"+trans_item_quantity+"' INTEGER , " +
                "'"+trans_item_cost+"' INTEGER NOT NULL );";
        //db.execSQL(create_transactions);

        db.execSQL(databaseContractclass.labourer.query_Oncreate_labourer);
    }**/

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(labourer.query_delete_table);
        db.execSQL(item.query_delete_table);
        db.execSQL(transactionn.query_delete_table);
        onCreate(db);
    }
}
