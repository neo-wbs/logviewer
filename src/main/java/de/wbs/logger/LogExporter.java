package de.wbs.logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class LogExporter {
    public void exportieren(List<LogEintrag> eintraege_gefiltert, Writer writer) throws IOException {
        BufferedWriter bw = new BufferedWriter(writer);
        for (LogEintrag eintrag : eintraege_gefiltert) {
            bw.write(eintrag.nachricht());
            bw.newLine();
        }
        bw.flush();
    }
}
