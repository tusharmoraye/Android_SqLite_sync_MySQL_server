package com.example.android.form;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.android.form.R.id.firstName;
import static com.example.android.form.R.id.lastName;

public class MainActivity extends AppCompatActivity {

    public static final String URL_SAVE_NAME = "http://192.168.1.122/form/save.php";
    public static final String URL_GET_NAME = "http://192.168.1.122/form/getusers.php";
    public static final String URL_UPDATE_SYNC_STATUS = "http://192.168.1.122/form/updatesyncsts.php";


    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    private List<ClientInfo> client_info;

    DatabaseHandler databaseHandler;

    public static final String DATA_SAVED_BROADCAST = "com.example.android.datasaved";
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button storeData = (Button) findViewById(R.id.storeData);
        Button showData = (Button) findViewById(R.id.showData);

        databaseHandler = new DatabaseHandler(this);

        storeData.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                EditText firstName = (EditText) findViewById(R.id.firstName);
                EditText lastName = (EditText) findViewById(R.id.lastName);
                EditText age = (EditText) findViewById(R.id.age);
                EditText qualification = (EditText) findViewById(R.id.qualification);
                if(firstName.getText().toString().equals("") || firstName.getText() == null || lastName.getText().toString().equals("") || lastName.getText() == null || age.getText().toString().equals("") || age.getText() == null || qualification.getText().toString().equals("") || qualification.getText() == null) {
                    Log.d("error", "Enter all fields");
                    return;
                }
                ClientInfo clientInfo = new ClientInfo(firstName.getText().toString(), lastName.getText().toString(), age.getText().toString(), qualification.getText().toString(), NAME_NOT_SYNCED_WITH_SERVER);
                saveToServer(clientInfo);
            }
        });

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ClientInfo> client_info = databaseHandler.getAllContacts();
                if(client_info.isEmpty()) {
                    Log.d("error", "No data to show");
                    return;
                }
                String info = "";
                for (ClientInfo cn : client_info) {
                    info += "\nId: " + cn.getID() + " ,First Name: " + cn.getFirstName() + " ,Last Name: " + cn.getLastName() + " ,Age: " + cn.getAge() + " ,Qualification: " + cn.getQualification() + " ,Status: " + cn.getStatus();
                }
                TextView displayData = (TextView) findViewById(R.id.displayData);
                displayData.setText(info);
                findViewById(R.id.left).setVisibility(View.VISIBLE);
                findViewById(R.id.right).setVisibility(View.GONE);
            }
        });

        findViewById(R.id.adduser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.left).setVisibility(View.GONE);
                findViewById(R.id.right).setVisibility(View.VISIBLE);
            }
        });





        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
                loadClientInfo();
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


//    // Options Menu (ActionBar Menu)
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    // When Options Menu is selected
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here.
//        int id = item.getItemId();
//        // When Sync action button is clicked
//        if (id == R.id.refresh) {
//            // Transfer data from remote MySQL DB to SQLite on Android and perform Sync
//            new NetworkStateChecker();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void loadClientInfo() {
        if(client_info != null)
            client_info.clear();
        List<ClientInfo> client_info = databaseHandler.getAllContacts();
        if(client_info.isEmpty()) {
            Log.d("error", "No data to show");
            return;
        }
        String info = "";
        for (ClientInfo cn : client_info) {
            info += "\nId: " + cn.getID() + " ,First Name: " + cn.getFirstName() + " ,Last Name: " + cn.getLastName() + " ,Age: " + cn.getAge() + " ,Qualification: " + cn.getQualification() + " ,Status: " + cn.getStatus();
        }
        TextView displayData = (TextView) findViewById(R.id.displayData);
        displayData.setText(info);
    }

    private void saveToServer(final ClientInfo client_info) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("string", response);
                            //response = response.replaceFirst("<b>.*?</b>", "");
                            //response = response.replaceFirst("<br />", "");

                            //Log.i("string", response);
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //if there is a success
                                //storing the name to sqlite with status synced
                                client_info.setStatus(NAME_SYNCED_WITH_SERVER);
                                saveToLocalStorage(client_info);
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                client_info.setStatus(NAME_NOT_SYNCED_WITH_SERVER);
                                saveToLocalStorage(client_info);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //on error storing the name to sqlite with status unsynced
                        client_info.setStatus(NAME_NOT_SYNCED_WITH_SERVER);
                        saveToLocalStorage(client_info);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("FirstName", client_info.getFirstName());
                params.put("LastName", client_info.getLastName());
                params.put("Age", client_info.getAge());
                params.put("Qualification", client_info.getQualification());

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    //saving the name to local storage
    private void saveToLocalStorage(ClientInfo client_info) {
        databaseHandler.addClient(client_info);

    }

}
