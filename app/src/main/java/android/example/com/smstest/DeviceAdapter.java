package android.example.com.smstest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DeviceAdapter extends ArrayAdapter<Device>
{

    private ArrayList<Device> devices;
    public DeviceAdapter(Context context, ArrayList<Device> devices)
    {

        super(context, 0, devices);
        this.devices = devices;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        Device device = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.device_list_item, parent, false);
        }
        TextView deviceNameView = (TextView) convertView.findViewById(R.id.device_name);
        TextView deviceIdView = (TextView) convertView.findViewById(R.id.device_id);

        deviceNameView.setText(device.getName());
        deviceIdView.setText(device.getId());

        return convertView;
    }

    ArrayList<Device> getDevices()
    {
        return devices;
    }
}
