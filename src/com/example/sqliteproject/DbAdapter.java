package com.example.sqliteproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter 
{
	public static final String KEY_ID = "Id";
	public static final String KEY_NAME = "name";
	public static final String TAG ="DbAdapter";
	
	private static final String DATABASE_NAME ="mydb";
	private static final String DATABASE_TABLE = "Contacts";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = 
		"create table Contacts(_id integer primary key autoincrement, "
					+ "name text not null)";
	
	
	private final Context context;
	private DatabaseHelper dbhelper;
	private SQLiteDatabase  db;
	
	
	public  DbAdapter(Context ctx) 
	{
		this.context = ctx;
		dbhelper = new DatabaseHelper(context, null, null, 0);		
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		
		
		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version)
		{
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			try
			{
				db.execSQL(DATABASE_CREATE);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, 
				int newVersion) 
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("Drop table if exists Contacts");
			onCreate(db);
			
		}		
	}
		// -Opens the database-
	public DbAdapter open() throws SQLException
	{
		db = dbhelper.getWritableDatabase();
		return this;
		
	}
		//-Closes the database-
	public void close()
	{
		dbhelper.close();
	}
	
		//  -- Insert a contact into the databse --
	public long insertContact(String name)
	{
		ContentValues initial = new ContentValues();
		initial.put(DATABASE_NAME, name);
		return db.insert(DATABASE_TABLE, null, initial);		
	}
	
		//  ** Delete a particular Contact**
	public boolean deleteContact(long rowid)
	{
		return db.delete(DATABASE_TABLE, KEY_ID +"="+rowid, null) > 0;
	}
	
		//  ** Retreive all the Contacts **
	public Cursor getAllContact()
	{
		return db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_NAME}, 
				null, null, null, null, null);		
		
	}
	
	public Cursor getContact(long rowid) throws SQLException
	{
		
		Cursor cur = db.query(true, DATABASE_TABLE, new String[] {KEY_ID,
				KEY_NAME}, KEY_ID + "=" + rowid, null,
				null, null, null, null);
		if (cur != null) 
		{
			cur.moveToFirst();
		}
		return cur;
		
	}
	
	
	//   ---- Update a contact   ----
	public boolean updateContact(long rowId, String name, String email)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name);		
		return db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	}
}
