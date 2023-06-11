package tbcm.bungeelink.server;

import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SpigotCommunicator {

    public static Plugin instance;

    public static final int SERVER_PORT = 10000;

    public HashMap<String, SpigotClient> clients = new HashMap<>();

    public Thread mainThread;

    public SpigotCommunicator(Plugin plugin) throws IOException {

        instance = plugin;

        ServerSocket socket = new ServerSocket(SERVER_PORT);
        instance.getLogger().info("[NOTICE] BungeeLink server is alive and listening.");
        boolean active = true;

        mainThread = new Thread(() -> {
        while (active) {
            try {
                Socket client = socket.accept();

                DataInputStream stream = new DataInputStream(client.getInputStream());

                // gets the init data.
                int pID = stream.readInt();
                String s_name = stream.readUTF();
                int type = stream.readInt();
                int state = stream.readInt();
                int max_players = stream.readInt();
                instance.getLogger().info("[NEW CLIENT]: " + s_name);
                SpigotClient c = new SpigotClient(s_name, pID, type, max_players, stream, client);
                c.onDestroySocket(this::destroyConnection);
                clients.put(s_name, c);

            } catch (IOException e) {
                instance.getLogger().info("[IOException in listener thread]: ");
                e.printStackTrace();
                //active = false;
            }
        }
        });
        mainThread.start();
    }
    public void stop(){
        for(SpigotClient client : clients.values()) destroyConnection(client);

        instance.getLogger().info("BungeeLink server disabled.");
    }
    public boolean destroyConnection(SpigotClient client)  {
        try {
            client.self.close();
            instance.getLogger().info(client.getName() + "'s connection has been destroyed.");
            clients.remove(client.getName());
            return true;
        } catch (IOException e) {
            return false;
        }

    }
    public SpigotClient getServerAsClient(String name){
        return clients.get(name);
    }
}
