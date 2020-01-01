package com.petfinder.ui.post;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.petfinder.MainActivity;
import com.petfinder.R;

import static android.app.Activity.RESULT_OK;

public class PostPetFragment extends Fragment implements postFormData.AsyncResponse {

    private EditText petTitle;
    private EditText petSpecies;
    private EditText petBreed;
    private EditText petColor;
    private CheckBox petUrgency;
    private EditText petDescription;
    private EditText petFoundDate;
    private Spinner petSize;
    private Calendar cal;
    private DatePickerDialog dpd;
    private Uri imageUri;
    private String imagePath;
    private ImageView imageView2;
    private EditText petLocation;
    private String encodedImage;

    private String titleStr;
    private String speciesStr;
    private String breedStr;
    private String colorStr;
    private String urgencyStr;
    private String descriptionStr;
    private String foundDateStr;
    private String sizeStr;
    private String locationStr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        this.petTitle = root.findViewById(R.id.title_input);
        this.petSpecies = root.findViewById(R.id.species_input);
        this.petBreed = root.findViewById(R.id.breed_input);
        this.petColor = root.findViewById(R.id.color_input);
        this.petColor = root.findViewById(R.id.color_input);
        this.petUrgency = root.findViewById(R.id.is_urgent_input);
        this.petDescription = root.findViewById(R.id.description_input);
        this.petSize = root.findViewById(R.id.size_input);
        this.petFoundDate = root.findViewById(R.id.date_input);
        this.petLocation = root.findViewById(R.id.location_input);

        cal = Calendar.getInstance(TimeZone.getDefault());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        petFoundDate.setText((month + 1) + "/" +  day + "/" + year);

        this.imageView2 = root.findViewById(R.id.imageView2);

        Button pickDateButton = root.findViewById(R.id.date_pick_button);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                dpd = new DatePickerDialog((MainActivity) getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        petFoundDate.setText((month + 1) + "/" +  dayOfMonth + "/" + year);
                    }
                }, day, month, year);
                dpd.updateDate(year,month,day);
                dpd.show();
            }
        });

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Small");
        arrayList.add("Medium");
        arrayList.add("Large");
        ArrayAdapter<String> petSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        this.petSize.setAdapter(petSpinnerAdapter);

        ImageButton imageButton = root.findViewById(R.id.camera_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File fileDirectory = ((MainActivity) getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = null;
                try {
                    file = File.createTempFile(fileName, ".jpg", fileDirectory);
                    if(file != null) {
                        imageUri = FileProvider.getUriForFile(getContext(), "com.petfinder.fileprovider", file);
                        imagePath = file.getAbsolutePath();

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent,10);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Could not take picture.", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button submitButton = (Button) root.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleStr = petTitle.getText().toString();
                speciesStr = petSpecies.getText().toString();
                breedStr = petBreed.getText().toString();
                colorStr = petColor.getText().toString();
                urgencyStr = "No Medical Urgency";
                if(petUrgency.isChecked()) {
                    urgencyStr = "In Serious Medical Need";
                }
                descriptionStr = petDescription.getText().toString();
                foundDateStr = petFoundDate.getText().toString();
                sizeStr = petSize.getSelectedItem().toString();
                locationStr = petLocation.getText().toString();

                postFormData dataPoster = new postFormData(titleStr,speciesStr,breedStr,colorStr,urgencyStr,descriptionStr,foundDateStr,sizeStr,locationStr,encodedImage);
                dataPoster.delegate = PostPetFragment.this;
                dataPoster.execute();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == 10) {
                Bitmap selectedImage = BitmapFactory.decodeFile(imagePath);
                //Toast.makeText(getContext(), imageUri.toString(), Toast.LENGTH_SHORT).show();
                imageView2.setImageBitmap(selectedImage);

                encodedImage = encodeImage(selectedImage);
                Toast.makeText(getContext(), "Picture taken Successfully.", Toast.LENGTH_SHORT).show();
                Log.d("Encoded Image: ", encodedImage);
            }
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage.replace("\n", "");
    }

    @Override
    public void processFinish(String s) {
        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
        if(s.equals("Pet posted for adoption with success")) {
            //((MainActivity) getActivity()).updateView();
        }
    }
}