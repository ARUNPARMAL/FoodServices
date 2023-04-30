package com.arunparmal.androidassignment.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arunparmal.androidassignment.Adapters.ImageAdapter;
import com.arunparmal.androidassignment.Adapters.RestaurantAdapter;
import com.arunparmal.androidassignment.MainActivity;
import com.arunparmal.androidassignment.R;
import com.arunparmal.androidassignment.databinding.FragmentHomeBinding;
import com.arunparmal.androidassignment.model.RestrauntModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class HomeFragment extends Fragment {

    FusedLocationProviderClient fusedLocationProviderClient;

    FragmentHomeBinding homeBinding;
    ArrayList<RestrauntModel> originalrestaurantlist =new ArrayList<>();
    ArrayList<RestrauntModel> querylist;
    ArrayList<RestrauntModel> filteredlist=new ArrayList<>();

    public  final static int REQUESTCODE=100;
    CardView tempcardview;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createResaurantData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        View view=homeBinding.getRoot();

        //location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLastLocation();

        querylist=originalrestaurantlist;
        populateRecyclerviewRestaurants(originalrestaurantlist);

        populateRecyclerviewImages();

        homeBinding.searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String query=s.toString();
                performSearch(query);
            }
        });

        homeBinding.cardall.setOnClickListener(v -> {
//            clearresults();
            filterresults(0);
        });
       homeBinding.cardpizza.setOnClickListener(v -> {
//            clearresults();
            filterresults(1);
        });
       homeBinding.cardchicken.setOnClickListener(v -> {
//            clearresults();
            filterresults(2);
        });
       homeBinding.cardsalad.setOnClickListener(v -> {
//            clearresults();
            filterresults(3);
        });
       homeBinding.cardburger.setOnClickListener(v -> {

            filterresults(4);
        });


        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUESTCODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(getActivity(), "Location Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null ){
                        Geocoder geocoder=new Geocoder(getActivity(),Locale.getDefault());
                        try {
                            List<Address> address=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            String locality="",division="",area="";
                            if (address.get(0).getThoroughfare()!=null){
                                locality=address.get(0).getThoroughfare();
                            }
                            division=address.get(0).getLocality();

                            area=address.get(0).getAdminArea();
                            homeBinding.locationtext.setText(locality+" "+division+" "+area);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }
            });

        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUESTCODE);

        }



    }


    private void filterresults(int i) {
        switch (i) {
            case 1:clearresults();
                this.tempcardview = homeBinding.cardpizza;
                tempcardview.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                homeBinding.textpizza.setText("Pizza");
                filterdata("Pizza");
                break;

            case 2:clearresults();
                this.tempcardview=homeBinding.cardchicken;
                homeBinding.cardchicken.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                homeBinding.textchicken.setText("Chicken");
                filterdata("chicken");

                break;

            case 3:clearresults();
                this.tempcardview=homeBinding.cardsalad;
                homeBinding.cardsalad.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                homeBinding.textsalad.setText("Salad");
                filterdata("salad");

                break;

            case 4:clearresults();
                this.tempcardview= homeBinding.cardburger;
                homeBinding.cardburger.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                homeBinding.textburgerr.setText("Burger");
                filterdata("Burger");

                break;

            case 0:clearresults();
                this.tempcardview = homeBinding.cardall;
                populateRecyclerviewRestaurants(originalrestaurantlist);
                break;

            default: break;

        }
    }

    private void filterdata(String filter) {
        filteredlist.clear();
        if(querylist!=null){
        for (int i=0;i<querylist.size();i++){
            String [] catlist;
            catlist=querylist.get(i).getCategory().clone();

            for (int j=0;j< catlist.length;j++){
                if (catlist[j].toLowerCase(Locale.ROOT).equals(filter.toLowerCase(Locale.ROOT))){
                    filteredlist.add(querylist.get(i));
                }
            }
        }
        populateRecyclerviewRestaurants(filteredlist);
    }

    }

    private void performSearch(String query) {
         querylist=new ArrayList<>();
        querylist.clear();
        RestrauntModel model;
        for (int i=0;i<originalrestaurantlist.size();i++){
            if (originalrestaurantlist.get(i).getRestaurant_name().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))){
                querylist.add(originalrestaurantlist.get(i));
            }
        }
        populateRecyclerviewRestaurants(querylist);
    }


    private void createResaurantData() {
        originalrestaurantlist.add(new RestrauntModel("https://as1.ftcdn.net/v2/jpg/01/70/65/74/1000_F_170657468_pPfzg7r59gnJijN01PTZwOPHrX0X4zQk.jpg",
                "Punjabi Dhaba","5","20",new String[]{"Paratha","Chicken","Paneer"},
                "Description of the restaurant: like the qualities, specialitiles and the exlusive food available there"));

         originalrestaurantlist.add(new RestrauntModel("https://as1.ftcdn.net/v2/jpg/01/70/65/74/1000_F_170657468_pPfzg7r59gnJijN01PTZwOPHrX0X4zQk.jpg",
                 "Domino's Pizza","4","20",new String[]{"Pizza","Burger","Chicken"},
                       "Description of the restaurant: like the qualities, specialitiles and the exlusive food available there"));

         originalrestaurantlist.add(new RestrauntModel("https://as1.ftcdn.net/v2/jpg/01/70/65/74/1000_F_170657468_pPfzg7r59gnJijN01PTZwOPHrX0X4zQk.jpg",
                 "Burger King","3","25",new String[]{"Chicken","Burger","Salad"},
                       "Description of the restaurant: like the qualities, specialitiles and the exlusive food available there"));

         originalrestaurantlist.add(new RestrauntModel("https://as1.ftcdn.net/v2/jpg/01/70/65/74/1000_F_170657468_pPfzg7r59gnJijN01PTZwOPHrX0X4zQk.jpg",
                 "KFC Restaurant","4","20",new String[]{"Pizza","Burger","Chicken"},
                       "Description of the restaurant: like the qualities, specialitiles and the exlusive food available there"));

         originalrestaurantlist.add(new RestrauntModel("https://as1.ftcdn.net/v2/jpg/01/70/65/74/1000_F_170657468_pPfzg7r59gnJijN01PTZwOPHrX0X4zQk.jpg",
                 "Jain Hotel","5","20",new String[]{"Pizza","Burger","Soup"},
                       "Description of the restaurant: like the qualities, specialitiles and the exlusive food available there"));

         originalrestaurantlist.add(new RestrauntModel("https://as1.ftcdn.net/v2/jpg/01/70/65/74/1000_F_170657468_pPfzg7r59gnJijN01PTZwOPHrX0X4zQk.jpg",
                 "Highway Kitchen","2","15",new String[]{"Food","Salad","Soup"},
                      "Description of the restaurant: like the qualities, specialitiles and the exlusive food available there"));
    }

    private void populateRecyclerviewRestaurants(ArrayList<RestrauntModel> restaurantlist) {
        RestaurantAdapter restaurantAdapter=new RestaurantAdapter(restaurantlist);
        homeBinding.restaurantRecyclerView.setAdapter(restaurantAdapter);

    }

    private void clearresults() {
        if (tempcardview!=null){
            tempcardview.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            homeBinding.textpizza.setText("");
            homeBinding.textsalad.setText("");
            homeBinding.textchicken.setText("");
            homeBinding.textburgerr.setText("");
        }
    }
    private void populateRecyclerviewImages() {
        int[] imageslist={R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image2,R.drawable.image3,R.drawable.image4};
        ImageAdapter imageAdapter=new ImageAdapter(imageslist);
        homeBinding.storiesRecyclerview.setAdapter(imageAdapter);
    }


}