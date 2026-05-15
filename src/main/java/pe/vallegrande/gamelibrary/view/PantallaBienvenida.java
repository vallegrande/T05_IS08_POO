package pe.vallegrande.gamelibrary.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * VIEW — PantallaBienvenida
 * Pantalla inicial del Sistema de Biblioteca de Videojuegos.
 */
public class PantallaBienvenida extends JFrame {

    // ── Colores ─────────────────────────────────────────────
    private static final Color C_BG      = new Color(10, 10, 20);
    private static final Color C_TEXT    = new Color(240, 240, 255);
    private static final Color C_SUBTEXT = new Color(148, 163, 184);
    private static final Color C_BTN_JG  = new Color(99, 102, 241);
    private static final Color C_BTN_CAT = new Color(168, 85, 247);

    public PantallaBienvenida() {
        configurarVentana();
        construirUI();
    }

    private void configurarVentana() {
        setTitle("GameLibrary — Sistema de Biblioteca de Videojuegos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void construirUI() {

        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(C_BG);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 20, 8, 20);

        // ── ICONO ─────────────────────────────────────────────
        JLabel lblIcono = new JLabel("🎮", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelPrincipal.add(lblIcono, gbc);

        // ── LOGO ─────────────────────────────────────────────
        ImageIcon logo = new ImageIcon("img/logo.jpg");
        Image img = logo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(img));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        panelPrincipal.add(lblLogo, gbc);

        // ── TÍTULO ───────────────────────────────────────────
        JLabel lblTitulo = new JLabel("GameLibrary", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(C_TEXT);

        gbc.gridy = 2;
        panelPrincipal.add(lblTitulo, gbc);

        // ── SUBTÍTULO ────────────────────────────────────────
        JLabel lblSub = new JLabel(
                "Sistema de Biblioteca de Videojuegos",
                SwingConstants.CENTER
        );
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSub.setForeground(C_SUBTEXT);

        gbc.gridy = 3;
        panelPrincipal.add(lblSub, gbc);

        // ── BOTONES ──────────────────────────────────────────
        JButton btnJuegos = crearBoton("🎯 Gestión de Juegos", C_BTN_JG);
        btnJuegos.addActionListener(e -> abrirJuegos());

        JButton btnCategorias = crearBoton("🏷️ Gestión de Categorías", C_BTN_CAT);
        btnCategorias.addActionListener(e -> abrirCategorias());

        gbc.gridwidth = 1;

        gbc.gridy = 4;
        gbc.gridx = 0;
        panelPrincipal.add(btnJuegos, gbc);

        gbc.gridx = 1;
        panelPrincipal.add(btnCategorias, gbc);

        setContentPane(panelPrincipal);
    }

    // ── BOTÓN ESTILO ───────────────────────────────────────
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }

                g2.fill(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 12, 12));

                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(220, 52));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // ── ACCIONES ───────────────────────────────────────────
    private void abrirJuegos() {
        new VentanaJuego().setVisible(true);
    }

    private void abrirCategorias() {
        new VentanaCategoria().setVisible(true);
    }

    // ── MAIN ───────────────────────────────────────────────
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() ->
                new PantallaBienvenida().setVisible(true)
        );
    }
}