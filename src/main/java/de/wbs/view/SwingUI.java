package de.wbs.view;

import de.wbs.logger.LogEintrag;
import de.wbs.logger.LogParser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class SwingUI extends JFrame {
    private final String[] LEVELS = {"Alle","TRACE","DEBUG","INFO","WARN","ERROR","FATAL"};
    private final String[] COLUMNS = {"Zeilennummer", "Level", "Nachricht"};
    private final DefaultTableModel tblModel = new DefaultTableModel(COLUMNS, 0) {
        @Override
        public boolean isCellEditable(int row, int col) { //readonly für alle Zellen
            return false;
        }

        @Override
        public Class<?> getColumnClass(int col) { //Je nach Spalte als int oder String interpretieren
            return col == 0? Integer.class: String.class;
        }
    };
    private final JTable tblLogs = new JTable(tblModel);
    private final JComboBox<String> cbLevel = new JComboBox<>(LEVELS);
    private final JTextField txtKeyword = new JTextField(18);
    private final JLabel lblStatus = new JLabel(" ");
    private final LogParser parser = new LogParser();
    private List<LogEintrag> eintraege = List.of();

    public SwingUI() {
        super("Log Viewer");
        buildUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(960, 640);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUI() {
        JButton btnLoad   = new JButton("Laden");
        btnLoad.addActionListener(e -> ladeLogDatei());
        JButton btnExport = new JButton("Export");

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        toolbar.add(btnLoad);
        //toolbar.add(new JSeparator(SwingConstants.VERTICAL));
        toolbar.add(new JLabel("Level:"));
        toolbar.add(cbLevel);
        toolbar.add(new JLabel("Keyword:"));
        toolbar.add(txtKeyword);
        toolbar.add(btnExport);

        tblLogs.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        tblLogs.setRowHeight(20);
        tblLogs.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tblLogs.getColumnModel().getColumn(1).setCellRenderer(new LevelRenderer());

        lblStatus.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        setLayout(new BorderLayout(0, 2));
        add(toolbar, BorderLayout.NORTH);
        add(new JScrollPane(tblLogs), BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);
    }

    private static class LevelRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(CENTER);
            String level = (String) value;
            Color bgFarbe = Color.WHITE;
            switch (level) {
                case "FATAL":
                    bgFarbe = new Color(205, 51, 51);
                    setForeground(Color.WHITE);
                    break;
                case "ERROR":
                    bgFarbe = Color.red;
                    setForeground(Color.WHITE);
                    break;
                case "WARN":
                    bgFarbe = Color.orange;
                    break;
                case "INFO":
                    bgFarbe = new Color(255, 165, 79);
                    break;
                case "DEBUG":
                    bgFarbe = Color.yellow;
                    break;
            }
            setBackground(bgFarbe);
            return this;
        }
    }

    private void ladeLogDatei() {
        JFileChooser opendlg = new JFileChooser();
        opendlg.setCurrentDirectory(new File(System.getProperty("user.dir")));
        if(opendlg.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File datei = opendlg.getSelectedFile();
        try (Reader reader = new BufferedReader(new FileReader(datei))) {
            eintraege = parser.erzeugeEintraege(reader);
            for(LogEintrag le: eintraege) {
                tblModel.addRow(new Object[]{le.zeilennummer(), le.level(), le.nachricht()});
            }
        } catch (IOException e) {
            lblStatus.setText(e.getMessage());
        }
    }
}
