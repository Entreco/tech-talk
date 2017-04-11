package entreco.nl.sample.techtalk.master;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloClient;

import dagger.Module;
import dagger.Provides;
import entreco.nl.sample.TechTalkApplication;
import entreco.nl.sample.techtalk.Navigator;

@Module
class TechTalkModule {

    @NonNull private final ApolloClient apolloClient;
    @NonNull private final Navigator navigator;

    TechTalkModule(@NonNull final Context context, @NonNull final Navigator navigator) {
        apolloClient = ((TechTalkApplication) context.getApplicationContext()).getApolloClient();
        this.navigator = navigator;
    }

    @Provides
    FetchTechTalkUsecase provideFetchTechTalkUsecase() {
        return new FetchTechTalkUsecase(apolloClient, new Handler(Looper.getMainLooper()));
    }

    @Provides
    Navigator provideNavigator() {
        return navigator;
    }
}
