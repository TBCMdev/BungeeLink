package tbcm.bungeelink.client;

import java.io.DataOutputStream;
import java.io.IOException;

@Deprecated
public class ServerSetPlayerCountPacket extends Packet{
    final int player_count;
    public ServerSetPlayerCountPacket(final int player_count) {
        super(2);
        this.player_count = player_count;
    }

    @Override
    public void pack(DataOutputStream stream) throws IOException {
        stream.writeInt(player_count);
    }
}
