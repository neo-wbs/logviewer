package de.wbs;

import de.wbs.view.SwingUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SwingUI());
    }
}
