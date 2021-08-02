package com.example.chuibbo_android.mypage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chuibbo_android.R;

import java.util.ArrayList;

public class MypageLikeJobPostingRecyclerViewAdapter extends RecyclerView.Adapter<MypageLikeJobPostingRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LikeJobPostingModel> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MypageLikeJobPostingRecyclerViewAdapter(Context context, ArrayList<LikeJobPostingModel> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public MypageLikeJobPostingRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mypage_like_job_posting, parent, false);
        return new MypageLikeJobPostingRecyclerViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull MypageLikeJobPostingRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.company_name.setText(mData.get(position).getCompany_name());
        holder.company_desc.setText(mData.get(position).getCompany_desc());
        holder.company_deadline.setText("D-" + mData.get(position).getCompany_deadline());
        holder.bindImage(mData.get(position).getCompany_logo());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    //// stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView company_name, company_desc, company_deadline;
        ImageView company_logo;

        ViewHolder(View itemView) {
            super(itemView);
            company_name = itemView.findViewById(R.id.company_name);
            company_desc = itemView.findViewById(R.id.company_desc);
            company_deadline = itemView.findViewById(R.id.company_deadline);
            company_logo = itemView.findViewById(R.id.company_logo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        String url = getItem(pos).getCompany_link();

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

        public void bindImage(String imageURL) {
            Glide.with(context)
                    .load(imageURL)
                    .into(company_logo);
        }
    }

    // convenience method for getting data at click position
    LikeJobPostingModel getItem(int id) {
        return mData.get(id);
    }
}