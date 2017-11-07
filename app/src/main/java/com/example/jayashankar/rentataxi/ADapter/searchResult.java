package com.example.jayashankar.rentataxi.ADapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jayashankar.rentataxi.Data.getSet;
import com.example.jayashankar.rentataxi.R;
import com.example.jayashankar.rentataxi.SearchTripDetails;

import java.util.List;

/**
 * Created by Jayashankar on 2017-10-19.
 */

public class searchResult extends RecyclerView.Adapter<searchResult.viewHolder> {
    Context c;
    List<getSet> items;
    private LayoutInflater inflater;

    public searchResult(Context c, List<getSet> items) {
        this.items = items;
        this.c = c;
        inflater = LayoutInflater.from(c);
    }


    @Override
    public searchResult.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.custom_searchresult, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(searchResult.viewHolder holder, int position) {
        getSet d = items.get(position);

        holder.custom_search_result_from.setText(d.getStrip_from());
        holder.custom_search_result_to.setText(d.getStrip_to());
        holder.custom_search_result_stime.setText(d.getStrip_time());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView custom_search_result_from, custom_search_result_to, custom_search_result_stime;
        View custom_search_result_container;

        public viewHolder(View itemView) {
            super(itemView);
            custom_search_result_container = itemView.findViewById(R.id.custom_search_result_container);
            custom_search_result_from = (TextView) itemView.findViewById(R.id.custom_search_result_from);

            custom_search_result_to = (TextView) itemView.findViewById(R.id.custom_search_result_to);

            custom_search_result_stime = (TextView) itemView.findViewById(R.id.custom_search_result_stime);

            custom_search_result_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSet d = items.get(getAdapterPosition());

                    Intent I = new Intent(c, SearchTripDetails.class).putExtra("tid", d.getStrip_id());
                    c.startActivity(I);


                }
            });
        }
    }
}
