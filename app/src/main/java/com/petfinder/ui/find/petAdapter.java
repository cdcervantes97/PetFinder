package com.petfinder.ui.find;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.petfinder.Pet;
import com.petfinder.R;
import com.petfinder.RoundedTransformation;

public class petAdapter extends ArrayAdapter<Pet> {

    private static class ViewHolder {
        private TextView title;
        private TextView species;
        private TextView isurgent;
        private TextView foundDate;
        private ImageView image;
        private Pet pet;
    }

    private Context inpContext;
    private List<Pet> petList = new ArrayList<>();

    public petAdapter(Context context, List<Pet> pets) {
        super(context, 0, pets);
        this.inpContext = context;
        this.petList = pets;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup val){
        View petListView = convertView;
        ViewHolder holder = null;

        if(petListView == null) {
            petListView = LayoutInflater.from(inpContext).inflate(R.layout.pet_row,val,false);

            holder = new ViewHolder();
            holder.title = (TextView) petListView.findViewById(R.id.petTitle);
            holder.species = (TextView) petListView.findViewById(R.id.pet_species);
            holder.isurgent = (TextView) petListView.findViewById(R.id.pet_urgent);
            holder.image = (ImageView) petListView.findViewById(R.id.pet_image);
            holder.foundDate = (TextView) petListView.findViewById(R.id.pet_found_date);

            petListView.setTag(holder);
        } else {
            holder = (ViewHolder) petListView.getTag();
        }

        holder.pet = this.petList.get(pos);
        holder.title.setText(holder.pet.getTitle());
        holder.species.setText(holder.pet.getSpecies());
        holder.isurgent.setText(holder.pet.getIsUrgent());
        holder.foundDate.setText(parseDate(holder.pet.getFoundDate()));

        String url = "http://petfinderapp.x10host.com/pet_images/"+ holder.pet.getId() +"/main.jpg";
        Picasso.with(getContext()).load(url).transform(new RoundedTransformation(30,0)).into(holder.image);


        return petListView;
    }

    private String parseDate(String sqlDate) {
        String month = sqlDate.substring(5,7);
        String day = sqlDate.substring(8,10);
        String year = sqlDate.substring(0,4);
        return month + "/" + day + "/" + year;
    }
}
