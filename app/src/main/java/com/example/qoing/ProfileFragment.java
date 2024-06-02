package com.example.qoing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ProfileFragment extends Fragment {

    private static final String ARG_USERNAME = "username";
    private String username;
    private SharedPreferences sharedPreferences;

    public static ProfileFragment newInstance(String username) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Set username
        TextView textViewUsername = view.findViewById(R.id.text_view_username);
        textViewUsername.setText(username);

        // Inisialisasi ImageView dan tambahkan OnClickListener
        ImageView buttonLogout = view.findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        // Inisialisasi ImageView dan tambahkan OnClickListener untuk menuju halaman Home
        ImageView iv_home = view.findViewById(R.id.IV_Home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, searchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Inisialisasi ImageView dan tambahkan OnClickListener untuk menuju halaman Profile
        ImageView iv_profile = view.findViewById(R.id.IV_Profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perhatikan bahwa di sini kita menggunakan newInstance dengan username yang sama
                ProfileFragment profileFragment = ProfileFragment.newInstance(username);
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void logout() {
        saveLoginStatus(false);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    // Metode untuk menyimpan status login, misalnya di SharedPreferences
    private void saveLoginStatus(boolean isLoggedIn) {
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }
}
