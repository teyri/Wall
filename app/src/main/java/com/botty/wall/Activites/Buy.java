package com.botty.wall.Activites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.botty.wall.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class Buy extends Activity implements BillingProcessor.IBillingHandler {

    ImageButton mBuyBtn;
    ImageView mImageWall;
    TextView mTextThank;
    FrameLayout mBackTxt;
    TextView mInfoBuyText;
    SharedPreferences sharedPref;
    BillingProcessor bp;
    private boolean readyToPurchase = false;
    private static final String LOG_TAG = "BottyShop";
    private static final String PRODUCT_ID = "removeads";
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsyx2xItfmUfkufFGgXegN/Z35ElVXuyTW0GPB1trFOJlkArKYUHV5SoOAnMVOLX6IL4if/EGA/Uz7Gw1uwVNZTSvgu9A6HlVvKcotD8sGdh5nujUSi9IH4KoL2yXApYzm0icNFeMSVKjl/9B2dFq+paDx/sZT+Ys2HgnxpNpmM4+ALAdJ7eAcK7BYMvaibURL1XDnm57nZBK+hVSSAHxaO2ZrA414chALCVMAPZ/aLAAwtYuLVsOwWRvD+B3cJxhdwjbnVVuVkHerB9+DdxgfPGqbv9VrJLhsS+TXZBi8fihNtg12HBypbCMOojJ+FwMaqmL2Z5cwqQDOLMgS/jomwIDAQAB";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, true);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mBuyBtn = (ImageButton) findViewById(R.id.buyBtn);
        mImageWall = (ImageView) findViewById(R.id.wallLayout);
        mBackTxt = (FrameLayout) findViewById(R.id.backText);
        mTextThank = (TextView) findViewById(R.id.thanks);
        mInfoBuyText = (TextView) findViewById(R.id.info_buy);
        mInfoBuyText.setText(Html.fromHtml("If you buy it:\n- Remove the Ads,\n- Enable the CasualWallpaper,\n- Support us.\n\n"
        +"Thanks anyway for Having downloaded the app !"));
        UrlImageViewHelper.setUrlDrawable(mImageWall, "https://raw.githubusercontent.com/BottyIvan/Wall/master/app/screen/wall_promo.png");
        if (settings.getBoolean("pref_remove", false)) {
            mBackTxt.setBackgroundColor(R.color.trasp);
            mTextThank.setText("Thanks you <3");
        }

        bp = new BillingProcessor(this, LICENSE_KEY, this);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, true);
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(PRODUCT_ID);
               // bp.consumePurchase(PRODUCT_ID);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }
    public void IsPurchased(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("pref_remove",true);
        editor.commit();
        Log.d("comprato", "vero");
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onProductPurchased(String s, TransactionDetails transactionDetails) {
        mBackTxt.setBackgroundColor(R.color.trasp);
        mTextThank.setText("Thanks you <3");
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onPurchaseHistoryRestored() {
        for(String sku : bp.listOwnedProducts())
            Log.d(LOG_TAG, "Owned Managed Product: " + sku);
        for(String sku : bp.listOwnedSubscriptions())
            Log.d(LOG_TAG, "Owned Subscription: " + sku);
        mBackTxt.setBackgroundColor(R.color.trasp);
        mTextThank.setText("Your purchase are restored !");
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBillingError(int i, Throwable throwable) {
        showToast("onBillingError: " + Integer.toString(i));
        mBackTxt.setBackgroundColor(R.color.trasp);
        mTextThank.setText("Sorry !");
        IsPurchased();
    }

    @Override
    public void onBillingInitialized() {
        readyToPurchase = true;
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}