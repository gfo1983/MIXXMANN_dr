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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gfo.mixxmann.BTAdapter;
import com.gfo.mixxmann.BTDeviceAdapter;
import com.gfo.mixxmann.MainActivity;
import com.gfo.mixxmann.R;
import com.gfo.mixxmann.databinding.FragmentConnectionsBinding;

import java.util.ArrayList;

public class ConnectionsFragment extends Fragment {

    private FragmentConnectionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConnectionsBinding.inflate(inflater, container, false);
        ConnectionsViewModel connectionModel = new ViewModelProvider(getActivity()).get(ConnectionsViewModel.class);
        connectionModel.getDeviceList().observe(getActivity(), new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {
                ArrayAdapter adapter = new BTDeviceAdapter(getActivity(), arrayList);
                binding.deviceList.setAdapter(adapter);
                if (arrayList.size()>0) {
                    binding.notnyngfound.setVisibility(View.GONE);
                    binding.notnyngfound1.setVisibility(View.GONE);
                } else {
                    binding.notnyngfound.setVisibility(View.VISIBLE);
                    binding.notnyngfound1.setVisibility(View.VISIBLE);
                }

            }
        });
        connectionModel.getButtontext().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.buttonrefresh.setText(s);
            }
        });
        binding.buttonrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).discoverBT();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}