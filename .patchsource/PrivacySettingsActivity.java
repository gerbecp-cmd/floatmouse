package com.floatmouse.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class PrivacySettingsActivity extends Activity {
    private int dp(float n) { return (int)(n * getResources().getDisplayMetrics().density + .5f); }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(11,14,20));
        buildUi();
    }

    private void buildUi() {
        ScrollView scroll = new ScrollView(this); scroll.setBackgroundColor(Color.rgb(11,14,20));
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(20),dp(22),dp(20),dp(28));
        scroll.addView(root, new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(text("PRIVACY & SETTINGS", 25, Color.WHITE, true));
        TextView sub = text("Review disclosures, manage permissions, and erase FloatMouse data.", 13, Color.rgb(151,164,187), false); sub.setPadding(0,dp(6),0,dp(16)); root.addView(sub);

        add(root, "Privacy Policy", "Camera, sensors, local-network communication, saved data", () -> LegalActivity.open(this, LegalActivity.TYPE_PRIVACY));
        add(root, "Terms & Safety", "Authorized use, safety limitations, responsibility for commands", () -> LegalActivity.open(this, LegalActivity.TYPE_TERMS));
        add(root, "Permissions & Data Use", "Exactly why FloatMouse uses each Android capability", () -> LegalActivity.open(this, LegalActivity.TYPE_PERMISSIONS));
        add(root, "Open Android App Permissions", "View or revoke camera and notification access", this::openAppSettings);
        add(root, "Delete Saved PC & Pairing", "Clears receiver IP, port, and pairing PIN only", this::confirmDeletePairing);
        add(root, "Reset All FloatMouse Data", "Stops FloatMouse and erases preferences, calibration, pairing, and disclosure acceptance", this::confirmResetAll);

        Button close = button("BACK TO FLOATMOUSE"); close.setOnClickListener(v -> finish()); LinearLayout.LayoutParams cp=full(); cp.topMargin=dp(16); root.addView(close, cp);
        setContentView(scroll);
    }

    private void confirmDeletePairing() {
        new AlertDialog.Builder(this).setTitle("Delete saved PC & pairing?")
                .setMessage("This removes the saved receiver IP, port, and PIN from this phone. Other FloatMouse preferences remain.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", (d,w) -> {
                    getSharedPreferences("floatmouse", MODE_PRIVATE).edit().remove("host").remove("pin").remove("port").apply();
                    Toast.makeText(this, "Saved PC and pairing deleted", Toast.LENGTH_SHORT).show();
                }).show();
    }

    private void confirmResetAll() {
        new AlertDialog.Builder(this).setTitle("Reset all FloatMouse data?")
                .setMessage("This stops the active service and erases pairing, calibration, sensitivity, preferences, and disclosure acceptance. The first-run privacy screen will appear next time.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Reset", (d,w) -> {
                    Intent stop = new Intent(this, MouseService.class); stop.setAction(MouseService.ACTION_STOP); startService(stop);
                    getSharedPreferences("floatmouse", MODE_PRIVATE).edit().clear().apply();
                    Toast.makeText(this, "FloatMouse data reset", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, ConsentActivity.class); i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(i); finish();
                }).show();
    }

    private void openAppSettings() {
        Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName())); startActivity(i);
    }

    private void add(LinearLayout root, String title, String detail, Runnable action) {
        Button b = button(title + "\n" + detail); b.setGravity(android.view.Gravity.START | android.view.Gravity.CENTER_VERTICAL); b.setTextSize(13); b.setOnClickListener(v -> action.run());
        root.addView(b, full()); android.widget.Space s = new android.widget.Space(this); root.addView(s, new LinearLayout.LayoutParams(dp(8),dp(8)));
    }
    private TextView text(String s, float sp, int c, boolean bold) { TextView v=new TextView(this); v.setText(s); v.setTextSize(sp); v.setTextColor(c); if(bold)v.setTypeface(Typeface.DEFAULT,Typeface.BOLD); return v; }
    private Button button(String s) { Button b=new Button(this); b.setText(s); b.setAllCaps(false); b.setTextColor(Color.WHITE); b.setBackgroundColor(Color.rgb(34,42,57)); b.setPadding(dp(12),dp(12),dp(12),dp(12)); return b; }
    private LinearLayout.LayoutParams full(){return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);}
}
