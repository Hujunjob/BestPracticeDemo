package com.hiscene.arcore;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hiscene.utils.NetworkMonitor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends Activity {
    private NetworkMonitor networkMonitor;
    private TextView txtLoss,txtDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkMonitor = new NetworkMonitor();
        setContentView(R.layout.activity_fullscreen);

        txtDelay = findViewById(R.id.txt_delay);
        txtLoss = findViewById(R.id.txt_loss);

        findViewById(R.id.btn_start).setOnClickListener(view -> {
            networkMonitor
                    .start()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(networkQuality -> {
                        if (networkQuality.delay == -1) {
                            txtDelay.setText("ping: ∞");
                        } else {
                            txtDelay.setText("ping:" + networkQuality.delay + "ms");
                        }
                        if (networkQuality.loss != 0) {
                            txtLoss.setText("loss:" + networkQuality.loss + "%");
                            txtLoss.setVisibility(View.VISIBLE);
                        } else {
                            txtLoss.setVisibility(View.GONE);
                        }
                    });
        });

        findViewById(R.id.btn_stop).setOnClickListener(view -> {
            networkMonitor.stop();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkMonitor.stop();
    }
}
