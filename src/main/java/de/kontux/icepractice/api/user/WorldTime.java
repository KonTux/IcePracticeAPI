package de.kontux.icepractice.api.user;


public enum WorldTime {
    DAY(1000), SUNSET(13000), NIGHT(14000);

    private final long timeCode;

    WorldTime(long timeCode) {
        this.timeCode = timeCode;
    }

    public long getTimeCode() {
        return timeCode;
    }
}

