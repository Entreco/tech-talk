package entreco.nl.sample.techtalk.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import entreco.nl.sample.R;
import entreco.nl.sample.databinding.ActivityTtDetailBinding;
import entreco.nl.sample.techtalk.ApolloModule;
import entreco.nl.sample.techtalk.data.TechTalkModel;

public class TechTalkDetailActivity extends AppCompatActivity {

    private ActivityTtDetailBinding binding;
    private DetailViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tt_detail);

        final String transition = getIntent().getStringExtra("transition");
        binding.setTransitionName(transition);

        viewModel = DaggerTechTalkDetailComponent.builder()
                .apolloModule(new ApolloModule(this))
                .techTalkDetailModule(new TechTalkDetailModule())
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
