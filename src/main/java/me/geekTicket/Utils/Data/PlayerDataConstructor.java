package me.geekTicket.Utils.Data;

public final class PlayerDataConstructor {
    public String PLAYER_NAME;
    public String PLAYER_UUID;
    public int TICKET;
    public PlayerDataConstructor(String name, String uuid, int ticket) {
        this.PLAYER_NAME = name;
        this.PLAYER_UUID = uuid;
        this.TICKET = ticket;
    }
}