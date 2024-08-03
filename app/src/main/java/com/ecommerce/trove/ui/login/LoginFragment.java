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

import java.util.Objects;

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

        login.setOnClickListener(v -> login());

        registerPage.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, RegisterFragment.newInstance());
            fragmentTransaction.commit();
        });
        return view;
    }

    private void login() {
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

    progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                            errorMessageTextView.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), ProductActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
        progressBar.setVisibility(View.GONE);
                            errorMessageTextView.setVisibility(View.VISIBLE);
                            errorMessageTextView.setText(Objects.requireNonNull(task.getException()).getMessage());

                        }

                });

    }
}