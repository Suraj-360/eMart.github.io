package com.suraj.emart.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.suraj.emart.R;
import com.suraj.emart.activities.CategoryActivity;
import com.suraj.emart.databinding.ItemCategoriesBinding;
import com.suraj.emart.models.Category;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    ArrayList<Category> categories;
    Context context;

    public CategoryAdapter(ArrayList<Category> categories, Context context)
    {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categories,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.itemCategoriesBinding.catLabel.setText(Html.fromHtml(category.getName(),0));
        Log.e("Icon 2  ", category.getIcon());
        GlideToVectorYou.init().with(context).load(Uri.parse(category.getIcon()),holder.itemCategoriesBinding.imgCategories);

        //holder.itemCategoriesBinding.imgCategories.setBackgroundColor(Color.parseColor(category.getColor()));*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent catPro = new Intent(context, CategoryActivity.class);
                catPro.putExtra("name",category.getName());
                context.startActivity(catPro);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        ItemCategoriesBinding itemCategoriesBinding;
        public CategoryViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemCategoriesBinding = ItemCategoriesBinding.bind(itemView);
        }
    }
}