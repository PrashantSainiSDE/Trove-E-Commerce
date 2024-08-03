package com.ecommerce.trove;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ThankYouFragment extends Fragment {

    public ThankYouFragment() {
        // Required empty public constructor
    }

    public static ThankYouFragment newInstance() {
        return new ThankYouFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thank_you, container, false);
        Button returnBtn = view.findViewById(R.id.backToProductBtn);

        returnBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProductActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        return view;
    }
}