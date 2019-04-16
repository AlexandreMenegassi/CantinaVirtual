package com.vendas.cantinavirtual;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.vendas.cantinavirtual.fragments.ClienteFragment;
import com.vendas.cantinavirtual.fragments.ProdutosFragment;
import com.vendas.cantinavirtual.fragments.VendasFragment;

import static com.vendas.cantinavirtual.R.id.fragment_container;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    addFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    addFragment(1);
                    return true;
                case R.id.navigation_notifications:
                    addFragment(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void addFragment(int i){

        Fragment fragment = null;
        Class fragmentClass = null;

        if (i == 0) {
            fragmentClass = ClienteFragment.class;
        } else if (i == 1) {
            fragmentClass = ProdutosFragment.class;
        } else if (i == 2) {
            fragmentClass = VendasFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(fragment_container, fragment).commit();
    }
}
