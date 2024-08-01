package com.ecommerce.trove;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class ProductPageAdaptor extends RecyclerView.Adapter<ProductPageAdaptor.ViewHolder> {
    private static List<Product> products;


    public ProductPageAdaptor(List<Product> products) {
        this.products = products;
    }

    //view holder class
    public static class ViewHolder extends RecyclerView.ViewHolder  {

        private final ImageView productImageView;
        private final TextView productNameTextView;
        private final TextView productPriceTextView;
        private final TextView productDescriptionTextView;
        private final Button addToCartButton;

        public ViewHolder(View view) {
            super(view);
            productImageView = view.findViewById(R.id.productImageView);
            productNameTextView = view.findViewById(R.id.productName);
            productPriceTextView = view.findViewById(R.id.productPrice);
            productDescriptionTextView = view.findViewById(R.id.productDescription);
            addToCartButton = view.findViewById(R.id.productAddToCartButton);

//             Define click listener for the ViewHolder's View
            addToCartButton.setOnClickListener(view1 -> {
                int position = getAdapterPosition();
                Toast.makeText(view.getContext(),String.valueOf(position), Toast.LENGTH_SHORT).show();
            });

            view.setOnClickListener(view1 -> {
                int position = getAdapterPosition();
                Product product = products.get(position);
                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("product", (Serializable) product);
                view.getContext().startActivity(intent);
            });
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_list_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

               Product product = products.get(position);
               viewHolder.productNameTextView.setText(product.getName());
               viewHolder.productPriceTextView.setText(String.format(Locale.getDefault(),"$ %.2f", product.getPrice())) ;
               viewHolder.productDescriptionTextView.setText(product.getDescription());
        Glide.with(viewHolder.itemView.getContext())
                .load(product.getImage())
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))// Android built-in placeholder
                .error(android.R.drawable.ic_dialog_alert)
                .into(viewHolder.productImageView);

    }

    // set no. of slides on viewPager
    @Override
    public int getItemCount() {
        return products.size();
    }

}
