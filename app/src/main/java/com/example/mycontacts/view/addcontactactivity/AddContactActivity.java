package com.example.mycontacts.view.addcontactactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mycontacts.R;

/**
 * Activity for screen that opens when clicking Add Contact
 * Has its own fragment
 */
public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }
}
