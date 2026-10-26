
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import es.ciudadescolar.Main;

public class MainTest {
    @Test
    void testGeneracionLog() throws IOException {

        Path ficheroLog = Path.of("curso2627_SVAP05_maven.log");
        Files.deleteIfExists(ficheroLog);
        // Ejecutamos la aplicación para generar el log:
        // new String[0] crea un array vacío de cadenas que equivale a ejecutar la
        // aplicación desde la consola sin argumentos
        Main.main(new String[0]);
        // Comprobamos que se ha creado el fichero de log
        // si assertTrue() recibe false, se interrumpe inmediatamente la ejecución del
        // test actual
        assertTrue(Files.isRegularFile(ficheroLog), "La aplicación no ha creado el fichero de log");
        // Si se creó el fichero, leemos los registros del log
        List<String> lineas = Files.readAllLines(ficheroLog);
        // Buscamos un registro INFO con el mensaje esperado
        String mensaje = "Programa ejecutado";
        Pattern patron = Pattern.compile("^\\S+\\s+\\S+\\s+\\[[^]]+\\]\\s+INFO\\s+.+? - .*"
                + Pattern.quote(mensaje) + ".*$");
        boolean encontrado = false;
        for (String linea : lineas) {
            if (patron.matcher(linea).matches()) {
                encontrado = true;
                break;
            }
        }
        // Tras recorrer todos los registros, si no encuentra uno INFO con el mensaje
        // esperado, dará error.
        assertTrue(encontrado, "No existe un registro INFO con el mensaje esperado");
    }
}