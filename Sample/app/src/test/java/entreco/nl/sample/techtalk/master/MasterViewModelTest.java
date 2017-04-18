package entreco.nl.sample.techtalk.master;

import android.view.View;
import android.view.ViewPropertyAnimator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entreco.nl.sample.techtalk.Navigator;
import entreco.nl.sample.techtalk.data.TechTalkModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MasterViewModelTest {

    @InjectMocks MasterViewModel subject;

    @Mock private FetchTechTalkUsecase mockUsecase;
    @Mock private Navigator mockNavigator;
    @Mock private TechTalkModel mockModel;

    @Mock private View mockView;
    @Mock private ViewPropertyAnimator mockViewPropertyAnimator;

    @Captor private ArgumentCaptor<FetchTechTalkUsecase.Callback> captorCallback;

    @Before
    public void setUp() throws Exception {
        when(mockView.animate()).thenReturn(mockViewPropertyAnimator);
        when(mockViewPropertyAnimator.scaleX(anyFloat())).thenReturn(mockViewPropertyAnimator);
        when(mockViewPropertyAnimator.scaleY(anyFloat())).thenReturn(mockViewPropertyAnimator);
        when(mockViewPropertyAnimator.alpha(anyFloat())).thenReturn(mockViewPropertyAnimator);
        when(mockViewPropertyAnimator.setDuration(anyInt())).thenReturn(mockViewPropertyAnimator);
        when(mockViewPropertyAnimator.withEndAction(any(Runnable.class))).thenReturn(mockViewPropertyAnimator);
    }

    @Test
    public void itShouldFetchTechTalksOnStart() throws Exception {
        subject.start();
        verify(mockUsecase).fetchUpcoming(any(FetchTechTalkUsecase.Callback.class));
    }

    @Test
    public void itShouldShowLoaderOnStart() throws Exception {
        subject.start();
        assertTrue(subject.isLoading.get());
    }

    @Test
    public void itShouldHaveEmptyListOfTechTalks() throws Exception {
        subject.start();
        assertEquals(0, subject.items.size());
    }

    @Test
    public void itShouldHideLoaderOnceFinished() throws Exception {
        simulateLoadFinished(new ArrayList<TechTalkModel>(0));

        assertFalse(subject.isLoading.get());
    }

    @Test
    public void itShouldHideLoaderOnError() throws Exception {
        simulateLoadError(new IllegalStateException("oops"));

        assertFalse(subject.isLoading.get());
    }

    @Test
    public void itShouldHaveCorrectNumberOfElements() throws Exception {
        simulateLoadFinished(Arrays.asList(mockModel, mockModel, mockModel));

        assertEquals(3, subject.items.size());
    }

    @Test
    public void itShouldStartLoadingAnimation() throws Exception {
        MasterViewModel.toggleVisibilityAnimation(mockView, true);

        verify(mockView).animate();
        verify(mockViewPropertyAnimator).scaleX(1);
        verify(mockViewPropertyAnimator).scaleY(1);
        verify(mockViewPropertyAnimator).alpha(1);
        verify(mockViewPropertyAnimator).start();
    }

    @Test
    public void itShouldStopLoadingAnimation() throws Exception {
        MasterViewModel.toggleVisibilityAnimation(mockView, false);

        verify(mockView).animate();
        verify(mockViewPropertyAnimator).scaleX(0.5f);
        verify(mockViewPropertyAnimator).scaleY(0.5f);
        verify(mockViewPropertyAnimator).alpha(0.5f);
        verify(mockViewPropertyAnimator).start();
    }

    @Test
    public void itShouldNotAddDuplicateItems() throws Exception {
        simulateLoadFinished(new ArrayList<TechTalkModel>(0));
        simulateLoadFinished(new ArrayList<TechTalkModel>(0));

        assertEquals( 2, subject.items.size() );

    }

    private void simulateLoadFinished(List<TechTalkModel> list) {
        subject.start();

        verify(mockUsecase).fetchUpcoming(captorCallback.capture());
        reset(mockUsecase);

        captorCallback.getValue().onFetched(list);
    }

    private void simulateLoadError(Exception exception){
        subject.start();

        verify(mockUsecase).fetchUpcoming(captorCallback.capture());
        reset(mockUsecase);

        captorCallback.getValue().oops(exception);
    }
}