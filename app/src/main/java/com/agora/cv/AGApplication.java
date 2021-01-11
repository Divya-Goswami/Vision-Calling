package com.agora.cv;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;




import com.agora.cv.model.AGEventHandler;
import com.agora.cv.model.CurrentUserSettings;
import com.agora.cv.model.EngineConfig;
import com.agora.cv.model.MyEngineEventHandler;

import io.agora.rtc.Constants;
import io.agora.rtc.RtcEngine;

public class AGApplication extends Application {
//    private CurrentUserSettings mVideoSettings = new CurrentUserSettings();


    private RtcEngine mRtcEngine;
    private EngineConfig mConfig;
    private MyEngineEventHandler mEventHandler;

    public RtcEngine rtcEngine() {
        return mRtcEngine;
    }

    public EngineConfig config() {
        return mConfig;
    }

    public void addEventHandler(AGEventHandler handler) {
        mEventHandler.addEventHandler(handler);
    }

    public void remoteEventHandler(AGEventHandler handler) {
        mEventHandler.removeEventHandler(handler);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createRtcEngine();
    }

    private void createRtcEngine() {
        Context context = getApplicationContext();
        String appId = context.getString(R.string.agora_app_id);
        if (TextUtils.isEmpty(appId)) {
            throw new RuntimeException("NEED TO use your App ID, get your own ID at https://dashboard.agora.io/");
        }

        mEventHandler = new MyEngineEventHandler();
        try {
            // Creates an RtcEngine instance
            mRtcEngine = RtcEngine.create(context, appId, mEventHandler);
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" );
        }


        mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);

        mRtcEngine.enableVideo();

        mRtcEngine.enableAudioVolumeIndication(200, 3, false);

        mConfig = new EngineConfig();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
