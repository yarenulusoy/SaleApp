package com.app.saleapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.app.saleapp.R;
import com.app.saleapp.constants.Constants;
import com.app.saleapp.databinding.ActivitySaleBinding;
import com.app.saleapp.utils.DialogUtils;
import com.app.saleapp.viewmodel.SaleViewModel;

import android.os.Bundle;

public class SaleActivity extends AppCompatActivity {
    private ActivitySaleBinding binding;
    private SaleViewModel saleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        initViews();
    }

    private void initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sale);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);

        binding.setViewModel(saleViewModel);
        binding.setLifecycleOwner(this);

        saleViewModel.productSuccess.observe(this, this::dataControl);

        saleViewModel.initializeReceiver(this);
    }

    private void dataControl(Boolean success) {
        if (success) {
            saleViewModel.sendToRegistry(SaleActivity.this);
        } else {
            DialogUtils.showDialog(this, Constants.invalidValue);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saleViewModel.releaseReceiver(this);
    }

}




