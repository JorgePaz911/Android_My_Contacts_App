package com.example.mycontacts.model.services;

import android.support.test.InstrumentationRegistry;

import com.example.mycontacts.model.domain.Contact;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ContactSvcSQLiteImplTest {
    private Contact contact1;
    private Contact contact2;
    private Contact contact3;
    private Contact temporaryContact;
    private ContactSvcSQLiteImpl contactSvc;

    @Before
    public void setUp(){
        contactSvc = new ContactSvcSQLiteImpl(InstrumentationRegistry.getTargetContext());
        contact1 = new Contact("James Smith", "5555555","smith@smith.com");
        contact2 = new Contact("Annie Little", "9999999","annie@annie.com");
        contact3 = new Contact("Annie Little", "9999998","annie@annie.com");
    }

    /**
     * We add a contact, then get all contacts and
     * save them to an arrayist. We check that arraylist
     * for the contact that was added.
     * We delete contact1 at the end so the test doesn't
     * interfere with other tests that use contact1
     */
    @Test
    public void addContactTrue(){
        contactSvc.create(contact1);
        ArrayList<Contact> contactList = contactSvc.getAllContacts();
        assertTrue(contactList.contains(contact1));
        contactSvc.delete(contact1);
    }

    /**
     * Add contact1, get all contacts into arraylist,
     * assert that contact2 is not in the database,
     * delete contact1 to not interfere with other tests.
     */
    @Test
    public void addContactFalse(){
        contactSvc.create(contact1);
        ArrayList<Contact> contactList = contactSvc.getAllContacts();
        assertFalse(contactList.contains(contact2));
        contactSvc.delete(contact1);
    }

    /**
     * Create and immediately delete a contact.
     * assert that the contact is not in the database.
     */
    @Test
    public void deleteTrue(){
        contactSvc.create(contact1);
        contactSvc.delete(contact1);
        ArrayList<Contact> contactList = contactSvc.getAllContacts();
        assertFalse(contactList.contains(contact1));
    }

    /**
     * add contact1 and contact2. Get all contacts and
     * assert that contact1 and 2 exist in the database.
     * assert that contact3 does not exist in the database.
     */
    @Test
    public void getAllContactsTest(){
        contactSvc.create(contact1);
        contactSvc.create(contact2);

        ArrayList<Contact> contactList = contactSvc.getAllContacts();
        assertTrue(contactList.contains(contact1));
        assertTrue(contactList.contains(contact2));
        assertFalse(contactList.contains(contact3));

        contactSvc.delete(contact1);
        contactSvc.delete(contact2);
    }

    /**
     * create contact1, modify it with contact2 state.
     * assert that contact2 is in the database
     * assert that contact1 is not in the database
     */
    @Test
    public void modifyTest(){
        contactSvc.create(contact1);
        contactSvc.modify(contact1,contact2);
        ArrayList<Contact> contactList = contactSvc.getAllContacts();
        assertTrue(contactList.contains(contact2));
        assertFalse(contactList.contains(contact1));
        contactSvc.delete(contact2);
    }

}