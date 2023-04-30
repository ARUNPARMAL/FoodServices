package com.arunparmal.androidassignment.Adapters;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.arunparmal.androidassignment.R;
import com.arunparmal.androidassignment.model.RestrauntModel;
import com.arunparmal.androidassignment.databinding.ItemRestrauntlayoutBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ResaurantViewHolder> {
    ArrayList<RestrauntModel> restaurantlist;
    ArrayList<RestrauntModel> searchlist;

    public RestaurantAdapter(ArrayList<RestrauntModel> restaurantlist) {
        this.restaurantlist = restaurantlist;
    }

    @NonNull
    @Override
    public ResaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRestrauntlayoutBinding restrauntlayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_restrauntlayout,parent,false);
        return new ResaurantViewHolder(restrauntlayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResaurantViewHolder holder, int position) {
        RestrauntModel restaurant=restaurantlist.get(position);
        holder.itemRestrauntlayoutBinding.setRestaurant(restaurant);
    }

    @Override
    public int getItemCount() {
        return restaurantlist.size();
    }

    public static class ResaurantViewHolder extends RecyclerView.ViewHolder{
        ItemRestrauntlayoutBinding itemRestrauntlayoutBinding;
        public ResaurantViewHolder(ItemRestrauntlayoutBinding itemRestrauntlayoutBinding) {
            super(itemRestrauntlayoutBinding.getRoot());
            this.itemRestrauntlayoutBinding=itemRestrauntlayoutBinding;
        }
    }

    @BindingAdapter("android:loadimage")
    public  static void loadimage(ImageView imageView,String url){
        Glide.with(imageView).load(url).into(imageView);
    }
    @BindingAdapter("android:discounttext")
    public static void discounttext(TextView textView,String  discount){
        textView.setText(new StringBuilder().append(discount).append("% FLAT OFF").toString());
    }
}
