package tbcm.bungeelink.client;

import java.io.DataOutputStream;
import java.io.IOException;

public class ServerConnectionInitPacket extends Packet {
    final String s_name;

    final int server_type;
    final int server_state;
    final int max_players;
    public ServerConnectionInitPacket(String s_name, final int server_type, final int server_state, final int max_players) {
        super(0);
        this.s_name = s_name;
        this.server_state = server_state;
        this.server_type = server_type;
        this.max_players = max_players;
    }


    @Override
    public void pack(DataOutputStream stream) throws IOException {
        stream.writeUTF(s_name);
        stream.writeInt(server_type);
        stream.writeInt(server_state);
        stream.writeInt(max_players);
    }
}
