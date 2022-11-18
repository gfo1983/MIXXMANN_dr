package com.gfo.mixxmann.ui.connections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gfo.mixxmann.BTAdapter;
import com.gfo.mixxmann.MainActivity;
import com.gfo.mixxmann.R;
import com.gfo.mixxmann.databinding.FragmentConnectionsBinding;

public class ConnectionsFragment extends Fragment {

    private FragmentConnectionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectionsViewModel connectionsViewModel =
                new ViewModelProvider(this).get(ConnectionsViewModel.class);
        binding = FragmentConnectionsBinding.inflate(inflater, container, false);
//test commit

//       final TextView textView = binding.textGallery;
        //connectionsViewModel.setDeviceList().;
        //ConnectionsViewModel. .observe(getViewLifecycleOwner(), textView::setText);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}