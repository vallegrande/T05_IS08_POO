package pe.vallegrande.gamelibrary.view;

import pe.vallegrande.gamelibrary.controller.CategoriaController;
import pe.vallegrande.gamelibrary.model.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * VIEW — VentanaCategoria
 * CRUD completo para la entidad Categoría.
 * Usa: JFrame, JPanel, JLabel, JTextField, JCheckBox, JTable, JOptionPane
 */
public class VentanaCategoria extends JFrame {

    // ── Colores ───────────────────────────────────────────────────────────────
    private static final Color C_BG      = new Color(15, 15, 30);
    private static final Color C_PANEL   = new Color(25, 25, 45);
    private static final Color C_ACCENT  = new Color(168, 85, 247);
    private static final Color C_TEXT    = new Color(240, 240, 255);
    private static final Color C_INPUT   = new Color(35, 35, 60);
    private static final Color C_BORDER  = new Color(60, 60, 90);

    // ── Componentes de formulario ─────────────────────────────────────────────
    private JTextField  txtNombre;
    private JTextField  txtDescripcion;
    private JCheckBox   chkActivo;
    private JTable      tabla;
    private DefaultTableModel modeloTabla;
    private JButton     btnGuardar, btnActualizar, btnEliminar, btnLimpiar;

    // ── Estado ────────────────────────────────────────────────────────────────
    private int idSeleccionado = -1;
    private final CategoriaController controller = new CategoriaController();

    public VentanaCategoria() {
        setTitle("🏷️  Gestión de Categorías — GameLibrary");
        setSize(820, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        construirUI();
        cargarTabla();
    }

    private void construirUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(C_BG);
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // ── Header ────────────────────────────────────────────────────────
        JLabel lblHeader = new JLabel("🏷️  Categorías de Videojuegos");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeader.setForeground(C_ACCENT);
        root.add(lblHeader, BorderLayout.NORTH);

        // ── Panel formulario (izquierda) ──────────────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(C_PANEL);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER, 1),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        panelForm.setPreferredSize(new Dimension(280, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 4, 6, 4);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelForm.add(label("Nombre *"), gbc);

        gbc.gridy = 1;
        txtNombre = campo();
        panelForm.add(txtNombre, gbc);

        // Descripción
        gbc.gridy = 2;
        panelForm.add(label("Descripción *"), gbc);

        gbc.gridy = 3;
        txtDescripcion = campo();
        panelForm.add(txtDescripcion, gbc);

        // CheckBox activo
        gbc.gridy = 4; gbc.insets = new Insets(12, 4, 6, 4);
        chkActivo = new JCheckBox("Categoría activa");
        chkActivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkActivo.setForeground(C_TEXT);
        chkActivo.setBackground(C_PANEL);
        chkActivo.setSelected(true);
        panelForm.add(chkActivo, gbc);

        // Botones
        gbc.gridy = 5; gbc.gridwidth = 1; gbc.insets = new Insets(16, 4, 4, 4);
        btnGuardar   = boton("💾 Guardar",    new Color(99, 102, 241));
        btnActualizar = boton("✏️ Actualizar", new Color(59, 130, 246));
        btnEliminar  = boton("🗑️ Eliminar",   new Color(239, 68, 68));
        btnLimpiar   = boton("✖ Limpiar",     new Color(75, 85, 99));

        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        panelForm.add(btnGuardar,    gbc);
        gbc.gridx = 1; panelForm.add(btnActualizar, gbc);
        gbc.gridx = 0; gbc.gridy = 6; panelForm.add(btnEliminar, gbc);
        gbc.gridx = 1;                panelForm.add(btnLimpiar,   gbc);

        root.add(panelForm, BorderLayout.WEST);

        // ── Tabla (derecha) ───────────────────────────────────────────────
        String[] columnas = {"ID", "Nombre", "Descripción", "Activo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        estilizarTabla();

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarFilaSeleccionada();
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(C_BG);
        scroll.getViewport().setBackground(new Color(20, 20, 40));
        scroll.setBorder(BorderFactory.createLineBorder(C_BORDER, 1));
        root.add(scroll, BorderLayout.CENTER);

        // ── Acciones ──────────────────────────────────────────────────────
        btnGuardar.addActionListener(e   -> accionGuardar());
        btnActualizar.addActionListener(e -> accionActualizar());
        btnEliminar.addActionListener(e  -> accionEliminar());
        btnLimpiar.addActionListener(e   -> limpiarFormulario());

        setContentPane(root);
    }

    // ── CRUD ACTIONS ──────────────────────────────────────────────────────────

    private void accionGuardar() {
        if (!validar()) return;

        Categoria c = new Categoria(
            txtNombre.getText().trim(),
            txtDescripcion.getText().trim(),
            chkActivo.isSelected()
        );

        if (controller.existeNombre(c.getNombre(), -1)) {
            JOptionPane.showMessageDialog(this,
                "Ya existe una categoría con ese nombre.",
                "Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (controller.insertar(c)) {
            JOptionPane.showMessageDialog(this, "✅ Categoría guardada correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizar() {
        if (idSeleccionado == -1) return;
        if (!validar()) return;

        Categoria c = new Categoria(
            idSeleccionado,
            txtNombre.getText().trim(),
            txtDescripcion.getText().trim(),
            chkActivo.isSelected()
        );

        if (controller.existeNombre(c.getNombre(), c.getId())) {
            JOptionPane.showMessageDialog(this,
                "Ya existe otra categoría con ese nombre.",
                "Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (controller.actualizar(c)) {
            JOptionPane.showMessageDialog(this, "✅ Categoría actualizada.");
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
        if (idSeleccionado == -1) return;
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar la categoría seleccionada?\nEsta acción no se puede deshacer.",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = controller.eliminar(idSeleccionado);
        if (ok) {
            JOptionPane.showMessageDialog(this, "✅ Categoría eliminada.");
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this,
                "❌ No se puede eliminar: la categoría tiene juegos asociados.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── HELPERS ───────────────────────────────────────────────────────────────

    private boolean validar() {
        if (txtNombre.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        if (txtNombre.getText().trim().length() < 3) {
            JOptionPane.showMessageDialog(this, "El nombre debe tener al menos 3 caracteres.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtDescripcion.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "La descripción es obligatoria.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtDescripcion.requestFocus();
            return false;
        }
        return true;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Categoria> lista = controller.listarTodos();
        for (Categoria c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getId(), c.getNombre(), c.getDescripcion(), c.isActivo() ? "✅ Sí" : "❌ No"
            });
        }
    }

    private void cargarFilaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
        txtDescripcion.setText((String) modeloTabla.getValueAt(fila, 2));
        chkActivo.setSelected(modeloTabla.getValueAt(fila, 3).toString().contains("Sí"));
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void limpiarFormulario() {
        idSeleccionado = -1;
        txtNombre.setText("");
        txtDescripcion.setText("");
        chkActivo.setSelected(true);
        tabla.clearSelection();
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void estilizarTabla() {
        tabla.setBackground(new Color(20, 20, 40));
        tabla.setForeground(C_TEXT);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.setGridColor(C_BORDER);
        tabla.setSelectionBackground(C_ACCENT);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.getTableHeader().setBackground(new Color(30, 30, 55));
        tabla.getTableHeader().setForeground(C_ACCENT);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(220);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
    }

    private JLabel label(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(C_SUBTEXT());
        return l;
    }

    private Color C_SUBTEXT() { return new Color(148, 163, 184); }

    private JTextField campo() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBackground(C_INPUT);
        tf.setForeground(C_TEXT);
        tf.setCaretColor(C_TEXT);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        tf.setPreferredSize(new Dimension(0, 32));
        return tf;
    }

    private JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(118, 36));
        return btn;
    }
}
