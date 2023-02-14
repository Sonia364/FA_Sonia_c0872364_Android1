package com.example.fa_sonia_c0872364_android1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.fa_sonia_c0872364_android1.databinding.FragmentSecondBinding;
import com.example.fa_sonia_c0872364_android1.model.MainModelView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SecondFragment extends Fragment implements OnMapReadyCallback {

    private FragmentSecondBinding binding;
    int productId = 0;
    private MainModelView viewModel;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        productId = getArguments().getInt("id",0);

        viewModel = new ViewModelProvider(requireActivity()).get(MainModelView.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    void loadData(GoogleMap googleMap){
        viewModel.getProducts(productId).observe(getViewLifecycleOwner(),product -> {
            if(product!=null){
                LatLng position = new LatLng(product.getProviderLat(),product.getProviderLng());
                googleMap.addMarker(new MarkerOptions().title(product.getName()).position(position));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,10));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        loadData(googleMap);
    }
}