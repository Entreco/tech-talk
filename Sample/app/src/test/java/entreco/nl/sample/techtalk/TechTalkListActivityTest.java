package entreco.nl.sample.techtalk;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import entreco.nl.sample.BuildConfig;
import entreco.nl.sample.databinding.ActivityTtListBinding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TechTalkListActivityTest {

  private TechTalkListActivity subject;

  @Mock private ActivityTtListBinding mockBinding;
  @Mock private TechTalkViewModel mockViewModel;
  @Captor private ArgumentCaptor<TechTalkViewModel> captorViewModel;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    subject = Robolectric.buildActivity(TechTalkListActivity.class).create().get();
  }

  @Test
  public void shouldNotBeNull() throws Exception {
    assertNotNull(subject);
  }

  @Test
  public void shouldCreateBinding() throws Exception {
    assertNotNull(subject.binding);
  }

  @Test
  public void shouldBeAbleToInjectBinding() throws Exception {
    setupWithBindingAndViewModel(mockBinding, mockViewModel);
    assertEquals(mockBinding, subject.binding);
  }

  @Test
  public void shouldBeAbleToInjectViewModel() throws Exception {
    setupWithBindingAndViewModel(mockBinding, mockViewModel);
    assertEquals(mockViewModel, subject.viewModel);
  }

  @Test
  public void shouldSetViewModelOnBinding() throws Exception {
    setupWithBindingAndViewModel(mockBinding, mockViewModel);
    assertEquals(mockViewModel, captorViewModel.getValue());
  }

  private void setupWithBindingAndViewModel(ActivityTtListBinding binding,
      TechTalkViewModel viewModel) {
    subject.setBinding(binding, viewModel);
    verify(mockBinding).setViewModel(captorViewModel.capture());
  }
}