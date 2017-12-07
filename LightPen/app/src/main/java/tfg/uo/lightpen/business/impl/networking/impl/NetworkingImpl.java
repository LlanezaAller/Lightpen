package tfg.uo.lightpen.business.impl.networking.impl;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import tfg.uo.lightpen.business.impl.networking.Networking;
import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.NetworkOperation;
import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.SocketOperations.Ports;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller.
 * Clase que gestiona las conexiones al objetivo de las prueba
 */

public class NetworkingImpl implements Networking {

    private int THREADS;
    private int TIMEOUT = 3000;
    private ContextData ctxD;
    private static final String TAG = "NetworkingImpl";
    private URL originalDomain;
    private ArrayList<URL> urlTree = new ArrayList<>();
    private final static String aHrefPattern = ".*<a href=.*";

    public NetworkingImpl(ContextData ctxD) {
        setContextData(ctxD);
        SharedPreferences settings = ctxD.getContext().getSharedPreferences("config.File", 0);
        THREADS = settings.getInt("maxThreads",
                Integer.parseInt(
                Factories
                .business
                .createConfigReaderFactory()
                .createConfigReader()
                .run(ctxD.getContext(), "maxThreads")));
        TIMEOUT = Integer.parseInt(
                Factories
                        .business
                        .createConfigReaderFactory()
                        .createConfigReader()
                        .run(ctxD.getContext(), "timeout"));
    }

    public NetworkingImpl(){}

    /**
     * Metodo con la capacidad de ejecutar la operación de red propuesta
     * recursivamente para los elementos enlazados de la web
     * @param url direccion a analizar
     * @param deep profundidad de analisis
     * @param netOp operacion de red a efectuar
     * @param socket socket>0 = operacion de sockets
     */
    @Override
    public void crawl(URL url, int deep, NetworkOperation netOp, boolean... socket){
        setOriginalDomain(url);
        if(socket.length>0){
            getSocket(url, (Ports) netOp);
        }else {
            stepUp(url, deep, netOp, -1);
        }
        Log.d(TAG, "crawl: End");
    }

    /**
     * Metodo lanzado por crawl para permitir el análisis recursivo
     * @param url direccion a analizar
     * @param deep profundidad de analisis
     * @param netOp operacion de red a efectuar
     * @param socket socket>0 = operacion de sockets
     */
    private void stepUp(URL url, int deep,NetworkOperation netOp, int steps, boolean... socket){
        if(steps < 0)
            get(url, netOp);
        steps++;
        if(steps >= deep || url == null)
            return;
        ArrayList<URL> urls =  getDoorsFromURL(url);
        for(URL u : urls) {
            if(assertURL(u)) {
                get(u,netOp);
                stepUp(u, deep, netOp, steps);
            }
        }

    }

    /**
     * Comprueba si la URL esta en el dominio
     * @param url URL a analizar
     * @return true si es valida, false si no lo es
     */
    public boolean assertURL(URL url){
        String domain = getOriginalDomain().getHost();
        if(getUrlTree().contains(url))
            return false;
        if(getOriginalDomain().equals(url))
            return true;
        return url.getHost().equals(domain);
    }

    /**
     * Aplica la operación de red a la URL
     * @param url Direccion
     * @param netOp Operacion de red
     * @return Operacion de red
     */
    @Override
    public NetworkOperation get(URL url, NetworkOperation netOp){
        HttpURLConnection con = null;
        try{
            con = (HttpURLConnection) url
                    .openConnection();
            con.setConnectTimeout(TIMEOUT);
            con.setReadTimeout(TIMEOUT);
            con.connect();
            if (con.getResponseCode() == 200) { //codigo correcto
                if(!getUrlTree().contains(url))
                    getUrlTree().add(url);
                netOp.setCon(con);
                netOp.run();
                netOp.setCon(null);
            }
        }catch (SocketTimeoutException e){
            Log.e(TAG, " get: ", e);
        }catch (UnknownHostException e){
            Log.e(TAG, " get: ", e);
        }catch (Exception e) {
            Log.e(TAG, " get: ", e);
        }
        finally {
            con.disconnect();
        }

        return netOp;
    }

    /**
     * Operación para el análisis de puertos mediante sockets
     * @param url dirección a analizar
     * @param ports operacion de red de puertos
     * @return
     */
    private NetworkOperation getSocket(URL url, Ports ports){
        final ExecutorService es = Executors.newFixedThreadPool(THREADS);
        final ArrayList<Future<Ports.ScanResult>> futures = new ArrayList<>();
        try{
            InetAddress address = InetAddress.getByName(url.getHost());
            String ip = address.getHostAddress();
            for (int port = 1; port <= 65535; port++) {
                futures.add(ports.portIsOpen(es, ip, port, TIMEOUT));
            }
            es.awaitTermination(200L, TimeUnit.MILLISECONDS);
            for (final Future<Ports.ScanResult> f : futures) {
                if (f.get().isOpen()) {
                    ports.getResponse().add("Port :"+f.get().getPort());
                }
            }

        } catch (UnknownHostException e){
            Log.e(TAG, " getSocket: ", e);
        }catch (Exception e) {
            Log.e(TAG, " getSocket: ", e);
        }

        return ports;
    }

    /**
     * Obtiene del código html las URL
     * @param url direccion de analisis
     * @return Lista de URL
     */
    private ArrayList<URL> getDoorsFromURL(URL url){
        ArrayList<URL> resp = new ArrayList<>();
        HttpURLConnection con = null;
        try{
            con = (HttpURLConnection) url
                    .openConnection();
            con.setConnectTimeout(TIMEOUT);
            con.setReadTimeout(TIMEOUT);
            con.connect();
            if (con.getResponseCode() == 200) { //codigo correcto
                resp = readUrls(con.getInputStream());
            }
        }catch (SocketTimeoutException e){
            Log.e(TAG, " getDoorsFromURL: ", e);
        }catch (UnknownHostException e){
            Log.e(TAG, " getDoorsFromURL: ", e);
        }catch (Exception e) {
            Log.e(TAG, " getDoorsFromURL: ", e);
        }
        finally {
            con.disconnect();
        }

        return resp;
    }

    /**
     * Obtiene una lista de URL de un recurso de la web
     * @param in
     * @return
     */
    private ArrayList<URL> readUrls(InputStream in){
        BufferedReader reader = null;
        URL url;
        ArrayList<URL> ans = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if(line.matches(aHrefPattern)) {
                    url = getUrlFromString(line, ans);
                    if (url != null)
                        ans.add(url);
                }
            }
            line = "";
        } catch (IOException e) {
            Log.e(TAG, "readUrls: Fallo con el fichero", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "readUrls: Problema cerrando el fichero", e);
                }
            }
        }
        return ans;
    }

    /**
     * Obtiene una URL de una cadena
     * @param in
     * @param urls
     * @return
     */
    private URL getUrlFromString(String in, ArrayList<URL> urls){
        URL url = null;
        String ur;
        try {
            String[] lines = in.split("href=\"");
            if(lines.length>2){
                lines = in.split(">");
                for(int i = 0 ; i < lines.length; i++)
                    getUrlFromString(lines[i], urls);
            }else {
                if(in.matches(aHrefPattern)) {
                    lines = in.split("href=");
                    if(lines.length>1) {
                        ur = lines[1].substring(1);
                        if (ur.contains("http") || ur.charAt(0) == '/') {
                            ur = ur.split("\"")[0];
                            if (!ur.contains(":")) {
                                if (ur.charAt(0) == '/')
                                    ur = getOriginalDomain().toString().split("://")[0]
                                            + "://" + getOriginalDomain().getHost() + ur;
                                else
                                    ur = getOriginalDomain().toString() + "/" + ur;
                            }
                            if (ur.split(";").length > 1)
                                url = new URL(ur.split(";")[0]);
                            else
                                url = new URL(ur);
                        }
                    }
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "getUrlFromString: Imposible generar la URL", e);
        }
        if(url != null) {
            if(assertURL(url)) {
                if (!urls.contains(url))
                    urls.add(url);
                //if (!getUrlTree().contains(url))
                //    getUrlTree().add(url);
            }
            else
                return null;
        }
        return url;
    }

    //region Getters & Setters
    public ContextData getContextData(){
        return this.ctxD;
    }
    public void setContextData(ContextData ctxD){
        this.ctxD = ctxD;
    }

    public URL getOriginalDomain() {
        return originalDomain;
    }

    public void setOriginalDomain(URL originalDomain) {
        this.originalDomain = originalDomain;
    }

    @Override
    public List<URL> getUrlTree() {
        return urlTree;
    }

    public void setUrlTree(ArrayList<URL> urlTree) {
        this.urlTree = urlTree;
    }
    //endregion
}
