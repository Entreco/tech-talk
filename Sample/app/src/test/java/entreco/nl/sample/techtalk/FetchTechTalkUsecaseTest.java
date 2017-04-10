package entreco.nl.sample.techtalk;

import android.os.Handler;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import entreco.nl.sample.techtalk.data.TechTalkModel;
import nl.entreco.AllTechTalksQuery;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ApolloClient.class})
public class FetchTechTalkUsecaseTest {

    private ArrayList<AllTechTalksQuery.Data.AllTeckTalk> emptyTechTalksList = new ArrayList<>();
    private ArrayList<AllTechTalksQuery.Data.AllTeckTalk> techTalkList = new ArrayList<>();

    @InjectMocks private FetchTechTalkUsecase subject;
    @Mock private ApolloClient mockClient;

    @Mock private Handler mockHandler;
    @Mock private FetchTechTalkUsecase.Callback mockCallback;
    @Mock private ApolloCall<AllTechTalksQuery.Data> mockCall;
    @Mock private Response<AllTechTalksQuery.Data> mockResponse;
    @Mock private AllTechTalksQuery.Data mockData;
    @Mock private AllTechTalksQuery.Data.AllTeckTalk mockTechTalk;

    @Captor private ArgumentCaptor<ApolloCall.Callback<AllTechTalksQuery.Data>> callbackCaptor;
    @Captor private ArgumentCaptor<List<TechTalkModel>> fetchedCaptor;
    @Captor private ArgumentCaptor<Runnable> runnableCaptor;

    @Test
    public void itShouldFetchAllTechTalks() throws Exception {
        simulateSuccess(emptyTechTalksList);

        verify(mockCallback).onFetched(fetchedCaptor.capture());
        assertEquals(emptyTechTalksList.size(), fetchedCaptor.getValue().size());
    }

    @Test
    public void itShouldFetchAllTechTalksWhenEmpty() throws Exception {
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

    private void simulateSuccess(List<AllTechTalksQuery.Data.AllTeckTalk> techTalks) {
        when(mockClient.newCall(any(Operation.class))).thenReturn(mockCall);
        when(mockResponse.data()).thenReturn(mockData);
        when(mockData.allTeckTalks()).thenReturn(techTalks);

        subject.fetchAll(mockCallback);

        verify(mockClient).newCall(any(Operation.class));
        verify(mockCall).enqueue(callbackCaptor.capture());
        callbackCaptor.getValue().onResponse(mockResponse);

        verify(mockHandler).post(runnableCaptor.capture());
        runnableCaptor.getValue().run();
    }

    private void simulateError(ApolloException exception){
        when(mockClient.newCall(any(Operation.class))).thenReturn(mockCall);
        when(mockResponse.data()).thenReturn(mockData);

        subject.fetchAll(mockCallback);

        verify(mockClient).newCall(any(Operation.class));
        verify(mockCall).enqueue(callbackCaptor.capture());
        callbackCaptor.getValue().onFailure(exception);
    }

}