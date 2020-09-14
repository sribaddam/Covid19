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

public class AdapterHospital extends RecyclerView.Adapter<AdapterHospital.Myviewholder> implements Filterable {
    private ArrayList<Map> fulllist, hospital_details_list = new ArrayList<>();


    public AdapterHospital(ArrayList<Map> hospital_list) {
        for (int i = 0; i < hospital_list.size(); i++) {
            if (hospital_list.get(i).get("state") != null)
                hospital_details_list.add(hospital_list.get(i));
        }
        fulllist = new ArrayList<>(hospital_details_list);
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hospital_list_items, viewGroup, false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        Map hospital = hospital_details_list.get(i);
        myviewholder.hospital_name.setText(hospital.get("state").toString());
        myviewholder.urban_hospitals.setText(hospital.get("urbanHospitals").toString());
        myviewholder.urban_beds.setText(hospital.get("urbanBeds").toString());
        myviewholder.rural_hospital.setText(hospital.get("ruralHospitals").toString());
        myviewholder.rural_beds.setText(hospital.get("ruralBeds").toString());
        myviewholder.total_hospitals.setText(hospital.get("totalHospitals").toString());
        myviewholder.total_beds.setText(hospital.get("totalBeds").toString());
    }

    @Override
    public int getItemCount() {
        return hospital_details_list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public TextView hospital_name;
        public TextView urban_hospitals, urban_beds, rural_hospital, rural_beds, total_hospitals, total_beds;
//        private TextView clientwise_sent_msgs_nosms;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            hospital_name = itemView.findViewById(R.id.client_name);
            urban_hospitals = itemView.findViewById(R.id.urban_hospitals);
            urban_beds = itemView.findViewById(R.id.urban_beds);
            rural_hospital = itemView.findViewById(R.id.rural_hospitals);
            rural_beds = itemView.findViewById(R.id.rural_beds);
            total_hospitals = itemView.findViewById(R.id.total_hospitals);
            total_beds = itemView.findViewById(R.id.total_beds);


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
                    if (student.get("state").toString().toLowerCase().contains(filterPattern))
                        filteredlist.add(student);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            hospital_details_list.clear();
            hospital_details_list.addAll((ArrayList) results.values);
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
