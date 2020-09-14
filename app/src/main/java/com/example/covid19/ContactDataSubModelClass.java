package com.example.covid19;

public class ContactDataSubModelClass {
    ContactSubModelClass2 contacts;

    public ContactDataSubModelClass(ContactSubModelClass2 contacts) {
        this.contacts = contacts;
    }

    public ContactSubModelClass2 getContacts() {
        return contacts;
    }

    public void setContacts(ContactSubModelClass2 contacts) {
        this.contacts = contacts;
    }
}
