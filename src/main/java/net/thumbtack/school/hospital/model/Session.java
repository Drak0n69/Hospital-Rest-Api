package net.thumbtack.school.hospital.model;

public class Session {

    private String cookie;
    private User user;

    public Session() {
    }

    public Session(String cookie, User user) {
        this.cookie = cookie;
        this.user = user;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;

        Session session = (Session) o;

        if (getCookie() != null ? !getCookie().equals(session.getCookie()) : session.getCookie() != null) return false;
        return getUser() != null ? getUser().equals(session.getUser()) : session.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = getCookie() != null ? getCookie().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Session{" +
                "cookie='" + cookie + '\'' +
                ", user=" + user +
                '}';
    }
}
