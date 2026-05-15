package pe.vallegrande.gamelibrary.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * VIEW — PantallaBienvenida
 * Pantalla inicial del Sistema de Biblioteca de Videojuegos.
 * Punto de entrada del programa.
 */
public class PantallaBienvenida extends JFrame {

    // ── Paleta de colores ─────────────────────────────────────────────────────
    private static final Color C_BG        = new Color(10, 10, 20);
    private static final Color C_ACCENT    = new Color(99, 102, 241);   // índigo
    private static final Color C_ACCENT2   = new Color(168, 85, 247);   // violeta
    private static final Color C_TEXT      = new Color(240, 240, 255);
    private static final Color C_SUBTEXT   = new Color(148, 163, 184);
    private static final Color C_BTN_JG    = new Color(99, 102, 241);
    private static final Color C_BTN_CAT   = new Color(168, 85, 247);

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
        // Panel principal con fondo degradado
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo oscuro
                g2.setColor(C_BG);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Círculo decorativo izquierdo
                g2.setColor(new Color(99, 102, 241, 40));
                g2.fillOval(-80, -80, 300, 300);

                // Círculo decorativo derecho
                g2.setColor(new Color(168, 85, 247, 30));
                g2.fillOval(getWidth() - 200, getHeight() - 200, 350, 350);

                // Línea divisoria top
                GradientPaint grad = new GradientPaint(
                    0, 4, C_ACCENT, getWidth(), 4, C_ACCENT2);
                g2.setPaint(grad);
                g2.fillRect(0, 0, getWidth(), 4);
            }
        };
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 20, 8, 20);

        // ── Ícono / Emoji ──────────────────────────────────────────────────
        JLabel lblIcono = new JLabel("🎮", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(lblIcono, gbc);

        // ── Título ─────────────────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("GameLibrary", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(C_TEXT);
        gbc.gridy = 1;
        panelPrincipal.add(lblTitulo, gbc);

        // ── Subtítulo ──────────────────────────────────────────────────────
        JLabel lblSub = new JLabel("Sistema de Biblioteca de Videojuegos", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSub.setForeground(C_SUBTEXT);
        gbc.gridy = 2; gbc.insets = new Insets(0, 20, 24, 20);
        panelPrincipal.add(lblSub, gbc);

        // ── Separador ─────────────────────────────────────────────────────
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(99, 102, 241, 80));
        sep.setBackground(C_BG);
        sep.setPreferredSize(new Dimension(500, 1));
        gbc.gridy = 3; gbc.insets = new Insets(0, 40, 20, 40);
        panelPrincipal.add(sep, gbc);

        // ── Texto "Selecciona un módulo" ───────────────────────────────────
        JLabel lblModulo = new JLabel("Selecciona un módulo para comenzar", SwingConstants.CENTER);
        lblModulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblModulo.setForeground(C_SUBTEXT);
        gbc.gridy = 4; gbc.insets = new Insets(0, 20, 12, 20);
        panelPrincipal.add(lblModulo, gbc);

        // ── Botones de módulos ─────────────────────────────────────────────
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton btnJuegos = crearBoton("🎯  Gestión de Juegos", C_BTN_JG);
        btnJuegos.addActionListener(e -> abrirJuegos());
        gbc.gridy = 5; gbc.gridx = 0; gbc.insets = new Insets(6, 40, 6, 10);
        panelPrincipal.add(btnJuegos, gbc);

        JButton btnCategorias = crearBoton("🏷️  Gestión de Categorías", C_BTN_CAT);
        btnCategorias.addActionListener(e -> abrirCategorias());
        gbc.gridx = 1; gbc.insets = new Insets(6, 10, 6, 40);
        panelPrincipal.add(btnCategorias, gbc);

        // ── Footer ─────────────────────────────────────────────────────────
        JLabel lblFooter = new JLabel(
            "I.E.S.T.P. Valle Grande — IS08 POO  |  2025",
            SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(100, 100, 120));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.insets = new Insets(24, 20, 16, 20);
        panelPrincipal.add(lblFooter, gbc);

        setContentPane(panelPrincipal);
    }

    /** Crea un botón con estilo gaming personalizado */
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
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

    private void abrirJuegos() {
        new VentanaJuego().setVisible(true);
    }

    private void abrirCategorias() {
        new VentanaCategoria().setVisible(true);
    }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        // Look & Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new PantallaBienvenida().setVisible(true));
    }
}
