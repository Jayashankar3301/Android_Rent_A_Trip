package com.example.jayashankar.rentataxi.ADapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jayashankar.rentataxi.Data.getSet;
import com.example.jayashankar.rentataxi.R;

import java.util.List;

/**
 * Created by Jayashankar on 2017-10-19.
 */

public class searchResultDetails extends RecyclerView.Adapter<searchResultDetails.viewHolder> {
    Context c;
    List<getSet> items;
    private LayoutInflater inflater;

    public searchResultDetails(Context c, List<getSet> items) {
        this.items = items;
        this.c = c;
        inflater = LayoutInflater.from(c);
    }


    @Override
    public searchResultDetails.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.custom_serachdatadetails, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(searchResultDetails.viewHolder holder, int position) {
        getSet d = items.get(position);

        holder.custom_searchdetails_location.setText(d.getCheck_location());
        holder.custom_searchdetails_sno.setText((position + 1) + "");


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView custom_searchdetails_location, custom_searchdetails_sno, custom_search_result_stime;
        View containers;

        public viewHolder(View itemView) {
            super(itemView);
            containers = itemView.findViewById(R.id.containers);
            custom_searchdetails_location = (TextView) itemView.findViewById(R.id.custom_searchdetails_location);

            custom_searchdetails_sno = (TextView) itemView.findViewById(R.id.custom_searchdetails_sno);


        }
    }
}
