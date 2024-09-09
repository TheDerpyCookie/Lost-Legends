package sabledream.studios.lostlegends.fabric.handlers;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import sabledream.studios.lostlegends.util.ClientUtil;


public class ClientEvents
{

	public static void init() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> ClientUtil.tickClient());
		ClientUtil.doClientStuff();

	}
}
