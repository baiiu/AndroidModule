/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 drakeet (drakeet.me@gmail.com)
 * http://drakeet.me
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.drakeet.library.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import me.drakeet.library.R;

/**
 * Created by drakeet(http://drakeet.me)
 * Date: 8/31/15 22:42
 */
public class CatchActivity extends Activity {

    public final static String EXTRA_CRASH_LOGS = "extra_crash_logs";
    public final static String EXTRA_CRASH_4_LOGCAT = "extra_crash_4_logcat";
    public final static String EXTRA_HIGHLIGHT_KEYS = "extra_highlight_keys";
    public final static String EXTRA_APPLICATION_NAME = "extra_application_name";
    private RecyclerView recyclerView;
    CrashListAdapter crashListAdapter;
    private String[] crashArray = { "Cause by 1", "At 2" };
    private String log4Cat, applicationName;
    private ArrayList<String> keys;


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);
        parseIntent();
        if (applicationName != null) setTitle("Crashes in " + applicationName);
        setUpRecyclerView();
    }


    private void parseIntent() {
        crashArray = getIntent().getStringArrayExtra(EXTRA_CRASH_LOGS);
        // TODO: 16/8/13 reload from files
        log4Cat = getIntent().getStringExtra(EXTRA_CRASH_4_LOGCAT);
        keys = getIntent().getStringArrayListExtra(EXTRA_HIGHLIGHT_KEYS);
        applicationName = getIntent().getStringExtra(EXTRA_APPLICATION_NAME);
    }


    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.crashes);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        crashListAdapter = new CrashListAdapter(crashArray, keys.toArray(new String[keys.size()]));
        recyclerView.setAdapter(crashListAdapter);
        if (!TextUtils.isEmpty(log4Cat)) {
            Log.e("CrashWoodpecker", log4Cat);
        }
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catch, menu);
        //menu.add(Html.fromHtml("<font color='#ffffff'>drakeet</font>"))
        //        .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
        //            @Override public boolean onMenuItemClick(MenuItem item) {
        //                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://drakeet.me"));
        //                startActivity(it);
        //                return true;
        //            }
        //        });
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share_action) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, log4Cat);
            sendIntent.setType("text/plain");
            String title = getResources().getString(R.string.share);
            Intent chooser = Intent.createChooser(sendIntent, title);
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
