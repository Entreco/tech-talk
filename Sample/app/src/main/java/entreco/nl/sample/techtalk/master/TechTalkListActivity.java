package entreco.nl.sample.techtalk.master;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import entreco.nl.sample.R;
import entreco.nl.sample.databinding.ActivityTtListBinding;
import entreco.nl.sample.techtalk.ApolloModule;
import entreco.nl.sample.techtalk.Navigator;
import entreco.nl.sample.techtalk.data.TechTalkModel;
import entreco.nl.sample.techtalk.detail.TechTalkDetailActivity;

public class TechTalkListActivity extends AppCompatActivity implements Navigator {

    private static final int EDIT_DETAILS = 1234;

    @Nullable ActivityTtListBinding binding;
    @Nullable MasterViewModel viewModel;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding(bindContentView(this, R.layout.activity_tt_list),
                DaggerTechTalkComponent.builder()
                        .apolloModule(new ApolloModule(this))
                        .techTalkModule(new TechTalkModule(this))
                        .build()
                        .viewModel());
    }

    private ActivityTtListBinding bindContentView(@NonNull final Activity activity,
                                                  @LayoutRes int layoutRes) {
        return DataBindingUtil.setContentView(activity, layoutRes);
    }

    @VisibleForTesting
    void setBinding(@NonNull final ActivityTtListBinding binding,
                    @NonNull final MasterViewModel viewModel) {
        this.binding = binding;
        this.viewModel = viewModel;
        this.binding.setViewModel(viewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (viewModel != null) {
            viewModel.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_DETAILS && resultCode == RESULT_OK && viewModel != null) {
            // Details have been added ->
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            viewModel.start();
        }
    }

    @Override
    public void onTechTalkClicked(@NonNull final View view, @NonNull final View shared,
                                  @NonNull final TechTalkModel techTalkModel) {
        final Intent intent = new Intent(this, TechTalkDetailActivity.class);

        intent.putExtra("transition", shared.getTransitionName());
        intent.putExtra("techTalk", techTalkModel.toBundle());
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, shared, shared.getTransitionName());
        startActivityForResult(intent, EDIT_DETAILS, options.toBundle());
    }
}
