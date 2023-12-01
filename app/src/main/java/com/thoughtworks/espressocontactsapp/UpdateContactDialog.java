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

public class UpdateContactDialog extends Dialog {

    private EditText editFirstName, editLastName, editPhoneNumber, editEmail;
    private Button btnUpdate, btnDelete, btnCancel;

    private Contact contact;
    private UpdateContactListener listener;

    public UpdateContactDialog(Context context, Contact contact, UpdateContactListener listener) {
        super(context);
        this.contact = contact;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_modify_contact);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editEmail = findViewById(R.id.editEmail);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        // Pre-fill the dialog with contact details
        editFirstName.setText(contact.getFirstName());
        editLastName.setText(contact.getLastName());
        editPhoneNumber.setText(contact.getPhoneNumber());
        editEmail.setText(contact.getEmail());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editFirstName.getText().toString().trim();
                String lastName = editLastName.getText().toString().trim();
                String phoneNumber = editPhoneNumber.getText().toString().trim();
                String email = editEmail.getText().toString().trim();

                if (validateInput(firstName, lastName, phoneNumber, email)) {
                    contact.setFirstName(firstName);
                    contact.setLastName(lastName);
                    contact.setPhoneNumber(phoneNumber);
                    contact.setEmail(email);

                    listener.onContactUpdated(contact);
                    dismiss();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onContactDeleted(contact);
                dismiss();
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

    public interface UpdateContactListener {
        void onContactUpdated(Contact contact);
        void onContactDeleted(Contact contact);
    }
}

