package entreco.nl.sample.techtalk;

import android.support.annotation.NonNull;
import android.view.View;

import entreco.nl.sample.techtalk.data.TechTalkModel;

public interface Navigator {
    void onTechTalkClicked(@NonNull final View view, @NonNull final View shared, @NonNull final TechTalkModel techTalkModel);
}
