package entreco.nl.sample.techtalk.master;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entreco.nl.sample.techtalk.DateUtil;
import entreco.nl.sample.techtalk.data.TechTalkModel;
import fragment.TechTalkFragment;
import nl.entreco.UpcomingTechTalksQuery;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ApolloClient.class})
public class FetchTechTalkUsecaseTest {

    private ArrayList<UpcomingTechTalksQuery.Data.AllTechTalk> emptyTechTalksList = new ArrayList<>();
    private ArrayList<UpcomingTechTalksQuery.Data.AllTechTalk> techTalkList = new ArrayList<>();

    @InjectMocks private FetchTechTalkUsecase subject;
    @Mock private ApolloClient mockClient;

    @Mock private Handler mockHandler;
    @Mock private FetchTechTalkUsecase.Callback mockCallback;
    @Mock private ApolloCall<UpcomingTechTalksQuery.Data> mockCall;
    @Mock private Response<UpcomingTechTalksQuery.Data> mockResponse;
    @Mock private UpcomingTechTalksQuery.Data mockData;
    @Mock private UpcomingTechTalksQuery.Data.AllTechTalk mockTechTalk;
    @Mock private UpcomingTechTalksQuery.Data.AllTechTalk.Fragments mockFragments;

    @Mock private TechTalkFragment mockTechTalkFragment;
    @Mock private TechTalkFragment.Room mockRoom;
    @Mock private TechTalkFragment.Speaker mockSpeaker;

    @Captor private ArgumentCaptor<ApolloCall.Callback<UpcomingTechTalksQuery.Data>> callbackCaptor;
    @Captor private ArgumentCaptor<List<TechTalkModel>> fetchedCaptor;
    @Captor private ArgumentCaptor<Runnable> runnableCaptor;

    @Before
    public void setUp() throws Exception {
        when(mockTechTalk.fragments()).thenReturn(mockFragments);
        when(mockFragments.techTalkFragment()).thenReturn(mockTechTalkFragment);
    }

    private void simulateTechTalk(@NonNull final String id,@NonNull final String description, @NonNull final String speaker, @NonNull final String topic, @NonNull final Date date) {
        when(mockTechTalkFragment.id()).thenReturn(id);
        when(mockTechTalkFragment.room()).thenReturn(mockRoom);
        when(mockRoom.description()).thenReturn(description);
        when(mockTechTalkFragment.speaker()).thenReturn(mockSpeaker);
        when(mockSpeaker.name()).thenReturn(speaker);
        when(mockTechTalkFragment.topic()).thenReturn(topic);
        when(mockTechTalkFragment.date()).thenReturn(date);
    }

    @Test
    public void itShouldFetchAllTechTalks() throws Exception {
        simulateSuccess(emptyTechTalksList);

        verify(mockCallback).onFetched(fetchedCaptor.capture());
        assertEquals(emptyTechTalksList.size(), fetchedCaptor.getValue().size());
    }

    @Test
    public void itShouldFetchAllTechTalksWhenEmpty() throws Exception {
        simulateTechTalk("id", "description", "speaker", "topic", new Date());
        techTalkList.add(mockTechTalk);

        simulateSuccess(techTalkList);

        verify(mockCallback).onFetched(fetchedCaptor.capture());
        assertEquals(techTalkList.size(), fetchedCaptor.getValue().size());
    }

    @Test
    public void itShouldReportErrorWhenResponseIsNull() throws Exception {
        final ApolloException exception = new ApolloException("null");

        simulateError(exception);

        verify(mockCallback).oops(eq(exception));
    }

    @Test
    public void itShouldGenerateCorrectDateForToday() throws Exception {
        assertEquals("1970-01-01", DateUtil.toDateString(new Date(0)));
        assertEquals("2017-04-18", DateUtil.toDateString(new Date(1492544754000L)));
    }

    private void simulateSuccess(List<UpcomingTechTalksQuery.Data.AllTechTalk> techTalks) {
        when(mockClient.newCall(any(Operation.class))).thenReturn(mockCall);
        when(mockResponse.data()).thenReturn(mockData);
        when(mockData.allTechTalks()).thenReturn(techTalks);

        subject.fetchUpcoming(mockCallback);

        verify(mockClient).newCall(any(Operation.class));
        verify(mockCall).enqueue(callbackCaptor.capture());
        callbackCaptor.getValue().onResponse(mockResponse);

        verify(mockHandler).post(runnableCaptor.capture());
        runnableCaptor.getValue().run();
    }

    private void simulateError(ApolloException exception){
        when(mockClient.newCall(any(Operation.class))).thenReturn(mockCall);
        when(mockResponse.data()).thenReturn(mockData);

        subject.fetchUpcoming(mockCallback);

        verify(mockClient).newCall(any(Operation.class));
        verify(mockCall).enqueue(callbackCaptor.capture());
        callbackCaptor.getValue().onFailure(exception);
    }

}