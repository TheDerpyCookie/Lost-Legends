package sabledream.studios.lostlegends.neoforge.handlers;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import sabledream.studios.lostlegends.util.ClientUtil;

public class ClientEvents {

    @SubscribeEvent
    public static void onTickEvent(ClientTickEvent.Post event) {
        ClientUtil.tickClient();
    }

}
