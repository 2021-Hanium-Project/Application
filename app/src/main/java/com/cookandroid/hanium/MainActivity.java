package com.cookandroid.hanium;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    homeFragment homeFragment = new homeFragment();
    bulletinFragment bulletinFragment = new bulletinFragment();
    recommendInitialFragment recommendInitialFragment = new recommendInitialFragment();
    myPageFragment myPageFragment = new myPageFragment();

    recommendInputFragment recommendFragment = new recommendInputFragment();
    recommendSearchFragment recommendSearchFragment = new recommendSearchFragment();
    freeBulletinFragment freeBulletinFragment = new freeBulletinFragment();
    freeBulletinRegisterFragment freeBulletinRegisterFragment = new freeBulletinRegisterFragment();
    menuDetailFragment menuDetailFragment = new menuDetailFragment();

    SharedPreferences sharedPreferences;

    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();

        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        id = sharedPreferences.getString("id",null);


        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        recommendFragment.setArguments(bundle);
        recommendInitialFragment.setArguments(bundle);
        recommendSearchFragment.setArguments(bundle);
        myPageFragment.setArguments(bundle);
        bulletinFragment.setArguments(bundle);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch(item.getItemId()){
                    case R.id.home:
                        transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.bulletin:
                        transaction.replace(R.id.frameLayout, bulletinFragment).commitAllowingStateLoss();
                        break;
                    case R.id.rr:
                        transaction.replace(R.id.frameLayout, recommendInitialFragment).commitAllowingStateLoss();
                        break;
                    case R.id.myPage:
                        transaction.replace(R.id.frameLayout, myPageFragment).commitAllowingStateLoss();
                        break;
                }

                return true;
            }
        });


    }
    public void onClickMyBtn(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.frameLayout,recommendFragment).commitAllowingStateLoss();
    }

    public void onClickBackBtn(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.frameLayout,recommendInitialFragment).commitAllowingStateLoss();
    }
    public void onClickInputBtn(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.frameLayout,recommendInitialFragment).commitAllowingStateLoss();
    }

    public void onClickFreeBulletinBtn() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.frameLayout,freeBulletinFragment).commitAllowingStateLoss();

    }

    public void onClickFreeBulletinRegister() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.frameLayout, freeBulletinRegisterFragment).commitAllowingStateLoss();

    }
    public void onClickMenuDetail() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.frameLayout, menuDetailFragment).commitAllowingStateLoss();

    }

}
