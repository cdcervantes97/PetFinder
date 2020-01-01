package com.petfinder.ui.find;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.petfinder.MainActivity;
import com.petfinder.Pet;
import com.petfinder.R;
import com.petfinder.RoundedTransformation;
import com.petfinder.ui.chat.SendFragment;

public class FindPetFragment extends Fragment {
    private List<Pet> pets;
    private petAdapter petAdapter;
    private ImageView petImage;
    private TextView petSpecies;
    private TextView petBreed;
    private TextView petSize;
    private TextView petColor;
    private TextView petUrgency;
    private TextView petLocation;
    private TextView petFoundDate;
    private TextView petPostDate;
    private TextView petDescription;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_find, container, false);

        MainActivity activity = (MainActivity) getActivity();
        this.pets = activity.getPetsArr();

        final ListView listview = root.findViewById(R.id.pet_listview);
        petAdapter adapter = activity.getPetAdapter();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> val, View view, int pos, long id) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_pop_enter_anim);
                view.startAnimation(anim);
                viewPet(pos);
            }
        });

        return root;
    }

    private void viewPet(int pos) {
        final Pet pet = this.pets.get(pos);
        AlertDialog.Builder itemBuilder = new AlertDialog.Builder(getContext());
        View petView = getLayoutInflater().inflate(R.layout.view_pet_dialog, null);

        this.petImage = petView.findViewById(R.id.dialog_image);
        this.petSpecies = petView.findViewById(R.id.dialog_species);
        this.petBreed = petView.findViewById(R.id.dialog_breed);
        this.petSize = petView.findViewById(R.id.dialog_size);
        this.petColor = petView.findViewById(R.id.dialog_color);
        this.petUrgency = petView.findViewById(R.id.dialog_urgency);
        this.petLocation = petView.findViewById(R.id.dialog_location);
        this.petLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "google.navigation:q=" + petLocation.getText().toString().replace(" ", "+");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);
            }
        });

        this.petFoundDate = petView.findViewById(R.id.dialog_found_date);
        this.petPostDate = petView.findViewById(R.id.dialog_post_date);
        this.petDescription = petView.findViewById(R.id.dialog_description);

        itemBuilder.setTitle(pet.getTitle());

        loadImageFromUrl("http://petfinderapp.x10host.com/pet_images/"+ pet.getId() +"/main.jpg");
        //loadImageFromUrl("http://192.168.15.8/petfinder/pet_images/"+ pet.getId() +"/main.jpg");
        this.petSpecies.setText(pet.getSpecies());
        this.petBreed.setText(pet.getBreed());
        this.petSize.setText(pet.getSize());
        this.petColor.setText(pet.getColor());
        this.petUrgency.setText(pet.getIsUrgent());

        SpannableString locationSpan = new SpannableString(pet.getLocation());
        locationSpan.setSpan(new UnderlineSpan(), 0, locationSpan.length(), 0);
        this.petLocation.setText(locationSpan);

        this.petFoundDate.setText(parseDate(pet.getFoundDate()));
        this.petPostDate.setText(parseDateTime(pet.getPostDate()));
        this.petDescription.setText(pet.getDescription());

        itemBuilder.setCancelable(false)
                .setPositiveButton("Contact", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SendFragment newSendFragment = new SendFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment, newSendFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                        //Log.d("SELECTED OPTION:", navigationView.getMenu().getItem(1).toString());
                        navigationView.getMenu().getItem(3).setChecked(true);
                    }
                }).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        itemBuilder.setView(petView);
        AlertDialog dialog = itemBuilder.create();
        dialog.show();
    }

    private void loadImageFromUrl(String url) {
        Picasso.with(getContext()).load(url).transform(new RoundedTransformation(30,0)).into(this.petImage);
    }

    private String parseDate(String sqlDate) {
        String month = sqlDate.substring(5,7);
        String day = sqlDate.substring(8,10);
        String year = sqlDate.substring(0,4);
        return month + "/" + day + "/" + year;
    }

    private String parseDateTime(String sqlDate) {
        String month = sqlDate.substring(5,7);
        String day = sqlDate.substring(8,10);
        String year = sqlDate.substring(0,4);
        String time = sqlDate.substring(11,16);
        return month + "/" + day + "/" + year + " - " + time;
    }
}