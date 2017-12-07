package tfg.uo.lightpen.business.impl.htmlBuilder;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import tfg.uo.lightpen.business.impl.htmlBuilder.impl.nameTools.NameTool;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 20/2/17.
 */
public interface HtmlBuilder {
    /**
     * Metodo que crea el fichero donde se almacenaran los resultados de las pruebas
    */
    public File processOutput(String url);
}
