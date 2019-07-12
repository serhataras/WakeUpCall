package com.groupnamenotfoundexception.wakeupcall.app.adapters;

import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.groupnamenotfoundexception.wakeupcall.app.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by User on 23.7.2015.
 */
public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.PersonViewHolder> {

    ArrayList<PersonInformation> persons;

    public CreditsAdapter(){
        initializeInformation();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_credits_card, viewGroup, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder viewHolder, int i){
        viewHolder.personName.setText(persons.get(i).getName());
        viewHolder.personFaculty.setText(persons.get(i).getFaculty());
        viewHolder.personPhoto.setImageResource(persons.get(i).getPhotoId());
    }

    @Override
    public int getItemCount(){
        return persons.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView personName;
        TextView personFaculty;
        ImageView personPhoto;

        PersonViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personFaculty = (TextView)itemView.findViewById(R.id.faculty);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }

    }

    public class PersonInformation {
        String name;
        String faculty;
        int photoId;

        PersonInformation(String name, String faculty, int photoId){
            this.name = name;
            this.faculty = faculty;
            this.photoId = photoId;

        }

        public String getName(){
            return this.name;
        }
        public String getFaculty(){
            return this.faculty;
        }
        public int getPhotoId(){
            return this.photoId;
        }
    }

    private void initializeInformation(){
        persons = new ArrayList<PersonInformation>();
        persons.add(new PersonInformation("Baris Poyraz", "CS Student", R.drawable.abc));
        persons.add(new PersonInformation("Caner Caliskaner", "CS Student", R.drawable.abc));
        persons.add(new PersonInformation("Efe Ulas Akay Seyitoglu", "CS Student", R.drawable.abc));
        persons.add(new PersonInformation("Irmak Turkoz", "CS Student", R.drawable.abc));
        persons.add(new PersonInformation("Serhat Aras", "CS Student", R.drawable.abc));
        persons.add(new PersonInformation("Mustafa Can Cavdar", "CS Grad Student - Our beloved TA", R.drawable.abc));
        persons.add(new PersonInformation("Ozcan Oztur", "Finally, The person who thought us everything", R.drawable.abc));
    }
}
