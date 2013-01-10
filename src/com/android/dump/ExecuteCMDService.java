package com.android.dump;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ExecuteCMDService extends Service {
//	private static final String cmd = "su -c tcpdump -i any -p -s 0 -vv -w "; //for user-version rooted device
	private static final String cmd = "tcpdump -i any -p -s 0 -vv -w ";
	private String scmd;
	Process process;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
	}
	@Override
	public void onStart(Intent intent, int startId) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = sdf.format(new Date()).replace(" ", "_")
				.replace(":", ".");
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			scmd = cmd + "/sdcard/" + date + ".cap";
			Toast.makeText(this, R.string.toast_save_complete, Toast.LENGTH_LONG)
			.show();
		} else {
			scmd = cmd + "/data/data/com.android.dump/" + date + ".cap";
		}
		try {
			process = Runtime.getRuntime().exec(scmd);
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("run", e.toString());
		}
		super.onStart(intent, startId);
	}
	@Override
	public void onDestroy() {
		Toast.makeText(this, R.string.toast_service_stop, Toast.LENGTH_LONG)
				.show();
		if (process != null) {
			process.destroy();
		}
		super.onDestroy();
	}
}
