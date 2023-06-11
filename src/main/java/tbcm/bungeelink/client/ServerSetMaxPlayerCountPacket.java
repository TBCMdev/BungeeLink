package tbcm.bungeelink.client;

import java.io.DataOutputStream;
import java.io.IOException;

public class ServerSetMaxPlayerCountPacket extends Packet{
    final int max_player_count;
    public ServerSetMaxPlayerCountPacket(final int max_player_count) {
        super(1);
        this.max_player_count = max_player_count;
    }

    @Override
    public void pack(DataOutputStream stream) throws IOException {
        stream.writeInt(max_player_count);
    }
}
