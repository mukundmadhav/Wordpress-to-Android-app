package com.mukundmadhav.crayonpaper;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Vholder> {

    private Context context;
    private List<DataMembers> dataMemberses;

    public RecyclerAdapter(Context context, List<DataMembers> dataMemberses) {
        this.context = context;
        this.dataMemberses = dataMemberses;
    }

    @Override
    public Vholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_item,parent,false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(Vholder holder, int position) {
        final DataMembers dataMembers = dataMemberses.get(position);
        holder.text.setText(dataMembers.postTitle);
        Glide.with(context).load(dataMembers.imgurl).placeholder(R.drawable.loading).centerCrop().into(holder.image);
        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Read More Clicked", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("url",dataMembers.posturl);
                Log.i("Sending:",dataMembers.posturl);
                MainActivity.tContent = dataMembers.posturl;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataMemberses.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView image;
        Button readMore;

        public Vholder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.main_image);
            text = (TextView) itemView.findViewById(R.id.post_title);
            readMore = (Button) itemView.findViewById(R.id.readmorebut);

        }
    }

}
