package com.example.footballapp.ui.contacts;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.footballapp.databinding.FragmentContactsBinding;
import java.util.List;

public class MyContactsRecyclerViewAdapter extends RecyclerView.Adapter<MyContactsRecyclerViewAdapter.ContactsHolder> {

    Context context;
    private List<Contact> contacts;

    public MyContactsRecyclerViewAdapter(Context context, List<Contact> items) {
        this.context = context;
        contacts = items;
    }

    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ContactsHolder(FragmentContactsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final MyContactsRecyclerViewAdapter.ContactsHolder holder, int position) {
        holder.nameView.setText(contacts.get(position).getName());
        holder.emailView.setText(contacts.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactsHolder extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView emailView;

        public ContactsHolder(FragmentContactsBinding binding) {
            super(binding.getRoot());
            nameView = binding.itemName;
            emailView = binding.itemEmail;
        }
    }

    public void setContacts(List<Contact> items) {
        contacts = items;
        this.notifyDataSetChanged();
    }
}