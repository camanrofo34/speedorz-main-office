package speedorz.crm.util;

import java.text.Normalizer;

public class NormalizadorBusquedaUtil {

    //Método para eliminar tíldes
    public static String normalizarTexto(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }
}
