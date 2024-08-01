package com.ecommerce.trove;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {
    //widgets
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private List<Product> products;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.productRecyclerView);
        progressBar = findViewById(R.id.productsProgressBar);

        products = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        db.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            Product product1 = new Product(
                                    document.getId(),
                                    data.get("name").toString(),
                                    data.get("image").toString(),
                                    Double.parseDouble(data.get("price").toString()),
                                    data.get("description").toString(),
                                    data.get("full_description").toString());

                            products.add(product1);
                        }
                        progressBar.setVisibility(View.GONE);
                        // Set up the RecyclerView
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        ProductPageAdaptor productPageAdaptor = new ProductPageAdaptor(products);
                        recyclerView.setAdapter(productPageAdaptor);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Log.w("Products Error", "Error getting documents.", task.getException());
                    }
                });
    }
}