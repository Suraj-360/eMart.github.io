package com.suraj.emart.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.suraj.emart.R;
import com.suraj.emart.activities.MainActivity;
import com.suraj.emart.adapters.CategoryAdapter;
import com.suraj.emart.adapters.ProductAdapter;
import com.suraj.emart.databinding.ActivityMainBinding;
import com.suraj.emart.databinding.FragmentHomeBinding;
import com.suraj.emart.models.Category;
import com.suraj.emart.models.Product;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentHomeBinding fragmentHomeBinding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        initCategories(8);
        initProducts();
        initSlider();

        fragmentHomeBinding.moreOrHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String text = fragmentHomeBinding.moreOrHide.getText().toString();
                if(text.equals("Show more"))
                {
                    initCategories(13);
                    fragmentHomeBinding.moreOrHide.setText("Hide");
                }
                else
                {
                    initCategories(8);
                    fragmentHomeBinding.moreOrHide.setText("Show more");
                }
            }
        });

        return fragmentHomeBinding.getRoot();
    }

    private void initSlider() {
        String img1 = "https://firebasestorage.googleapis.com/v0/b/quiz-application-4c25b.appspot.com/o/eMart%2FSliderAdsImages%2F262002-Pink-Cow_banner-ad_990x468.jpg?alt=media&token=4a4950df-a534-48a3-a67b-4d501ed76de9";
        String img2 = "https://firebasestorage.googleapis.com/v0/b/quiz-application-4c25b.appspot.com/o/eMart%2FSliderAdsImages%2FShoptonic_Nepal_offer_banner_3..jpg?alt=media&token=773900e5-cd03-4db1-9d5d-8f755d758776";
        String img3 = "https://firebasestorage.googleapis.com/v0/b/quiz-application-4c25b.appspot.com/o/eMart%2FSliderAdsImages%2Fmakeup-products-vector-template-banner-collection-face-cosmetics-beauty-products-advertising_572293-1443.webp?alt=media&token=6f918c57-9db8-4e8f-b350-d39c7f2999d2";
        String img4 = "https://firebasestorage.googleapis.com/v0/b/quiz-application-4c25b.appspot.com/o/eMart%2FSliderAdsImages%2Fmaxresdefault.jpg?alt=media&token=9585a47a-7c5d-4534-bedb-da4632cc26dc";
        String img5 = "https://firebasestorage.googleapis.com/v0/b/quiz-application-4c25b.appspot.com/o/eMart%2FSliderAdsImages%2FmaxresdefaultIi.jpg?alt=media&token=96345510-0525-44a4-9c89-decaed76ae36";

        fragmentHomeBinding.carousel.addData(new CarouselItem(img1, "Ads 1"));
        fragmentHomeBinding.carousel.addData(new CarouselItem(img2, "Ads 2"));
        fragmentHomeBinding.carousel.addData(new CarouselItem(img3, "Ads 3"));
        fragmentHomeBinding.carousel.addData(new CarouselItem(img4, "Ads 4"));
        fragmentHomeBinding.carousel.addData(new CarouselItem(img5, "Ads 5"));
    }

    void initCategories(int l)
    {
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categories, getActivity());

        // Add in arrayList Categories from firebase
        GridLayoutManager layoutManagerCategories = new GridLayoutManager(getActivity(), 4);
        firebaseFirestore.collection("eMartCategories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                categories.clear();
                assert value != null;
                int i = 0;
                for(DocumentSnapshot documentSnapshot : value.getDocuments())
                {
                    if(i<l)
                    {
                        Category category = documentSnapshot.toObject(Category.class);
                        categories.add(category);
                        i++;
                    }
                    else
                    {
                        break;
                    }
                }
                fragmentHomeBinding.categoriesRecyclerView.setLayoutManager(layoutManagerCategories);
                fragmentHomeBinding.categoriesRecyclerView.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    void initProducts()
    {
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(products, getActivity());

        // Add in arrayList Products from firebase

        GridLayoutManager layoutManagerProduct = new GridLayoutManager(getActivity(), 2);
        firebaseFirestore.collection("eMartProducts").addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                products.clear();
                assert value != null;
                for (DocumentSnapshot documentSnapshot : value.getDocuments())
                {
                    Product product = documentSnapshot.toObject(Product.class);
                    products.add(product);
                }
                fragmentHomeBinding.productsRecyclerView.setLayoutManager(layoutManagerProduct);
                fragmentHomeBinding.productsRecyclerView.setAdapter(productAdapter);
            }
        });
    }
}