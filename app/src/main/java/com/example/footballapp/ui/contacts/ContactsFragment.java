package com.example.footballapp.ui.contacts;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.footballapp.databinding.FragmentContactsListBinding;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
    private FragmentContactsListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.contactslist.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Contact> contacts = new ArrayList<>();
        MyContactsRecyclerViewAdapter adapter = new MyContactsRecyclerViewAdapter(requireContext(), contacts);
        binding.contactslist.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            contacts = getContactsByEmailDomain("knu.ua");
            adapter.setContacts(contacts);
        }
    }

    private List<Contact> getContactsByEmailDomain(String domain) {
        List<Contact> contacts = new ArrayList<>();

        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Email.ADDRESS
        };

        String selection = "UPPER(" + ContactsContract.CommonDataKinds.Email.ADDRESS + ") LIKE ?";
        String[] selectionArgs = { "%" + domain.toUpperCase() };

        Cursor cursor = requireContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );

        if(cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                contact.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS)));
                contacts.add(contact);
            } while(cursor.moveToNext());
        }
        return contacts;
    }
}