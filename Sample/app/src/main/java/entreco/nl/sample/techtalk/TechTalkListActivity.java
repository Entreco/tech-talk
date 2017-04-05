package entreco.nl.sample.techtalk;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import entreco.nl.sample.R;
import entreco.nl.sample.databinding.ActivityTtListBinding;

public class TechTalkListActivity extends AppCompatActivity {

  @Nullable ActivityTtListBinding binding;
  @Nullable TechTalkViewModel viewModel;

  @Override
  protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setBinding(bindContentView(this, R.layout.activity_tt_list), DaggerTechTalkComponent.create().viewModel());
  }

  private ActivityTtListBinding bindContentView(@NonNull final Activity activity,
      @LayoutRes int layoutRes) {
    return DataBindingUtil.setContentView(activity, layoutRes);
  }

  void setBinding(@NonNull final ActivityTtListBinding binding,
      @NonNull final TechTalkViewModel viewModel) {
    this.binding = binding;
    this.viewModel = viewModel;
    this.binding.setViewModel(viewModel);
  }

  @Override protected void onStart() {
    super.onStart();
    if (viewModel != null) {
      viewModel.start();
    }
  }
}
