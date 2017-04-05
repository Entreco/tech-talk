package entreco.nl.sample.techtalk;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Collection;

import javax.inject.Inject;

import entreco.nl.sample.BR;
import entreco.nl.sample.R;
import entreco.nl.sample.techtalk.data.TechTalkModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TechTalkViewModel implements FetchTechTalkUsecase.Callback {

    @NonNull public final ObservableBoolean isLoading;
    @NonNull public final ObservableList<TechTalkModel> items;
    @NonNull public final ItemBinding<TechTalkModel> itemBinding = ItemBinding.of(BR.techTalk, R.layout.tech_talk_item);

    @NonNull private final FetchTechTalkUsecase fetchTechTalkUsecase;

    @Inject
    TechTalkViewModel(@NonNull FetchTechTalkUsecase fetchTechTalkUsecase) {
        this.fetchTechTalkUsecase = fetchTechTalkUsecase;
        this.isLoading = new ObservableBoolean();
        this.items = new ObservableArrayList<>();
    }

    public void start() {
        isLoading.set(true);
        fetchTechTalkUsecase.fetch(this);
    }

    @Override
    public void onFetched(@NonNull Collection<TechTalkModel> talks) {
        isLoading.set(false);
        items.addAll(talks);
    }

    @Override
    public void oops(Exception oops) {
        isLoading.set(false);
    }

    @BindingAdapter("tt_visibility")
    public static void toggleVisibilityAnimation(@NonNull final View view,
                                                 final boolean isLoading) {
        final float scale = isLoading ? 1F : 0F;
        final float alpha = isLoading ? 1F : 0F;

        view.animate().cancel();
        view.animate().scaleX(scale).scaleY(scale).alpha(alpha)
                .setDuration(android.R.integer.config_shortAnimTime)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    }
                })
                .start();
    }
}
