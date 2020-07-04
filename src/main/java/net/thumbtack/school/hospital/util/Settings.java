package net.thumbtack.school.hospital.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Settings {
    @Value("50")
    private int maxNameLength;
    @Value("10")
    private int minPasswordLength;
    @Value("8888")
    private int restHttpPort;

    public int getMaxNameLength() {
        return maxNameLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public int getMinPasswordLength() {
        return minPasswordLength;
    }

    public void setMinPasswordLength(int minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    public int getRestHttpPort() {
        return restHttpPort;
    }

    public void setRestHttpPort(int restHttpPort) {
        this.restHttpPort = restHttpPort;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "maxNameLength=" + maxNameLength +
                ", minPasswordLength=" + minPasswordLength +
                ", restHttpPort=" + restHttpPort +
                '}';
    }
}
