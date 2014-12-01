package com.foodrecognitionapp.mobilecomputing;

import java.util.ArrayList;

import com.foodrecognitionapp.mobilecomputing.DatabaseContract.ColorDB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper
{
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "NUTRIENT_DBe.db";
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_NUTRIENT_ENTRIES =
	    "CREATE TABLE " + ColorDB.TABLE_NAME + " (" +
	    ColorDB._ID + " INTEGER PRIMARY KEY," +
	    ColorDB.COLUMN_COLOR + TEXT_TYPE + COMMA_SEP +
	    ColorDB.COLUMN_NUTRIENT + TEXT_TYPE + COMMA_SEP +
	    ColorDB.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
	    ColorDB.COLUMN_NAME_FOOD_GROUP + " TEXT_TYPE" + " )";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + ColorDB.TABLE_NAME;

	public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	//todo pass in color, pull nutrients
	public ArrayList<String> getNutrients( SQLiteDatabase aDb, String color, String foodGroup )
	{
		String theQuery = "SELECT DISTINCT " + ColorDB.COLUMN_NUTRIENT + " FROM " + ColorDB.TABLE_NAME + " WHERE " + ColorDB.COLUMN_COLOR + "= \"" + color + "\"";;
		Cursor c = aDb.rawQuery( theQuery, null );
		ArrayList<String> theNutrients = new ArrayList<String>();
		if (c.moveToFirst()) {
			do {
				theNutrients.add( c.getString(0));
			} while (c.moveToNext());
		}
		return theNutrients;
	}
	
	public long insertNutrient( SQLiteDatabase aDb, String color, String foodgroup, String nutrient, String description  )
	{
		ContentValues values = new ContentValues();
		values.put(ColorDB.COLUMN_COLOR, color);
		values.put(ColorDB.COLUMN_NAME_FOOD_GROUP, foodgroup);
		values.put(ColorDB.COLUMN_NUTRIENT, nutrient);
		values.put(ColorDB.COLUMN_DESCRIPTION, description );
		
		long newRowId;
		newRowId = aDb.insert(
				ColorDB.TABLE_NAME,
				ColorDB.COLUMN_NAME_NULLABLE,
		         values);
		return newRowId;
	}
	
	@Override
	public void onCreate( SQLiteDatabase aDb )
	{
		aDb.execSQL(SQL_DELETE_ENTRIES);
		aDb.execSQL(SQL_CREATE_NUTRIENT_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//no op
	}
}