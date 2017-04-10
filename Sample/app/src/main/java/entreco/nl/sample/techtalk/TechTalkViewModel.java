package entreco.nl.sample.techtalk;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Collection;

import javax.inject.Inject;

import entreco.nl.sample.BR;
import entreco.nl.sample.R;
import entreco.nl.sample.techtalk.data.TechTalkModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TechTalkViewModel implements FetchTechTalkUsecase.Callback {

    @NonNull public final ObservableBoolean isLoading;
    @NonNull public final ObservableField<String> snack;
    @NonNull public final ObservableList<TechTalkModel> items;
    @NonNull public final ItemBinding<TechTalkModel> itemBinding = ItemBinding.of(BR.techTalk, R.layout.tech_talk_item);

    @NonNull private final FetchTechTalkUsecase fetchTechTalkUsecase;

    @Inject
    TechTalkViewModel(@NonNull FetchTechTalkUsecase fetchTechTalkUsecase) {
        this.fetchTechTalkUsecase = fetchTechTalkUsecase;

        this.isLoading = new ObservableBoolean();
        this.snack = new ObservableField<>();
        this.items = new ObservableArrayList<>();
        this.itemBinding.bindExtra(BR.viewModel, this);
    }

    public void start() {
        isLoading.set(true);
        fetchTechTalkUsecase.fetch(this);
    }

    @Override
    public void onFetched(@NonNull Collection<TechTalkModel> talks) {
        Log.d("TAG", "Thread" + Thread.currentThread());
        isLoading.set(false);
        items.addAll(talks);
    }

    @Override
    public void oops(Exception oops) {
        isLoading.set(false);
    }

    public void onTechTalkClicked(@NonNull final TechTalkModel techTalkModel){
        snack.set("On tech talk clicked:" + techTalkModel.data.topic());
    }

    @BindingAdapter("tt_visibility")
    public static void toggleVisibilityAnimation(@NonNull final ProgressBar view,
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
    public static void showSnack(@NonNull final View view, final String message){
        Snackbar.make(view.getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }
}
