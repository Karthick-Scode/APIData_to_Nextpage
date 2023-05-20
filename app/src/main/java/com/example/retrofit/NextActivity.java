package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class NextActivity extends AppCompatActivity {

    CircleImageView imageView;
    TextView first, last, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        imageView = findViewById(R.id.n_image);
        first = findViewById(R.id.n_first);
        last = findViewById(R.id.n_last);
        email = findViewById(R.id.n_email);

        first.setText(getIntent().getExtras().getString("first"));
        last.setText(getIntent().getExtras().getString("second"));
        email.setText(getIntent().getExtras().getString("mail"));

        Picasso.with(getApplicationContext())
                .load(getIntent().getExtras().getString("image"))
                .placeholder(R.drawable.user)
                .fit()
                .into(imageView);

    }
}