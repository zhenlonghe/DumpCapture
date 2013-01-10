package com.android.dump;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author zhenlonghe
 *
 */
public class DumpActivity extends Activity {
	private Button start;
	private Button stop;
	private Button exit;

	private boolean isStop = true;

	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		start = (Button) findViewById(R.id.start_button_id);
		stop = (Button) findViewById(R.id.stop_button_id);
		exit = (Button) findViewById(R.id.exit_button_id);

		intent = new Intent();
		intent.setAction("com.android.dump.ExecuteCMDService");
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startService(intent);
				Toast.makeText(DumpActivity.this, R.string.toast_service_start,
						Toast.LENGTH_LONG).show();
				isStop = false;
				start.setClickable(false);
				start.setBackgroundColor(Color.RED);
			}
		});
		stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopService(intent);
				start.setClickable(true);
				start.setBackgroundResource(R.drawable.menu_bg);
				isStop = true;
			}
		});

		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isStop) {
					new AlertDialog.Builder(DumpActivity.this)
							.setTitle(R.string.notice)
							.setMessage(R.string.Message)
							.setIcon(R.drawable.notice)
							.setPositiveButton(android.R.string.ok,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											DumpActivity.this.finish();
										}
									})
							.setNegativeButton(android.R.string.cancel, null)
							.show();
				} else {
					DumpActivity.this.finish();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		this.finish();
		super.onDestroy();
	}
}