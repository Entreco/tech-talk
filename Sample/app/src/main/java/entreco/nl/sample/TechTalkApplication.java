package entreco.nl.sample;

import android.app.Application;
import com.apollographql.apollo.ApolloClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class TechTalkApplication extends Application {

    private static final String BASE_URL = "https://api.graph.cool/simple/v1/cj15clqsb0g1n0118rncsymy7";
    private ApolloClient apolloClient;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public synchronized ApolloClient getApolloClient() {
        if (apolloClient == null) {
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            final OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();
            apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(client).build();
        }

        return apolloClient;
    }
}
