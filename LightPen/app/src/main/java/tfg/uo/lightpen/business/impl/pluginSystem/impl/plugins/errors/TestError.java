package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors;


import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;

/**
 * Created by Iñigo Llaneza Aller on 2/3/17.
 */

public class TestError extends Error {

        public TestError(Plugin plugin, Double version){
            super.setDescription("descripcion del error");
            super.setVersion(version);
        }

        @Override
        public String printError() {
            return "<div> <p> Error de prueba </p> </div>";
        }
}
