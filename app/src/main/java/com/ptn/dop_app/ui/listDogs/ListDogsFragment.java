package com.ptn.dop_app.ui.listDogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ptn.dop_app.databinding.FragmentListDogsBinding;
import com.ptn.dop_app.model.DogBreed;
import com.ptn.dop_app.viewmodel.DogsAPIService;
import com.ptn.dop_app.viewmodel.DogsAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListDogsFragment extends Fragment {

    private FragmentListDogsBinding binding;
    private DogsAPIService dogsAPIService;
    private RecyclerView rvDogs;
    private ArrayList<DogBreed> dogBreeds;
    private DogsAdapter dogsAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable(); // Quản lý subscriptions

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListDogsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDogs = binding.rvDogs;
        dogBreeds = new ArrayList<>();
        dogsAdapter = new DogsAdapter(dogBreeds);
        rvDogs.setAdapter(dogsAdapter);
        rvDogs.setLayoutManager(new LinearLayoutManager(getContext()));
        // Hiển thị spinner
        binding.progressBar.setVisibility(View.VISIBLE);

        dogsAPIService = new DogsAPIService();
        DisposableSingleObserver<List<DogBreed>> disposableObserver = dogsAPIService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> breeds) {
                        if (isAdded()) { // Kiểm tra xem Fragment có được gắn vào Activity hay không
                            binding.progressBar.setVisibility(View.GONE);
                            dogBreeds.clear();
                            dogBreeds.addAll(breeds);
                            dogsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        if (getView() != null) {
                            binding.progressBar.setVisibility(View.GONE);

                            Snackbar.make(getView(), "Error: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

        compositeDisposable.add(disposableObserver); // Quản lý subscription
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear(); // Hủy các subscriptions khi View bị hủy
        binding = null;
    }
}
