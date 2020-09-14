package com.example.covid19;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
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

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.Myviewholder> implements Filterable {
    private ArrayList<Map> fulllist, client_details_list = new ArrayList<>();
    Context context;

    public AdapterNotification(ArrayList<Map> clientdetails) {
        for (int i = 0; i < clientdetails.size(); i++) {
            client_details_list.add(clientdetails.get(i));
        }
        fulllist = new ArrayList<>(client_details_list);
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification_list_items, viewGroup, false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        final Map client = client_details_list.get(i);
        myviewholder.title.setText(client.get("title").toString());
        myviewholder.link.setText(client.get("link").toString());
        myviewholder.link.setPaintFlags(myviewholder.link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        myviewholder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(client.get("link").toString()));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return client_details_list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView link;
//        private TextView clientwise_sent_msgs_nosms;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            link = itemView.findViewById(R.id.link);


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
            client_details_list.clear();
            client_details_list.addAll((ArrayList) results.values);
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
