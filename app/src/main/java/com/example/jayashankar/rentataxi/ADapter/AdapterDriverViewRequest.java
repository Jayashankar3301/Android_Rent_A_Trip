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

public class AdapterDriverViewRequest extends RecyclerView.Adapter<AdapterDriverViewRequest.viewHolder> {
    Context c;
    List<getSet> items;
    private LayoutInflater inflater;

    public AdapterDriverViewRequest(Context c, List<getSet> items) {
        this.items = items;
        this.c = c;
        inflater = LayoutInflater.from(c);
    }


    @Override
    public AdapterDriverViewRequest.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.custom_driverviewrequest, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterDriverViewRequest.viewHolder holder, int position) {
        getSet d = items.get(position);

        holder.custom_driverviewrequest_from.setText(d.getFrom());
        holder.custom_driverviewrequest_to.setText(d.getTo());
        holder.custom_driverviewrequest_tfrom.setText(d.getTfrom());
        holder.custom_driverviewrequest_tto.setText(d.getTo());
        holder.custom_driverviewrequest_date.setText("Date   :" + d.getDate());

        holder.custom_driverviewrequest_username.setText("Name   :" + d.getUsername());
        holder.custom_driverviewrequest_contact.setText("Phone  :" + d.getPhone());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView custom_driverviewrequest_from, custom_driverviewrequest_to, custom_driverviewrequest_contact, custom_driverviewrequest_username, custom_driverviewrequest_tfrom, custom_driverviewrequest_tto, custom_driverviewrequest_date;
        View custom_search_result_container;
        Button custom_driverviewrequest_accept, custom_driverviewrequest_reject;

        public viewHolder(View itemView) {
            super(itemView);
            // custom_search_result_container=itemView.findViewById(R.id.custom_search_result_container);
            custom_driverviewrequest_from = (TextView) itemView.findViewById(R.id.custom_driverviewrequest_from);

            custom_driverviewrequest_to = (TextView) itemView.findViewById(R.id.custom_driverviewrequest_to);

            custom_driverviewrequest_tfrom = (TextView) itemView.findViewById(R.id.custom_driverviewrequest_tfrom);
            custom_driverviewrequest_tto = (TextView) itemView.findViewById(R.id.custom_driverviewrequest_tto);
            custom_driverviewrequest_date = (TextView) itemView.findViewById(R.id.custom_driverviewrequest_date);

            custom_driverviewrequest_username = (TextView) itemView.findViewById(R.id.custom_driverviewrequest_username);
            custom_driverviewrequest_contact = (TextView) itemView.findViewById(R.id.custom_driverviewrequest_contact);

            custom_driverviewrequest_accept = (Button) itemView.findViewById(R.id.custom_driverviewrequest_accept);
            custom_driverviewrequest_reject = (Button) itemView.findViewById(R.id.custom_driverviewrequest_reject);
            custom_driverviewrequest_accept.setOnClickListener(this);
            custom_driverviewrequest_reject.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            getSet d = items.get(getAdapterPosition());


            String status = "0";
            if (view == custom_driverviewrequest_accept) {
                status = "1";
            } else if (view == custom_driverviewrequest_reject) {
                status = "2";

            }

            items.remove(items.get(getAdapterPosition()));
            notifyDataSetChanged();
            update up = new update();

            up.execute(d.getReqId() + "", status);


        }

        private class update extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... strings) {
                WebServiceCaller caller = new WebServiceCaller();
                caller.setSoapObject("approverequest");
                caller.addProperty("request_id", strings[0]);
                caller.addProperty("status", strings[1]);
                caller.callWebService();
                String res = caller.getResponse();
                return res;
            }
        }
    }
}
