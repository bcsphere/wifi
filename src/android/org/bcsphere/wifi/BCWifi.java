package org.bcsphere.wifi;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class BCWifi extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray data,
			CallbackContext callbackContext) throws JSONException {

		Context context = cordova.getActivity().getApplicationContext();
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (action.equals("getConnectedWifiInfo")) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("BSSID", wifiInfo.getBSSID());
				obj.put("SSID", wifiInfo.getSSID());
				obj.put("MacAddress", wifiInfo.getMacAddress());
				obj.put("IPAddress", convertIp2Str(wifiInfo.getIpAddress()));
			} catch (JSONException e) {
				e.printStackTrace();
				callbackContext.error("JSON Exception");
			}
			callbackContext.success(obj);
		}
		return true;
	}

	private static String convertIp2Str(int ip) {
		String result;

		result = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff),
				(ip >> 24 & 0xff));

		return result;
	}
}
