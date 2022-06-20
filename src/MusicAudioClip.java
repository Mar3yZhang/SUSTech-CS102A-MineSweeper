import java.applet.AudioClip;

public class MusicAudioClip {
    AudioClip clip = null;

    public AudioClip getAudioClip() {
        return this.clip;
    }

    public void setAudioClip(AudioClip clip) {
        this.clip = clip;
    }

    public void play() {//播放
        if (getAudioClip() != null) {
            getAudioClip().play();
        }
    }

    public void loop() {//循环
        if (getAudioClip() != null) {
            getAudioClip().loop();
        }
    }

    public void stop() {//停止
        if (getAudioClip() != null) {
            getAudioClip().stop();
        }
    }
}

//    public static void main(String[] args) {
//        MusicAudioClip mac = new MusicAudioClip();
//        try {
//            mac.setAudioClip(Applet.newAudioClip((new java.io.File("MainFrameBGM.wav")).toURL()));//填写你自己的文件路径
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        mac.loop();//循环播放
//    }
//}