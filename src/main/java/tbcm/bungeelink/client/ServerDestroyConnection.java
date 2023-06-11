package tbcm.bungeelink.client;

import java.io.DataOutputStream;
import java.io.IOException;

public class ServerDestroyConnection extends Packet{


    public ServerDestroyConnection() {
        super(4);
    }

    @Override
    public void pack(DataOutputStream stream) throws IOException {}
}
