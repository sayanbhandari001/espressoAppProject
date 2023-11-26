package com.thoughtworks.espressocontactsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.espressocontactsapp.adapter.ContactAdapter;
import com.thoughtworks.espressocontactsapp.db.DatabaseHelper;
import com.thoughtworks.espressocontactsapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AddContactDialog.AddContactListener,
        ContactAdapter.ContactClickListener,
        UpdateContactDialog.UpdateContactListener {

    private RecyclerView recyclerView;
    private TextView noContactsText;
    private Button addContactButton;

    private List<Contact> contacts;
    private DatabaseHelper databaseHelper;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewContacts);
        noContactsText = findViewById(R.id.textNoContacts);
        addContactButton = findViewById(R.id.btnAddContact);

        databaseHelper = new DatabaseHelper(this);
        contacts = new ArrayList<>();
        contactAdapter = new ContactAdapter(contacts, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactAdapter);

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddContactDialog();
            }
        });

        loadContacts();
    }

    private void loadContacts() {
        contacts.clear();
        contacts.addAll(databaseHelper.getAllContacts());

        if (contacts.isEmpty()) {
            noContactsText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noContactsText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        contactAdapter.notifyDataSetChanged();
    }

    private void showAddContactDialog() {
        AddContactDialog addContactDialog = new AddContactDialog(this, this);
        addContactDialog.show();
    }

    @Override
    public void onContactCreated(Contact contact) {
        long contactId = databaseHelper.addContact(contact);
        contact.setId(contactId);
        contacts.add(contact);
        contactAdapter.notifyDataSetChanged();

        if (contacts.isEmpty()) {
            noContactsText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noContactsText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            showToast("Contact created successfully");
        }
    }

    @Override
    public void onEditClick(Contact contact) {
        showUpdateContactDialog(contact);
    }

    private void showUpdateContactDialog(Contact contact) {
        UpdateContactDialog updateContactDialog = new UpdateContactDialog(this, contact, this);
        updateContactDialog.show();
    }

    @Override
    public void onContactUpdated(Contact contact) {
        int index = findContactIndex(contact.getId());
        databaseHelper.updateContact(contact);
        if (index != -1) {
            contacts.set(index, contact);
            contactAdapter.notifyItemChanged(index);
            showToast("Contact updated successfully");
        }
    }

    @Override
    public void onContactDeleted(Contact contact) {
        int index = findContactIndex(contact.getId());
        databaseHelper.deleteContact(contact.getId());
        if (index != -1) {
            contacts.remove(index);
            contactAdapter.notifyItemRemoved(index);
            showToast("Contact deleted successfully");

            if (contacts.isEmpty()) {
                noContactsText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    private int findContactIndex(long contactId) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == contactId) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
