package it.jaschke.alexandria;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by James Yang on 11/23/2015.
 */
public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private String TAG="BarCode";
    public static final String BAR_TAG = "BAR_TAG";
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        //IntentIntegrator scanIntegrator = new IntentIntegrator(this);
       // scanIntegrator.initiateScan();
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
       setContentView(mScannerView);// Set the scanner view as the content view


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Context context = getApplicationContext();
        CharSequence text = rawResult.getBarcodeFormat().toString();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(BAR_TAG, rawResult.getText());
        setResult(RESULT_OK, resultIntent);
        finish();

//        Log.v(TAG, rawResult.getText()); // Prints scan results
//        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
    }
}
