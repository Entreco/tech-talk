package entreco.nl.sample.techtalk.master;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import entreco.nl.sample.techtalk.Navigator;

@Module
class TechTalkModule {

    @NonNull private final Navigator navigator;

    TechTalkModule(@NonNull final Navigator navigator) {
        this.navigator = navigator;
    }


    @Provides
    Navigator provideNavigator() {
        return navigator;
    }
}
