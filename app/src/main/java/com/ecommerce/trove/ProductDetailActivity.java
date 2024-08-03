package com.ecommerce.trove;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView productPrice;
    private EditText quantity;
    private Double unitPrice;
    private int qty = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView productImage = findViewById(R.id.detailImageView);
        TextView productName = findViewById(R.id.nameDetailTextView);
        TextView productFullDescription = findViewById(R.id.fullDescriptionTextView);
        productPrice = findViewById(R.id.priceDetailTextView);
        quantity = findViewById(R.id.quantityEditText);
        Button addToCartBtn = findViewById(R.id.addToCartDetailBtn);
        Button goToCartBtn = findViewById(R.id.goToCartBtn);


        Product product = (Product) getIntent().getSerializableExtra("product");
        unitPrice = product.getPrice();

        Glide.with(this).load(product.getImage()).transition(GenericTransitionOptions.with(android.R.anim.fade_in)).into(productImage);
        productName.setText(product.getName());
        productFullDescription.setText(product.getFullDescription());
        productPrice.setText(String.format(Locale.getDefault(), "$ %.2f", product.getPrice()));
        quantity.setText(String.valueOf(qty));



        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.w("Prince", "Here");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!quantity.getText().toString().isEmpty()) {
                    qty = Integer.parseInt(quantity.getText().toString());
                    productPrice.setText(String.format(Locale.getDefault(), "$ %.2f", unitPrice * qty));
                }
            }
        });
        addToCartBtn.setOnClickListener(view -> {
            CartManager.getInstance().addToCart(product, qty);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
        goToCartBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle back button press
                getOnBackPressedDispatcher().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
