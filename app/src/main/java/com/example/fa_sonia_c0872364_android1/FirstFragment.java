package com.example.fa_sonia_c0872364_android1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fa_sonia_c0872364_android1.adapter.FirstFragmentAdapter;
import com.example.fa_sonia_c0872364_android1.databinding.AddProductDialogBinding;
import com.example.fa_sonia_c0872364_android1.databinding.FragmentFirstBinding;
import com.example.fa_sonia_c0872364_android1.model.MainModelView;
import com.example.fa_sonia_c0872364_android1.model.Product;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements FirstFragmentAdapter.OnItemClickListener{

    private FragmentFirstBinding binding;

    private List<Product> categoryList;
    private FirstFragmentAdapter adapter;
    private MainModelView viewModel;
    private AlertDialog dialog;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainModelView.class);
        setCategoryListRecyclerView();
        binding.fabProduct.setOnClickListener(v->{
            showAddProductModal();
        });


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadRecyclerData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadRecyclerData(newText);
                return false;
            }
        });
//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    public void loadRecyclerData(String keyword){
        viewModel.getAllProducts(keyword).observe(getViewLifecycleOwner(),products -> {
            categoryList = products;
            adapter.setDataList(products);

            if(keyword.isEmpty()){
                binding.categoryToolbar.setTitle("All Products ("+products.size()+" available)");
            }
            else{
                binding.categoryToolbar.setTitle("Searched Products ("+products.size()+" available)");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCategoryList();
    }

    public void updateCategoryList(){
//        categoryList = viewModel.getAllProducts();
//        adapter.setDataList(viewModel.getAllProducts());
    }

    @Override
    public void onItemClick(int position) {
        Product product = categoryList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",product.getId());
        NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
    }

    // set adapter

    private void setCategoryListRecyclerView(){

        adapter = new FirstFragmentAdapter(requireContext(),this,viewModel);
        binding.categoryRecycler.setAdapter(adapter);
        binding.categoryRecycler.setHasFixedSize(true);
        binding.categoryRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL,false));

        loadRecyclerData("");

    }

    // add new category dialog

    private void showAddProductModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AddProductDialogBinding addProductDialogBinding = AddProductDialogBinding.inflate(LayoutInflater.from(requireContext()),binding.getRoot(),false);
        builder.setView(addProductDialogBinding.getRoot());
        dialog = builder.create();

        addProductDialogBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the new category to the database or data source
                String name = addProductDialogBinding.productName.getText().toString();
                String desc = addProductDialogBinding.productDesc.getText().toString();
                String price = addProductDialogBinding.productPrice.getText().toString();
                String lat = addProductDialogBinding.providorLat.getText().toString();
                String lng = addProductDialogBinding.providorLng.getText().toString();

                Product product = new Product();
                product.setName(name);
                product.setDescription(desc);
                product.setPrice(Double.parseDouble(price));
                product.setProviderLat(Double.parseDouble(lat));
                product.setProviderLng(Double.parseDouble(lng));

                viewModel.addProduct(product);
                dialog.dismiss();
                Toast.makeText(getContext(), "Product added successfully!", Toast.LENGTH_SHORT).show();
                updateCategoryList();
            }
        });

        addProductDialogBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the modal dialog
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}