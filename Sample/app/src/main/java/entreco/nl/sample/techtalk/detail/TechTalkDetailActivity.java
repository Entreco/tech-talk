package entreco.nl.sample.techtalk.detail;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;

import entreco.nl.sample.R;
import entreco.nl.sample.databinding.ActivityTtDetailBinding;
import entreco.nl.sample.techtalk.ApolloModule;
import entreco.nl.sample.techtalk.data.TechTalkModel;

public class TechTalkDetailActivity extends AppCompatActivity {

    @Nullable private ActivityTtDetailBinding binding;
    @Nullable private DetailViewModel viewModel;

    @NonNull private final Observable.OnPropertyChangedCallback onChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if(binding != null) {

                final Slide slide = new Slide(Gravity.BOTTOM);
                TransitionManager.beginDelayedTransition(binding.editables, slide);

                final TransitionSet set = new TransitionSet();
                set.addTransition(new Slide(Gravity.TOP));
                set.addTransition(new Fade());
                TransitionManager.beginDelayedTransition(binding.viewables, set);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tt_detail);

        final String transition = getIntent().getStringExtra("transition");
        binding.setTransitionName(transition);

        viewModel = DaggerTechTalkDetailComponent.builder()
                .apolloModule(new ApolloModule(this))
                .techTalkDetailModule(new TechTalkDetailModule(onChangedCallback))
                .build().viewModel();
        viewModel.setTechTalk(new TechTalkModel(getIntent().getBundleExtra("techTalk")));

        binding.setViewModel(viewModel);
    }

    @Override
    public void onBackPressed() {
        setResult(viewModel.didEditModel.get() ? RESULT_OK : RESULT_CANCELED);
        supportFinishAfterTransition();
        super.onBackPressed();
    }
}
