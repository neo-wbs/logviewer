package de.wbs.logger;

import java.util.List;

public class LogFilter {
    public List<LogEintrag> filtere(List<LogEintrag> eintraege, String level, String keyword) {
        return eintraege.stream()
            .filter(e -> passtLevel(e, level))
            .filter(e -> passtKeyword(e, keyword))
            .toList();
    }

    private boolean passtKeyword(LogEintrag eintrag, String keyword) {
        if (keyword == null ||  keyword.isEmpty()) {
            return true;
        }
        return eintrag.nachricht().toLowerCase().contains(keyword.toLowerCase());
    }

    private boolean passtLevel(LogEintrag eintrag, String level) {
        if (level == null || level.isEmpty()) {
            return true;
        }
        return eintrag.level().equalsIgnoreCase(level);
    }
}
