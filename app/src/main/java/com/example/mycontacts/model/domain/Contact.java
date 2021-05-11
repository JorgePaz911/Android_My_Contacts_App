package com.example.mycontacts.model.domain;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.Objects;

/**
 * Contact class whose objects contain Contact information like Name, Phone, and Email
 */
public class Contact implements Serializable, Comparable<Contact> {

    private static final long serialVersionUID = 1L;
    /**Name of Contact*/
    private String name;
    /**Phone of Contact*/
    private String phone;
    /**Email of Contact*/
    private String email;

    /**
     * Constructor
     * @param name
     * @param phone
     * @param email
     */
    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Gets name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets phone
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    /**
     * Displays state of the object as a String
     */
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /**
     * checks if two contacts have the same state
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return name.equals(contact.name) &&
                phone.equals(contact.phone) &&
                email.equals(contact.email);
    }

    /**
     * Automatically generated by IDE
     * @return
     */
    @SuppressLint("NewApi")
    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email);
    }


    /**
     * Used to sort an arraylist of contacts
     * @param contact
     * @return
     */
    @Override
    public int compareTo(Contact contact) {
        return this.getName().compareTo(contact.getName());
    }
}
