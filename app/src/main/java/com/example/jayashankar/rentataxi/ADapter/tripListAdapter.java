package com.example.jayashankar.rentataxi.ADapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jayashankar.rentataxi.Data.getSet;
import com.example.jayashankar.rentataxi.GoogLeLocations;
import com.example.jayashankar.rentataxi.R;

import java.util.List;

/**
 * Created by Jayashankar on 2017-10-19.
 */

public class tripListAdapter extends RecyclerView.Adapter<tripListAdapter.viewHolder> {
    Context c;
    List<getSet> items;
    private LayoutInflater inflater;

    public tripListAdapter(Context c, List<getSet> items) {
        this.items = items;
        this.c = c;
        inflater = LayoutInflater.from(c);
    }


    @Override
    public tripListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.custom_triplist, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(tripListAdapter.viewHolder holder, int position) {
        getSet d = items.get(position);
        holder.custom_triplist_tripname.setText(d.getStrip_from());
        holder.custom_triplist_to.setText(d.getStrip_to());
        holder.custom_triplist_date.setText(d.getStrip_date());
        holder.custom_triplist_time.setText(d.getStrip_time());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView custom_triplist_tripname, custom_triplist_to, custom_triplist_date, custom_triplist_time;
        View custom_triplist_container;

        public viewHolder(View itemView) {
            super(itemView);
            custom_triplist_container = itemView.findViewById(R.id.custom_triplist_container);
            custom_triplist_tripname = (TextView) itemView.findViewById(R.id.custom_triplist_tripname);
            custom_triplist_to = (TextView) itemView.findViewById(R.id.custom_triplist_to);
            custom_triplist_date = (TextView) itemView.findViewById(R.id.custom_triplist_date);
            custom_triplist_time = (TextView) itemView.findViewById(R.id.custom_triplist_time);
            custom_triplist_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSet d = items.get(getAdapterPosition());

                    Intent I = new Intent(c, GoogLeLocations.class).putExtra("tid", d.getStrip_id());
                    c.startActivity(I);


                }
            });
        }
    }
}
