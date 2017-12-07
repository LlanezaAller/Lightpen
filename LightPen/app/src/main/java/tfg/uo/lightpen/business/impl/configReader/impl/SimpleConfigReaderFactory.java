package tfg.uo.lightpen.business.impl.configReader.impl;

import tfg.uo.lightpen.business.impl.configReader.ConfigReader;
import tfg.uo.lightpen.business.impl.configReader.ConfigReaderFactory;

/**
 * Created by Iñigo Llaneza Aller on 1/3/17.
 */

public class SimpleConfigReaderFactory implements ConfigReaderFactory {


    @Override
    public ConfigReader createConfigReader() { return new ConfigReaderImpl();}
}
