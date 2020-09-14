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

public class AdapterColleges extends RecyclerView.Adapter<AdapterColleges.Myviewholder> implements Filterable {
    private ArrayList<Map> fulllist, college_list = new ArrayList<>();


    public AdapterColleges(ArrayList<Map> clientdetails) {
        for (int i = 0; i < clientdetails.size(); i++) {
            if (clientdetails.get(i).get("name") != null) college_list.add(clientdetails.get(i));
        }
        fulllist = new ArrayList<>(college_list);
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.college_list_items, viewGroup, false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        Map college = college_list.get(i);
        myviewholder.college_name.setText(college.get("name").toString());
        myviewholder.state.setText(college.get("state").toString());
        myviewholder.city.setText(college.get("city").toString());
        myviewholder.ownership.setText(college.get("ownership").toString());
        myviewholder.hospitalBeds.setText(college.get("hospitalBeds").toString());
        myviewholder.admissionCapacity.setText(college.get("admissionCapacity").toString());

    }

    @Override
    public int getItemCount() {
        return college_list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public TextView college_name;
        public TextView city, state, ownership, admissionCapacity, hospitalBeds;
//        private TextView clientwise_sent_msgs_nosms;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            college_name = itemView.findViewById(R.id.client_name);
            city = itemView.findViewById(R.id.city);
            state = itemView.findViewById(R.id.state);
            ownership = itemView.findViewById(R.id.ownership);
            admissionCapacity = itemView.findViewById(R.id.capacity);
            hospitalBeds = itemView.findViewById(R.id.beds);

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
                    if (student.get("name").toString().toLowerCase().contains(filterPattern))
                        filteredlist.add(student);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            college_list.clear();
            college_list.addAll((ArrayList) results.values);
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
