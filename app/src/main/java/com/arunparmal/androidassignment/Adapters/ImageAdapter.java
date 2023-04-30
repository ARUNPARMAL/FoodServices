package com.arunparmal.androidassignment.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.arunparmal.androidassignment.R;
import com.arunparmal.androidassignment.databinding.ItemImagelayoutBinding;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    int[] imagearray;

    public ImageAdapter(int[] imagearray){
        this.imagearray=imagearray;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImagelayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_imagelayout,parent
        ,false);

        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.itemImagelayoutBinding.itemImage.setImageResource(imagearray[position]);

    }

    @Override
    public int getItemCount() {
        return imagearray.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
            ItemImagelayoutBinding itemImagelayoutBinding;
        public ImageViewHolder(ItemImagelayoutBinding itemImagelayoutBinding) {
            super(itemImagelayoutBinding.getRoot());
            this.itemImagelayoutBinding=itemImagelayoutBinding;
        }
    }
}
