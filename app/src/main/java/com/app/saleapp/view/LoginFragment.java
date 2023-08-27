package com.app.saleapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.saleapp.R;
import com.app.saleapp.constants.Constants;
import com.app.saleapp.databinding.FragmentLoginBinding;
import com.app.saleapp.utils.DialogUtils;
import com.app.saleapp.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;
    private NavController navController;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false);
        binding.setViewModel(loginViewModel);
        binding.setLifecycleOwner(this);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        loginViewModel.loginSuccess.observe(getViewLifecycleOwner(), this::login);
        return binding.getRoot();
    }

    private void login(Boolean success) {
        if (success) {
            navController.navigate(R.id.action_login_to_sale);
        } else {
            DialogUtils.showDialog(requireContext(), Constants.invalidValue);
        }
    }

}