package com.xp.movie.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xp.movie.R;

/**
 * Created by XP on 2015/5/7.
 */
public class SettingFragment extends PreferenceFragment {
    private static final String BUILD_TIME = "2015-5-8";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        findPreference(getString(R.string.about_key)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                openAbout();
                return true;
            }
        });
        findPreference(getString(R.string.build_time_key)).setSummary(BUILD_TIME);
//        findPreference(getString(R.string.image_quality_key)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object o) {
//                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test",
//                        Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("name",(Boolean)o);
//                editor.commit();
//                Toast.makeText(getActivity(), "checkBox_0改变的值为" + (Boolean)o, Toast.LENGTH_LONG).show();
//
    }

    public void openAbout() {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.about)
                .customView(R.layout.dialog_webview, false)
                .positiveText(android.R.string.ok)
                .build();
        WebView webView = (WebView) dialog.getCustomView().findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/webview.html");
        dialog.show();
    }
}
