package com.example.qoing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Mendapatkan username dari intent jika ada
        String username = getIntent().getStringExtra("username");

        // Membuat instance HomeFragment dengan menyertakan username jika ada
        HomeFragment homeFragment = HomeFragment.newInstance(username);

        // Memeriksa apakah fragment HomeFragment sudah ada dalam back stack
        Fragment fragment = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());

        // Jika fragment belum ada, tambahkan fragment baru
        if (!(fragment instanceof HomeFragment)){
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, homeFragment, HomeFragment.class.getSimpleName())
                    .commit();
        }
    }
}
