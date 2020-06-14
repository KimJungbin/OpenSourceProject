package app.android.ww.com.myfit;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Water {

    public String waterMount;

    public Water() {
        // Default constructor required for calls to DataSnapshot.getValue(Water.class)
    }

    public Water(String waterMount) {
        this.waterMount = waterMount;
    }

    public String getWaterMount() {
        return waterMount;
    }

    public void setWaterMount(String waterMount) {
        this.waterMount = waterMount;
    }

    @Override
    public String toString() {
        return "Water{" +
                "waterMount='" + waterMount + '\'' + '}';
    }
}
