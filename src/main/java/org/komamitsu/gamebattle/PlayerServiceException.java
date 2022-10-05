package org.komamitsu.gamebattle;

public class PlayerServiceException extends RuntimeException {
    private final String targetId;

    public PlayerServiceException(Throwable cause, String targetId) {
        super(cause);
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "PlayerServiceException{" +
                "targetId='" + targetId + '\'' +
                "} " + super.toString();
    }
}