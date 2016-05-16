/*
 * Copyright 2015 MbientLab Inc. All rights reserved.
 */

package com.ozge.bitirme.BabyWirstband;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Array adapter to display information about the scanned Bluetooth LE devices
 * @author Eric Tsai
 */
public class ScannedDeviceInfoAdapter extends ArrayAdapter<ScannedDeviceInfo> {
    private final static int RSSI_BAR_LEVELS= 5;
    private final static int RSSI_BAR_SCALE= 100 / RSSI_BAR_LEVELS;
    public String macadressmy;
    public ScannedDeviceInfoAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView= LayoutInflater.from(getContext()).inflate(com.mbientlab.bletoolbox.scanner.R.layout.blescan_entry, parent, false);

            viewHolder= new ViewHolder();
            viewHolder.deviceAddress= (TextView) convertView.findViewById(R.id.ble_mac_address);
            viewHolder.deviceName= (TextView) convertView.findViewById(R.id.ble_device);
            viewHolder.deviceRSSI= (TextView) convertView.findViewById(R.id.ble_rssi_value);
            viewHolder.rssiChart= (ImageView) convertView.findViewById(R.id.ble_rssi_png);

            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        ScannedDeviceInfo deviceInfo= getItem(position);
        final String deviceName= deviceInfo.btDevice.getName();
        macadressmy=deviceInfo.btDevice.getAddress();

        if (deviceName != null && deviceName.length() > 0)
         viewHolder.deviceName.setText(deviceName);

        else
            viewHolder.deviceName.setText(com.mbientlab.bletoolbox.scanner.R.string.label_unknown_device);
        viewHolder.deviceAddress.setText(deviceInfo.btDevice.getAddress());
        viewHolder.deviceRSSI.setText(String.format(Locale.US, "%d dBm", deviceInfo.rssi));
        viewHolder.rssiChart.setImageLevel(Math.min(RSSI_BAR_LEVELS - 1, (127 + deviceInfo.rssi + 5) / RSSI_BAR_SCALE));

        return convertView;
    }

    private class ViewHolder {
        public TextView deviceAddress;
        public TextView deviceName;
        public TextView deviceRSSI;
        public ImageView rssiChart;
    }

    public void update(ScannedDeviceInfo newInfo) {
        int pos= getPosition(newInfo);
        if (pos == -1) {
            add(newInfo);
        } else {
            getItem(pos).rssi= newInfo.rssi;
            notifyDataSetChanged();
        }
    }
};
