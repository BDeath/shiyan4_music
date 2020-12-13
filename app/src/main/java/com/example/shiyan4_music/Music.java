package com.example.shiyan4_music;

public class Music {
    private String song;
    private String singer;
    private int id;

    public Music(String song, String singer, int id) {
        this.song = song;
        this.singer = singer;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
