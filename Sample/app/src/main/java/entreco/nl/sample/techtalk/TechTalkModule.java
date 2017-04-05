package entreco.nl.sample.techtalk;

import android.content.Context;

import com.apollographql.apollo.ApolloClient;

import dagger.Module;
import dagger.Provides;
import entreco.nl.sample.TechTalkApplication;

@Module
class TechTalkModule {

    private ApolloClient apolloClient;

    TechTalkModule(Context context) {
        apolloClient = ((TechTalkApplication) context.getApplicationContext()).getApolloClient();
    }

    @Provides
    FetchTechTalkUsecase provideFetchTechTalkUsecase() {
        return new FetchTechTalkUsecase(apolloClient);
    }
}
