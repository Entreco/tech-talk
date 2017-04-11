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

    @NonNull public final ObservableBoolean isLoading;
    @NonNull public final ObservableField<String> snack;
    @NonNull public final ObservableList<TechTalkModel> items;
    @NonNull public final ItemBinding<TechTalkModel> itemBinding =
            ItemBinding.of(BR.techTalk, R.layout.tech_talk_item);

    @NonNull private final FetchTechTalkUsecase fetchTechTalkUsecase;

    @Inject
    MasterViewModel(@NonNull FetchTechTalkUsecase fetchTechTalkUsecase,
                    @NonNull final Navigator navigator) {
        this.fetchTechTalkUsecase = fetchTechTalkUsecase;
        this.isLoading = new ObservableBoolean();
        this.snack = new ObservableField<>();
        this.items = new ObservableArrayList<>();
        this.itemBinding.bindExtra(BR.navigator, navigator);
    }

    void start() {
        isLoading.set(true);
        fetchTechTalkUsecase.fetchAll(this);
    }

    @Override
    public void onFetched(@NonNull Collection<TechTalkModel> talks) {
        isLoading.set(false);
        items.addAll(talks);
    }

    @Override
    public void oops(Exception oops) {
        isLoading.set(false);
        snack.set(oops.getMessage());
    }

    @BindingAdapter("tt_visibility")
    public static void toggleVisibilityAnimation(@NonNull final View view,
                                                 final boolean isLoading) {
        final float scale = isLoading ? 1.8F : 0.5F;
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
        Snackbar.make(view.getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @BindingAdapter("custom_play")
    public static void playAnimation(@NonNull final ImageView view, final boolean play) {

        Drawable drawable = view.getDrawable();
        if (drawable instanceof Animatable2) {
//            if (callback != null) {
//                ((Animatable2) drawable).registerAnimationCallback(callback);
//            }
            ((Animatable2) drawable).start();
        }
    }
}
