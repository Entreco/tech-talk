package entreco.nl.sample.techtalk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloClient;

import dagger.Module;
import dagger.Provides;
import entreco.nl.sample.TechTalkApplication;

@Module
public class ApolloModule {


    @NonNull private final ApolloClient apolloClient;

    public ApolloModule(@NonNull final Context context) {
        apolloClient = ((TechTalkApplication) context.getApplicationContext()).getApolloClient();
    }

    @Provides
    ApolloClient provideApolloClient(){
        return apolloClient;
    }

    @Provides
    Handler provideHandler(){
        return new Handler(Looper.getMainLooper());
    }
}
