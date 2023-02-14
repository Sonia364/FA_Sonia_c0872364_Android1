package com.example.fa_sonia_c0872364_android1.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fa_sonia_c0872364_android1.R;
import com.example.fa_sonia_c0872364_android1.databinding.AddProductDialogBinding;
import com.example.fa_sonia_c0872364_android1.databinding.ProductRowBinding;
import com.example.fa_sonia_c0872364_android1.model.MainModelView;
import com.example.fa_sonia_c0872364_android1.model.Product;

import java.util.List;

public class FirstFragmentAdapter extends RecyclerView.Adapter<FirstFragmentAdapter.ViewHolder> {

    final private Context context;
    private OnItemClickListener onItemClickListener;
    private List<Product> productList ;

    MainModelView viewModel;
    private AlertDialog dialog;


    public FirstFragmentAdapter(Context context,OnItemClickListener onItemClickListener, MainModelView viewModel){
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.viewModel = viewModel;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductRowBinding binding = ProductRowBinding.inflate(
                LayoutInflater.from(context),parent,false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(productList.get(position));
//        int randomIndex = new Random().nextInt(backgroundImages.length);
//        holder.itemView.setBackgroundResource(backgroundImages[randomIndex]);


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Show the action popup
                int position = holder.getAdapterPosition();
                showPopupMenu(v,position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ProductRowBinding binding;
        public ViewHolder(ProductRowBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Product model){
            binding.cardViewAdapter.setOnClickListener(this);
            binding.productName.setText(model.getName());
            binding.productDescription.setText(model.getName());
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        @Nullable
        View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

        void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState);

        void onItemClick(int position);
    }

    public void setDataList(List<Product> products) {
        this.productList = products;
        notifyDataSetChanged();
    }

    // pop menu

    private void showPopupMenu(View view, int position) {
        Product product = productList.get(position);
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.inflate(R.menu.product_context_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        // Handle the edit action
                        showEditCategoryDialog(product);
                        return true;
                    case R.id.action_delete:
                        // Handle the delete action
                        showDeleteCategoryDialog(product);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    // edit dialog box

    private void showEditCategoryDialog(Product product) {

        // Create the dialog using an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Inflate the layout for the dialog
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.product_edit_dialog, null);
        AddProductDialogBinding dialogView = AddProductDialogBinding.inflate(LayoutInflater.from(context));

        // Get references to the dialog's views
        dialogView.tvTitle.setText("Update Product");
        dialogView.productName.setText(product.getName());
        dialogView.productDesc.setText(product.getDescription());
        dialogView.productPrice.setText(product.getPrice()+"");
        dialogView.providorLat.setText(product.getProviderLat()+"");
        dialogView.providorLng.setText(product.getProviderLng()+"");

        // Set the dialog's view
        builder.setView(dialogView.getRoot());

        // Show the dialog
        Dialog dialogEdit = builder.show();

        // Add a positive button for saving the changes
        dialogView.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the category object with the new data
                String name = dialogView.productName.getText().toString();
                String desc = dialogView.productDesc.getText().toString();
                String price = dialogView.productPrice.getText().toString();
                String lat = dialogView.providorLat.getText().toString();
                String lng = dialogView.providorLng.getText().toString();

                product.setName(name);
                product.setDescription(desc);
                product.setPrice(Double.parseDouble(price));
                product.setProviderLat(Double.parseDouble(lat));
                product.setProviderLng(Double.parseDouble(lng));

//                int id = product.getId();
                viewModel.updateProduct(product);
                dialogEdit.dismiss();
                Toast.makeText(context, "Product updated successfully!", Toast.LENGTH_SHORT).show();
                // Notify the adapter that the data has changed
                //setDataList(viewModel.getAllProducts());
            }
        });

        // Add a negative button for cancelling the changes
        dialogView.cancelButton.setOnClickListener(v -> {
            // Perform action on click
                dialogEdit.dismiss();
        });


    }

    // delete dialog box
    private void showDeleteCategoryDialog(Product product) {

        // Create the dialog using an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.product_delete_dialog, null);
        // Set the dialog's view
        builder.setView(dialogView);
        dialog = builder.create();

        // Get references to the dialog's views
        Button btnNegative = dialogView.findViewById(R.id.btn_negative);
        Button btnPositive = dialogView.findViewById(R.id.btn_positive);
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click
                dialog.dismiss();
            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click
                viewModel.deleteProduct(product);
                dialog.dismiss();
                Toast.makeText(context, "Product removed successfully!", Toast.LENGTH_SHORT).show();
                // Notify the adapter that the data has changed
                //setDataList(viewModel.getAllProducts());
            }
        });

        dialog.show();
    }

    private void setBackgroundOpacity(View view, float opacity) {
        AlphaAnimation alpha = new AlphaAnimation(opacity, opacity);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        view.startAnimation(alpha);
    }
}
