package com.thoughtworks.espressocontactsapp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thoughtworks.espressocontactsapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_EMAIL = "email";

    private static final String CREATE_TABLE_CONTACTS =
            "CREATE TABLE " + TABLE_CONTACTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_FIRST_NAME + " TEXT," +
                    COLUMN_LAST_NAME + " TEXT," +
                    COLUMN_GENDER + " TEXT," +
                    COLUMN_PHONE_NUMBER + " TEXT," +
                    COLUMN_EMAIL + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    // CREATE (Add a new contact to the database)
    public long addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, contact.getFirstName());
        values.put(COLUMN_LAST_NAME, contact.getLastName());
        values.put(COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
        values.put(COLUMN_EMAIL, contact.getEmail());

        long id = db.insert(TABLE_CONTACTS, null, values);
        db.close();
        return id;
    }

    // Get a specific contact based on ID
    @SuppressLint("Range")
    public Contact getContact(long contactId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " WHERE " + COLUMN_ID + " = " + contactId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        Contact contact = null;

        if (cursor.moveToFirst()) {
            contact = new Contact();
            contact.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            contact.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
            contact.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
            contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
            contact.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
        }

        cursor.close();
        db.close();

        return contact;
    }


    // READ (Retrieve all contacts from the database)
    @SuppressLint("Range")
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                Contact contact = new Contact(id, firstName, lastName, phoneNumber, email);
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contactList;
    }

    // UPDATE (Update an existing contact in the database)
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, contact.getFirstName());
        values.put(COLUMN_LAST_NAME, contact.getLastName());
        values.put(COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
        values.put(COLUMN_EMAIL, contact.getEmail());

        int rowsAffected = db.update(
                TABLE_CONTACTS,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())}
        );

        db.close();
        return rowsAffected;
    }

    // DELETE (Delete a contact from the database)
    public void deleteContact(long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID + " = ?", new String[]{String.valueOf(contactId)});
        db.close();
    }
}

