package com.example.chuibbo_android.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chuibbo_android.R;

import java.util.ArrayList;

public class MypageAlbumRecyclerViewAdpater extends RecyclerView.Adapter<MypageAlbumRecyclerViewAdpater.ViewHolder> {
    private Context context;
    private ArrayList<AlbumModel> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MypageAlbumRecyclerViewAdpater(Context context, ArrayList<AlbumModel> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public MypageAlbumRecyclerViewAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mypage_album_item, parent, false);
        return new MypageAlbumRecyclerViewAdpater.ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull MypageAlbumRecyclerViewAdpater.ViewHolder holder, int position) {
        holder.bindImage(mData.get(position).getImage());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    //// stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView resume_photo;
        ImageButton star;

        ViewHolder(View itemView) {
            super(itemView);
            resume_photo = itemView.findViewById(R.id.resume_photo);
            star = itemView.findViewById(R.id.star);

//            star.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getBindingAdapterPosition();
//                    if (pos != RecyclerView.NO_POSITION) {
//                        getItem(pos).
//                        if (star.resources.equals(R.drawable.star_empty)) // TODO: 추후에 좋아요 기능 구현
//                            star.setImageResource(R.drawable.star_fill)
//                        else
//                            star.setImageResource(R.drawable.star_empty)
//                    }
//                }
//            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        String image = getItem(pos).getImage();

                        MypageMyalbumDialogFragment myalbumDialog = new MypageMyalbumDialogFragment();
                        myalbumDialog.setCancelable(false); // dialog 영영 밖(외부) 클릭 시, dismiss되는 현상 막기
                    }
                }
            });
        }

        public void bindImage(String imageURL) {
            Glide.with(context)
                    .load(imageURL)
                    .into(resume_photo);
        }
    }

    // convenience method for getting data at click position
    AlbumModel getItem(int id) {
        return mData.get(id);
    }
}
