package net.thumbtack.school.hospital.error;

public class ServerException extends Exception {

    private ServerError error;
    private String field;

    public ServerException(ServerError error) {
        this.error = error;
    }

    public ServerException(ServerError error, String field) {
        setError(error);
        this.field = field;
    }

    public ServerError getError() {
        return error;
    }

    public void setError(ServerError error) {
        error.setMessage(String.format(error.getMessage(), field));
        this.error = error;
    }
}
