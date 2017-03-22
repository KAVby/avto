package com.example.avto;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper implements BaseColumns{

	// имя базы данных
	private static final String DATABASE_NAME = "mydatabase2.db";
	// версия базы данных
	private static final int DATABASE_VERSION = 1;

	
	// имя таблицы
	private static final String DATABASE_TABLE = "toplivo2";
	// названия столбцов
	public static final String date_COLUMN = "dat_zaprav";
	public static final String kurs_USD = "kurs_usd";
	public static final String cost_l__BYN = "Price_1_liter_in_byn";
	public static final String cost_l__USD = "Price_1_liter_in_USD";
	public static final String litrov_zalito = "Zapravleno_litrov";
	public static final String cena_USD = "Cena_zapravki_USD";
	public static final String cena_BYN = "Cena_zapravki_BYN";
	private static final String DATABASE_CREATE_SCRIPT = "create table "
	            + DATABASE_TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement, " 
	            + date_COLUMN + " text not null, " 
	            + kurs_USD + " text not null, " 
	            + cost_l__BYN + " text not null, " 
	            + cost_l__USD + " text not null, " 
	            + litrov_zalito + " text not null, " 
	            + cena_USD + " text not null, " 
	            + cena_BYN + " text not null);"; 
	           
	
	
	DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE_SCRIPT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}


}


