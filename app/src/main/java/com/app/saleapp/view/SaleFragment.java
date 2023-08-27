package com.app.saleapp.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.saleapp.R;
import com.app.saleapp.constants.Constants;
import com.app.saleapp.databinding.FragmentSaleBinding;
import com.app.saleapp.utils.DialogUtils;
import com.app.saleapp.viewmodel.SaleViewModel;

public class SaleFragment extends Fragment {
    private FragmentSaleBinding binding;
    private SaleViewModel saleViewModel;

    public SaleFragment() {
        // Required empty public constructor
    }
    public static SaleFragment newInstance() {
        return new SaleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sale, container, false);
        binding.setViewModel(saleViewModel);
        binding.setLifecycleOwner(this);

        saleViewModel.productSuccess.observe(getViewLifecycleOwner(), this::dataControl);

        saleViewModel.initializeReceiver(requireContext());


        return binding.getRoot();
    }

    private void dataControl(Boolean success) {
        if (success) {
            saleViewModel.sendToRegistry(requireContext());
        } else {
            DialogUtils.showDialog(requireContext(), Constants.invalidValue);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saleViewModel.releaseReceiver(requireContext());
    }

}