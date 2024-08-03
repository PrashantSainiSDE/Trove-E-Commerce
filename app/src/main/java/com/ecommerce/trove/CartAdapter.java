package com.ecommerce.trove;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        Product product = cartItem.getProduct();

        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .into(holder.productImageView);
        holder.productNameTextView.setText(product.getName());
        holder.productQuantityTextView.setText(String.valueOf(cartItem.getQuantity()));
        holder.productTotalPriceTextView.setText(String.format(Locale.getDefault(), "$ %.2f", cartItem.getTotalPrice()));

        holder.removeItemButton.setOnClickListener(v -> {
            CartManager.getInstance().removeFromCart(product);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            ((CartActivity) holder.itemView.getContext()).updateTotalPrice();
        });

        holder.increaseQuantityButton.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            notifyItemChanged(position);
            ((CartActivity) holder.itemView.getContext()).updateTotalPrice();
        });

        holder.decreaseQuantityButton.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                notifyItemChanged(position);
                ((CartActivity) holder.itemView.getContext()).updateTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImageView;
        public TextView productNameTextView;
        public TextView productQuantityTextView;
        public TextView productTotalPriceTextView;
        public ImageButton removeItemButton;
        public Button increaseQuantityButton;
        public Button decreaseQuantityButton;

        public ViewHolder(View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.imageViewCart);
            productNameTextView = itemView.findViewById(R.id.textViewCartItemName);
            productQuantityTextView = itemView.findViewById(R.id.textViewCartQuantity);
            productTotalPriceTextView = itemView.findViewById(R.id.cartPriceTextView);
            removeItemButton = itemView.findViewById(R.id.removeItemButton);
            increaseQuantityButton = itemView.findViewById(R.id.increaseQtyBtn);
            decreaseQuantityButton = itemView.findViewById(R.id.decreaseQtyBtn);
        }
    }
}