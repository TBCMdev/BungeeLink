package tbcm.bungeelink.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class Packet {
    private final int pID;

    public Packet(final int pID){
        this.pID = pID;
    }
    public void packageAndSendTo(Socket conn) throws IOException {
        DataOutputStream stream = new DataOutputStream(conn.getOutputStream());
        stream.writeInt(pID);
        pack(stream);

        stream.flush();
    }
    public abstract void pack(DataOutputStream stream) throws IOException;

}
