package android.example.com.smstest.Network;



import android.content.Context;
import android.content.SharedPreferences;
import android.example.com.smstest.Device;
import android.example.com.smstest.R;
import android.preference.Preference;
import android.support.v7.preference.PreferenceManager;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class NetworkUtils
{
    private  static OkHttpClient client;

    public static void intHttp()
    {
        client = new OkHttpClient.Builder()
            .addNetworkInterceptor(new StethoInterceptor())
            .build();
    }


    //private static String AUTH = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhZG1pbiIsImlhdCI6MTU0OTEyMzkzNywiZXhwIjo0MTAyNDQ0ODAwLCJ1aWQiOjY3NTk1LCJyb2xlcyI6WyJST0xFX1VTRVIiXX0.siXyRsufe0TDqrPxyirHFRzMeTFxVl-7XMfHXEs7m6c";
    private static String AUTH ;
    private static final String URL_SMS = "https://smsgateway.me/api/v4";

    private static final String DEVICE_PATH = "device";

    private static final String SEARCH_QUERY = "search";

    private static final String AUTHORIZATION_KEY = "Authorization";

    private static final String MESSAGE_PATH = "message/send";

    private static final MediaType JSON
            = MediaType.parse("application/json, text/plain, */*");

    private static final String WEB_HOOK = "https://webhook.site/e99dcfdd-dc2d-4d66-a170-83243fd9c58f";

    private NetworkUtils(){}


    /**

     */
    public static String sendMessage(String json,Context context) throws IOException
    {

        initAuth(context);
        //build the url with its paths
        HttpUrl.Builder url = HttpUrl.parse(URL_SMS).newBuilder();
        url.addPathSegments(MESSAGE_PATH);

        //add the json to the link to post it
        RequestBody body = RequestBody.create(JSON, json);

        //create the request from the url and the post it with the created json
        Request request = new Request.Builder()
                .url(url.build().toString())
                .addHeader(AUTHORIZATION_KEY,AUTH)
                .post(body)
                .build();

        //receive the response
        Response response = client.newCall(request).execute();

        System.out.println("TESTT  NetworkUtils.doPost " + response);
        return response.toString();
    }

    private static void initAuth(Context context)
    {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        AUTH = preferences.getString("token_pref",null);
    }

    public static String getSpecificDeviceInfo () throws IOException
    {
        HttpUrl.Builder url = HttpUrl.parse(URL_SMS).newBuilder();
        url.addPathSegments(DEVICE_PATH);
        url.addPathSegments("109039");
        //RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url.build().toString())
                .addHeader(AUTHORIZATION_KEY,AUTH)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("TESTT getDeviceInfo method"+request);
        return response.body().string();
    }

    public static String getDeviceInfo(String json,Context context) throws IOException
    {
        initAuth(context);
        HttpUrl.Builder url = HttpUrl.parse(URL_SMS).newBuilder()
                .addPathSegment(DEVICE_PATH)
                .addPathSegment(SEARCH_QUERY);

        Request request = new Request.Builder()
                .url(url.build().toString())
                .addHeader(AUTHORIZATION_KEY,AUTH)
                .post(RequestBody.create(JSON,json))
                .build();
        Response response = client.newCall(request).execute();

        System.out.println("TESTT NetworkUtils.GetDeviceInfo " + response);

        return response.body().string();
    }

    public static ArrayList<Device> extractDevices(String json) throws JSONException
    {

        ArrayList<Device> devices = new ArrayList<>();

        JSONObject object = new JSONObject(json);

        JSONArray array = object.getJSONArray("results");
        for (int x = 0;x<array.length();x++)
        {
            Device device = new Device();
            JSONObject object1 = array.getJSONObject(x);
            device.setName(object1.getString("name"));
            device.setId(object1.getString("id"));
            devices.add(device);
        }
        return devices;
    }
}
