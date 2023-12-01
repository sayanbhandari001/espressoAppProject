package com.thoughtworks.espressocontactsapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thoughtworks.espressocontactsapp.model.Contact;

public class AddContactDialog extends Dialog {

    private EditText editFirstName, editLastName, editPhoneNumber, editEmail;
    private Button btnCreate, btnCancel;

    private AddContactListener listener;

    public AddContactDialog(Context context, AddContactListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_contact);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editEmail = findViewById(R.id.editEmail);
        btnCreate = findViewById(R.id.btnCreate);
        btnCancel = findViewById(R.id.btnCancel);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editFirstName.getText().toString().trim();
                String lastName = editLastName.getText().toString().trim();
                String phoneNumber = editPhoneNumber.getText().toString().trim();
                String email = editEmail.getText().toString().trim();

                if (validateInput(firstName, lastName, phoneNumber, email)) {
                    Contact newContact = new Contact((System.currentTimeMillis()/1000),firstName, lastName, phoneNumber, email);
                    newContact.setFirstName(firstName);
                    newContact.setLastName(lastName);
                    newContact.setPhoneNumber(phoneNumber);
                    newContact.setEmail(email);

                    listener.onContactCreated(newContact);
                    dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private boolean validateInput(String firstName, String lastName, String phoneNumber, String email) {
        String msg = "";
        boolean returnVal = true;
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phoneNumber)
                || TextUtils.isEmpty(email)) {
            msg = "All fields are mandatory";
            returnVal = false;
        }

        if (!(TextUtils.isEmpty(phoneNumber)) && !isValidPhoneNumber(phoneNumber)) {
            msg = msg + "\nInvalid phone number";
            returnVal = false;
        }

        if(!(TextUtils.isEmpty(email)) && !isValidEmail(email)){
            msg = msg + "\nInvalid email ID";
            returnVal = false;
        }
        if(msg.length() > 0)
            showToast(msg.trim());
        return returnVal;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // For simplicity, you can perform a basic check here
        return phoneNumber.matches("^[7-9][0-9]{9}$");
    }

    private boolean isValidEmail(CharSequence target) {
        return (Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public interface AddContactListener {
        void onContactCreated(Contact contact);
    }
}

