package com.example.mycontacts.model.services;

import android.content.Context;

import com.example.mycontacts.model.domain.Contact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This class provides the CRUD services for contacts
 */
public class ContactSvcImpl implements IContactSvc {

    /** ArrayList that holds all of the contacts */
    private ArrayList<Contact> contacts =  new ArrayList<Contact>();

    /** Context used in class constructor */
    private Context appContext;

    /**
     * Constructor that receives the context and initiates the readFile method
     * @param context
     */
    public ContactSvcImpl(Context context){
        appContext = context;
        readFile();
    }

    /**
     * Reads from file RegisteredContacts.txt and saves all of the
     * objects found, into the contacts arraylist variable.
     */
    private void readFile(){
        File file = new File("RegisteredContacts.txt");
        ObjectInputStream input = null;
        try {
                input = new ObjectInputStream(appContext.openFileInput(file.getName()));
                contacts = (ArrayList<Contact>) input.readObject();

        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }finally{
            try {
                if(input != null)
                    input.close();

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes the full contacts arraylist to the file RegisteredContacts.txt
     */
    private void writeFile(){
        ObjectOutputStream output = null;
        File file = new File("RegisteredContacts.txt");
        try {
                output = new ObjectOutputStream(appContext.openFileOutput(file.getName(),Context.MODE_PRIVATE));
                output.writeObject(contacts);
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            try {
                if(output != null) {
                    output.flush();
                    output.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Receives a contact as a parameter then adds
     * that individual contact to the contacts arraylist.
     * Writes the full contacts arraylist onto the file.
     * @param contact
     */
    public void create(Contact contact) {
        contacts.add(contact);
        writeFile();
    }

    /**
     * Receives a contact as a parameter then searches
     * for it in the contacts arraylist and removes it.
     * Writes the full contacts arraylist onto the file.
     * @param contact
     */
    public void delete(Contact contact) {
        contacts.remove(contact);
        writeFile();
    }

    /**
     * Returns all of the contacts saved on the file
     * @return
     */
    public ArrayList<Contact> getAllContacts() {
        return contacts;
    }

    /**
     * Receives an old contact and a new contact as parameters.
     * First removes the old contact and then adds the new contact which
     * serves the function of modification.
     * Writes the full contacts arraylist onto the file.
     * @param contactOld
     * @param contactNew
     */
    public void modify(Contact contactOld, Contact contactNew) {
        contacts.remove(contactOld);
        contacts.add(contactNew);
        writeFile();
    }

    /**
     * receives a contact, checks if that contact is in the contacts
     * arraylist. iof it is it returns the contact, iof it isnt it returns null.
     * @param contact
     * @return
     */
    public Contact getContact(Contact contact){
        if(contacts.contains(contact) == true){
            return contact;
        }else
            return null;
    }

    /**
     * Testing method used to remove all contacts from the file.
     */
    public void removeAllContacts(){
        contacts.clear();
        writeFile();
    }
}
