package com.example.mycontacts.model.services;

import android.support.test.InstrumentationRegistry;

import com.example.mycontacts.model.domain.Contact;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactSvcImplTest2 {

    private Contact contact1;
    private Contact contact2;
    private Contact contact3;
    private ContactSvcImpl contactSvc;

    @Before
    public void setUp(){
        contactSvc = new ContactSvcImpl(InstrumentationRegistry.getContext());
        contact1 = new Contact("James Smith", "5555555","smith@smith.com");
        contact2 = new Contact("Annie Little", "9999999","annie@annie.com");
        contact3 = new Contact("Annie Little", "9999999","annie@annie.com");
    }

    /**
     * First we save contact1 to the file,
     * then we get contact1 from the file
     * and compare it to the original contact1 variable.
     * We delete contact1 at the end so the test doesn't
     * interfere with other tests that use contact1
     */
    @Test
    public void addContactTrue(){
        contactSvc.create(contact1);
        Contact temporaryContact = contactSvc.getContact(contact1);
        assertTrue(contact1.equals(temporaryContact));
        contactSvc.delete(contact1);
    }

    /**
     *Add contact1 and contact2 to the file,
     * get contact2 from the file and compare it to
     * the contact1 original variable
     * assert that contact1 is not equal to contact2
     */
    @Test
    public void addContactFalse(){
        contactSvc.create(contact1);
        contactSvc.create(contact2);
        Contact temporaryContact = contactSvc.getContact(contact2);
        assertFalse(contact1.equals(temporaryContact));
        contactSvc.delete(contact1);
        contactSvc.delete(contact2);
    }

    /**
     * add contact1 then delete immediately
     * try to get contact 1 from the file
     * assert that contact1 is null and not found in the file.
     */
    @Test
    public void deleteTrue(){
        contactSvc.create(contact1);
        contactSvc.delete(contact1);
        Contact temporaryContact = contactSvc.getContact(contact1);
        assertTrue(temporaryContact == null);
    }

    /**
     * add contact1, don't delete it immediately,
     * get contact1 from the file,
     * assert that the contact we got from the file
     * is not null.
     */
    @Test
    public void deleteFalse(){
        contactSvc.create(contact1);
        Contact temporaryContact = contactSvc.getContact(contact1);
        assertFalse(temporaryContact == null);
        contactSvc.delete(contact1);
    }

    /**
     * Add 3 contacts to the empty file
     * then assert that there are 3 contacts saved to the file.
     */
    @Test
    public void getAllContactsTrue(){
        contactSvc.create(contact1);
        contactSvc.create(contact2);
        contactSvc.create(contact3);
        assertTrue(contactSvc.getAllContacts().size() == 3);
        contactSvc.delete(contact1);
        contactSvc.delete(contact2);
        contactSvc.delete(contact3);
    }

    /**
     * Add contact1 to the file,
     * modify contact1 with attributes of contact2
     * get contact2 from the file and compare it to the
     * original contact2 variable.
     * only delete contact2 at the end because contact1 doesn't exist.
     */
    @Test
    public void modifyTrue(){
        contactSvc.create(contact1);
        contactSvc.modify(contact1,contact2);
        Contact temporaryContact = contactSvc.getContact(contact2);
        assertTrue(temporaryContact.equals(contact2));
        contactSvc.delete(contact2);
    }

    /**
     * Add contact1 to the file,
     * modify contact1 with attributes of contact2
     * get contact2 from the file
     * assert that the contact that is in the file
     * is no longer contact1.
     * Remove contact2 from the file
     */
    @Test
    public void modifyFalse(){
        contactSvc.create(contact1);
        contactSvc.modify(contact1,contact2);
        Contact temporaryContact = contactSvc.getContact(contact2);
        assertFalse(temporaryContact.equals(contact1));
        contactSvc.delete(contact2);
    }


}