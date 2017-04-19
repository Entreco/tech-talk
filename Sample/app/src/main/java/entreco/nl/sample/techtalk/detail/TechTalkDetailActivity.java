package entreco.nl.sample.techtalk.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;

import entreco.nl.sample.R;
import entreco.nl.sample.databinding.ActivityTtDetailBinding;
import entreco.nl.sample.techtalk.ApolloModule;
import entreco.nl.sample.techtalk.data.TechTalkModel;

public class TechTalkDetailActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private ActivityTtDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tt_detail);

        final String transition = getIntent().getStringExtra("transition");
        binding.setTransitionName(transition);

        final DetailViewModel viewModel =
                DaggerTechTalkDetailComponent.builder()
                        .apolloModule(new ApolloModule(this))
                        .techTalkDetailModule(new TechTalkDetailModule())
                        .build().viewModel();

        viewModel.setTechTalk(new TechTalkModel(getIntent().getBundleExtra("techTalk")));
        
        binding.setViewModel(viewModel);
        binding.appbarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
    }
}
