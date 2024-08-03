package com.ecommerce.trove.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.trove.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;


public class RegisterFragment extends Fragment {

    private EditText registerEmail;
    private EditText registerUsername;
    private EditText registerPassword;
    private TextView errorMessageTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        registerEmail = view.findViewById(R.id.registerEmail);
        registerUsername = view.findViewById(R.id.registerUsername);
        registerPassword = view.findViewById(R.id.registerPassword);

        errorMessageTextView = view.findViewById(R.id.errorTextView);
        progressBar = view.findViewById(R.id.progressBarRegister);

        Button register = view.findViewById(R.id.register);
        Button loginPage = view.findViewById(R.id.loginPageButton);

        loginPage.setOnClickListener(v -> changePage());
        register.setOnClickListener(v -> saveNewUser());


        return view;
    }

    private void changePage() {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, LoginFragment.newInstance());
            ft.commit();
    }

    private void saveNewUser() {
        String username = registerUsername.getText().toString();
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest updateRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                            if (user != null) {
                                user.updateProfile(updateRequest);
                            }
                            progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "User Added Successfully", Toast.LENGTH_SHORT).show();
                            errorMessageTextView.setVisibility(View.GONE);
                            changePage();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        errorMessageTextView.setVisibility(View.VISIBLE);
                        errorMessageTextView.setText(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }
}