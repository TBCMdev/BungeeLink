package tbcm.bungeelink.server;

import mirage.mainhub.Mainhub;

import java.io.DataInputStream;
import java.io.IOException;

public class PacketHandler {
    public static final int P_SET_SERVER_STATE = 0;
    public static final int P_SET_MAX_PLAYERS = 1;
    public static final int P_SET_SERVER_TYPE = 2;

    public static final int P_SET_JOIN_PERMS = 3;

    public static final int P_DESTROY_CONNECTION = 4;
    public static void handle(SpigotClient c, int pID, DataInputStream rest) throws IOException {
        switch(pID){
            case P_SET_SERVER_STATE -> c.serverData.setState(rest.readInt());
            case P_SET_MAX_PLAYERS -> c.serverData.setMaxPlayerCount(rest.readInt());
            case P_SET_SERVER_TYPE -> c.serverData.setServerType(rest.readInt());
            case P_DESTROY_CONNECTION -> c.destroy();
            default -> Mainhub.instance.getLogger().info("[UNKNOWN PACKET (with id: " + pID + ")]: " + c.serverData.getServerName());
        }
    }
}
