package com.meseems.mereach;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.meseems.mereach.database.DatabaseController;
import com.meseems.mereach.database.DatabaseStructure;
import com.meseems.mereach.networking.ReachabilityServiceSocketImpl;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView dummyTextView;
    private ArrayList<String> servers;
    private ArrayList<Integer> serversToRemove;
    private DatabaseController databaseController;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        servers = new ArrayList<String>();
        databaseController = new DatabaseController(this);
        loadServers();
        createListView();

    }

    private void loadServers(){
        servers.clear();
        databaseController.open();
        Cursor cursor = databaseController.getAllServers();
        if(cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++) {
                servers.add(cursor.getString(DatabaseStructure.SERVER_URL_COLLUMN));
                cursor.moveToNext();
            }
        }
        databaseController.close();
    }


    private void createListView(){
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new ListViewAdapter(this, servers));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "clickou: " + position,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onClickAdd(View v){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        final EditText url = (EditText)view.findViewById(R.id.editTextNewServer);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle("INSERIR").setView(view)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        databaseController.open();
                        if (!url.getText().toString().isEmpty()) {
                            if (servers.contains(url.getText().toString())) {
                                Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                                v.vibrate(200);

                                Toast.makeText(getApplicationContext(), "Servidor não inserido. " +
                                        "Este servidor já existe em nosso sistema.", Toast.LENGTH_LONG).show();
                            }
                            else {
                                databaseController.insertServer(url.getText().toString());
                            }
                        }
                        else {
                            Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(200);

                            Toast.makeText(getApplicationContext(), "Servidor não inserido. " +
                                    "Por favor digite a url do servidor", Toast.LENGTH_LONG).show();
                        }
                        databaseController.close();

                        loadServers();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();

    }

    public void onClickRemove(View v){

        serversToRemove = new ArrayList<Integer>();

        RemoveListAdapter adapter = new RemoveListAdapter(this, servers, serversToRemove);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle("Remover")
                .setAdapter(adapter, null)
                        // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("MAIN", "ToRemove: "+serversToRemove.size()+" - server: "+servers.size());
                        databaseController.open();
                        for(int i=0;i<serversToRemove.size();i++){
                            Log.d("MAIN", "Removing: "+servers.get(serversToRemove.get(i)));
                            databaseController.deleteServer(servers.get(serversToRemove.get(i)));
                        }
                        databaseController.close();

                        loadServers();
                        createListView();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });

        // create alert dialog
        AlertDialog dialog = builder.create();


        // show it
        dialog.show();

    }

}
