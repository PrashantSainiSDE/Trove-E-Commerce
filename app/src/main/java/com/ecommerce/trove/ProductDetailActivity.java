package com.ecommerce.trove;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView productImage = findViewById(R.id.detailImageView);
        TextView productName = findViewById(R.id.nameDetailTextView);
        TextView productFullDescription = findViewById(R.id.fullDescriptionTextView);
        TextView productPrice = findViewById(R.id.priceDetailTextView);
        EditText quantity = findViewById(R.id.quantityEditText);
        Button addToCartBtn = findViewById(R.id.addToCartDetailBtn);
        Button goToCartBtn = findViewById(R.id.goToCartBtn);

        Intent intent = getIntent();
        // get the list of car through extra
        Product product = (Product) intent.getSerializableExtra("product");
        Glide.with(this).load(product.getImage()).transition(GenericTransitionOptions.with(android.R.anim.fade_in)).into(productImage);
        productName.setText(product.getName());
        productFullDescription.setText(product.getFullDescription());
        productPrice.setText(String.format(Locale.getDefault(),"$ %.2f",product.getPrice()));
        quantity.setText("1");
        addToCartBtn.setOnClickListener(view -> {});
        goToCartBtn.setOnClickListener(view -> {});


    }
}