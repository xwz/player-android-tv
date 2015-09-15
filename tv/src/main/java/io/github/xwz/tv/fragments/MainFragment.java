package io.github.xwz.tv.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.BrowseFrameLayout;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.github.xwz.tv.adapters.BaseArrayAdapter;
import io.github.xwz.tv.adapters.EpisodePresenter;
import io.github.xwz.tv.content.ContentManager;
import io.github.xwz.tv.models.EpisodeModel;

public abstract class MainFragment extends BrowseFragment {

    private static final String TAG = "MainFragment";
    private ProgressBar progress;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "Action: " + action);
            if (ContentManager.CONTENT_SHOW_LIST_DONE.equals(action)) {
                updateAdapter();
                getContentManger().updateRecommendations(getActivity());
            }
        }
    };

    protected abstract ContentManager getContentManger();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);
        setupUIElements();
        setupListeners();
    }

    @SuppressWarnings("deprecation")
    protected abstract void setupUIElements();
    /*{
        setHeadersState(HEADERS_DISABLED | HEADERS_HIDDEN);

        View root = getView();
        if (root != null) {
            BrowseFrameLayout frame = (BrowseFrameLayout) root.findViewById(android.support.v17.leanback.R.id.browse_frame);
            progress = new ProgressBar(getActivity());
            progress.setLayoutParams(new FrameLayout.LayoutParams(150, 150, Gravity.CENTER));
            frame.addView(progress);
        }
    }*/

    private void hideProgress() {
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }
    }

    private void setupListeners() {
        setOnSearchClickedListener(getSearchClickedListener());
        setOnItemViewClickedListener(getItemClickedListener());
    }

    protected abstract View.OnClickListener getSearchClickedListener();

    protected abstract OnItemViewClickedListener getItemClickedListener();

    private void updateRows(ArrayObjectAdapter adapter) {
        LinkedHashMap<String, List<EpisodeModel>> all = getContentManger().getAllShowsByCategories();
        int currentRows = adapter.size();
        int newRows = all.size();
        EpisodePresenter card = new EpisodePresenter();
        List<String> categories = new ArrayList<>(all.keySet());
        for (int i = 0; i < newRows; i++) {
            String category = categories.get(i);
            if (i < currentRows) { // update row
                ListRow row = (ListRow) adapter.get(i);
                row.setHeaderItem(new HeaderItem(category));
                BaseArrayAdapter<EpisodeModel> items = (BaseArrayAdapter<EpisodeModel>) row.getAdapter();
                items.replaceItems(all.get(category));
            } else { // add
                BaseArrayAdapter<EpisodeModel> items = new BaseArrayAdapter<>(card);
                items.addAll(0, all.get(category));
                HeaderItem header = new HeaderItem(category);
                ListRow row = new ListRow(header, items);
                adapter.add(row);
            }
        }
        int deleteRows = currentRows - newRows;
        if (deleteRows > 0) {
            adapter.removeItems(newRows, deleteRows);
        }
    }

    private void updateAdapter() {
        ArrayObjectAdapter adapter = (ArrayObjectAdapter) getAdapter();
        if (adapter == null) {
            adapter = new ArrayObjectAdapter(new ListRowPresenter());
            updateRows(adapter);
            setAdapter(adapter);
        } else {
            updateRows(adapter);
        }
        hideProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
        getContentManger().fetchShowList();
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    private void registerReceiver() {
        Log.i(TAG, "Register receiver");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ContentManager.CONTENT_SHOW_LIST_START);
        filter.addAction(ContentManager.CONTENT_SHOW_LIST_DONE);
        filter.addAction(ContentManager.CONTENT_SHOW_LIST_ERROR);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);
    }
}
