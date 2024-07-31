package com.ecommerce.trove.ui.login;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.trove.ProductActivity;
import com.ecommerce.trove.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private EditText loginEmail;
    private EditText loginPassword;
    private TextView errorMessageTextView;
    private ProgressBar progressBar;


    private FirebaseAuth mAuth;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginEmail = view.findViewById(R.id.loginEmail);
        loginPassword = view.findViewById(R.id.loginPassword);

        errorMessageTextView = view.findViewById(R.id.errorLoginTextView);
        progressBar = view.findViewById(R.id.progressBarLogin);

        Button login = view.findViewById(R.id.loginBtn);
        Button registerPage = view.findViewById(R.id.registrationPageButton);

        //login click listener
        login.setOnClickListener(v -> login());

        //Click listener to switch on Register Page
        registerPage.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, RegisterFragment.newInstance());
            fragmentTransaction.commit();
        });
        return view;
    }

    private void login() {
        // Get the values from the editable fields
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

    progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                            FirebaseUser user = mAuth.getCurrentUser();
                            errorMessageTextView.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), ProductActivity.class);
                            //send data to dashboard
                            intent.putExtra("user", user);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
        progressBar.setVisibility(View.GONE);
                            errorMessageTextView.setVisibility(View.VISIBLE);
                            errorMessageTextView.setText(task.getException().getMessage());

                        }

                });

    }
}