package com.example.sqlitelistview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String JSON_URL="https://api.androidhive.info/json/contacts.json";
    ListView listView;
    DatabaseHelper databaseHelper;
    ArrayList<Contacts> contactsArrayList;
    ListViewAdapter listViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        contactsArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        getDataFromJSON();

        viewData();

       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //Toast.makeText(getApplicationContext(),"Longggggggggggggggg",Toast.LENGTH_LONG).show();
                final String phone =contactsArrayList.get(i).phone;
                String name = contactsArrayList.get(i).name;
               String ans = databaseHelper.getNamee(phone);
              // Log.i("nameeeeeeeeeeeeeeeeee",ans);
                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + name);
                final int positionToRemove = i;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //MyDataObject.remove(positionToRemove);
                        contactsArrayList.remove(positionToRemove);
                        listViewAdapter.notifyDataSetChanged();
                        databaseHelper.deleteItem(phone);
                        int ans = databaseHelper.getProfilesCount();
                        Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
                    }});
                adb.show();
               return false;
            }
        });




    }

    private void viewData()
    {
        contactsArrayList = databaseHelper.getAllData();
        listViewAdapter =  new ListViewAdapter(contactsArrayList, this);
        listView.setAdapter(listViewAdapter);
    }

    private void getDataFromJSON()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                       // textView.setText(response);
                        databaseHelper.deleteAllData();
                        //Log.i("ddddddddddddddddddd",response);
                        try {
                            JSONArray jsonArray =new JSONArray(response);

                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject dataObject = jsonArray.getJSONObject(i);
                                String name = dataObject.getString("name");
                                String img = dataObject.getString("image");
                                String phone = dataObject.getString("phone");
                                //Log.i("ggggggggggggggggg: ",name);
                                databaseHelper.insertData(name,img,phone);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
       // int len = databaseHelper.getProfilesCount();
       // Log.i("lengthhhhhhhhhhhhhhhhhh",String.valueOf(len));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
