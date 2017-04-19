package entreco.nl.sample.techtalk.master;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Collection;

import javax.inject.Inject;

import entreco.nl.sample.BR;
import entreco.nl.sample.R;
import entreco.nl.sample.techtalk.Navigator;
import entreco.nl.sample.techtalk.data.TechTalkModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class MasterViewModel implements FetchTechTalkUsecase.Callback {

    @NonNull public final ObservableBoolean isLoading = new ObservableBoolean();
    @NonNull public final ObservableField<String> snack = new ObservableField<>();
    @NonNull public final ObservableList<TechTalkModel> items = new ObservableArrayList<>();

    @NonNull public final ItemBinding<TechTalkModel> itemBinding =
            ItemBinding.of(BR.techTalk, R.layout.tech_talk_item);

    @NonNull private final FetchTechTalkUsecase fetchTechTalkUsecase;

    @Inject
    MasterViewModel(@NonNull final FetchTechTalkUsecase fetchTechTalkUsecase,
                    @NonNull final Navigator navigator) {
        this.fetchTechTalkUsecase = fetchTechTalkUsecase;
        this.itemBinding.bindExtra(BR.navigator, navigator);
    }

    void start() {
        isLoading.set(true);
        fetchTechTalkUsecase.fetchUpcoming(this);
    }

    public void onRefresh() {
        start();
    }

    @Override
    public void onFetched(@NonNull Collection<TechTalkModel> talks) {
        isLoading.set(false);
        addItemsIfNotAddedAlready(talks);
    }

    /* Alas, we have to do check ourselves if the item was not added already ;( */
    private void addItemsIfNotAddedAlready(@NonNull final Collection<TechTalkModel> talks) {
        for (final TechTalkModel ttm : talks) {
            if (!items.contains(ttm)) {
                items.add(ttm);
            }
        }
    }

    @Override
    public void oops(Exception oops) {
        isLoading.set(false);
        snack.set(oops.getMessage());
    }

    @BindingAdapter("tt_visibility")
    public static void toggleVisibilityAnimation(@NonNull final View view,
                                                 final boolean isLoading) {
        final float scale = isLoading ? 1F : 0.5F;
        final float alpha = isLoading ? 1F : 0.5F;

        view.animate().scaleX(scale).scaleY(scale).alpha(alpha)
                .setDuration(android.R.integer.config_shortAnimTime)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG", "endAction");
                        view.setVisibility(isLoading ? View.GONE : View.VISIBLE);
                    }
                })
                .start();
    }

    @BindingAdapter("snack")
    public static void showSnack(@NonNull final View view, final String message) {
        if (!TextUtils.isEmpty(message)) {
            Snackbar.make(view.getRootView(), message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @BindingAdapter("custom_play")
    public static void playAnimation(@NonNull final ImageView view, final boolean play) {

        Drawable drawable = view.getDrawable();
        if (drawable instanceof Animatable2) {
            ((Animatable2) drawable).start();
        }
    }
}
