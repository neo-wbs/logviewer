package de.wbs.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class LogParserTest {
    private LogParser parser;

    @BeforeEach
    void setup() {
        parser = new LogParser();
    }

    @Test
    void correct_line_numbers() throws IOException {
        //Hier wird deutlich, warum erzeugeEintraege einen Reader entgegennimmt:
        //SwingUI übergibt BufferedReader, Test übergibt StringReader
        var eintraege = parser.erzeugeEintraege(new StringReader("text\ntext\ntext"));
        assertEquals(3, eintraege.size());
        assertEquals(1, eintraege.get(0).zeilennummer());
        assertEquals(3, eintraege.get(2).zeilennummer());
    }

    @Test
    void check_empty_reader() throws IOException {
        assertTrue(parser.erzeugeEintraege(new StringReader("")).isEmpty());
    }

    @Test
    void raw_line_preserved() throws IOException {
        String line = "2024-01-15 ERROR something broke!  ";
        var eintraege = parser.erzeugeEintraege(new StringReader(line));
        assertEquals(line, eintraege.get(0).nachricht());
    }

    @Test
    void level_detection_case_insensitive() throws IOException {
        var eintraege = parser.erzeugeEintraege(new StringReader("error something failed"));
        assertEquals("ERROR", eintraege.get(0).level());
    }

    @Test
    void no_level_returns_empty_string() throws IOException {
        var eintraege = parser.erzeugeEintraege(new StringReader("plain log line"));
        assertEquals("", eintraege.get(0).level());
    }
}
