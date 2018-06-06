package com.example.android.form;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;
import static com.example.android.form.R.id.age;
import static com.example.android.form.R.id.qualification;

/**
 * Created by TUSHAR on 30-09-2017.
 */

public class NetworkStateChecker extends BroadcastReceiver {

    private Context context;
    private DatabaseHandler db;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        db = new DatabaseHandler(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced names
                Cursor cursor = db.getUnsyncedNames();
                if (cursor.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced name to MySQL
                        saveName(
                                cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_FIRST_NAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LAST_NAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_AGE)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_QUALIFICATION))

                        );
                    } while (cursor.moveToNext());
                }
                getName();
            }
        }

    }

    private void saveName(final int id, final String firstname, final String lastname, final String age, final String qualification) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //updating the status in sqlite
                                db.updateNameStatus(id, MainActivity.NAME_SYNCED_WITH_SERVER);

                                //sending the broadcast to refresh the list
                                context.sendBroadcast(new Intent(MainActivity.DATA_SAVED_BROADCAST));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("FirstName", firstname);
                params.put("LastName", lastname);
                params.put("Age", age);
                params.put("Qualification", qualification);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    private void getName() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.URL_GET_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("getusers", response);
                            JSONArray arr = new JSONArray(response);
                            List<String> allNames = new ArrayList<String>();

                            for (int i=0; i<arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String name = obj.getString("FirstName");
                                //allNames.add(name);
                                //Log.i("name" + i, name);
                                db.addClient(new ClientInfo(obj.getString("FirstName"), obj.getString("LastName"), obj.getString("Age"), obj.getString("Qualification"), MainActivity.NAME_SYNCED_WITH_SERVER));
                                updateMysqlStatus(obj.getInt("Id"), 1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    private void updateMysqlStatus(final int id, final int status) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.URL_UPDATE_SYNC_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", ""+id);
                params.put("Status", ""+status);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}
