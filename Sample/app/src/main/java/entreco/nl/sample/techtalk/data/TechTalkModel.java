package entreco.nl.sample.techtalk.data;

import android.support.annotation.NonNull;

import nl.entreco.AllTechTalksQuery;

public class TechTalkModel {

    @NonNull public final AllTechTalksQuery.Data.AllTeckTalk data;

    public TechTalkModel(@NonNull final AllTechTalksQuery.Data.AllTeckTalk data){
        this.data = data;
    }
}
