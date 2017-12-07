package tfg.uo.lightpen.business.impl.networking.impl;

import tfg.uo.lightpen.business.impl.networking.Networking;
import tfg.uo.lightpen.business.impl.networking.NetworkingFactory;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 20/2/17.
 */

public class SimpleNetworkingFactory implements NetworkingFactory {

    @Override
    public Networking createNetworking(ContextData ctxD) {
        return new NetworkingImpl(ctxD);
    }
}
