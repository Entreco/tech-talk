package entreco.nl.sample.techtalk.master;

import entreco.nl.sample.BuildConfig;
import entreco.nl.sample.databinding.ActivityTtListBinding;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TechTalkListActivityViewModelTest {

  private TechTalkListActivity subject;

  @Mock private ActivityTtListBinding mockBinding;
  @Mock private MasterViewModel mockViewModel;
  @Captor private ArgumentCaptor<MasterViewModel> captorViewModel;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    final ActivityController<TechTalkListActivity> controller =
        Robolectric.buildActivity(TechTalkListActivity.class).create();
    subject = controller.get();

    assertNotNull( subject );

    subject.setBinding(mockBinding, mockViewModel);
    subject = controller.start().get();

    verify(mockBinding).setViewModel(captorViewModel.capture());
    verify(mockViewModel).start();
  }

  @After
  public void tearDown() throws Exception {
    Robolectric.reset();
  }

  @Test
  public void shouldNotifyViewModelOnStart() throws Exception {
    assertNotNull ( subject );
  }
}