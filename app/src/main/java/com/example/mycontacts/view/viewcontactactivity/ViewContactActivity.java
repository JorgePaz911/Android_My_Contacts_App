package com.example.mycontacts.view.viewcontactactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mycontacts.R;
import com.example.mycontacts.model.domain.Contact;
import com.example.mycontacts.view.mainactivity.MainActivity;

/**
 * Activity that for viewing a Contact
 * Has its own fragment
 */
public class ViewContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

    }

    /**
     * Method used to send a contact to its fragment ViewContactFragment
     * @return Contact
     */
    public Contact getContactInfo()
    {
        Intent intent = getIntent();
        String name = intent.getStringExtra("CONTACT_NAME");
        String phone = intent.getStringExtra("CONTACT_PHONE");
        String email = intent.getStringExtra("CONTACT_EMAIL");

        return new Contact(name,phone,email);
    }

    /**
     * overrides the back button to call the main activity through intent.
     * This is done so that the listview updates any modifications done
     * to it when returning to the main screen.
     */
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
