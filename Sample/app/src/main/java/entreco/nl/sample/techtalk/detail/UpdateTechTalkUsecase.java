package entreco.nl.sample.techtalk.detail;

import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import nl.entreco.UpdateTechTalk;

class UpdateTechTalkUsecase {

    private final ApolloClient client;

    interface Callback {
        void done();

        void oops(Exception error);
    }

    @Inject
    UpdateTechTalkUsecase(@NonNull final ApolloClient client) {
        this.client = client;
    }

    void save(String id, String speaker, String room, String topic, final Callback callback) {
        client.newCall(new UpdateTechTalk(id, speaker, room, topic))
                .enqueue(

                        new ApolloCall.Callback<UpdateTechTalk.Data>() {
                            @Override
                            public void onResponse(
                                    @Nonnull Response<UpdateTechTalk.Data> response) {
                                callback.done();
                            }

                            @Override
                            public void onFailure(@Nonnull ApolloException e) {
                                callback.oops(e);
                            }
                        });
    }
}
