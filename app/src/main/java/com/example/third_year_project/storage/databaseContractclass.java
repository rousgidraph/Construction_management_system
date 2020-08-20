package com.example.third_year_project.storage;

import android.provider.BaseColumns;

public class databaseContractclass {
    //this conract class will have the basic stuff needed for each table in the database
    static class labourer  {
         static String ID = "id";
         static String TABLE_NAME= "Workers";
         static String FIRST_NAME= "First_name";
         static String OTHER_NAME ="Other_name";
         static String SPECIALTY="Specialty";
         static String WEEKLY_WAGE="Wage";
         static String PHONE="Phone_number";

         static String query_Oncreate_labourer = "CREATE TABLE "+ TABLE_NAME+
                "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                FIRST_NAME+" TEXT NOT NULL, "+ OTHER_NAME+" TEXT NOT NULL, "+
                SPECIALTY+" TEXT NOT NULL, "+ WEEKLY_WAGE+" INTEGER NOT NULL, "+
                PHONE+" TEXT NOT NULL );";


         static String query_delete_table = "DROP TABLE IF EXISTS "+TABLE_NAME+" ;";

         static String query_fetch_data = "SELECT * FROM "+ TABLE_NAME+" ;";

    }

    static class item {
        static String TABLE_items = "ITEMS_DEFINITION" ;
        static String item_id ="ITEM_ID";
        static String item_name ="ITEM_NAME";
        static String unit_of_measure="MEASURE_UNIT";
        static String item_description ="ITEM_DESCRIPTION";

        //queries
        static String oncreate = "CREATE TABLE '"+TABLE_items+"' " +
                "('"+item_id+"' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'"+item_name+"' VARCHAR NOT NULL ," +
                unit_of_measure+" VARCHAR, "+
                "'"+item_description+"' VARCHAR);";

        static String query_delete_table = "DROP TABLE IF EXISTS "+TABLE_items+" ;";

        static String query_fetch_data = "SELECT * FROM "+ TABLE_items+" ;";
    }

    static class transactionn{
        static String TABLE_transactions = "TRANSACTIONZ";
        static String trans_id= "TRANSACTION_ID";
       // static String trans_date="DATE";
        static String trans_item_id ="ITEM_ID";
        static String trans_unit_change ="UNITS";
        static String receipt_id = "RECEIPT_ID";
        static String transaction_cost = "ITEM_COST";


        static String oncreate  = "CREATE TABLE '"+TABLE_transactions+"' " +
                "('"+trans_id+"' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'"+receipt_id+"' INTEGER,  "+
                "'"+trans_item_id+"' INTEGER , " +
                "'"+trans_unit_change+"' INTEGER, "+
                transaction_cost+" INTEGER ); ";



        static String query_delete_table = "DROP TABLE IF EXISTS "+TABLE_transactions+" ;";

        static String query_fetch_data = "SELECT * FROM "+ TABLE_transactions+" ;";


    }

    static class receipt{
        static String TABLE_RECEIPT = "RECEIPT";
        static String receipt_id = "RECEIPT_ID";
        static String date = "DATEE";
        static String costs = "TOTAL_COSTS";
        static String vendor = "VENDOR";
        static String receipt_number = "RECEIPT_NUMBER";

        static String oncreate  = "CREATE TABLE "+TABLE_RECEIPT+
                "(" +receipt_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                receipt_number+" VARCHAR, "+
                date+" BIGINT NOT NULL, "+
                vendor+" VARCHAR, "+
                costs+" INTEGER "+
                ");";

        static String query_delete_table = "DROP TABLE IF EXISTS "+TABLE_RECEIPT+" ;";

        static String query_fetch_data = "SELECT * FROM "+ TABLE_RECEIPT+" ;";

        //ALTER TABLE RECEIPT
        //RENAME COLUMN  sikuu to DATEE;
        static String rename_date_to_datee = " ALTER TABLE  "+TABLE_RECEIPT+" RENAME COLUMN DATE TO "+date+" ; ";
    }

    static class stock_view {
        /***
         * it will combine data from two seperate tables
         * from item_definition => item_name , units of measure , item description
         * from transaction => item_id , current_units
         * seperate method from receipts => date
         */

        static String stock_view_name = " STOCK ";
        //CREATE VIEW STOCK  AS
        //SELECT TRANSACTIONZ.ITEM_ID, sum(TRANSACTIONZ.UNITS), ITEMS_DEFINITION.ITEM_NAME, ITEMS_DEFINITION.MEASURE_UNIT, TRANSACTIONZ.RECEIPT_ID, RECEIPT.DATEE
        //FROM TRANSACTIONZ
        //LEFT JOIN RECEIPT ON RECEIPT.RECEIPT_ID = TRANSACTIONZ.RECEIPT_ID
        //LEFT JOIN ITEMS_DEFINITION  ON ITEMS_DEFINITION.ITEM_ID = TRANSACTIONZ.ITEM_ID
        //GROUP BY TRANSACTIONZ.ITEM_ID
        static String on_create_plus_date = "CREATE VIEW IF NOT EXISTS '"+stock_view_name+"' AS "+
                "SELECT "+transactionn.TABLE_transactions+"."+transactionn.trans_item_id+", sum("+transactionn.TABLE_transactions+"."+transactionn.trans_unit_change+"), "+item.TABLE_items+"."+item.item_name+", "+item.TABLE_items+"."+item.unit_of_measure+", "+transactionn.TABLE_transactions+"."+transactionn.receipt_id+", "+receipt.TABLE_RECEIPT+"."+receipt.date+" "+
                "FROM "+transactionn.TABLE_transactions+" "+
                "LEFT JOIN "+receipt.TABLE_RECEIPT+" ON "+receipt.TABLE_RECEIPT+"."+receipt.receipt_id+" = "+transactionn.TABLE_transactions+"."+transactionn.receipt_id+" "+
                "LEFT JOIN "+item.TABLE_items+"  ON "+item.TABLE_items+"."+item.item_id+" = "+transactionn.TABLE_transactions+"."+transactionn.trans_item_id+" "+
                "GROUP BY "+transactionn.TABLE_transactions+"."+item.item_id+" ; "
                ;


        static String drop = "DROP VIEW  IF EXISTS  '"+stock_view_name+"';";
        static String oncreate = " CREATE VIEW IF NOT EXISTS '"+stock_view_name+"' AS "+" SELECT "+transactionn.TABLE_transactions+"."+transactionn.trans_item_id+", "
                +"sum("+transactionn.TABLE_transactions+"."+transactionn.trans_unit_change+"), "+
                item.TABLE_items+"."+item.item_name+", "+item.TABLE_items+"."+item.unit_of_measure+", "
                +transactionn.TABLE_transactions+"."+transactionn.receipt_id+
                " FROM "+transactionn.TABLE_transactions+
                " INNER JOIN "+item.TABLE_items+" ON "+transactionn.TABLE_transactions+"."+transactionn.trans_item_id+" = "
                +item.TABLE_items+"."+item.item_id+
                " GROUP BY "+transactionn.TABLE_transactions+"."+transactionn.trans_item_id+" ;";
        static String fetch_the_stocks =" SELECT * FROM  '"+stock_view_name+"'";

        static String fetch_item_past_transaction ;

    }

    static class colours {

        static String table_colors = "COLOURS";
        static String col_colour= "Colour";
        static String col_specialty = "Specialty";


        static String oncreate= "CREATE TABLE  IF NOT EXISTS "+table_colors
                +"("+col_specialty+" TEXT , "
                +col_colour+" INTEGER "+
                " ) ;";

        static String fetch_colors = " SELECT * FROM "+table_colors+" ; ";

        //this class will deal with any colors in the database
    }

    static class Specialty_VS_MONEY_view{
        static String specialty_view_name = "Specialty_VS_MONEY";
        static String onCreate = "CREATE VIEW IF NOT EXISTS  " +specialty_view_name+" AS "+
                " SELECT "+labourer.TABLE_NAME+"."+labourer.SPECIALTY+",sum( "+labourer.TABLE_NAME+"."+labourer.WEEKLY_WAGE+"), count("+labourer.TABLE_NAME+"."+labourer.ID+"), sum( "+labourer.TABLE_NAME+"."+labourer.WEEKLY_WAGE+")/count("+labourer.TABLE_NAME+"."+labourer.ID+") " +
                " FROM "+labourer.TABLE_NAME +
                " GROUP BY "+labourer.TABLE_NAME+"."+labourer.SPECIALTY+" ;";
        static String fetch_all = " SELECT * FROM "+specialty_view_name+" ; ";
    }
    static class VENDOR_SUMS_view{
        static String vendor_sums_name = "VENDOR_SUMS ";
        static String onCreate = " CREATE VIEW IF NOT EXISTS "+vendor_sums_name+" AS  " +
                " SELECT "+receipt.TABLE_RECEIPT+"."+receipt.vendor+" , sum("+receipt.TABLE_RECEIPT+"."+receipt.costs+") " +
                " FROM " +receipt.TABLE_RECEIPT+
                " GROUP BY "+receipt.TABLE_RECEIPT+"."+receipt.vendor+" ;";
        static String fetchAll = " SELECT * FROM "+vendor_sums_name+" ; ";
    }

    static class ITEM_COSTS_view{
        static String item_costs_name = "ITEM_COSTS";
        // TODO: 8/9/20 this is a 75 percent
        static String onCreate = " CREATE VIEW IF NOT EXISTS "+item_costs_name+" AS " +
                "SELECT TRANSACTIONZ.ITEM_ID, ITEMS_DEFINITION.ITEM_NAME, sum(TRANSACTIONZ.UNITS),sum(TRANSACTIONZ.ITEM_COST), sum(TRANSACTIONZ.ITEM_COST)/sum(TRANSACTIONZ.UNITS)\n" +
                "FROM TRANSACTIONZ " +
                "LEFT JOIN ITEMS_DEFINITION ON ITEMS_DEFINITION.ITEM_ID = TRANSACTIONZ.ITEM_ID " +
                "LEFT JOIN RECEIPT ON RECEIPT.RECEIPT_ID = TRANSACTIONZ.RECEIPT_ID " +
                "WHERE TRANSACTIONZ.ITEM_COST NOT NULL " +
                "GROUP BY TRANSACTIONZ.ITEM_ID ;";

        static String fetch_all = " SELECT * FROM "+item_costs_name+" ; ";
    }
    static class STOCK_HISTORY_view{
        static String stock_history_name = "STOCK_HISTORY" ;
        // TODO: 8/9/20 THis is another 75 percent
        static String onCreate = " CREATE VIEW IF NOT EXISTS "+stock_history_name+" AS " +
                " SELECT TRANSACTIONZ.ITEM_ID , TRANSACTIONZ.UNITS , ITEMS_DEFINITION.ITEM_NAME , RECEIPT.DATEE , RECEIPT.VENDOR" +
                " FROM TRANSACTIONZ " +
                " LEFT JOIN ITEMS_DEFINITION ON ITEMS_DEFINITION.ITEM_ID = TRANSACTIONZ.ITEM_ID " +
                " LEFT JOIN RECEIPT ON RECEIPT.RECEIPT_ID = TRANSACTIONZ.RECEIPT_ID ; ";
        static String fetch_the_history = " SELECT * FROM "+stock_history_name+" ; ";
    }
}
