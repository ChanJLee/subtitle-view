package com.chan.subtitleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chan.lib.SubtitleView;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SubtitleView subtitleView = findViewById(R.id.subtitle);
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				subtitleView.render("1234abcABC苟利国家生死以，岂因祸福避趋之毒又双叒叕");
			}
		});
	}
}
