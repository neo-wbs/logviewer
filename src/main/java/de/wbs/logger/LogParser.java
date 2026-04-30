package de.wbs.logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LogParser {
    private final String[] LEVELS = {"TRACE","DEBUG","INFO","WARN","ERROR","FATAL"};

    public List<LogEintrag> erzeugeEintraege(Reader reader) throws IOException {
        List<LogEintrag> eintraege = new ArrayList<>();
        BufferedReader br = new BufferedReader(reader);
        String zeile;
        int zeilennummer = 1;
        while ((zeile = br.readLine()) != null) {
            eintraege.add(new LogEintrag(zeilennummer++,extrahiereLevel(zeile), zeile));
        }
        return eintraege;
    }

    private String extrahiereLevel(String zeile) {
        if (zeile == null) {
            return "";
        }
        String zeile_gross = zeile.toUpperCase();
        for (String level: LEVELS) {
            if (zeile_gross.contains(level)) {
                return level;
            }
        }
        return "";
    }
}
