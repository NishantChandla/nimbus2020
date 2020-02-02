package com.nith.appteam.nimbus2020.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nith.appteam.nimbus2020.Adapters.WorkshopRecyclerViewAdapter;
import com.nith.appteam.nimbus2020.Models.WorkshopModel;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.Constant;
import com.nith.appteam.nimbus2020.Utils.PrefsWorkshop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Workshops extends AppCompatActivity {
    private RecyclerView recyclerViewwor;
    private List<WorkshopModel> workshopList;
    private WorkshopRecyclerViewAdapter workshopRecyclerViewAdapter;
    private RequestQueue requestQueuework;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        requestQueuework = Volley.newRequestQueue(this);
        Toolbar toolbar = findViewById(R.id.toolbarworksop);
        setSupportActionBar(toolbar);

        FloatingActionButton fabwo = findViewById(R.id.fabworkshop);
        fabwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Workshops.this, Add_Workshop.class);
                startActivity(intent);

            }
        });
        recyclerViewwor = findViewById(R.id.recyclerViewWorkshop);
        recyclerViewwor.setHasFixedSize(true);
        recyclerViewwor.setLayoutManager(new LinearLayoutManager(this));
        workshopList = new ArrayList<>();
        PrefsWorkshop prefsWorkshop = new PrefsWorkshop(this);
        String search = prefsWorkshop.getSearch();
        workshopList = getWorkshop(search);

    }

    public List<WorkshopModel> getWorkshop(String searchTerm)//all info returned from api
    {
        workshopList.clear();
        workshopRecyclerViewAdapter = new WorkshopRecyclerViewAdapter(this, workshopList);
        recyclerViewwor.setAdapter(workshopRecyclerViewAdapter);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                Constant.Url + searchTerm, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Log.d("Response",response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject workshopObj= response.getJSONObject(i);
                        WorkshopModel workshop=new WorkshopModel();
//                        talk.setName("Aysuh KAusnldjhlkhfkllnewlfnlwenflkjewlkjfljwhekjksdjkjhkuhkjhkjsdhlehlkjhalhldhll");
//                        talk.setVenue("LEcture aHAljewnfkljcnkjhfewkkjhefkjwhkfjwkejfhkwehkfhkwejnfkll");
                 //      workshop.setUrlWor("https://github.com/appteam-nith/nimbus2019");
//                        talk.setInfo("HE is veryhlhfeldijvoikbfewkjbkfjwkejfkjwejeovijoeijvoeijdvoijeoijeovjioejioeijvovjoeidjvlkdsnlkvn jsndoviejoiejvoljkdlkjvoeijvoiejovijdokjdeoivjolj");
//                        talk.setDate("19 2022002345453453453450 2");
                        workshop.setNameWor(workshopObj.getString("name"));
                        workshop.setDateWor("On: " + workshopObj.getString("date"));
                        workshop.setImageWor(workshopObj.getString("image"));
                        workshop.setInfoWor(workshopObj.getString("info"));
                        workshop.setUrlWor(workshopObj.getString("regUrl"));
                        workshop.setVenueWor("Venue: " + workshopObj.getString("venue"));
                        workshop.setTypeWor("Type:" + workshopObj.getString("type"));
                        // Log.d("Talk",talk.getName());
//                       Log.d("date",talk.getDate());
                        workshopList.add(workshop);
                        workshopRecyclerViewAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());

            }
        });
        requestQueuework.add(jsonArrayRequest);


        return workshopList;
    }

}
