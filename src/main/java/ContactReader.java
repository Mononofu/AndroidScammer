package org.furidamu.androidscammer;

import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ContactReader {
	Context context;

	public ContactReader(Context context) {
		this.context = context;
	}
	public List<String> names() {
		List<String> names = new ArrayList<String>();

		Uri uri = ContactsContract.Contacts.CONTENT_URI;
    String[] projection = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };
    String[] selectionArgs = null;
    String sortOrder = ContactsContract.Contacts.DISPLAY_NAME+ " COLLATE LOCALIZED ASC";
    Cursor cursor = context.getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);

    while (cursor.moveToNext()) {
      String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
      names.add(displayName);
    }

    cursor.close();

    return names;
	}
	public List<List<String>> read() {
		List<List<String>> contacts = new ArrayList<List<String>>();

		ContentResolver cr = context.getContentResolver();

		for(String name : names())
		{

		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
		    "DISPLAY_NAME = '" + name + "'", null, null);
		if (cursor.moveToFirst()) {
		    String contactId =
		        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
		    //
		    //  Get all phone numbers.
		    //
		    Cursor phones = cr.query(Phone.CONTENT_URI, null,
		        Phone.CONTACT_ID + " = " + contactId, null, null);
		    while (phones.moveToNext()) {
		        String number = phones.getString(phones.getColumnIndex(Phone.NUMBER));

		        contacts.add(Arrays.asList(name, number));

		        int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
		        switch (type) {
		            case Phone.TYPE_HOME:
		                // do something with the Home number here...
		                break;
		            case Phone.TYPE_MOBILE:
		                // do something with the Mobile number here...
		                break;
		            case Phone.TYPE_WORK:
		                // do something with the Work number here...
		                break;
		            }
		    }
		    phones.close();
		   }
		   cursor.close();
		 }

		return contacts;
	}
}