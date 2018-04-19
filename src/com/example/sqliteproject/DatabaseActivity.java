package com.example.sqliteproject;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DatabaseActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
		
		final Button b1 = (Button)findViewById(R.id.btInsert);
		final Button b2 = (Button)findViewById(R.id.btSelect);
		final Button b3 = (Button)findViewById(R.id.btSelectAll);
		final Button b4	= (Button)findViewById(R.id.btUpdate);
		final Button b5 =(Button)findViewById(R.id.btUpdate);
		
		final EditText et1 = (EditText)findViewById(R.id.etNumber);
		final EditText et2 = (EditText)findViewById(R.id.etname);
		
		final DbAdapter db = new DbAdapter(DatabaseActivity.this);
		
		//  ------Insert Contact ------
		
		b1.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				db.open();
				db.insertContact(et2.getText().toString());				
				db.close();
				Toast.makeText(getBaseContext(), "Inserted",
					Toast.LENGTH_SHORT).show();
			}
		});
 
		//---Select All contacts---
		b2.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				db.open();
				Cursor c = db.getAllContact();
				if (c.moveToFirst())
				{
					do {
						DisplayContact(c);
					} while (c.moveToNext());
				}
				db.close();
			}
 
			private void DisplayContact(Cursor c)
			{
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(),"id: " + c.getString(0) + 
					"\n" +"Name: " + c.getString(1) + "\n" +
					"Email: " + c.getString(2),
					Toast.LENGTH_LONG).show();
			}
		});
 
		//      ---     Select a contact   ---    
		b3.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				db.open();
				Cursor c = db.getContact(Integer.parseInt
					(et1.getText().toString()));
				if (c.moveToFirst())        
					DisplayContact(c);
				else
					Toast.makeText(getBaseContext(), "No contact found", 
						Toast.LENGTH_LONG).show();
				db.close();
			}
 
			private void DisplayContact(Cursor c) 
			{
				
				Toast.makeText(getBaseContext(),"id: " + c.getString(0) + 
						"\n" +"Name: " + c.getString(1) + "\n" + c.getString(2),
						Toast.LENGTH_LONG).show();
			}
		}) ;
 
		//   ---   Updates a contact   ---       
		b4.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				db.open();
				if (db.updateContact
					(Integer.parseInt(et1.getText().toString()), 
					et2.getText().toString(), null))

					Toast.makeText(getBaseContext(), "Updated Successfully",
						 Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getBaseContext(), "Update failed.", 
							Toast.LENGTH_LONG).show();
				db.close();
			}
		});
		//---delete a contact---  
		b5.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
						
				db.open();
				db.deleteContact(Integer.parseInt(et1.getText().toString()));
				db.close();		
			}
		});
	}
}
