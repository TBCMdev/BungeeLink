package tbcm.bungeelink.server;

import net.md_5.bungee.api.plugin.Plugin;
public class ConnectedSpigotServerData {

    public enum SPIGOT_SERVER_DATA_STATE{
        OPEN,
        NEEDS_PLAYERS,
        FULL,
        MAINTENANCE,
        DOWN
    }

    private int max_player_count;
    private SPIGOT_SERVER_DATA_STATE state = SPIGOT_SERVER_DATA_STATE.OPEN;
    private String s_identifier;

    private SERVER_TYPE type;
    public ConnectedSpigotServerData(){}

    private void debug(String info, Object change){

        getLogger().info("[SPIGOT SERVER:" + s_identifier + "]: updated " + info + " to: " + change);
    }
    public void setServerType(int t){
        type = SERVER_TYPE.values()[t];
        debug("server type", type);
    }
    public SERVER_TYPE getServerType(){return type;}
    public int getMaxPlayerCount() {
        return max_player_count;
    }

    public SPIGOT_SERVER_DATA_STATE getState() {
        return state;
    }

    public void setMaxPlayerCount(int max_player_count) {
        this.max_player_count = max_player_count;
        debug("max player count", max_player_count);
    }

    public void setState(int state) {
        this.state = SPIGOT_SERVER_DATA_STATE.values()[state];
        debug("server state", this.state);
    }

    public void setServerName(String s_identifier) {
        this.s_identifier = s_identifier;
    }

    public String getServerName() {
        return s_identifier;
    }
}
