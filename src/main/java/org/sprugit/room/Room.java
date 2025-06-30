package org.sprugit.room;

import org.sprugit.game.Color;
import org.sprugit.game.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class Room {

    public static class InvalidPasswordException extends RuntimeException {}
    public static class FullRoomException extends RuntimeException {}
    public static class NoPermissionException extends RuntimeException {}


    private final Map<String, Color> players = new HashMap<>();
    private final Game game;
    private Optional<String> password = Optional.empty();
    private final String owner;

    public Room(Game game, String owner) {
        this.game = game;
        this.owner = owner;
    }

    public void setPassword(String password) {
        this.password = Optional.of(Objects.requireNonNull(password));
    }

    private Optional<Color> getColor() {
        return game.getColors().stream()
                .filter(color -> !players.containsValue(color))
                .findAny();
    }

    private void addPlayer(String playerName) {
        Optional<Color> color = getColor();
        if (color.isEmpty()) {
            throw new FullRoomException();
        }
        players.put(playerName, color.get());
    }

    private void removePlayer(String playerName) {
        players.remove(playerName);
    }

    private void joinRoom(RoomUpdate ru){
        if(password.isPresent() && ru.getPassword().isEmpty())
            throw new InvalidPasswordException();

        if(password.isPresent() && ru.getPassword().isPresent() &&
                !ru.getPassword().get().equals(password.get()))
            throw new InvalidPasswordException();

        addPlayer(ru.getPlayer());
    }

    public void leaveRoom(RoomUpdate ru){
        removePlayer(ru.getPlayer());
    }

    public void changePassword(RoomUpdate ru){
        if(ru.getPlayer().equals(owner))
            throw new NoPermissionException();
        if(ru.getPassword().isEmpty())
            throw new InvalidPasswordException();
        this.password = Optional.of(ru.getPassword().get());
    }

    public void apply(RoomUpdate ru) {

        switch(ru.getRoomOperation()) {
            case JOIN -> joinRoom(ru);
            case LEAVE -> leaveRoom(ru);
            case CHANGE_PW -> changePassword(ru);
        }
    }

}
