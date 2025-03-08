package speedorz.crm.util;

import java.text.Normalizer;

/**
 * Utilidad para la normalización de texto en búsquedas.
 *
 * Esta clase proporciona un método para eliminar acentos (tildes) de los caracteres,
 * facilitando la comparación de textos en búsquedas insensibles a acentos.
 *
 * Ejemplo de uso:
 * <pre>
 * String textoNormalizado = NormalizadorBusquedaUtil.normalizarTexto("Café");
 * System.out.println(textoNormalizado); // Salida: "Cafe"
 * </pre>
 */
public class NormalizadorBusquedaUtil {

    /**
     * Normaliza un texto eliminando caracteres diacríticos (tildes, diéresis, etc.).
     *
     * Utiliza la normalización en la forma NFD para descomponer los caracteres acentuados en su
     * versión base y los diacríticos por separado, luego elimina los diacríticos con una expresión regular.
     *
     * @param texto Texto de entrada que puede contener caracteres acentuados.
     * @return Texto sin caracteres diacríticos.
     */
    public static String normalizarTexto(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // Elimina caracteres diacríticos
    }
}
