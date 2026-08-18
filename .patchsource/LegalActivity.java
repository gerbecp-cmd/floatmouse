package com.floatmouse.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class LegalActivity extends Activity {
    public static final String EXTRA_TYPE = "type";
    public static final String TYPE_PRIVACY = "privacy";
    public static final String TYPE_TERMS = "terms";
    public static final String TYPE_PERMISSIONS = "permissions";

    public static void open(Context c, String type) {
        Intent i = new Intent(c, LegalActivity.class);
        i.putExtra(EXTRA_TYPE, type);
        c.startActivity(i);
    }

    private int dp(float n) { return (int)(n * getResources().getDisplayMetrics().density + .5f); }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(11,14,20));
        String type = getIntent().getStringExtra(EXTRA_TYPE);
        if (TYPE_TERMS.equals(type)) render("TERMS & SAFETY", termsText());
        else if (TYPE_PERMISSIONS.equals(type)) render("PERMISSIONS & DATA USE", permissionsText());
        else render("PRIVACY POLICY", privacyText());
    }

    private void render(String heading, String body) {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(11,14,20));
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(22), dp(24), dp(22), dp(30));
        scroll.addView(root, new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView h = new TextView(this); h.setText(heading); h.setTextSize(25); h.setTextColor(Color.WHITE); h.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        root.addView(h);
        TextView updated = new TextView(this); updated.setText("Effective August 18, 2026"); updated.setTextSize(12); updated.setTextColor(Color.rgb(151,164,187)); updated.setPadding(0,dp(4),0,dp(16));
        root.addView(updated);
        TextView content = new TextView(this); content.setText(body); content.setTextSize(14); content.setTextColor(Color.rgb(210,218,231)); content.setLineSpacing(0,1.18f);
        root.addView(content, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button close = new Button(this); close.setText("CLOSE"); close.setAllCaps(false); close.setTextColor(Color.WHITE); close.setBackgroundColor(Color.rgb(34,42,57)); close.setPadding(dp(8),dp(10),dp(8),dp(10));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); p.topMargin = dp(22); root.addView(close,p);
        close.setOnClickListener(v -> finish());
        setContentView(scroll);
    }

    public static String privacyText() {
        return "FloatMouse Privacy Policy\n\n" +
                "FloatMouse turns a compatible Android phone into a remote mouse for a computer running the FloatMouse Receiver.\n\n" +
                "CAMERA\nDesk Mouse can use the rear camera to estimate relative movement across a physical surface. Camera frames are processed locally on the phone for motion estimation. FloatMouse does not save camera frames, upload them, or send them to the paired computer.\n\n" +
                "MOTION SENSORS\nAir Mouse uses gyroscope and accelerometer readings to calculate pointer movement and orientation. These readings are processed locally.\n\n" +
                "LOCAL NETWORK\nWhen you connect to a receiver, FloatMouse sends mouse-control packets and the pairing credential to the IP address and port you choose on your local network. The current protocol is intended for trusted local networks and is not designed for untrusted or public Wi-Fi.\n\n" +
                "SAVED DATA\nFloatMouse may store the receiver IP address, port, pairing PIN, sensitivity, calibration grade, automatic mode preferences, and acceptance of the privacy disclosure in Android app preferences on your device. You can delete the saved PC/pairing information or reset all FloatMouse data from Privacy & Settings.\n\n" +
                "BACKGROUND / SCREEN-OFF OPERATION\nAn active mouse session can use an Android foreground service, wake lock, camera in Desk Mouse mode, and motion sensors so controls can continue while the display is off. Android displays an ongoing notification while the active service is running.\n\n" +
                "ANALYTICS, ADS, AND REMOTE SERVERS\nThis version of FloatMouse does not include advertising SDKs, analytics SDKs, account creation, or a FloatMouse cloud service. If those features are added in a future version, this policy must be updated before that version is distributed.\n\n" +
                "DATA SHARING\nFloatMouse does not sell personal data. Mouse commands are transmitted only to the receiver address configured by the user.\n\n" +
                "SECURITY\nUse FloatMouse only with computers you are authorized to control and on networks you trust. Rotate the receiver PIN if you believe another device has learned it.\n\n" +
                "CHANGES\nMaterial privacy changes should be presented in an updated disclosure and policy.\n\n" +
                "CONTACT\nFor support or privacy questions, use the support contact listed with the official FloatMouse distribution.";
    }

    public static String termsText() {
        return "FloatMouse Terms & Safety Notice\n\n" +
                "FloatMouse is a remote input utility. By using it, you agree to use it only on computers and networks you own or are authorized to control.\n\n" +
                "You are responsible for all pointer movement, clicks, scrolling, and other commands sent from FloatMouse. Do not use the app while driving, operating machinery, or where an unintended command could create a safety risk.\n\n" +
                "Wireless networks, sensors, cameras, Android power management, phone cases, surface texture, and computer configuration can affect accuracy and responsiveness. FloatMouse is not intended for medical, emergency, industrial-safety, life-support, or other safety-critical control.\n\n" +
                "The software is provided as a general-purpose utility without a guarantee that every phone, computer, network, camera configuration, or Android vendor will behave identically. Stop the active FloatMouse session if unexpected input occurs.\n\n" +
                "You are responsible for complying with applicable laws, workplace rules, software licenses, and network policies when using FloatMouse.\n\n" +
                "These terms are product-use terms and are not a substitute for legal advice. Additional distribution-specific terms may apply if FloatMouse is later sold through an app store or other marketplace.";
    }

    public static String permissionsText() {
        return "Why FloatMouse requests access\n\n" +
                "CAMERA — Only needed for Desk Mouse optical surface tracking. FloatMouse requests this when you choose Desk Mouse or Calibrate Surface. Camera frames are processed locally and not stored or transmitted.\n\n" +
                "NOTIFICATIONS — Used for the ongoing notification that identifies an active foreground mouse session and provides visibility when FloatMouse is working in the background or with the screen off.\n\n" +
                "LOCAL NETWORK / INTERNET PERMISSION — Android uses the INTERNET permission for local socket communication. FloatMouse uses it to send mouse commands to the receiver IP you configure.\n\n" +
                "WAKE LOCK — Used during an active session so sensor-based controls can keep working reliably while the screen is off.\n\n" +
                "VIBRATION — Used for tactile confirmation of clicks, mode changes, calibration, and recenter actions.\n\n" +
                "MOTION SENSORS — Android does not present a runtime permission dialog for ordinary gyroscope/accelerometer use. FloatMouse uses those sensors for Air Mouse movement, automatic Desk/Air switching, and twist scrolling.\n\n" +
                "You can revoke Android permissions at any time from the system App Info screen. Desk Mouse requires camera access; the other modes can continue without the camera.";
    }
}
