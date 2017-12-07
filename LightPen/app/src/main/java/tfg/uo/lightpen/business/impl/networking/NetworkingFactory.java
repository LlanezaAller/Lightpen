package tfg.uo.lightpen.business.impl.networking;

import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 20/2/17.
 */

public interface NetworkingFactory {

    public Networking createNetworking(ContextData ctx);
}
