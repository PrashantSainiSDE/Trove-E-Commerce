package com.ecommerce.trove.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.trove.R;


public class RegisterFragment extends Fragment {
    EditText registerEmail;
    EditText registerUsername;
    EditText registerPassword;
    TextView errorMessageTextView;



    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

                View view =  inflater.inflate(R.layout.fragment_register, container, false);

         registerEmail = view.findViewById(R.id.registerEmail);
         registerUsername = view.findViewById(R.id.registerUsername);
         registerPassword = view.findViewById(R.id.registerPassword);

        errorMessageTextView = view.findViewById(R.id.errorTextView);

        Button register = view.findViewById(R.id.register);
        Button loginPage = view.findViewById(R.id.loginPageButton);

        //Click listener to switch on Login Page
        loginPage.setOnClickListener(v -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, LoginFragment.newInstance());
            ft.commit();
        });

        register.setOnClickListener(v -> saveNewUser());


        return view;
    }

    private void saveNewUser() {
        // Get the values from the editable fields
        String username = registerUsername.getText().toString();
        String password = registerPassword.getText().toString();
        String email = registerEmail.getText().toString();

        Toast.makeText(getContext(), username+" "+password+" "+email, Toast.LENGTH_SHORT).show();


    }
}