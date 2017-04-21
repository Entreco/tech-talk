package entreco.nl.sample.techtalk.detail;

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.EditText;

import javax.inject.Inject;

import entreco.nl.sample.techtalk.data.TechTalkModel;

public class DetailViewModel implements UpdateTechTalkUsecase.Callback {

    @NonNull public final ObservableBoolean isInEditMode = new ObservableBoolean(false);
    @NonNull public final ObservableBoolean didEditModel = new ObservableBoolean(false);
    @NonNull public final ObservableField<TechTalkModel> techTalk = new ObservableField<>();
    @NonNull private final UpdateTechTalkUsecase updateUsecase;

    @Inject
    DetailViewModel(@NonNull final UpdateTechTalkUsecase updateUsecase,
                    @NonNull final Observable.OnPropertyChangedCallback onChangedCallback) {
        this.updateUsecase = updateUsecase;
        this.isInEditMode.addOnPropertyChangedCallback(onChangedCallback);
    }

    void setTechTalk(@NonNull TechTalkModel techTalkModel) {
        techTalk.set(techTalkModel);
    }

    public void toggleEditMode(@NonNull final EditText room, @NonNull final EditText speaker,
                               @NonNull final EditText topic) {
        saveUpdate(isInEditMode.get(), room.getText().toString(), speaker.getText().toString(),
                topic.getText().toString());
        isInEditMode.set(!isInEditMode.get());
    }

    private void saveUpdate(final boolean save, String room, String speaker, String topic) {
        if (save) {
            final TechTalkModel ttm = techTalk.get().updateModel(room, speaker, topic);
            updateUsecase.save(ttm, this);
        }
    }

    @Override
    public void done(@NonNull final TechTalkModel update) {
        isInEditMode.set(false);
        didEditModel.set(!update.equals(techTalk.get()));
        setTechTalk(update);
    }

    @Override
    public void oops(Exception error) {
        isInEditMode.set(false);
    }
}
