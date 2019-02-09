package android.example.com.smstest;


import android.content.Intent;
import android.content.SharedPreferences;
import android.example.com.smstest.Network.NetworkUtils;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.facebook.stetho.Stetho;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    private SharedPreferences preferences;
    private ListView listView;
    private DeviceAdapter adapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        listView = (ListView)findViewById(R.id.list);
        adapter = new DeviceAdapter(this,new ArrayList<Device>());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
              Intent intent = new Intent(MainActivity.this,SendMessagesActivity.class);
              intent.putExtra(Intent.EXTRA_INTENT,adapter.getDevices().get((int)id).getId());
              startActivity(intent);
            }
        });
        Stetho.initializeWithDefaults(this);
        NetworkUtils.intHttp();

        //check AUTH key if exists

     /**   if (checkKey())
        {
            showLoading();
            new Task().execute();
        }
        else
        {
            Toast.makeText(this,"please enter enter the token in the setting section",Toast.LENGTH_LONG).show();
        }*/

    }
    private boolean checkKey()
    {
        return preferences.contains(getString(R.string.token_string_id));
    }
    private class Task extends AsyncTask<Void,Void,String>
    {
       private static final String json =
                "{\n" +
                        "  \"filters\": [\n" +
                        "    [\n" +
                        "      {\n" +
                        "        \"field\": \"name\",\n" +
                        "        \"operator\": \"=\",\n" +
                        "        \"value\": \"New Device\"\n" +
                        "      }\n" +
                        "]\n" +
                        "]\n" +
                "}\n";

        @Override
        protected String doInBackground(Void... voids)
        {

            try
            {
                return NetworkUtils.getDeviceInfo(json,getApplicationContext());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            hideLoading();
            ArrayList<Device> devices = new ArrayList<>();
            System.out.println("TESTT OnPostExcute " + s);
            try
            {
                devices = NetworkUtils.extractDevices(s);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            adapter = new DeviceAdapter(MainActivity.this,devices);
            listView.setAdapter(adapter);
        }
    }

    private void showLoading()
    {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading()
    {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.setting_id:
                Intent intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (checkKey())
        {
            showLoading();
            new Task().execute();
        }
        else
        {
            Toast.makeText(this,"please enter enter the token in the setting section",Toast.LENGTH_LONG).show();
        }
    }
}