package tbcm.bungeelink.server;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class SpigotClient extends Thread{

    public ConnectedSpigotServerData serverData = new ConnectedSpigotServerData();

    private DataInputStream in;

    private final ServerInfo info;

    public final Socket self;


    private Consumer<SpigotClient> onDestroySocketCallable;
    public SpigotClient(String s_name, int state, final int type, int max_players, DataInputStream stream, Socket self) throws IOException {
        this.in = stream;

        serverData.setServerName(s_name);

        serverData.setState(state);
        serverData.setServerType(type);
        serverData.setMaxPlayerCount(max_players);

        this.self = self;
        info = ProxyServer.getInstance().getServerInfo(s_name);
    }
    public void onDestroySocket(Consumer<SpigotClient> d){
        onDestroySocketCallable = d;
    }
    public boolean destroy() {
        try {

            self.close();
            onDestroySocketCallable.accept(this);
            return true;
        } catch (IOException e) {
            return false;
        }

    }
    public void run(){
        while(true){
            try {
                int id = in.readInt();
                PacketHandler.handle(this, id, in);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public ServerInfo getServerInfo(){return info;}
    public int getPlayerCount(){return info.getPlayers().size();}
    public ConnectServerResponse canPlayerConnect(ProxiedPlayer player){
        if(serverData.getState() == ConnectedSpigotServerData.SPIGOT_SERVER_DATA_STATE.DOWN ||
           serverData.getState() == ConnectedSpigotServerData.SPIGOT_SERVER_DATA_STATE.MAINTENANCE ||
           serverData.getState() == ConnectedSpigotServerData.SPIGOT_SERVER_DATA_STATE.FULL) return ConnectServerResponse.UNKNOWN_FAILURE;
        if(getPlayerCount() >= serverData.getMaxPlayerCount()) return ConnectServerResponse.FULL;

        return ConnectServerResponse.SUCCESS;
    }
}
