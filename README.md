# LogViewer
## Lädt, filtert und speichert Logdateien

**Ablauf:**
```yaml
Entwickler:  git push origin/main
                    │
                    ▼
CI:          Tests laufen (build-and-test)
                    │ grün
                    ▼
CD:          JAR bauen → GitHub Release erstellen (git tag)
                    │
                    ▼
Nutzer:      github.com/.../releases → log-viewer.jar herunterladen
```
