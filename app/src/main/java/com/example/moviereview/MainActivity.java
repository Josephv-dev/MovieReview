package com.example.moviereview;

import android.content.Intent;
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
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    //Variable
    EditText movieInput;
    Spinner genre;
    RatingBar ratingBar;
    Button submitBtn, viewBtn;
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
        viewBtn = findViewById(R.id.viewBtn);

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

            if(movieName.isEmpty()){
                Toast.makeText(this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
            }else{
                saveMovieData(movieName, genreChoice, ratingValue);
                // This tells the code: "Go get the Primary Text color defined in the current theme"
                addMovieToDisplay(movieName, genreChoice, ratingValue);
                movieInput.setText("");
                ratingBar.setRating(0);
            }
        });

        viewBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewReviewsActivity.class);
            startActivity(intent);
        });
    }

    //This Method help to save the data
    private void saveMovieData(String name, String genre, float rating){
        SharedPreferences pref = getSharedPreferences("MoviePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String fullReview = "🎬" + name + " ("+ genre +") - " + rating +"stars";

        String existingHistory = pref.getString("history", "");

        String updatedHistory;
        if (existingHistory.isEmpty()) {
            updatedHistory = fullReview;
        } else {
            updatedHistory = existingHistory + "\n" + fullReview;
        }

        editor.putString("History", updatedHistory);
        editor.apply();
        Toast.makeText(this, "Review Saved", Toast.LENGTH_SHORT).show();
    }

    private void addMovieToDisplay(String name, String genre, float rating){
        TextView movieEntry = new TextView(this);
        movieEntry.setText("🎬" + name + " ("+ genre +") - " + "⭐" + rating +"stars");
        movieEntry.setTextSize(18);
        savedContainer.addView(movieEntry);
    }
}