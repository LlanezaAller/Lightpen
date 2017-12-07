package tfg.uo.lightpen.business.impl.networking;

import java.net.URL;
import java.util.List;

import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.NetworkOperation;

/**
 * Created by Iñigo on 23/06/2017.
 */

public interface Networking {

    public void crawl(URL url, int deep, NetworkOperation netOp, boolean... socket);

    public NetworkOperation get(URL url, NetworkOperation netOp);

    public List<URL> getUrlTree();
}
