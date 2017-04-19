package entreco.nl.sample.techtalk.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import fragment.TechTalkFragment;


public class TechTalkModel {

    private static final String ID = "id";
    private static final String ROOM = "room";
    private static final String SPEAKER = "speaker";
    private static final String TOPIC = "topic";
    private static final String DATE = "date";

    private final String id;
    private final String room;
    private final String speaker;
    private final String topic;
    private final String date;

    public String getId() {
        return id;
    }

    public String getRoom() {
        return room;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getTopic() {
        return topic;
    }

    public String getDate() {
        return date;
    }

    public TechTalkModel(@NonNull final TechTalkFragment data){
        id =  data.id();
        room = data.room() == null ? "" : data.room().description();
        speaker = data.speaker() == null ? "" : data.speaker().name();
        topic = data.topic();
        date = data.date() == null ? "" : data.date().toString();
    }

    public TechTalkModel(@NonNull final Bundle bundle){
        id = bundle.getString(ID);
        room = bundle.getString(ROOM);
        speaker = bundle.getString(SPEAKER);
        topic = bundle.getString(TOPIC);
        date = bundle.getString(DATE);
    }

    public Bundle toBundle(){
        final Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        bundle.putString(ROOM, room);
        bundle.putString(SPEAKER, speaker);
        bundle.putString(TOPIC, topic);
        bundle.putString(DATE, date);
        return bundle;
    }

    public TechTalkModel updateModel(@NonNull final String room, @NonNull final String speaker, @NonNull final String topic) {
        final Bundle bundle = toBundle();
        bundle.putString(ROOM, room);
        bundle.putString(SPEAKER, speaker);
        bundle.putString(TOPIC, topic);
        return new TechTalkModel(bundle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechTalkModel that = (TechTalkModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (room != null ? !room.equals(that.room) : that.room != null) return false;
        if (speaker != null ? !speaker.equals(that.speaker) : that.speaker != null) return false;
        if (topic != null ? !topic.equals(that.topic) : that.topic != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (speaker != null ? speaker.hashCode() : 0);
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
