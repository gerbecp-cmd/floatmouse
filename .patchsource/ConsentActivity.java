package com.floatmouse.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ConsentActivity extends Activity {
    public static final int DISCLOSURE_VERSION = 1;
    private int dp(float n) { return (int)(n * getResources().getDisplayMetrics().density + .5f); }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSharedPreferences("floatmouse", MODE_PRIVATE).getInt("disclosureVersion", 0) >= DISCLOSURE_VERSION) {
            openMain();
            return;
        }
        getWindow().setStatusBarColor(Color.rgb(11,14,20));
        buildUi();
    }

    private void buildUi() {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(Color.rgb(11,14,20));
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(22), dp(24), dp(22), dp(30));
        scroll.addView(root, new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView title = text("FLOATMOUSE PRIVACY & SAFETY", 25, Color.WHITE, true);
        root.addView(title);
        TextView sub = text("Please review this before activating mouse controls.", 14, Color.rgb(151,164,187), false);
        sub.setPadding(0, dp(8), 0, dp(18));
        root.addView(sub);

        card(root, "Motion sensors", "Air Mouse uses your phone's gyroscope and accelerometer to translate phone movement into cursor movement. Motion sensor readings are processed on the device.");
        card(root, "Desk Mouse camera", "Desk Mouse uses the rear camera to detect relative movement over a surface. Camera frames are processed locally for motion estimation and are not saved or transmitted to the paired computer or FloatMouse servers.");
        card(root, "Local network control", "FloatMouse sends mouse commands and the pairing credential to the PC receiver over your local Wi-Fi network. Use FloatMouse only on a trusted network; the current local-network protocol is not intended for hostile or public Wi-Fi.");
        card(root, "Screen-off operation", "When you start an active Air Mouse or Desk Mouse session, FloatMouse may run a foreground service and keep motion controls active while the display is off. Android shows an ongoing notification while the service is active.");
        card(root, "Hardware buttons", "When an active session is enabled, FloatMouse may use the volume buttons as mouse controls. Volume Down is left click and Volume Up is right click.");
        card(root, "Safety", "Do not use FloatMouse while driving, operating machinery, or in any situation where accidental computer input could cause injury, loss, or damage. You remain responsible for commands sent to the paired computer.");

        TextView agreement = text("By choosing Agree & Continue, you acknowledge these disclosures and agree to the FloatMouse Terms & Safety notice. You can review Privacy, Terms, permissions, and data controls from the app at any time.", 13, Color.rgb(206,214,228), false);
        agreement.setPadding(0, dp(14), 0, dp(18));
        root.addView(agreement);

        Button agree = button("AGREE & CONTINUE", true);
        agree.setOnClickListener(v -> {
            getSharedPreferences("floatmouse", MODE_PRIVATE).edit()
                    .putInt("disclosureVersion", DISCLOSURE_VERSION)
                    .putLong("disclosureAcceptedAt", System.currentTimeMillis())
                    .apply();
            openMain();
        });
        root.addView(agree, full());
        space(root, 10);

        Button privacy = button("READ PRIVACY POLICY", false);
        privacy.setOnClickListener(v -> LegalActivity.open(this, LegalActivity.TYPE_PRIVACY));
        root.addView(privacy, full());
        space(root, 8);

        Button terms = button("READ TERMS & SAFETY", false);
        terms.setOnClickListener(v -> LegalActivity.open(this, LegalActivity.TYPE_TERMS));
        root.addView(terms, full());
        space(root, 8);

        Button exit = button("NOT NOW / EXIT", false);
        exit.setOnClickListener(v -> finishAffinity());
        root.addView(exit, full());
        setContentView(scroll);
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void card(LinearLayout root, String head, String body) {
        LinearLayout c = new LinearLayout(this);
        c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(dp(14), dp(13), dp(14), dp(13));
        c.setBackgroundColor(Color.rgb(22,27,37));
        c.addView(text(head, 14, Color.WHITE, true));
        TextView b = text(body, 13, Color.rgb(176,188,207), false);
        b.setPadding(0, dp(5), 0, 0);
        c.addView(b);
        root.addView(c, full());
        space(root, 9);
    }

    private TextView text(String s, float sp, int color, boolean bold) {
        TextView v = new TextView(this); v.setText(s); v.setTextSize(sp); v.setTextColor(color);
        if (bold) v.setTypeface(Typeface.DEFAULT, Typeface.BOLD); return v;
    }
    private Button button(String s, boolean primary) {
        Button b = new Button(this); b.setText(s); b.setAllCaps(false); b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        b.setTextColor(Color.WHITE); b.setBackgroundColor(primary ? Color.rgb(47,100,196) : Color.rgb(34,42,57));
        b.setPadding(dp(8),dp(10),dp(8),dp(10)); return b;
    }
    private void space(LinearLayout l, int n) { android.widget.Space s = new android.widget.Space(this); l.addView(s, new LinearLayout.LayoutParams(dp(n), dp(n))); }
    private LinearLayout.LayoutParams full() { return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); }
}
