package com.example.mycontacts.view.addcontactactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycontacts.R;
import com.example.mycontacts.model.domain.Contact;
import com.example.mycontacts.model.services.ContactSvcImpl;
import com.example.mycontacts.model.services.ContactSvcSQLiteImpl;
import com.example.mycontacts.view.mainactivity.MainActivity;

/**
 * Fragment attached to AddContactActivity
 */
public class AddContactFragment extends Fragment
{
    /** Name entered by the user into the edittext */
    private EditText name;

    /** phone entered by the user into the edittext */
    private EditText phone;

    /** email entered by the user into the edittext */
    private EditText email;

    /** Button to cancel adding a contact */
    private Button cancel;

    /** Button to confirm contact added */
    private Button save;

    /** Service variable */
    private ContactSvcSQLiteImpl contactSvc;

    /**
     * on creation of the add contact fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.add_contact_fragment_layout, container,false);

        contactSvc =  new ContactSvcSQLiteImpl(this.getContext());
        name = (EditText)view.findViewById(R.id.editText);
        phone = (EditText)view.findViewById(R.id.editText2);
        email = (EditText)view.findViewById(R.id.editText3);
        cancel = (Button)view.findViewById(R.id.button2);
        save = (Button)view.findViewById(R.id.button3);

        cancel.setOnClickListener(new View.OnClickListener() {

            /**
             * When clicking on the cancel button, it returns you to the
             * Main activity through an intent.
             */
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override

            /**
             * When clicking on the save button, it grabs the information typed
             * into the edittext fields by the user and creates a new contact object.
             * Adds the contact object to the save file, clears all of the edittext fields, and
             * shows a toast message confirming the contact was saved.
             * There must be text in the name field
             */
            public void onClick(View view) {
                if(name.getText().toString().matches(""))
                {
                    Toast.makeText(getActivity(), getString(R.string.contact_name_blank), Toast.LENGTH_LONG).show();
                }else{
                    Contact contact = new Contact(name.getText().toString(),
                            phone.getText().toString(),
                            email.getText().toString());
                    contactSvc.create(contact);
                    name.getText().clear();
                    phone.getText().clear();
                    email.getText().clear();
                    Toast.makeText(getActivity(), getString(R.string.contact_saved), Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
