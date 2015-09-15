package io.github.xwz.tv.content;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;

import java.util.LinkedHashMap;
import java.util.List;

import io.github.xwz.tv.models.IEpisodeModel;

public interface IContentManager {

    public static final String CONTENT_ID = "io.github.xwz.abciview.CONTENT_ID";
    public static final String CONTENT_TAG = "io.github.xwz.abciview.CONTENT_TAG";

    public static final String CONTENT_SHOW_LIST_START = "io.github.xwz.abciview.CONTENT_SHOW_LIST_START";
    public static final String CONTENT_SHOW_LIST_DONE = "io.github.xwz.abciview.CONTENT_SHOW_LIST_DONE";
    public static final String CONTENT_SHOW_LIST_ERROR = "io.github.xwz.abciview.CONTENT_SHOW_LIST_ERROR";

    public static final String CONTENT_EPISODE_START = "io.github.xwz.abciview.CONTENT_EPISODE_START";
    public static final String CONTENT_EPISODE_DONE = "io.github.xwz.abciview.CONTENT_EPISODE_DONE";
    public static final String CONTENT_EPISODE_ERROR = "io.github.xwz.abciview.CONTENT_EPISODE_ERROR";

    public static final String CONTENT_AUTH_START = "io.github.xwz.abciview.CONTENT_AUTH_START";
    public static final String CONTENT_AUTH_DONE = "io.github.xwz.abciview.CONTENT_AUTH_DONE";
    public static final String CONTENT_AUTH_ERROR = "io.github.xwz.abciview.CONTENT_AUTH_ERROR";

    public static final String AUTH_FAILED_NETWORK = "AUTH_FAILED_NETWORK";
    public static final String AUTH_FAILED_TOKEN = "AUTH_FAILED_TOKEN";
    public static final String AUTH_FAILED_URL = "AUTH_FAILED_URL";

    public static final String OTHER_EPISODES = "OTHER_EPISODES";
    public static final String GLOBAL_SEARCH_INTENT = "GLOBAL_SEARCH_INTENT";

    //The columns we'll include in the video database table
    public static final String KEY_SERIES_TITLE = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public static final String KEY_TITLE = SearchManager.SUGGEST_COLUMN_TEXT_2;

    public static final String KEY_IMAGE = SearchManager.SUGGEST_COLUMN_RESULT_CARD_IMAGE;
    public static final String KEY_DATA_TYPE = SearchManager.SUGGEST_COLUMN_CONTENT_TYPE;
    public static final String KEY_IS_LIVE = SearchManager.SUGGEST_COLUMN_IS_LIVE;
    public static final String KEY_VIDEO_WIDTH = SearchManager.SUGGEST_COLUMN_VIDEO_WIDTH;
    public static final String KEY_VIDEO_HEIGHT = SearchManager.SUGGEST_COLUMN_VIDEO_HEIGHT;
    public static final String KEY_PRODUCTION_YEAR = SearchManager.SUGGEST_COLUMN_PRODUCTION_YEAR;
    public static final String KEY_COLUMN_DURATION = SearchManager.SUGGEST_COLUMN_DURATION;
    public static final String KEY_ACTION = SearchManager.SUGGEST_COLUMN_INTENT_ACTION;
    public static final String KEY_EXTRA_DATA = SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA;
    public static final String KEY_EXTRA_NAME = SearchManager.EXTRA_DATA_KEY;

    public void updateRecommendations(Context context);
    public LinkedHashMap<String, List<IEpisodeModel>> getAllShowsByCategories();
    public void fetchShowList();
    public IEpisodeModel getEpisode(String href);
    public void fetchEpisode(IEpisodeModel episode);
    public List<String> suggestions(String query);
    public List<IEpisodeModel> searchShows(String query);
    public void fetchAuthToken(IEpisodeModel episode);
    public Uri getEpisodeStreamUrl(IEpisodeModel episode);
    public IEpisodeModel findNextEpisode(List<String> urls, String current);
}
