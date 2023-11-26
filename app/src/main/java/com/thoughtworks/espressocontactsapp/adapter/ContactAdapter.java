package com.thoughtworks.espressocontactsapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.espressocontactsapp.R;
import com.thoughtworks.espressocontactsapp.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contacts;
    private ContactClickListener listener;

    public ContactAdapter(List<Contact> contacts, ContactClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView textContactName;
        private ImageView imgEditContact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textContactName = itemView.findViewById(R.id.textContactName);
            imgEditContact = itemView.findViewById(R.id.imgEditContact);

            imgEditContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Contact contact = contacts.get(position);
                        listener.onEditClick(contact);
                    }
                }
            });
        }

        public void bind(Contact contact) {
            String fullName = contact.getFirstName() + " " + contact.getLastName();
            textContactName.setText(fullName);
        }
    }

    public interface ContactClickListener {
        void onEditClick(Contact contact);
    }
}


