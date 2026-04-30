package de.wbs.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogFilterTest {
    private LogFilter filter;
    private List<LogEintrag> eintraege;

    @BeforeEach
    void setup() {
        filter = new LogFilter();
        eintraege = List.of(
            new LogEintrag(1, "DEBUG", "Connecting database ..."),
            new LogEintrag(2, "INFO", "Connecting server ..."),
            new LogEintrag(3, "ERROR", "Connecting docker ..."),
            new LogEintrag(4, "WARN", "Connecting website ..."),
            new LogEintrag(5, "", "Connecting whatever ...")
        );
    }

    //Wenn Level + Keyword leer > Alle anzeigen
    @Test
    @DisplayName("Alle Einträge")
    void filter_empty_level() {
        assertEquals(5, filter.filtere(eintraege, "", "").size());
    }

    @Test
    void filter_by_level() {
        var ergebnis = filter.filtere(eintraege, "ERROR", "");
        assertEquals(1, ergebnis.size());
        assertEquals("ERROR", ergebnis.get(0).level());
    }

    @Test
    void filter_by_keyword() {
        var ergebnis = filter.filtere(eintraege, "", "website");
        assertEquals(1, ergebnis.size());
        assertTrue(ergebnis.get(0).nachricht().contains("website"));
    }

    @Test
    void filter_by_level_and_keyword() {
        var ergebnis = filter.filtere(eintraege, "DEBUG", "database");
        assertEquals(1, ergebnis.size());
    }

    @Test
    void filter_no_match() {
        assertTrue(filter.filtere(eintraege, "", "unittests").isEmpty());
    }

    @Test
    void keyword_search_case_insensitive() {
        var lower = filter.filtere(eintraege, "", "database");
        var upper = filter.filtere(eintraege, "", "DATABASE");
        assertEquals(lower.size(), upper.size());
    }

    @Test
    void level_filter_case_insensitive() {
        var lower = filter.filtere(eintraege, "error", "");
        var upper = filter.filtere(eintraege, "ERROR", "");
        assertEquals(upper.size(), lower.size());
    }
}
