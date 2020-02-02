package com.nith.appteam.nimbus2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nith.appteam.nimbus2020.Models.TalkModel;
import com.nith.appteam.nimbus2020.Models.WorkshopModel;
import com.nith.appteam.nimbus2020.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_workshop_details extends AppCompatActivity {
    private WorkshopModel workshopModel;
    private TextView nameDetWor,infoDetWor,venueDetWor,dateDetWor,tupeWor;
    private Button regDetWOr;
    private CircleImageView imgDetWor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop_details);
        workshopModel=(WorkshopModel) getIntent().getSerializableExtra("workshop");
        setUpUI();
        getMovieDetails();
        regDetWOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oprnURLWor(workshopModel.getUrlWor());
            }
        });


    }

    private void oprnURLWor(String regURL) {
        Uri uri=Uri.parse(regURL);
        Intent launch= new Intent(Intent.ACTION_VIEW,uri);
        startActivity(launch);
    }

    private void getMovieDetails() {
        if(workshopModel!=null) {
            nameDetWor.setText(workshopModel.getNameWor());
            infoDetWor.setText(workshopModel.getInfoWor());
            venueDetWor.setText(workshopModel.getVenueWor());
            dateDetWor.setText(workshopModel.getDateWor());
            tupeWor.setText(workshopModel.getTypeWor());
            Picasso.with(getApplicationContext()).load(workshopModel.getImageWor()).placeholder(android.R.drawable.ic_btn_speak_now).into(imgDetWor);
        }


    }

    private void setUpUI() {

        nameDetWor=findViewById(R.id.NameIDDetWor);
        infoDetWor=findViewById(R.id.InfoIDDetWor);
        venueDetWor=findViewById(R.id.VenueIDDetWor);
        dateDetWor=findViewById(R.id.DateDetWor);
        regDetWOr=findViewById(R.id.registerDetWor);
        imgDetWor=findViewById(R.id.ImgDetWor);
        tupeWor=findViewById(R.id.workshopTypeIDDet);
    }
}


