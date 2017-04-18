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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechTalkModel that = (TechTalkModel) o;

        if (all != null ? !all.equals(that.all) : that.all != null) return false;
        return upcoming != null ? upcoming.equals(that.upcoming) : that.upcoming == null;

    }

    @Override
    public int hashCode() {
        int result = all != null ? all.hashCode() : 0;
        result = 31 * result + (upcoming != null ? upcoming.hashCode() : 0);
        return result;
    }
}
