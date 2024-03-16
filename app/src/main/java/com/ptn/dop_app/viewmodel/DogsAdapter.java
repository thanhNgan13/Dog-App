package com.ptn.dop_app.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptn.dop_app.R;
import com.ptn.dop_app.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.ArrayList;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder>{

    private ArrayList<DogBreed> dogBreeds;
    public  DogsAdapter(ArrayList<DogBreed> dogBreeds){
        this.dogBreeds = dogBreeds;
    }
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public DogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dog_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsAdapter.ViewHolder holder, int position) {
        // Get element from your dataset at this position
        // Replace the contents of the view with that element
        DogBreed dogBreed = dogBreeds.get(position);
        holder.tvDogName.setText(dogBreed.getName());
        holder.tvDogOrigin.setText(dogBreed.getOrigin());
        Picasso.get().load(dogBreed.getUrl()).into(holder.imgDog);

    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDogName;
        public TextView tvDogOrigin;
        public ImageView imgDog;

        public TextView getTvDogName() {
            return tvDogName;
        }

        public void setTvDogName(TextView tvDogName) {
            this.tvDogName = tvDogName;
        }

        public TextView getTvDogOrigin() {
            return tvDogOrigin;
        }

        public void setTvDogOrigin(TextView tvDogOrigin) {
            this.tvDogOrigin = tvDogOrigin;
        }

        public ImageView getImgDog() {
            return imgDog;
        }

        public void setImgDog(ImageView imgDog) {
            this.imgDog = imgDog;
        }



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDogName = itemView.findViewById(R.id.tvDogName);
            tvDogOrigin = itemView.findViewById(R.id.tvDogOrigin);
            imgDog = itemView.findViewById(R.id.imgDog);
        }

    }
}
