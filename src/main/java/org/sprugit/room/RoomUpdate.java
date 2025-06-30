package org.sprugit.room;

import java.util.Optional;

public final class RoomUpdate {

    private RoomOperation roomOperation;
    private String player;
    private Optional<String> password = Optional.empty();

    public static RoomUpdate get(RoomOperation ro, String player, String pw){
        RoomUpdate update = new RoomUpdate();
        update.roomOperation = ro;
        update.player = player;
        update.password = Optional.ofNullable(pw);
        return update;
    }

    public static RoomUpdate get(RoomOperation ro, String player){
        return get(ro,player,null);
    }

    public RoomOperation getRoomOperation() {
        return roomOperation;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public String getPlayer() {
        return player;
    }
}
