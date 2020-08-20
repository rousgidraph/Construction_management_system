package com.example.third_year_project.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.example.third_year_project.logic.Item;
import com.example.third_year_project.logic.Labourer;
import com.example.third_year_project.logic.Stock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.third_year_project.storage.databaseContractclass.*;

public class Datamanager {
    SQLiteOpenHelper databasehelper;
    String TAG = "Database ";

    //this will hold any data from the database to be queried by the system

    public Datamanager(SQLiteOpenHelper databasehelper) {
        this.databasehelper = databasehelper;
    }


    public String get_reciept_date(int reciept_number){
        String date = " ";
        SQLiteDatabase db = databasehelper.getReadableDatabase();
        Cursor cursor = null;
        //db.execSQL(receipt.rename_date_to_datee);
        String selection = receipt.receipt_id+" LIKE ? ";
        String[] args = new String [] {String.valueOf(reciept_number)};
        String[] columns = new String[]{receipt.date};
        cursor= db.query(receipt.TABLE_RECEIPT,columns,selection,args,null,null,null);
        cursor.moveToFirst();
        int pos = cursor.getColumnIndex(receipt.date);
        date = String.valueOf(cursor.getInt(pos));
        //Log.i(TAG, "get_reciept_date: THe date in milliseconds is "+date);

        //resposible for fetching the date on the reciept number passed
        return date;
    }
    public ArrayList<String> search_by_name(String name){
        ArrayList<String> found_workers_list = new ArrayList<>();
        SQLiteDatabase db = databasehelper.getReadableDatabase();
        Cursor cursor= null;
        if(db == null){
            Log.i(TAG, "search_by_name: Database is null");
            return null;
        }

        String[] columns = new String[]{labourer.ID,labourer.FIRST_NAME,labourer.OTHER_NAME,labourer.PHONE,labourer.SPECIALTY,labourer.WEEKLY_WAGE};
        String selection = labourer.FIRST_NAME+" LIKE ?";
        String[] selectionArgs = new String[]{name};
        cursor = db.query(
                labourer.TABLE_NAME,columns,selection,selectionArgs,null,null,labourer.ID);
        if(cursor.moveToFirst()){
            int namePos = cursor.getColumnIndex(labourer.FIRST_NAME);
            int specialtypos = cursor.getColumnIndex(labourer.SPECIALTY);
            int idpos = cursor.getColumnIndex(labourer.ID);
            do{
                String jina  = cursor.getString(namePos);
                int id = cursor.getInt(idpos);
                String specialty = cursor.getString(specialtypos);

                String details = id+" :   "+jina+" "+" "+specialty;
                found_workers_list.add(details);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return found_workers_list;
    }
    public ArrayList<String> search_by_specialty(String Specialty){
        ArrayList<String> found_workers_list = new ArrayList<>();
        SQLiteDatabase db = databasehelper.getReadableDatabase();
        Cursor cursor= null;
        if(db == null){
            Log.i(TAG, "search_by_name: Database is null");
            return null;
        }

        String[] columns = new String[]{labourer.ID,labourer.FIRST_NAME,labourer.OTHER_NAME,labourer.PHONE,labourer.SPECIALTY,labourer.WEEKLY_WAGE};
        String selection = labourer.SPECIALTY+" LIKE ?";
        String[] selectionArgs = new String[]{Specialty};
        cursor = db.query(
                labourer.TABLE_NAME,columns,selection,selectionArgs,null,null,labourer.ID);
        if(cursor.moveToFirst()){
            int namePos = cursor.getColumnIndex(labourer.FIRST_NAME);
            int specialtypos = cursor.getColumnIndex(labourer.SPECIALTY);
            int idpos = cursor.getColumnIndex(labourer.ID);
            do{
                String jina  = cursor.getString(namePos);
                int id = cursor.getInt(idpos);
                String specialty = cursor.getString(specialtypos);

                String details = id+" :   "+jina+" "+" "+specialty;
                found_workers_list.add(details);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return found_workers_list;
    }

    public Labourer fetch_specific_labourer(int fetch_this_id){
        Labourer returnable_worker = null;
        SQLiteDatabase db = databasehelper.getReadableDatabase();
        Cursor cursor= null;
        if(db != null){
            String selection = labourer.ID+" LIKE ? ";
            String []args = new String[] {String.valueOf(fetch_this_id)};
            String [] columns = new String []{labourer.FIRST_NAME,labourer.OTHER_NAME,labourer.SPECIALTY,labourer.WEEKLY_WAGE,labourer.PHONE};
            cursor = db.query(labourer.TABLE_NAME,columns,selection,args,null,null,null);
           // Log.i(TAG, "labourerList: data fetched successfully ");
            // return null;
        }

        if(cursor == null){
            //Toast.makeText(context, "Oya bro ", Toast.LENGTH_SHORT).show();
            Log.i(TAG, " Something went wrong cz there is no data in the cursosr");
        }

       // int idpos = cursor.getColumnIndex(databaseContractclass.labourer.ID);
        int firstpos = cursor.getColumnIndex(labourer.FIRST_NAME);
        int otherpos = cursor.getColumnIndex(labourer.OTHER_NAME);
        int specialtypos = cursor.getColumnIndex(labourer.SPECIALTY);
        int wagepos = cursor.getColumnIndex(labourer.WEEKLY_WAGE);
        int phonepos = cursor.getColumnIndex(labourer.PHONE);

       if(cursor.moveToNext()){
           // int id = cursor.getInt(idpos);
            int wages = cursor.getInt(wagepos);
            String first = cursor.getString(firstpos);
            String others = cursor.getString(otherpos);
            String phone = cursor.getString(phonepos);
            String specialty = cursor.getString(specialtypos);
           returnable_worker = new Labourer(fetch_this_id,first,others,specialty,wages,phone);
        }
        cursor.close();
        db.close();
       // Log.i(TAG, "fetch_specific_labourer: "+returnable_worker.toString());
        return returnable_worker;
    }

    public ArrayList<Labourer> labourerList(){
        ArrayList<Labourer> labourers = new ArrayList<>();
        SQLiteDatabase db = databasehelper.getReadableDatabase();
        Cursor cursor= null;
        if(db != null){
            cursor = db.rawQuery(labourer.query_fetch_data,null);
            Log.i(TAG, "labourerList: data fetched successfully ");
            // return null;
        }

        if(cursor == null){
            //Toast.makeText(context, "Oya bro ", Toast.LENGTH_SHORT).show();
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
        cursor.close();
        db.close();
        return labourers;
    }

    public ArrayList<Stock> fetch_stock_data(){
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        ArrayList<Stock> returnable_list = new ArrayList<>();
        //db.execSQL(stock_view.oncreate);
        //db.execSQL(receipt.rename_date_to_datee);

        //db.execSQL(stock_view.drop);
        db.execSQL(stock_view.on_create_plus_date);
        //Log.i(TAG, "fetch_stock_data: "+stock_view.oncreate);
        Cursor cursor = null;
        cursor = db.rawQuery(stock_view.fetch_the_stocks,null);
        int item_name_pos = cursor.getColumnIndex(item.item_name);
        int item_units_of_measure_pos = cursor.getColumnIndex(item.unit_of_measure);
        int item_id_pos = cursor.getColumnIndex(item.item_id);
        int current_units_pos = 1;
       // int current_units_pos = cursor.getColumnIndex("sum(TRANSACTIONZ.UNITS)");
        int date_pos = cursor.getColumnIndex(receipt.date);

        if(cursor.moveToFirst()){
            do{
                String item_name = cursor.getString(item_name_pos);
                String unit_of_measure = cursor.getString(item_units_of_measure_pos);
                int curr_units = cursor.getInt(current_units_pos);
                int item_id = cursor.getInt(item_id_pos);
                //long date = cursor.getLong(date_pos);
                String datee = getFormattedDate(cursor.getLong(date_pos));
                Stock stock = new Stock(item_id,curr_units,item_name,unit_of_measure,datee);
                returnable_list.add(stock);
                //Log.i(TAG, "fetch_stock_data: "+stock.toString());
            }while (cursor.moveToNext());
        }else{returnable_list = null;}

        return returnable_list;
    }

    public Stock fetch_item_stock_data(int fetch_this_id){
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        Stock returnable_item = null;
        //db.execSQL(stock_view.oncreate);
        //db.execSQL(receipt.rename_date_to_datee);

        //db.execSQL(stock_view.drop);
        db.execSQL(stock_view.on_create_plus_date);
        //Log.i(TAG, "fetch_stock_data: "+stock_view.oncreate);

        Cursor cursor = null;

        String query = "SELECT * FROM \"main\".\" STOCK \" WHERE ITEM_ID LIKE "+fetch_this_id+" ; ";
        //String[] colomns = new String[]{" * "};
        //String selection = item.item_id+" LIKE ? ";
        //String[] args = new String[] {String.valueOf(fetch_this_id)};
        //cursor = db.query(stock_view.stock_view_name,colomns,selection,args,null,null,null);
        cursor = db.rawQuery(query,null);
        Log.i(TAG, "fetch_item_stock_data: "+query);
        if(cursor == null){
            Log.i(TAG, "fetch_item_stock_data: you already know its null");
        }

        int item_name_pos = cursor.getColumnIndex(item.item_name);
        int item_units_of_measure_pos = cursor.getColumnIndex(item.unit_of_measure);
        int item_id_pos = cursor.getColumnIndex(item.item_id);
        int current_units_pos = 1;
        // int current_units_pos = cursor.getColumnIndex("sum(TRANSACTIONZ.UNITS)");
        int date_pos = cursor.getColumnIndex(receipt.date);


            if(cursor.moveToNext()){
                String item_name = cursor.getString(item_name_pos);
                String unit_of_measure = cursor.getString(item_units_of_measure_pos);
                int curr_units = cursor.getInt(current_units_pos);
                int item_id = cursor.getInt(item_id_pos);
                //long date = cursor.getLong(date_pos);
                String datee = getFormattedDate(cursor.getLong(date_pos));
                returnable_item = new Stock(item_id,curr_units,item_name,unit_of_measure,datee);

                Log.i(TAG, "fetch_stock_data: "+returnable_item.toString());
            }


        return returnable_item;
    }

    public ArrayList<String> fetch_all_item_history(){
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        ArrayList<String> results = new ArrayList<>();
        db.execSQL(STOCK_HISTORY_view.onCreate);
        //Cursor cursor = db.rawQuery(STOCK_HISTORY_view.fetch_the_history,null);
        String [] columns = new String[]{transactionn.trans_unit_change,receipt.date,receipt.vendor,item.item_id,item.item_name};
       // String selection = transactionn.trans_item_id+" LIKE ? ";
        //String[] args = new String[]{String.valueOf(item_id)};
        Cursor cursor = db.query(STOCK_HISTORY_view.stock_history_name,columns,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int idpos = cursor.getColumnIndex(item.item_id);
            int namepos = cursor.getColumnIndex(item.item_name);
            int unitspos = cursor.getColumnIndex(transactionn.trans_unit_change);
            int datepos = cursor.getColumnIndex(receipt.date);
            int vendorpos = cursor.getColumnIndex(receipt.vendor);
            do{
                int item_id = cursor.getInt(idpos);
                String item_name = cursor.getString(namepos);
                String vendor = cursor.getString(vendorpos);
                int units = cursor.getInt(unitspos);
                long time_in_millis = cursor.getLong(datepos);
                String date = getFormattedDate(time_in_millis);
                if(vendor != null) {
                    String details = item_id+" : "+item_name+" on "+ date + " increased by " + units;
                    results.add(details);
                }else{
                    String details = item_id+" : "+item_name+" Reduced by "+Math.abs(units);
                    results.add(details);
                }
            }while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return results;
    }

    public ArrayList<String> fetch_item_history(int item_id){
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        ArrayList<String> results = new ArrayList<>();
        db.execSQL(STOCK_HISTORY_view.onCreate);
        //Cursor cursor = db.rawQuery(STOCK_HISTORY_view.fetch_the_history,null);
        String [] columns = new String[]{transactionn.trans_unit_change,receipt.date,receipt.vendor};
        String selection = transactionn.trans_item_id+" LIKE ? ";
        String[] args = new String[]{String.valueOf(item_id)};
        Cursor cursor = db.query(STOCK_HISTORY_view.stock_history_name,columns,selection,args,null,null,null);
        if(cursor.moveToFirst()){
            //int namepos = cursor.getColumnIndex(item.item_name);
            int unitspos = cursor.getColumnIndex(transactionn.trans_unit_change);
            int datepos = cursor.getColumnIndex(receipt.date);
            int vendorpos = cursor.getColumnIndex(receipt.vendor);
            do{
                // String item_name = cursor.getString(namepos);
                String vendor = cursor.getString(vendorpos);
                int units = cursor.getInt(unitspos);
                long time_in_millis = cursor.getLong(datepos);
                String date = getFormattedDate(time_in_millis);
                if(vendor != null) {
                    String details = date + " | from " + vendor + " units : " + units;
                    results.add(details);
                }else{
                    String details = "Reduced by "+Math.abs(units);
                    results.add(details);
                }
            }while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return results;
    }

    public ArrayList<String> fetch_item_history_via_name(String item_jina){
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        ArrayList<String> results = new ArrayList<>();
        db.execSQL(STOCK_HISTORY_view.onCreate);
        //Cursor cursor = db.rawQuery(STOCK_HISTORY_view.fetch_the_history,null);
        String [] columns = new String[]{transactionn.trans_unit_change,receipt.date,receipt.vendor,item.item_id,item.item_name};
        String selection = item.item_name+" LIKE ? ";
        String[] args = new String[]{item_jina};
        Cursor cursor = db.query(STOCK_HISTORY_view.stock_history_name,columns,selection,args,null,null,null);
        if(cursor.moveToFirst()){
            int idpos = cursor.getColumnIndex(item.item_id);
            int namepos = cursor.getColumnIndex(item.item_name);
            int unitspos = cursor.getColumnIndex(transactionn.trans_unit_change);
            int datepos = cursor.getColumnIndex(receipt.date);
            int vendorpos = cursor.getColumnIndex(receipt.vendor);
            do{
                int item_id = cursor.getInt(idpos);
                 String item_name = cursor.getString(namepos);
                String vendor = cursor.getString(vendorpos);
                int units = cursor.getInt(unitspos);
                long time_in_millis = cursor.getLong(datepos);
                String date = getFormattedDate(time_in_millis);
                if(vendor != null) {
                    String details = item_id+" : "+item_name+" on "+ date + " increased by " + units;
                    results.add(details);
                }else{
                    String details = item_id+" : "+item_name+" Reduced by "+Math.abs(units);
                    results.add(details);
                }
            }while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return results;
    }

    /**
    public ArrayList<Stock> get_stock_list(){
        ArrayList<Stock> returnable_list = new ArrayList<>();
        String query_get_stock = "SELECT "+transactionn.TABLE_transactions+"."+transactionn.trans_item_id+", "
                +"sum("+transactionn.TABLE_transactions+"."+transactionn.trans_unit_change+"), "+
                item.TABLE_items+"."+item.item_name+", "+item.TABLE_items+"."+item.unit_of_measure+", "
                +transactionn.TABLE_transactions+"."+transactionn.receipt_id+
                " FROM "+transactionn.TABLE_transactions+
                " INNER JOIN "+item.TABLE_items+" ON "+transactionn.TABLE_transactions+"."+transactionn.trans_item_id+" = "
                +item.TABLE_items+"."+item.item_id+
                " GROUP BY "+transactionn.TABLE_transactions+"."+transactionn.trans_item_id+" ;";
       // Log.i(TAG, "get_stock_list: "+query_get_stock);
        SQLiteDatabase db = databasehelper.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(query_get_stock,null);
        if(cursor == null||!cursor.moveToFirst()){
            Log.i(TAG, "get_stock_list: bro the data is empty");
            return null;
        }else{
       // Log.i(TAG, "get_stock_list: the cursor isnt empty ");
        }
        int item_name_pos = cursor.getColumnIndex(item.item_name);
        int unit_measure_pos = cursor.getColumnIndex(item.unit_of_measure);
        int item_rec_id_pos = cursor.getColumnIndex(transactionn.receipt_id);
        int item_id_pos = cursor.getColumnIndex(item.item_id);
        int item_curent_units_pos = cursor.getColumnIndex(transactionn.trans_unit_change);

        Log.i(TAG, "get_stock_list: "+cursor.getString(item_name_pos));
        if(cursor.moveToFirst()) {
            do {
                int item_id = cursor.getInt(item_id_pos);
                String item_name = cursor.getString(item_name_pos);
                String unit_of_measure = cursor.getString(unit_measure_pos);
                int rec_id = cursor.getInt(item_rec_id_pos);
                int trans_unit_change = 9;
//                int trans_unit_change = cursor.getInt(item_curent_units_pos);//something is wrong here
                // TODO: 8/3/20 wondering why all your units are 9?.... find out
                String date = get_reciept_date(rec_id);

                Stock stock = new Stock(item_id, trans_unit_change, item_name, unit_of_measure, date);
               // Log.i(TAG, "get_stock_list: " + stock.toString());
                returnable_list.add(stock);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
       // Log.i(TAG, "get_stock_list: Closed the database");
        return returnable_list;
    }
    **/
    public void get_item_history(){

    }

    public String getFormattedDate(long time_in_millis){
        String result = "dd/mm/yy";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time_in_millis);
        result = formatter.format(calendar.getTime());


        return result;
    }

    public ArrayList<String > search_by_item_name(String item_to_search_for){
        ArrayList<String> item_results = new ArrayList<>();
        SQLiteDatabase db = databasehelper.getReadableDatabase();
        Cursor cursor = null;
        String selection = item.item_name+" LIKE ? ";
        String[] args = new String[] {item_to_search_for};
        String [] columns = new String [] {item.item_id,item.item_name};
        cursor = db.query(item.TABLE_items, columns,selection,args,null,null,null);
        if(cursor.moveToFirst()){
            int namepos = cursor.getColumnIndex(item.item_name);
            int idpos = cursor.getColumnIndex(item.item_id);
            do{
                String name = cursor.getString(namepos);
                int id = cursor.getInt(idpos);
                String details = id+" : "+name;
                item_results.add(details);

            }while (cursor.moveToNext());
        }


        return item_results;
    }



    public Item fetch_specific_item(int fetch_this_id){
        Item returnable_item = null;
        SQLiteDatabase db = databasehelper.getReadableDatabase();
        Cursor cursor;
        String[] colomns = new String[]{item.item_id,item.item_name,item.unit_of_measure,item.item_description};
        String selection = item.item_id+" LIKE ? ";
        String[] args = new String[] {String.valueOf(fetch_this_id)};
        cursor = db.query(item.TABLE_items,colomns,selection,args,null,null,null);
        if(cursor.moveToNext()) {
            int namepos = cursor.getColumnIndex(item.item_name);
            int idpos = cursor.getColumnIndex(item.item_id);
            int decsriptionpos = cursor.getColumnIndex(item.item_description);
            int measureunitpos = cursor.getColumnIndex(item.unit_of_measure);
            //int new_id = cursor.getInt(idpos);
            String new_name = cursor.getString(namepos);
            String new_desc = cursor.getString(decsriptionpos);
            String new_measure = cursor.getString(measureunitpos);
            returnable_item = new Item(fetch_this_id, new_name, new_measure, new_desc);
        }

        return returnable_item;
    }

    public ArrayList<String >item_names(){
       ArrayList<String > itemslist = new ArrayList<>();
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        Cursor cursor;
        String [] columns = new String []{item.item_id,item.item_name};
        if(db == null){
            Log.i(TAG, "itemArrayList: error expected the db is null");
        }

        cursor = db.query(item.TABLE_items,columns,null,null,null,null,null);
        int intpos = cursor.getColumnIndex(item.item_id);
        int namepos = cursor.getColumnIndex(item.item_name);
        if(cursor != null){
            while (cursor.moveToNext()){
                itemslist.add(cursor.getString(namepos));
            }
        }
        cursor.close();
        db.close();
        return itemslist;
    }
    public ArrayList<Item> itemArrayList(){
        ArrayList<Item> itemslist = new ArrayList<>();
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        Cursor cursor;
        String [] columns = new String []{item.item_id,item.item_name};
        if(db == null){
            Log.i(TAG, "itemArrayList: error expected the db is null");
        }

        cursor = db.query(item.TABLE_items,columns,null,null,null,null,null);
        int intpos = cursor.getColumnIndex(item.item_id);
        int namepos = cursor.getColumnIndex(item.item_name);
        if(cursor != null){
            while (cursor.moveToNext()){
                itemslist.add(new Item(cursor.getInt(intpos),cursor.getString(namepos)));
            }
        }
        cursor.close();
        db.close();
        return itemslist;
    }


        public List<DataEntry> item_vs_cost(){
            List<DataEntry> data = new ArrayList<>();
            SQLiteDatabase db = databasehelper.getWritableDatabase();
            db.execSQL(ITEM_COSTS_view.onCreate);
            Cursor cursor = db.rawQuery(ITEM_COSTS_view.fetch_all,null);
            if(cursor.moveToFirst()){
                do{
                    String jina = cursor.getString(1);
                    double cost = cursor.getDouble(3);
                    data.add(new ValueDataEntry(jina,cost));

                }while (cursor.moveToNext());
            }


            return data;
        }

    public List<DataEntry> specialty_vs_money(){
        List<DataEntry> data = new ArrayList<>();
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        db.execSQL(Specialty_VS_MONEY_view.onCreate);
        Cursor cursor = db.rawQuery(Specialty_VS_MONEY_view.fetch_all,null);
        if(cursor.moveToFirst()){
            do{
                String jina = cursor.getString(0);
                double cost = cursor.getDouble(1);
                data.add(new ValueDataEntry(jina,cost));

            }while (cursor.moveToNext());
        }


        return data;
    }


    public List<DataEntry> Vendor_vs_money(){
        List<DataEntry> data = new ArrayList<>();
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        db.execSQL(VENDOR_SUMS_view.onCreate);
        Cursor cursor = db.rawQuery(VENDOR_SUMS_view.fetchAll,null);
        if(cursor.moveToFirst()){
            do{
                String jina = cursor.getString(0);
                double cost = cursor.getDouble(1);
                data.add(new ValueDataEntry(jina,cost));

            }while (cursor.moveToNext());
        }


        return data;
    }

}

