package tbcm.bungeelink.client;

import tbcm.bungeelink.client.Packet;
import tbcm.bungeelink.client.ServerConnectionInitPacket;

import java.io.IOException;
import java.net.Socket;

public class BungeeCordManager {

    public static final int P_SET_SERVER_STATE = 0;
    public static final int P_SET_MAX_PLAYERS = 1;
    public static final int P_SET_PLAYER_COUNT = 2;
    public static final int P_SET_JOIN_PERMS = 3;
    private static Socket connection;

    public static boolean init(ServerConnectionInitPacket initPacket){
        try {
            connection = new Socket("localhost", 10000);

            initPacket.packageAndSendTo(connection);

            return true;
        }catch(IOException ignored){return false;}
    }
    public static void close() throws IOException {
        connection.close();
    }
    public static boolean sendMessage(Packet p){
        try {
            p.packageAndSendTo(connection);
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }
}
