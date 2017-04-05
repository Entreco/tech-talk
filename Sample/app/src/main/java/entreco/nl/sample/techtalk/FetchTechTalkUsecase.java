package entreco.nl.sample.techtalk;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import entreco.nl.sample.techtalk.data.TechTalkModel;
import nl.entreco.AllTechTalksQuery;

class FetchTechTalkUsecase {

    private final ApolloClient client;
    private final Handler mainThreadHandler;

    @Inject
    FetchTechTalkUsecase(ApolloClient client) {
        this.client = client;
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    public void fetch(@NonNull final Callback capture) {

        client.newCall(new AllTechTalksQuery()).enqueue(
                new ApolloCall.Callback<AllTechTalksQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<AllTechTalksQuery.Data> response) {

                        final List<AllTechTalksQuery.Data.AllTeckTalk> dataList =
                                response.data().allTeckTalks();

                        final List<TechTalkModel> modelList =
                                new ArrayList<>(dataList.size());
                        for (AllTechTalksQuery.Data.AllTeckTalk data : dataList) {
                            modelList.add(new TechTalkModel(data));
                        }

                        mainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                capture.onFetched(modelList);
                            }
                        });

                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        capture.oops(e);
                    }
                });
    }

    public interface Callback {
        void onFetched(@NonNull final Collection<TechTalkModel> talks);

        void oops(Exception oops);
    }
}
