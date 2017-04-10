package entreco.nl.sample.techtalk;

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

import entreco.nl.sample.techtalk.data.TechTalkModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TechTalkViewModelTest {

    @InjectMocks TechTalkViewModel subject;

    @Mock private FetchTechTalkUsecase mockUsecase;
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
    }

    @Test
    public void itShouldFetchTechTalksOnStart() throws Exception {
        subject.start();
        verify(mockUsecase).fetchAll(any(FetchTechTalkUsecase.Callback.class));
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
        simulateLoadError(any(Exception.class));

        assertFalse(subject.isLoading.get());
    }

    @Test
    public void itShouldHaveCorrectNumberOfElements() throws Exception {
        simulateLoadFinished(Arrays.asList(mockModel, mockModel, mockModel));

        assertEquals(3, subject.items.size());
    }

    @Test
    public void itShouldStartLoadingAnimation() throws Exception {
        TechTalkViewModel.toggleVisibilityAnimation(mockView, true);

        verify(mockView).animate();
        verify(mockViewPropertyAnimator).scaleX(1);
        verify(mockViewPropertyAnimator).scaleY(1);
        verify(mockViewPropertyAnimator).alpha(1);
        verify(mockViewPropertyAnimator).start();
    }

    @Test
    public void itShouldStopLoadingAnimation() throws Exception {
        TechTalkViewModel.toggleVisibilityAnimation(mockView, false);

        verify(mockView).animate();
        verify(mockViewPropertyAnimator).scaleX(0);
        verify(mockViewPropertyAnimator).scaleY(0);
        verify(mockViewPropertyAnimator).alpha(0);
        verify(mockViewPropertyAnimator).start();
    }

    private void simulateLoadFinished(List<TechTalkModel> list) {
        subject.start();

        verify(mockUsecase).fetchAll(captorCallback.capture());
        captorCallback.getValue().onFetched(list);
    }

    private void simulateLoadError(Exception exception){
        subject.start();

        verify(mockUsecase).fetchAll(captorCallback.capture());
        captorCallback.getValue().oops(exception);
    }
}