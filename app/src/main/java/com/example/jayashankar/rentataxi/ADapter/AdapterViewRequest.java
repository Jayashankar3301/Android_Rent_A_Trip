package com.example.jayashankar.rentataxi.ADapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jayashankar.rentataxi.Data.getSet;
import com.example.jayashankar.rentataxi.R;
import com.example.jayashankar.rentataxi.WebServiceCaller;

import java.util.List;

/**
 * Created by Jayashankar on 2017-10-19.
 */

public class AdapterViewRequest extends RecyclerView.Adapter<AdapterViewRequest.viewHolder> {
    Context c;
    List<getSet> items;
    private LayoutInflater inflater;

    public AdapterViewRequest(Context c, List<getSet> items) {
        this.items = items;
        this.c = c;
        inflater = LayoutInflater.from(c);
    }


    @Override
    public AdapterViewRequest.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.custom_viewrequest, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterViewRequest.viewHolder holder, int position) {
        getSet d = items.get(position);

        holder.custom_viewrequest_from.setText(d.getFrom());
        holder.custom_viewrequest_to.setText(d.getTo());

        holder.custom_viewrequest_date.setText(d.getDate());

        holder.custom_viewrequest_username.setText(d.getUsername());
        holder.custom_viewrequest_contact.setText(d.getPhone());
        holder.custom_viewrequest_noseeats.setText(d.getSeat());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView custom_viewrequest_from, custom_viewrequest_to, custom_viewrequest_noseeats, custom_viewrequest_contact, custom_viewrequest_username, custom_viewrequest_tfrom, custom_viewrequest_tto, custom_viewrequest_date;
        View custom_search_result_container;
        Button custom_viewrequest_cancel;

        public viewHolder(View itemView) {
            super(itemView);
            // custom_search_result_container=itemView.findViewById(R.id.custom_search_result_container);
            custom_viewrequest_from = (TextView) itemView.findViewById(R.id.custom_viewrequest_from);

            custom_viewrequest_to = (TextView) itemView.findViewById(R.id.custom_viewrequest_to);

            custom_viewrequest_date = (TextView) itemView.findViewById(R.id.custom_viewrequest_date);

            custom_viewrequest_username = (TextView) itemView.findViewById(R.id.custom_viewrequest_username);
            custom_viewrequest_contact = (TextView) itemView.findViewById(R.id.custom_viewrequest_contact);
            custom_viewrequest_noseeats = (TextView) itemView.findViewById(R.id.custom_viewrequest_noseeats);
            custom_viewrequest_cancel = (Button) itemView.findViewById(R.id.custom_viewrequest_cancel);

            custom_viewrequest_cancel.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            getSet d = items.get(getAdapterPosition());
            items.remove(items.get(getAdapterPosition()));
            notifyDataSetChanged();


            update up = new update();

            up.execute(d.getReqId() + "");


        }

        private class update extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... strings) {
                WebServiceCaller caller = new WebServiceCaller();
                caller.setSoapObject("deleterequest");
                caller.addProperty("request_id", strings[0]);

                caller.callWebService();
                String res = caller.getResponse();
                return res;
            }
        }
    }
}
