package com.nith.appteam.nimbus2020.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nith.appteam.nimbus2020.Activities.Add_talk_details;
import com.nith.appteam.nimbus2020.Activities.Add_workshop_details;
import com.nith.appteam.nimbus2020.Models.TalkModel;
import com.nith.appteam.nimbus2020.Models.WorkshopModel;
import com.nith.appteam.nimbus2020.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkshopRecyclerViewAdapter extends
        RecyclerView.Adapter<WorkshopRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<WorkshopModel> workshopList;

    public WorkshopRecyclerViewAdapter(Context context, List<WorkshopModel> workshop) {
        this.context = context;
        workshopList = workshop;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_info, parent,
                false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkshopModel workshop=workshopList.get(position);
        String imageLinkWor=workshop.getImageWor();
        //holder.regUrlWor.setText(workshop.getUrlWor());
        //holder.infoWor.setText(workshop.getInfoWor());
        holder.dateWor.setText(workshop.getDateWor());
        holder.venueWor.setText(workshop.getVenueWor());
        holder.nameWor.setText(workshop.getNameWor());
        //holder.typeWor.setText(workshop.getTypeWor());
        Picasso.with(context).load(imageLinkWor).placeholder(android.R.drawable.ic_btn_speak_now).into(holder.imgSpkrWork);
    }


    @Override
    public int getItemCount() {
        return workshopList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nameWor,dateWor,venueWor;
        ImageView imgSpkrWork;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);
            context=ctx;
            nameWor=(TextView) itemView.findViewById(R.id.workshopNameID);
            imgSpkrWork=(ImageView) itemView.findViewById(R.id.WorkshopImageID);
            venueWor=(TextView) itemView.findViewById(R.id.workshopVenueID);
            dateWor=(TextView)itemView.findViewById(R.id.WorkshopDate);
            //infoWor=(TextView) itemView.findViewById(R.id.workshopInfoID);
            //regUrlWor=(TextView) itemView.findViewById(R.id.regURLWor);
            //typeWor=(TextView)itemView.findViewById(R.id.workshopTypeID);
            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    WorkshopModel workshopModel= workshopList.get(getAdapterPosition());
                    Intent intent=new Intent(context, Add_workshop_details.class);
                    intent.putExtra("workshop",workshopModel);
                    ctx.startActivity(intent);
                }

            });

        }

        @Override
        public void onClick(View view) {

        }
    }


}
