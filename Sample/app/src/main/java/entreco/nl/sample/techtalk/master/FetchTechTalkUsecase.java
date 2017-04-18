package entreco.nl.sample.techtalk.master;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import entreco.nl.sample.techtalk.data.TechTalkModel;
import nl.entreco.AllTechTalksQuery;
import nl.entreco.UpcomingTechTalks;

class FetchTechTalkUsecase {

    @NonNull private final ApolloClient client;
    @NonNull private final Handler mainThreadHandler;

    @Inject
    FetchTechTalkUsecase(@NonNull final ApolloClient client, @NonNull final Handler handler) {
        this.client = client;
        this.mainThreadHandler = handler;
    }

    void fetchAll(@NonNull final Callback callback) {

        client.newCall(new AllTechTalksQuery()).enqueue(
                new ApolloCall.Callback<AllTechTalksQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<AllTechTalksQuery.Data> response) {
                        parseResponse(response.data().allTeckTalks(), callback);
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        callback.oops(e);
                    }
                });
    }

    private <T> void parseResponse(@NonNull final List<T> dataList, @NonNull final Callback callback) {
        final List<TechTalkModel> allTalks = new ArrayList<>(dataList.size());
        for (final T data : dataList) {
            if(data instanceof AllTechTalksQuery.Data.AllTeckTalk) {
                allTalks.add(new TechTalkModel((AllTechTalksQuery.Data.AllTeckTalk) data));
            } else if(data instanceof UpcomingTechTalks.Data.AllTeckTalk){
                allTalks.add(new TechTalkModel((UpcomingTechTalks.Data.AllTeckTalk) data));
            }
        }

        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFetched(allTalks);
            }
        });
    }

    void fetchUpcoming(@NonNull final Callback callback) {

        final String today = generateDateString(new Date());
        client.newCall(new UpcomingTechTalks(today)).enqueue(
                new ApolloCall.Callback<UpcomingTechTalks.Data>() {
                    @Override
                    public void onResponse(
                            @Nonnull final Response<UpcomingTechTalks.Data> response) {
                        parseResponse(response.data().allTeckTalks(), callback);
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        callback.oops(e);
                    }
                });
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    String generateDateString(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    public interface Callback {

        void onFetched(@NonNull final Collection<TechTalkModel> talks);

        void oops(Exception oops);
    }
}
