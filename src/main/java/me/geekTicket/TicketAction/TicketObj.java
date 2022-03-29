package me.geekTicket.TicketAction;

import java.util.UUID;

public final class TicketObj {
    public String PLAYER_NAME;
    public UUID PLAYER_UUID;
    public int TICKET;
    public TicketObj(String name, UUID uuid, int ticket) {
        this.PLAYER_NAME = name;
        this.PLAYER_UUID = uuid;
        this.TICKET = ticket;
    }
}