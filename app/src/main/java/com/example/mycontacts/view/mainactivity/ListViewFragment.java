package com.example.mycontacts.view.mainactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mycontacts.R;
import com.example.mycontacts.model.domain.Contact;
import com.example.mycontacts.model.services.ContactSvcImpl;
import com.example.mycontacts.model.services.ContactSvcSQLiteImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Fragment attached to MainActivity
 */
public class ListViewFragment extends Fragment
{
    /** Holds the Contact objects*/
    private List<Contact> list =  null;

    /** Holds the Names of the Contact objects*/
    private List<String> listNames = null;

    /** ListView where the Contacts are displayed */
    private ListView listView = null;

    //public ContactSvcImpl contactSvc =  new ContactSvcImpl(this.getContext());
    @Nullable
    @Override
    /**
     * On Creation of this fragment
     * Sets up the ListView and adds the Contact names
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.listview_fragment_layout, container, false);
        ContactSvcSQLiteImpl contactSvc =  new ContactSvcSQLiteImpl(this.getContext());
        list = contactSvc.getAllContacts();
        listNames = new ArrayList<String>();

        /**
         * Creates a list that contains on the names of the contacts.
         * This is used in the listView to only show names.
         */
        for(Contact contact: list)
        {
            listNames.add(contact.getName());
        }

        listView = (ListView) view.findViewById(R.id.list_view);

        adapterModifier(view);

        ImageButton b = (ImageButton) view.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            /**
             * Method executed when Add Contact button is pressed
             */
            public void onClick(View v) {
                addContactActivity(v);
            }
        });

        return view;
    }

    /**
     * Implicit Intent when clicking on Add Contact button
     * @param v
     */
    public void addContactActivity(View v)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_INSERT);
        startActivity(intent);
    }

    /**
     * Implicit Intent when clicking on a Contact in the ListView
     * Sends data through the intent to be used in another activity
     * @param v
     * @param contact
     */
    public void viewContactActivity(View v, Contact contact)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_EDIT);
        intent.putExtra("CONTACT_NAME",contact.getName());
        intent.putExtra("CONTACT_PHONE",contact.getPhone());
        intent.putExtra("CONTACT_EMAIL",contact.getEmail());
        startActivity(intent);
    }

    /**
     * The adapter used to set up the listView
     * the adapter.sort method is used to sort the contacts
     * alphabetically by name.
     * @param view
     */
    public void adapterModifier(View view){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, listNames);
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            /**
             * Method executed when an item from ListView is clicked
             */
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                Collections.sort(list);
                viewContactActivity(v,list.get(position));
            }
        });
    }
}

