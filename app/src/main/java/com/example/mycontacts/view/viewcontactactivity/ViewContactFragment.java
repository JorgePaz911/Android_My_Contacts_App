package com.example.mycontacts.view.viewcontactactivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontacts.R;
import com.example.mycontacts.model.domain.Contact;
import com.example.mycontacts.model.services.ContactSvcImpl;
import com.example.mycontacts.model.services.ContactSvcSQLiteImpl;
import com.example.mycontacts.view.mainactivity.MainActivity;

/**
 * Fragment attached to ViewContactActivity
 */
public class ViewContactFragment extends Fragment
{
    /** service initialization */
    private ContactSvcSQLiteImpl contactSvc;

    /** contact variable used to display the contact information */
    private Contact contact;

    /** variable used to check if the call permission is granted */
    private static final int REQUEST_CALL = 1;

    /** variable used to make the phone call */
    private TextView phoneTextView;

    @Nullable
    @Override
    /**
     * Gets the Contact from its activity and displays the contact info in the TextView fields
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        contactSvc = new ContactSvcSQLiteImpl(this.getContext());
        View view  = inflater.inflate(R.layout.view_contact_fragment_layout,container,false);

        /**
         * receives the contact info from its activity
         */
        ViewContactActivity activity = (ViewContactActivity)getActivity();
        contact = activity.getContactInfo();

        /**
         * contact name, phone, and email are displayed in textviews
         */
        final TextView tv =(TextView)view.findViewById(R.id.textView);
        tv.setText(contact.getName());
        final TextView tv2 =(TextView)view.findViewById(R.id.textView2);
        tv2.setText(contact.getPhone());
        phoneTextView = tv2;
        final TextView tv3 =(TextView)view.findViewById(R.id.textView3);
        tv3.setText(contact.getEmail());

        Button deleteContact = (Button)view.findViewById(R.id.button4);
        deleteContact.setOnClickListener(new View.OnClickListener() {

            /**
             * when the delete contact button is pressed,
             * it deletes the contact, sends the user to the main
             * activity, and displays a toast message confirming the
             * contact was deleted.
             * @param view
             */
            @Override
            public void onClick(View view) {
                contactSvc.delete(contact);
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),getString(R.string.contact_deleted),Toast.LENGTH_LONG).show();
            }
        });

        Button modifyContact = (Button)view.findViewById(R.id.button5);
        modifyContact.setOnClickListener(new View.OnClickListener() {
            /**
             * When the "modify contact" button is pressed,
             * a new dialog is created with 3 editTexts and 2 buttons
             * @param view
             */
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View mView = getLayoutInflater().inflate(R.layout.dialog_modify_contact_layout,null);
                final EditText mName = (EditText)mView.findViewById(R.id.editText);
                final EditText mPhone = (EditText)mView.findViewById(R.id.editText2);
                final EditText mEmail = (EditText)mView.findViewById(R.id.editText3);
                mName.setText(contact.getName());
                mPhone.setText(contact.getPhone());
                mEmail.setText(contact.getEmail());
                Button mCancel = (Button)mView.findViewById(R.id.button2);
                Button mSave = (Button)mView.findViewById(R.id.button3);

                builder.setView(mView);
                final AlertDialog dialog = builder.create();

                mCancel.setOnClickListener(new View.OnClickListener() {
                    /**
                     * When the cancel button is pressed,
                     * we dismiss the dialog
                     * @param view
                     */
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                mSave.setOnClickListener(new View.OnClickListener() {
                    /**
                     * When the save button is pressed,
                     * it creates a new contact with the information entered by the user
                     * it uses the past contact and the new contact to make the modification
                     * the name field cannot be blank
                     * @param view
                     */
                    @Override
                    public void onClick(View view) {
                        if(mName.getText().toString().matches("")){
                            Toast.makeText(getActivity(), getString(R.string.contact_name_blank), Toast.LENGTH_LONG).show();
                        }else {
                            Contact newContact = new Contact(mName.getText().toString(), mPhone.getText().toString(), mEmail.getText().toString());
                            contactSvc.modify(contact, newContact);

                            tv.setText(newContact.getName());
                            tv2.setText(newContact.getPhone());
                            phoneTextView = tv2;
                            tv3.setText(newContact.getEmail());
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });

        /**
         * The button used to call the contact
         * calls the makePhoneCall method.
         */
        ImageButton callBtn = (ImageButton)view.findViewById(R.id.button8);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        /**
         * When the text button is pressed, we search for another through an intent
         * that can send text messages and start that app.
         *
         */
        ImageButton textBtn = (ImageButton)view.findViewById(R.id.button6);
        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneTextView.getText().toString(), null));
                startActivity(intent);
            }
        });


        return view;
    }

    /**
     * Checks if the app has permission to make a phone call.
     * If it doesn't, it asks the user in real time for permission.
     * If it does it makes the phone call.
     */
    private void makePhoneCall(){
        String number = phoneTextView.getText().toString();
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            startActivity(callIntent);
        }
    }

    /**
     * This method runs when the call permission is either granted or denied by the user.
     * If the permission is accepted, we call makePhoneCall method again to make the call
     * without having to press the call button again,
     * if not we show a toast message saying the permission was denied.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            } else{
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
