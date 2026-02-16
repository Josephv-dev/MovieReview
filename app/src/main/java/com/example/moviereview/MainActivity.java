package com.example.moviereview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Variable
    EditText movieInput;
    Spinner genre;
    RatingBar ratingBar;
    Button submitBtn;
    LinearLayout savedContainer;

    String movieName, genreChoice;
    Float ratingValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        movieInput= findViewById(R.id.editTextText);
        genre = findViewById(R.id.spinner);
        ratingBar = findViewById(R.id.ratingBar);
        submitBtn = findViewById(R.id.submitBtn);
        savedContainer = findViewById(R.id.savedMoviesContainer);

        String[] options = {"Action","Horror","Drama","Comedy","Romance","Sci-fi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, options);
        //Defines the layout for the item currently selected and visible in the closed Spinner box. It usually has less padding
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Defines the layout for the items inside the dropdown list after you click the Spinner.
        // This version usually has more padding and sometimes a radio-button-style selector to make it easier for fingers to tap.
        genre.setAdapter(adapter);

        submitBtn.setOnClickListener(v -> {
            movieName = movieInput.getText().toString();
            genreChoice = genre.getSelectedItem().toString();
            ratingValue = ratingBar.getRating();

            if(movieName.equalsIgnoreCase("")){
                Toast.makeText(this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Review Save Successful", Toast.LENGTH_SHORT).show();
                TextView movieEntry = new TextView(this);
                movieEntry.setText("🎬" + movieName + " ("+ genreChoice +") - " + ratingValue +"stars");
                movieEntry.setTextSize(18);
                // This tells the code: "Go get the Primary Text color defined in the current theme"
                int themeTextColor = com.google.android.material.color.MaterialColors.getColor(this, android.R.attr.textColorPrimary, android.graphics.Color.BLACK);
                movieEntry.setTextColor(themeTextColor);
                savedContainer.addView(movieEntry);
                movieInput.setText("");
                ratingBar.setRating(0);
            }
        });
    }
}