package android.example.com.smstest.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

public final class JsonCreation
{
    private static int countPhoneNumbers;
    private JsonCreation()
    {

    }


    public static JSONArray getArrayOfNumbers(String numbers, String messageContent, String deviceId)
    {

        ArrayList<String> arrayList = getNumbersArrayList(numbers);
        JSONArray array = new JSONArray();
        for (int start = 0;start<countPhoneNumbers;start++)
        {

            try
            {
                array.put(start,createJsonObject(arrayList.get(start),messageContent,deviceId));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    private static JSONObject createJsonObject(String phoneNumber,String content,String deviceId)
    {
        JSONObject object = new JSONObject();
        try
        {
            object.put("phone_number",phoneNumber);
            object.put("message",content);
            object.put("device_id",deviceId);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return object;
    }

    public static ArrayList<String> getNumbersArrayList(String numbers)
    {
        StringTokenizer tokenizePhoneNumbers = new StringTokenizer(numbers,",");
        countPhoneNumbers = tokenizePhoneNumbers.countTokens();
        ArrayList<String> phoneNumbers = new ArrayList<>();
        for (int start = 0;start<countPhoneNumbers;start++)
        {
            phoneNumbers.add(tokenizePhoneNumbers.nextToken());
        }

        System.out.println("TESTT numbers"+phoneNumbers);
        return phoneNumbers;
    }
}
