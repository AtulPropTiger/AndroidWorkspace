package com.alarmdb;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "AlarmTestDb16";
	public static final String TABLE_ALARMS = "alarms";
	public static final String KEY_ALARM = "alarm"; //alarm,hour, minute, length all ints, the rest booleans
	public static final String KEY_HOUR = "hour";
	public static final String KEY_MINUTE = "minute";
	public static final String KEY_LENGTH = "length";
	public static final String KEY_MONDAY = "monday";
	public static final String KEY_TUESDAY = "tuesday";
	public static final String KEY_WEDNESDAY = "wednesday";
	public static final String KEY_THURSDAY = "thursday";
	public static final String KEY_FRIDAY = "friday";
	public static final String KEY_SATURDAY = "saturday";
	public static final String KEY_SUNDAY = "sunday";
	public static final String KEY_ACTIVE = "active";
	public Boolean dbCreateCalled = false;
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		//db = this.getWritableDatabase();
		//Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
		//if (!c.moveToFirst()) { // if database has no table lets create one
			String CREATE_ALARM_TABLE = "CREATE TABLE " + TABLE_ALARMS + "(" + KEY_ALARM + " INTEGER," 
					+ KEY_HOUR  + " INTEGER," + KEY_MINUTE + " INTEGER," + KEY_LENGTH  + " INTEGER," + KEY_MONDAY + 
					" BOOLEAN," + KEY_TUESDAY + " BOOLEAN," + KEY_WEDNESDAY + " BOOLEAN," + KEY_THURSDAY + 
					" BOOLEAN," + KEY_FRIDAY + " BOOLEAN," + KEY_SATURDAY + " BOOLEAN," + KEY_SUNDAY + " BOOLEAN," + 
					KEY_ACTIVE + " BOOLEAN" + ")";
			db.execSQL(CREATE_ALARM_TABLE);
			dbCreateCalled = true;
		//}
	}

	public Boolean dbCreate(){
		return dbCreateCalled;
	}
	
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		
		//I HID THIS  db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		//
		//
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */////

	// Adding new contact
	Boolean addAlarmRow(ContentValues values) {
		SQLiteDatabase db = this.getWritableDatabase();
		int alarmNumber = values.getAsInteger(KEY_ALARM);
		String tableCheck = KEY_ALARM + Integer.toString(alarmNumber);
		Boolean rowExists = false;
		if ( rowExists(alarmNumber ) ){ //~RT 04-12-2014 02:39 PM , removed the alarmNumber + 1 , just alarmNumber now
			db.update(TABLE_ALARMS, values, KEY_ALARM +"="+ Integer.toString(alarmNumber) , null);
			rowExists  = true;
		}
		else{
			db.insert(TABLE_ALARMS, null, values);
		}
		db.close(); 
		return rowExists;
	}

	/*
	public Boolean rowExists(int alarmNumber) throws OperationCanceledException {
		String dbStr = "";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT alarm FROM alarms", null);
		Boolean returnVal = cursor.moveToPosition(alarmNumber);
		cursor.close();
		return returnVal;
	}*/
	
	public Boolean rowExists(int alarmNumber) throws OperationCanceledException {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor tempCursor = db.rawQuery("SELECT " + this.KEY_ALARM +  " FROM alarms", null);
		if (tempCursor.moveToFirst()){
			while(!tempCursor.isAfterLast()){
				if(tempCursor.getInt(0) ==  alarmNumber){
					return true;
				}
				tempCursor.moveToNext();
			}
		}
		tempCursor.close();
		return false;
	}
	
	public String readDatabase(){
		String dbStr = "";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM alarms", null);
		int count = 0;
				    while ( !cursor.isAfterLast() ) {
				        ///Toast.makeText(, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
				    	Log.i("Cursor c", "table columns  as name = " + cursor.getColumnName(0));
				    	String[] arrayCol = cursor.getColumnNames();
				    	String tempor = "";
				    	Log.i(" array col length", " is arraycol.lenth casuing it?");
				    	for (int i = 0; i < arrayCol.length; i++){
				    		Log.i("array col in for", "in readdatabase for loop , i = " + i);
				    		//if ( i <=3 ){
				    			tempor+= arrayCol[i];
				    			Log.i("array col in for", "column name in for loop = " + arrayCol[i]);
				    			Cursor tempCursor = db.rawQuery("SELECT " + arrayCol[i] +  " FROM alarms", null);
				    			if (tempCursor.moveToPosition(count)){
					    			tempor+= ": " + tempCursor.getInt(0) +  " \n";
					    			Log.i("array col in for", "column INT in for loop = " + tempCursor.getInt(0));
				    			}
				    			tempCursor.close();
				    		//}
				    		//else{
				    			//tempor+= arrayCol[i] + ": Boolean ";
				    		//}
				    	}
				    	
				    	Log.i("tempor ", "\n " + tempor);
				    	dbStr+= " " + "[ " + tempor + " ]";
				        cursor.moveToNext();
				       count++;
				   }
				    Log.i("while loop readDatabase", "while loop in read database looped " + count + " times");
		cursor.close();
		return dbStr;
	}

	public String readTable(int alarmNumber){ // idk why this is called read table, change to row
		//if (rowExists(alarmNumber)){
			String dbStr = "";
			SQLiteDatabase db = this.getReadableDatabase();
			//Cursor cursor = db.rawQuery("SELECT * FROM alarms", null);
			int count = 0; //move til you find the correct row, equal to the alarm number
			Cursor tempCursor = db.rawQuery("SELECT " + this.KEY_ALARM +  " FROM alarms", null);
			if (tempCursor.moveToFirst()){
				while(!tempCursor.isAfterLast()){
					if(tempCursor.getInt(0) ==  alarmNumber){
						Cursor cursor = db.rawQuery("SELECT * FROM alarms", null);
						String[] arrayCol = cursor.getColumnNames();
						String tempor = "";//
						if (cursor.moveToPosition(count)){
							for (int i = 0; i < arrayCol.length; i++){
								tempor+= arrayCol[i] + ": " + cursor.getInt(i) +"\n "; 
								//cursor.moveToNext();
							}
							return " " + "[ " + tempor + " ]";
						}
						cursor.close();
					}
					tempCursor.moveToNext();
					count++;
				}
			}
			tempCursor.close();
			return " there is no alarm: " + alarmNumber;
		//}
		//return " there is no alarm: " + alarmNumber; //else
	}
	
	
	// make this return a dictionary object
	public ContentValues getRowContent(int alarmNumber){
		//if (rowExists(alarmNumber)){
			ContentValues cvals = new ContentValues();
			String dbStr = "";
			SQLiteDatabase db = this.getReadableDatabase();
			//Cursor cursor = db.rawQuery("SELECT * FROM alarms", null);
			int count = 0; //move til you find the correct row, equal to the alarm number
			Cursor tempCursor = db.rawQuery("SELECT " + this.KEY_ALARM +  " FROM alarms", null);
			if (tempCursor.moveToFirst()){
				while(!tempCursor.isAfterLast()){
					if(tempCursor.getInt(0) ==  alarmNumber){
						Cursor cursor = db.rawQuery("SELECT * FROM alarms", null);
						String[] arrayCol = cursor.getColumnNames();
						String tempor = "";//
						if (cursor.moveToPosition(count)){
							for (int i = 0; i < arrayCol.length; i++){
								tempor+= arrayCol[i] + ": " + cursor.getInt(i) +"\n ";
								cvals.put(arrayCol[i], cursor.getInt(i));
								//cursor.moveToNext();
							}
							return cvals;
						}
						cursor.close();
					}
					tempCursor.moveToNext();
					count++;
				}
			}
			tempCursor.close();
			return cvals; // need to either break or displpay  toast saying row empty
		//}
		//return " there is no alarm: " + alarmNumber; //else
	}
	
	
	public int getAlarmThatShouldBePlaying(double currentTime, String day){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM alarms", null);
		int count = 0;
		while ( !cursor.isAfterLast() ) {
			Cursor tempCursor = db.rawQuery("SELECT " + this.KEY_ACTIVE + "," + day+ ","+ this.KEY_HOUR + "," + this.KEY_MINUTE+ "," +  
					this.KEY_LENGTH + "," + this.KEY_ALARM + " FROM alarms", null);
			if (tempCursor.moveToPosition(count)){
				if (tempCursor.getInt(0) == 1 && tempCursor.getInt(1) == 1){
					//alarm is active on current day, otherwise no reason to get hours, minutes, and length
			        double rowHour = tempCursor.getInt(2), rowMinute = tempCursor.getInt(3), rowLength = tempCursor.getInt(4);
			        Log.i("rowHour", "rowHour = " + rowHour + " rowMinute = " + rowMinute);
					double alarmStart = rowHour + (rowMinute/100);
					double alarmEnd = alarmStart + (rowLength/100);
					Log.i("ALARMSTART alarm END", "alarmStart in getalarmshoyldbeplaying = " + alarmStart + " alarmEnd = "+alarmEnd 
							+ " currenTime = " + currentTime);
					if (alarmStart <= currentTime && currentTime <= alarmEnd){
						cursor.close();
						return tempCursor.getInt(5); // return alarm number that should be playing , ie: current time is 9:20, 
						//alarm 1 has an hour of 9 and minute of 20 and length 0f 10, so return 1.
					}
				} 
			}
			tempCursor.close();
			cursor.moveToNext();
			count++;
		}
		cursor.close();
		return -1; // there is no alarm that should be playing right now
	}
	

}


/*
SQLiteDatabase db = this.getWritableDatabase();
//Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
Cursor c = db.rawQuery("SELECT alarm from alarms where alarm =" + Integer.toString(alarmNumber), null);
if (c.moveToFirst()) {
	String tableCheck = KEY_ALARM + Integer.toString(alarmNumber);
    while ( !c.isAfterLast() ) {
        //Toast.makeText(, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
        if (c.getString(0) == tableCheck){return true;}
        c.moveToNext();
    }
}
return false; // no table name matched the new alarm we want to add, ie: rowExists(2) , couldn't find alarm2 table so return false

SQLiteDatabase db = this.getWritableDatabase();
Cursor c = db.rawQuery("SELECT alarm from alarms where alarm =" + Integer.toString(alarmNumber), null);
return c.moveToFirst(); //returns false if empty, true if not
}
*/

/*
void addAlarmRow(ContentValues values) {
SQLiteDatabase db = this.getWritableDatabase();
int alarmNumber = values.getAsInteger(KEY_ALARM);
String tableCheck = KEY_ALARM + Integer.toString(alarmNumber);
if ( rowExists(alarmNumber) ){
	db.update(tableCheck, values, null, null);
}
else{
	db.insert(TABLE_ALARMS, null, values);
}
db.close(); 
}
*/