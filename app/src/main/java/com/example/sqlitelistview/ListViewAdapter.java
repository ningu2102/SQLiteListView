package com.example.sqlitelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sqlitelistview.R.layout.movie_layout_view;


public class ListViewAdapter extends ArrayAdapter<Contacts>
{
    private List<Contacts>contactsList;
    private Context context;


    public ListViewAdapter(List<Contacts> contactsList, Context context) {
        super(context, movie_layout_view, contactsList);

        this.context = context;
        this.contactsList=contactsList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(context);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(movie_layout_view, null, true);

        //getting text views
        TextView textViewName = listViewItem.findViewById(R.id.name);
        TextView textViewPhone = listViewItem.findViewById(R.id.phone);
       // ImageView imageViewPoster = listViewItem.findViewById(R.id.photo);
        CircleImageView circleImageView = listViewItem.findViewById(R.id.photoo);


        Contacts contacts = contactsList.get(position);

        textViewName.setText(contacts.getName());
        textViewPhone.setText(contacts.getPhone());
        Picasso.with(context).load(contacts.getImg()).into(circleImageView);


        //returning the listitem
        return listViewItem;
    }

}
