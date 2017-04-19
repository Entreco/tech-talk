package entreco.nl.sample.techtalk.master;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import entreco.nl.sample.techtalk.DateUtil;
import entreco.nl.sample.techtalk.data.TechTalkModel;
import nl.entreco.UpcomingTechTalksQuery;

class FetchTechTalkUsecase {

    @NonNull private final ApolloClient client;
    @NonNull private final Handler mainThreadHandler;

    @Inject
    FetchTechTalkUsecase(@NonNull final ApolloClient client, @NonNull final Handler handler) {
        this.client = client;
        this.mainThreadHandler = handler;
    }

    void fetchUpcoming(@NonNull final Callback callback) {

        final String today = DateUtil.toDateString(new Date());
        client.newCall(new UpcomingTechTalksQuery(today)).enqueue(
                new ApolloCall.Callback<UpcomingTechTalksQuery.Data>() {
                    @Override
                    public void onResponse(
                            @Nonnull final Response<UpcomingTechTalksQuery.Data> response) {
                        parseResponse(response.data().allTechTalks(), callback);
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        callback.oops(e);
                    }
                });
    }

    private void parseResponse(@NonNull final List<UpcomingTechTalksQuery.Data.AllTechTalk> dataList,
                                   @NonNull final Callback callback) {
        final List<TechTalkModel> allTalks = new ArrayList<>(dataList.size());
        for (final UpcomingTechTalksQuery.Data.AllTechTalk data : dataList) {
            allTalks.add(new TechTalkModel(data.fragments().techTalkFragment()));
        }

        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFetched(allTalks);
            }
        });
    }

    public interface Callback {

        void onFetched(@NonNull final Collection<TechTalkModel> talks);

        void oops(Exception oops);
    }
}
