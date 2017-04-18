package entreco.nl.sample.techtalk.data;

import android.support.annotation.NonNull;

import nl.entreco.AllTechTalksQuery;
import nl.entreco.UpcomingTechTalks;

public class TechTalkModel {

    @NonNull public final AllTechTalksQuery.Data.AllTeckTalk all;
    @NonNull public final UpcomingTechTalks.Data.AllTeckTalk upcoming;

    public TechTalkModel(@NonNull final AllTechTalksQuery.Data.AllTeckTalk data){
        this.all = data;
        this.upcoming = null;
    }

    public TechTalkModel(@NonNull final UpcomingTechTalks.Data.AllTeckTalk data){
        this.all = null;
        this.upcoming = data;
    }
}
