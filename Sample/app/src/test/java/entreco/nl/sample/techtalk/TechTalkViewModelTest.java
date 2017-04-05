package entreco.nl.sample.techtalk;

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
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TechTalkViewModelTest {

    @InjectMocks TechTalkViewModel subject;

    @Mock private FetchTechTalkUsecase mockUsecase;
    @Mock private TechTalkModel mockModel;

    @Captor private ArgumentCaptor<FetchTechTalkUsecase.Callback> captorCallback;

    @Test
    public void itShouldFetchTechTalksOnStart() throws Exception {
        subject.start();
        verify(mockUsecase).fetch(any(FetchTechTalkUsecase.Callback.class));
    }

    @Test
    public void itShouldShowLoaderOnStart() throws Exception {
        subject.start();
        assertTrue(subject.isLoading.get());
    }

    @Test
    public void itShouldHaveEmptyListOfTechTalks() throws Exception {
        subject.start();
        assertEquals(0, subject.techTalks.get().size());
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

        assertEquals(3, subject.techTalks.get().size());
    }

    private void simulateLoadFinished(List<TechTalkModel> list) {
        subject.start();

        verify(mockUsecase).fetch(captorCallback.capture());
        captorCallback.getValue().onFetched(list);
    }

    private void simulateLoadError(Exception exception){
        subject.start();

        verify(mockUsecase).fetch(captorCallback.capture());
        captorCallback.getValue().oops(exception);
    }
}