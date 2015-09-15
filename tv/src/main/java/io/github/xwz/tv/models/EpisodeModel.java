package io.github.xwz.tv.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EpisodeModel extends Serializable {

    public void merge(EpisodeModel ep);

    void setOtherEpisodes(Map<String, List<EpisodeModel>> more);

    Map<String, List<EpisodeModel>> getOtherEpisodes();

    List<String> getOtherEpisodeUrls(String cat);

    String getDurationText();

    void addCategory(String cat);

    void setCategories(List<String> cats);

    String toString();

    String getSeriesTitle();

    String getHref();

    String getFormat();

    String getFormatBgColour();

    String getFormatTextColour();

    String getChannel();

    String getPubDate();

    String getThumbnail();

    String getLivestream();

    String getEpisodeHouseNumber();

    List<String> getCategories();

    String getTitle();

    int getDuration();

    String getLabel();

    String getRating();

    int getEpisodeCount();

    String getDescription();

    String getRelated();

    String getAvailability();

    String getStream();

    String getCaptions();

    String getShare();

    boolean hasExtras();

    boolean hasOtherEpisodes();
}
