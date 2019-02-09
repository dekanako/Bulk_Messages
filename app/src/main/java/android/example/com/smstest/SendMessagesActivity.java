package android.example.com.smstest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.com.smstest.Network.JsonCreation;
import android.example.com.smstest.Network.NetworkUtils;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;

public class SendMessagesActivity extends AppCompatActivity
{
    private Button button;
    private EditText mPhonerNumberEditTest;
    private EditText mMessageEditText;
    private String id;
    private String mMessageToSend;
    private String mPhoneNumbers;
    private String mDeviceId;
    private JSONArray mMessagePayload;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_messages);





        button = (Button)findViewById(R.id.message_send_button_id);
        mPhonerNumberEditTest = (EditText)findViewById(R.id.phone_numbers_edit_text_id);
        mMessageEditText = (EditText)findViewById(R.id.message_edit_id);

        if (getIntent().hasExtra(Intent.EXTRA_INTENT))
        {
            mDeviceId = id = getIntent().getStringExtra(Intent.EXTRA_INTENT);

        }

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (mMessageToSend == null || mPhoneNumbers == null || mMessageToSend.equals("") || mMessageToSend.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"please fill in the blanks",Toast.LENGTH_LONG).show();
                    return;
                }

                mMessagePayload = JsonCreation.getArrayOfNumbers(mPhoneNumbers,mMessageToSend,mDeviceId);

                new Task().execute();

            }
        });

        mMessageEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                mMessageToSend = s.toString();
            }
        });

        mPhonerNumberEditTest.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
            @Override
            public void afterTextChanged(Editable s)
            {
                mPhoneNumbers = s.toString();

            }
        });
    }



    private class Task extends AsyncTask<Void, Void, String>
    {
        String json =
                "[\n" +
                        "  {\n" +
                        "    \"phone_number\": \"07791064781\",\n" +
                        "    \"message\": \"Hello World\",\n" +
                        "    \"device_id\": 109039\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"from\": \"AcmeLtd\",\n" +
                        "    \"phone_number\": \"07791064782\",\n" +
                        "    \"message\": \"Hello World\",\n" +
                        "    \"device_id\": 109039\n" +
                        "  }\n" +
                        "]";

        @Override
        protected String doInBackground(Void... strings)
        {
            try
            {
                return NetworkUtils.sendMessage(mMessagePayload.toString(),getApplicationContext());
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
            System.out.println("TESTT onPost Excute" + s);
            finish();
        }
    }
}
/** private static void createJson(String phoneNumber,String content) throws JSONException
 {
 int count = getCount();
 JSONArray array = new JSONArray();
 for (int start = 0;start<count;start++)
 {
 JSONObject message = new JSONObject();
 message.put("phone_number",phoneNumber);
 message.put("message",content);
 }

 JSONObject message = new JSONObject();



 array.put(message);

 System.out.println("TESTT GSON"+array.toString());
 }

 private static int getCount()
 {
 return 0;
 }*/
