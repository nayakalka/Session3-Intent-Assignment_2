package com.example.android.assignment2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1;
    private String LOG_TAG;
    private TextView textViewContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LOG_TAG = getClass().getSimpleName();

        textViewContact = (TextView) findViewById(R.id.textViewContact);
        Button buttonContact = (Button) findViewById(R.id.buttonContact);
        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Cursor cursor = null;
                try {
                    String phoneNo = null;
                    String name = null;
                    // getData() method will have the Content Uri of the selected contact
                    Uri uri = data.getData();
                    // Query the content uri
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    // column index of the phone number
                    int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    // column index of the contact name
                    int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    phoneNo = cursor.getString(phoneIndex);
                    name = cursor.getString(nameIndex);
                    textViewContact.setText("Phone number : " + phoneNo + "\n" + "Name : " + name);
                    Log.d(LOG_TAG, "Phone number:" + phoneNo);
                    Log.d(LOG_TAG, "Name:" + name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
