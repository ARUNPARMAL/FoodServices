package com.arunparmal.androidassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DiffUtil;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;

import com.arunparmal.androidassignment.Fragments.AboutFragment;
import com.arunparmal.androidassignment.Fragments.HomeFragment;
import com.arunparmal.androidassignment.Fragments.SavedFragment;
import com.arunparmal.androidassignment.Fragments.TutorialFragment;
import com.arunparmal.androidassignment.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        //set bottom navigation bar
        setbottomnavbar();

    }

    private void setbottomnavbar() {

        binding.navbar.getMenu().getItem(2).setEnabled(false);
        binding.navbar.setBackgroundColor(getResources().getColor(R.color.white));
        binding.navbar.setItemRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
        binding.navbar.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

        //on selected
        binding.navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case R.id.home:replaceFragment(new HomeFragment());
                            break;
                    case R.id.tutorials:replaceFragment(new TutorialFragment());
                            break;
                    case R.id.saved:replaceFragment(new SavedFragment());
                            break;
                    case R.id.about:replaceFragment(new AboutFragment());
                            break;
                }
                return true;
            }
        });
    }

    public  void replaceFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.framelayoutdefault.getId(),fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.framelayoutdefault.getId(),new HomeFragment());
        transaction.commit();
    }

}