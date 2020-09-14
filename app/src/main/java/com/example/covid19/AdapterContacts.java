package com.example.covid19;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class AdapterContacts extends RecyclerView.Adapter<AdapterContacts.Myviewholder> implements Filterable {
    private ArrayList<Map> fulllist, contacts_details_list = new ArrayList<>();


    public AdapterContacts(ArrayList<Map> contactdetails) {
        for (int i = 0; i < contactdetails.size(); i++) {
            contacts_details_list.add(contactdetails.get(i));
        }
        fulllist = new ArrayList<>(contacts_details_list);
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_list_items, viewGroup, false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        Map client = contacts_details_list.get(i);
        if (client.get("loc") != null)
            myviewholder.client_name.setText(client.get("loc").toString());
        myviewholder.clientwise_assignments_sent.setText(client.get("number").toString());
    }

    @Override
    public int getItemCount() {
        return contacts_details_list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public TextView client_name;
        public TextView clientwise_assignments_sent;
//        private TextView clientwise_sent_msgs_nosms;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            client_name = itemView.findViewById(R.id.client_name);
            clientwise_assignments_sent = itemView.findViewById(R.id.clientwise_assignment_sent);


        }
    }

    @Override
    public Filter getFilter() {
        return studentfilter;
    }

    private Filter studentfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Map> filteredlist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredlist.addAll(fulllist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Map student : fulllist) {
                    if (student.get("loc").toString().toLowerCase().contains(filterPattern))
                        filteredlist.add(student);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contacts_details_list.clear();
            contacts_details_list.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
