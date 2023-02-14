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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fa_sonia_c0872364_android1.adapter.FirstFragmentAdapter;
import com.example.fa_sonia_c0872364_android1.databinding.FragmentFirstBinding;
import com.example.fa_sonia_c0872364_android1.model.Product;

import java.util.List;

public class FirstFragment extends Fragment implements FirstFragmentAdapter.OnItemClickListener{

    private FragmentFirstBinding binding;

    private List<Product> categoryList;
    private FirstFragmentAdapter adapter;
    //private MainViewModel viewModel;
    private AlertDialog dialog;

    private RecyclerView recyclerView;

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
        //viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        setCategoryListRecyclerView();
        binding.fabProduct.setOnClickListener(v->{
            showAddProductModal();
        });

//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
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

    }

    // set adapter

    private void setCategoryListRecyclerView(){
        //categoryList = viewModel.getAllProducts();
//        adapter = new ProductListRecyclerViewAdapter(categoryList, getContext(), this, viewModel);
//        recyclerView = binding.categoryRecycler;
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL,false));
//        recyclerView.setAdapter(adapter);
    }

    // add new category dialog

    private void showAddProductModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.add_product_dialog, null);
        builder.setView(view);
        dialog = builder.create();
        final EditText product_name = view.findViewById(R.id.product_name);
        final EditText product_desc = view.findViewById(R.id.product_desc);
        final EditText product_price = view.findViewById(R.id.product_price);
        Button btnAdd = view.findViewById(R.id.add_button);
        Button btnCancel = view.findViewById(R.id.cancel_button);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the new category to the database or data source
                String productTitle = product_name.getText().toString();
                Product product = new Product();
                product.setName(productTitle);
                //viewModel.addProduct(product);
                dialog.dismiss();
                Toast.makeText(getContext(), "Product added successfully!", Toast.LENGTH_SHORT).show();
                updateCategoryList();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the modal dialog
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}