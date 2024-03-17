package com.ptn.dop_app.ui.detailDog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ptn.dop_app.R;
import com.ptn.dop_app.databinding.FragmentDetailDogsBinding;
import com.ptn.dop_app.model.DogBreed;
import com.squareup.picasso.Picasso;


public class DetailDogsFragment extends Fragment {
    private DogBreed dogBreed;
    private FragmentDetailDogsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dogBreed = (DogBreed) getArguments().getParcelable("dogBreed");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_detail_dogs, null, false);
        View viewRoot = binding.getRoot();
        binding.setDog(dogBreed);

        Picasso.get().load(dogBreed.getUrl()).into(binding.ivAvatar);

        return viewRoot;
    }
}