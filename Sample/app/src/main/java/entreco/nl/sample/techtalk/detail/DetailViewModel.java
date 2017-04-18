package entreco.nl.sample.techtalk.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class DetailViewModel implements UpdateTechTalkUsecase.Callback {

    public final ObservableBoolean isInEditMode;
    public final ObservableField<String> title;
    public final ObservableField<String> speaker;
    public final ObservableField<String> room;
    public final ObservableField<String> topic;

    private UpdateTechTalkUsecase updateUsecase;

    @Inject
    DetailViewModel(@NonNull final UpdateTechTalkUsecase updateUsecase) {
        this.updateUsecase = updateUsecase;
        isInEditMode = new ObservableBoolean(false);
        title = new ObservableField<>("Title");
        speaker = new ObservableField<>("Speaker");
        room = new ObservableField<>("Room");
        topic = new ObservableField<>("Topic");
    }

    public void toggle() {
        final boolean save = isInEditMode.get();
        isInEditMode.set(!isInEditMode.get());
        if (save) {
            updateUsecase.save("error", speaker.get(), room.get(), topic.get(), this);
        }
    }

    @Override
    public void done() {
        isInEditMode.set(false);
    }

    @Override
    public void oops(Exception error) {
        isInEditMode.set(false);
    }
}
