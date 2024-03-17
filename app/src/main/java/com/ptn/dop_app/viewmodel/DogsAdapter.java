package com.ptn.dop_app.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ptn.dop_app.R;
import com.ptn.dop_app.helper.OnSwipeTouchListener;
import com.ptn.dop_app.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> implements Filterable {

    private ArrayList<DogBreed> dogBreeds, dogsBreedsSearch;

    public DogsAdapter(ArrayList<DogBreed> dogBreeds) {
        this.dogBreeds = dogBreeds;
        dogsBreedsSearch = dogBreeds;
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
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString().toLowerCase();
                List<DogBreed> filteredDog = new ArrayList<>();

                if (!input.isEmpty()) {
                    for (DogBreed dog : dogsBreedsSearch) {
                        if (dog.getName().toString().toLowerCase().contains(input)) {
                            filteredDog.add(dog);
                        }
                    }
                } else {
                    filteredDog.addAll(dogsBreedsSearch);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredDog;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dogBreeds = new ArrayList<>();
                dogBreeds.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDogName, tvDogOrigin;
        public ImageView imgDog, btnLike, btnUnlike;
        public LinearLayout layoutLike, layoutUnlike;

        LinearLayout layout1,layout2;
        TextView layoutName,layoutLifeSpan,layoutOrigin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDogName = itemView.findViewById(R.id.tvDogName);
            tvDogOrigin = itemView.findViewById(R.id.tvDogOrigin);
            imgDog = itemView.findViewById(R.id.imgDog);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnUnlike = itemView.findViewById(R.id.btnUnlike);
            layoutLike = itemView.findViewById(R.id.layoutLike);
            layoutUnlike = itemView.findViewById(R.id.layoutUnlike);

            layoutName=itemView.findViewById(R.id.layout2_name);
            layoutLifeSpan=itemView.findViewById(R.id.layout2_life_span);
            layoutOrigin=itemView.findViewById(R.id.layout2_origin);
            layout1=itemView.findViewById(R.id.layout1);
            layout2=itemView.findViewById(R.id.layout2);




            itemView.setOnTouchListener(new OnSwipeTouchListener() {
                @Override
                public void onClick() {
                    DogBreed dog = dogBreeds.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("dogBreed", dog);
                    Navigation.findNavController(itemView).navigate(R.id.detailDogsFragment, bundle);
                    super.onClick();
                }

                @Override
                public void onSwipeLeft() {
                    Log.d("SwipeLeft", "onSwipeLeft: " + getAdapterPosition());
                    if(layout1.getVisibility()==View.VISIBLE)
                    {
                        layout1.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        layout2.setVisibility(View.GONE);
                        layout1.setVisibility(View.VISIBLE);
                    }
                    super.onSwipeLeft();
                }
            });


            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layoutLike.getVisibility() == View.VISIBLE) {
                        layoutLike.setVisibility(View.GONE);
                        layoutUnlike.setVisibility(View.VISIBLE);
                    }
                }
            });
            btnUnlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layoutUnlike.getVisibility() == View.VISIBLE) {
                        layoutUnlike.setVisibility(View.GONE);
                        layoutLike.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }
}
