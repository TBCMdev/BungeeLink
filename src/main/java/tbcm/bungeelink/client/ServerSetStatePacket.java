package tbcm.bungeelink.client;

import java.io.DataOutputStream;
import java.io.IOException;


// Sets the state of this spigot server.

public class ServerSetStatePacket extends Packet{

    public enum SERVER_STATE{
        OPEN,
        NEEDS_PLAYERS,
        FULL,
        MAINTENANCE,
        DOWN
    }

    final SERVER_STATE state;
    public ServerSetStatePacket(SERVER_STATE state) {
        super(0);
        this.state = state;
    }

    @Override
    public void pack(DataOutputStream stream) throws IOException {
        stream.writeInt(this.state.ordinal());
    }
}
