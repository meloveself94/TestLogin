package com.example.android.testlogin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Soul on 9/20/2017.
 */

public class GridAdapter extends BaseAdapter implements Filterable {

    CustomFilter mCustomFilter;
    ArrayList<GridItem> filterList;
    private Context mContext;
    private ArrayList<GridItem> gtem;

    //Constructor


    public GridAdapter(Context mContext, ArrayList<GridItem> gtem) {
        this.filterList = gtem;
        this.mContext = mContext;
        this.gtem = gtem;
    }

    @Override
    public int getCount() {
        return gtem.size();
    }

    @Override
    public Object getItem(int position) {
        return gtem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.grid_item, null);
        ImageView putPic = (ImageView)v.findViewById(R.id.imageHere);
        TextView putTitle = (TextView)v.findViewById(R.id.titleHere);
        TextView putCountry = (TextView)v.findViewById(R.id.countryHere);
        TextView putPrice = (TextView)v.findViewById(R.id.per_price);



        //
        putTitle.setText(gtem.get(position).getmTitleHere());
        putCountry.setText("Country: " + gtem.get(position).getCountry());
        putPrice.setText("Price: " + "$" + gtem.get(position).getmPriceHere());
        Picasso.with(mContext).load(gtem.get(position).getmImageView()).resize(450 , 500).into(putPic);


        return v;
    }


    @Override
    public Filter getFilter() {
        //To-do Auto-generated method stub
        if (mCustomFilter == null )
        {
            mCustomFilter = new CustomFilter();
        }

        return mCustomFilter;
    }

    //Inner Class
    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length()>0)
            {
                //Constraint to Upper
                constraint = constraint.toString().toUpperCase();

                ArrayList<GridItem> filters = new ArrayList<GridItem>();

                //to get specific items
                for (int i = 0; i<filterList.size(); i++)
                {
                    if (filterList.get(i).getmTitleHere().toUpperCase().contains(constraint))
                    {
                        filters.add(filterList.get(i));
                    }
                }
                results.count = filters.size();
                results.values = filters;

            }else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            gtem = (ArrayList<GridItem>) results.values;
            notifyDataSetChanged();


        }
    }
}
