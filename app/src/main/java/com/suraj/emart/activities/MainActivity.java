package com.suraj.emart.activities;

import static com.suraj.emart.R.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.suraj.emart.R;
import com.suraj.emart.adapters.CategoryAdapter;
import com.suraj.emart.adapters.ProductAdapter;
import com.suraj.emart.fragments.CartFragment;
import com.suraj.emart.fragments.HomeFragment;
import com.suraj.emart.fragments.OrderHistoryFragment;
import com.suraj.emart.models.Category;
import com.suraj.emart.models.Product;
import com.suraj.emart.databinding.ActivityMainBinding;
import com.suraj.emart.models.UserModel;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActivityMainBinding activityMainBinding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    FirebaseFirestore firebaseFirestore;
    MaterialSearchBar searchBar;
    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    String userId;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        setSupportActionBar(activityMainBinding.toolBar);


        setSupportActionBar(activityMainBinding.toolBar);
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, color.blue));
        toggle = new ActionBarDrawerToggle(this, activityMainBinding.drawer, activityMainBinding.toolBar, string.openTgl, string.closeTgl);
        activityMainBinding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(AppCompatResources.getDrawable(this, drawable.ic_baseline_menu_24));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activityMainBinding.navMenu.setNavigationItemSelectedListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerBase, new HomeFragment());
        fragmentTransaction.commit();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        firebaseFirestore.collection("eMartUser").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserModel userModel = documentSnapshot.toObject(UserModel.class);
                assert userModel != null;
                NavigationView navigationView = activityMainBinding.navMenu;
                View header = navigationView.getHeaderView(0);
                TextView navUser = (TextView) header.findViewById(id.navHeaderUserName);
                navUser.setText(userModel.getUserName().toString());
                TextView navEmail = (TextView) header.findViewById(id.navHeaderEmail);
                navEmail.setText(userModel.getEmail().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId())
        {
            case R.id.myAccountNav:
                fragmentTransaction.replace(R.id.containerBase, new HomeFragment());
                fragmentTransaction.commit();
                activityMainBinding.drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.cartNav:
                fragmentTransaction.replace(R.id.containerBase, new CartFragment());
                fragmentTransaction.commit();
                activityMainBinding.drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.orderHisNav:
                fragmentTransaction.replace(R.id.containerBase, new OrderHistoryFragment());
                fragmentTransaction.commit();
                activityMainBinding.drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.logoutNav:
                FirebaseAuth.getInstance().signOut();
                Intent resultIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(resultIntent);
                activityMainBinding.drawer.closeDrawer(GravityCompat.START);
                finish();
                break;
        }

        DrawerLayout drawer = activityMainBinding.drawer;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}