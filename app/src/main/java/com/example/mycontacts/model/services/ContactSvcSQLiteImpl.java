package com.example.mycontacts.model.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mycontacts.model.domain.Contact;

import java.util.ArrayList;

/**
 * Service class that implements the CRUD methods for
 * contacts using the SQLite database
 */
public class ContactSvcSQLiteImpl extends SQLiteOpenHelper implements IContactSvc
{
    /** database declaration */
    private static final String DBNAME = "contacts.db";

    /** database version */
    private static final int DBVERSION = 1;

    /**
     * creating the database table with the
     * columns primary key, name, phone, and email
     */
    private String createContactTable = "create table contact(id integer primary key autoincrement, " +
                                        "name text not null, phone text, email text)";

    /**
     * class constructor
     * @param context
     */
    public ContactSvcSQLiteImpl(Context context){
        super (context, DBNAME, null, DBVERSION);
    }

    /**
     * Method used to initially create the database
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createContactTable);
    }

    /**
     * Method used to update the database schema
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(sqLiteDatabase);
    }

    /**
     * receives a contact as a parameter,
     * adds the values of the contact for the row,
     * then inserts the row into the database.
     * @param contact
     */
    @Override
    public void create(Contact contact) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",contact.getName());
        values.put("phone",contact.getPhone());
        values.put("email",contact.getEmail());

        long rowIdOfInsertedRecord = sqLiteDatabase.insert("contact", null, values);
        sqLiteDatabase.close();

    }

    /**
     * receives a contact as a parameter,
     * call the delete method on the database specifying the table
     * that will be manipulated, and exactly which part of the database to manipulate.
     * @param contact
     */
    @Override
    public void delete(Contact contact) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int rowsDeleted = sqLiteDatabase.delete("contact","name = ? AND phone = ? AND email = ?",
                new String[]{contact.getName(), contact.getPhone(), contact.getEmail()});
        sqLiteDatabase.close();
    }

    /**
     * receives a new contact and old contact.
     * Saves the values of the new contact for the row,
     * calls the update method and replaces the old contact
     * with the new contact
     * @param contactOld
     * @param contactNew
     */
    @Override
    public void modify(Contact contactOld, Contact contactNew) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contactNew.getName());
        values.put("phone", contactNew.getPhone());
        values.put("email", contactNew.getEmail());
        int numberOfRowsUpdated = sqLiteDatabase.update("contact", values, "name = ? AND phone = ? AND email = ?",
                new String[]{contactOld.getName(), contactOld.getPhone(), contactOld.getEmail()});
    }

    /**
     * positions the cursor at the very first row,
     * gets the contact positioned there,
     * adds it to an arraylist of contacts
     * moves to the next cursor and
     * continues adding contacts to the arraylist until there are no
     * contacts left.
     * Returns the full list of contacts.
     *
     * @return
     */
    @Override
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor =  sqLiteDatabase.query("contact", new String[]{"id", "name", "phone", "email"},
                null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Contact contact = getContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }

    /**
     * gets the contact that is positioned at a specific cursor that is
     * received as a parameter. Creates a new contact with the contents that
     * are in column 1, 2, and 3 on the row of the cursor.
     * returns the contact.
     * @param cursor
     * @return
     */
    private Contact getContact(Cursor cursor){
        Contact contact = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return contact;
    }
}
