package com.ecommerce.trove;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class CheckoutFormFragment extends Fragment {

    private EditText firstNameEditText, lastNameEditText, addressEditText, emailEditText, phoneEditText;
    private EditText cardNumberEditText, expiryDateEditText, cvvEditText;
    private Button submitButton;


    public CheckoutFormFragment() {
        // Required empty public constructor
    }

    public static CheckoutFormFragment newInstance() {
        return new CheckoutFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_form, container, false);
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        addressEditText = view.findViewById(R.id.addressEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        cardNumberEditText = view.findViewById(R.id.cardNumberEditText);
        expiryDateEditText = view.findViewById(R.id.expiryDateEditText);
        cvvEditText = view.findViewById(R.id.cvvEditText);
        submitButton = view.findViewById(R.id.submitButton);

        expiryDateEditText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private int prevLength;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isFormatting) {
                    return;
                }

                isFormatting = true;

                String input = editable.toString();
                String formattedInput = formatExpiryDate(input);

                if (!input.equals(formattedInput)) {
                    expiryDateEditText.setText(formattedInput);
                    expiryDateEditText.setSelection(formattedInput.length());
                }

                isFormatting = false;
            }
            private String formatExpiryDate(String input) {
                String cleanInput = input.replaceAll("[^\\d]", "");
                int length = cleanInput.length();

                if (length >= 3 && prevLength <= length) {
                    return cleanInput.substring(0, 2) + "/" + cleanInput.substring(2);
                } else {
                    return cleanInput;
                }
            }
        });
        submitButton.setOnClickListener(v -> {


        if (validateInput()) {
                submitOrder();
            }
        });
        
        return view;
    }

    private void submitOrder() {
        CartManager.getInstance().clearCart();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.checkoutContainer, ThankYouFragment.newInstance());
        fragmentTransaction.commit();
    }

    private boolean validateInput() {
        boolean valid = true;

        if (TextUtils.isEmpty(firstNameEditText.getText().toString().trim())) {
            firstNameEditText.setError("First name is required");
            valid = false;
        }
        if (TextUtils.isEmpty(lastNameEditText.getText().toString().trim())) {
            lastNameEditText.setError("Last name is required");
            valid = false;
        }
        if (TextUtils.isEmpty(addressEditText.getText().toString().trim())) {
            addressEditText.setError("Address is required");
            valid = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString().trim()).matches()) {
            emailEditText.setError("Invalid email address");
            valid = false;
        }
        if (TextUtils.isEmpty(phoneEditText.getText().toString().trim()) || phoneEditText.getText().toString().trim().length() != 10) {
            phoneEditText.setError("Invalid phone number");
            valid = false;
        }
        if (TextUtils.isEmpty(cardNumberEditText.getText().toString().trim()) || cardNumberEditText.getText().toString().trim().length() != 16) {
            cardNumberEditText.setError("Invalid card number");
            valid = false;
        }
        if (TextUtils.isEmpty(expiryDateEditText.getText().toString().trim()) || expiryDateEditText.getText().toString().trim().length() != 5) {
            expiryDateEditText.setError("Invalid expiry date (MM/YY)");
            valid = false;
        }
        if (TextUtils.isEmpty(cvvEditText.getText().toString().trim()) || cvvEditText.getText().toString().trim().length() != 3) {
            cvvEditText.setError("Invalid CVV");
            valid = false;
        }

        return valid;
    }
}