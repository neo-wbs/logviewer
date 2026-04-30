package de.wbs.logger;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogIntegrationsTest {
    @Test
    void read_filter_correct_output() throws IOException {
        String eingabe = """
            INFO server started
            ERROR database down
            WARN high memory
            ERROR timeout
            """;
        LogParser parser = new LogParser();
        List<LogEintrag> eintraege = parser.erzeugeEintraege(new StringReader(eingabe));
        LogFilter filter = new LogFilter();
        List<LogEintrag> gefiltert = filter.filtere(eintraege, "ERROR", "timeout");
        LogExporter exporter = new LogExporter();
        StringWriter sw = new StringWriter();
        exporter.exportieren(gefiltert, sw);
        String ausgabe = sw.toString();

        assertTrue(ausgabe.contains("timeout"));
        assertFalse(ausgabe.contains("server"));
        assertEquals(1, ausgabe.lines().count());
    }
}
