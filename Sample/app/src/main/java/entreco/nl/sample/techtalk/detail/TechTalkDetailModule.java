package entreco.nl.sample.techtalk.detail;

import android.databinding.Observable;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

@Module
class TechTalkDetailModule {

    @NonNull private final Observable.OnPropertyChangedCallback onChangedCallback;

    TechTalkDetailModule(@NonNull final Observable.OnPropertyChangedCallback onChangedCallback) {
        this.onChangedCallback = onChangedCallback;
    }

    @Provides
    Observable.OnPropertyChangedCallback provideOnChangedCallback(){
        return onChangedCallback;
    }
}
