package tbcm.bungeelink.server;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SpigotCommunicator {
    public static final int SERVER_PORT = 10000;

    public HashMap<String, SpigotClient> clients = new HashMap<>();

    public Thread mainThread;

    public SpigotCommunicator() throws IOException {
        ServerSocket socket = new ServerSocket(SERVER_PORT);
        BungeeCord.instance.getLogger().info("[NOTICE] server is alive and listening.");
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
                Mainhub.instance.getLogger().info("[NEW CLIENT]: " + s_name);
                SpigotClient c = new SpigotClient(s_name, pID, type, max_players, stream, client);
                c.onDestroySocket(this::destroyConnection);
                clients.put(s_name, c);

            } catch (IOException e) {
                Mainhub.instance.getLogger().info("[IOException in listener thread]: ");
                e.printStackTrace();
                //active = false;
            }
        }
        });
        mainThread.start();
    }
    public boolean destroyConnection(SpigotClient client)  {
        try {
            client.self.close();
            Mainhub.instance.getLogger().info(client.getName() + "'s connection has been destroyed.");
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
