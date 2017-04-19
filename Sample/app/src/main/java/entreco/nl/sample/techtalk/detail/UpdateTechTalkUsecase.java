package entreco.nl.sample.techtalk.detail;

import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import entreco.nl.sample.techtalk.data.TechTalkModel;
import fragment.TechTalkFragment;
import nl.entreco.UpdateTechTalk;

class UpdateTechTalkUsecase {

    private final ApolloClient client;

    interface Callback {
        void done(@NonNull final TechTalkModel update);

        void oops(Exception error);
    }

    @Inject
    UpdateTechTalkUsecase(@NonNull final ApolloClient client) {
        this.client = client;
    }

    void save(TechTalkModel model, final Callback callback) {
        client.newCall(new UpdateTechTalk(model.getId(), model.getSpeaker(), model.getRoom(), model.getTopic()))
                .enqueue(
                        new ApolloCall.Callback<UpdateTechTalk.Data>() {
                            @Override
                            public void onResponse(
                                    @Nonnull Response<UpdateTechTalk.Data> response) {
                                final TechTalkFragment fragment = response.data().updateTechTalk().fragments().techTalkFragment();
                                callback.done(new TechTalkModel(fragment));
                            }

                            @Override
                            public void onFailure(@Nonnull ApolloException e) {

                                callback.oops(e);
                            }
                        });
    }
}
