package com.example.mycontacts.model.services;

import com.example.mycontacts.model.domain.Contact;

import java.util.ArrayList;

/**
 * Interface class to the ContactSvcImpl class
 * that specifies the methods that must be implemented
 */
public interface IContactSvc {

    /** creates a new contact */
    public void create (Contact contact);

    /** deletes an existing contact */
    public void delete (Contact contact);

    /** modifies an existing contact */
    public void modify (Contact contactOld, Contact contactNew);

    /** Returns all contacts found on the saved file */
    public ArrayList<Contact> getAllContacts();

}
