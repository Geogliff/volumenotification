/*
 * Copyright (C) 2017 Seht (R) Hyx Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.hyx.app.volumenotification;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActivityMain extends AppCompatActivity {

    protected PrefSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = new PrefSettings(this);

        NotificationFactory.newInstance(this).startService();

        setTheme(settings.getAppTheme());
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!settings.getDialogAlertNonceChecked(1)) {
                DialogFragment newFragment = DialogAlertNonce.newInstance(1, getResources().getString(R.string.target_api_welcome_message_N));
                newFragment.show(getSupportFragmentManager(), null);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_dark_app_theme).setChecked(settings.getAppThemeDark());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_pref:
                startActivity(new Intent(this, ActivityPref.class));
                return true;
            case R.id.menu_dark_app_theme:
                boolean dark_theme = !item.isChecked();
                settings.edit().putBoolean("pref_dark_app_theme", dark_theme).apply();
                item.setChecked(dark_theme);
                setTheme(settings.getAppTheme());
                recreate();
                return true;
            case R.id.menu_about:
                Uri url = Uri.parse(getResources().getString(R.string.menu_about_url));
                startActivity(new Intent(Intent.ACTION_VIEW, url));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}