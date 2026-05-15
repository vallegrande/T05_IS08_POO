package pe.vallegrande.gamelibrary.view;

import pe.vallegrande.gamelibrary.controller.CategoriaController;
import pe.vallegrande.gamelibrary.controller.JuegoController;
import pe.vallegrande.gamelibrary.model.Categoria;
import pe.vallegrande.gamelibrary.model.Juego;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * VIEW — VentanaJuego
 * CRUD completo para la entidad Juego.
 * Usa: JFrame, JPanel, JLabel, JTextField, JCheckBox, JComboBox, JTable, JOptionPane
 */
public class VentanaJuego extends JFrame {

    // ── Colores ───────────────────────────────────────────────────────────────
    private static final Color C_BG     = new Color(10, 10, 20);
    private static final Color C_PANEL  = new Color(20, 20, 38);
    private static final Color C_ACCENT = new Color(99, 102, 241);
    private static final Color C_TEXT   = new Color(240, 240, 255);
    private static final Color C_INPUT  = new Color(30, 30, 55);
    private static final Color C_BORDER = new Color(55, 55, 85);

    // ── Componentes ───────────────────────────────────────────────────────────
    private JTextField  txtTitulo, txtDesarrollador, txtAnio, txtPlataforma, txtPrecio;
    private JCheckBox   chkDisponible;
    private JComboBox<Categoria> cbCategoria;
    private JTable      tabla;
    private DefaultTableModel modeloTabla;
    private JButton     btnGuardar, btnActualizar, btnEliminar, btnLimpiar;

    // ── Estado ────────────────────────────────────────────────────────────────
    private int idSeleccionado = -1;
    private final JuegoController     juegoCtrl     = new JuegoController();
    private final CategoriaController categoriaCtrl = new CategoriaController();

    public VentanaJuego() {
        setTitle("🎯  Gestión de Juegos — GameLibrary");
        setSize(1050, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        construirUI();
        cargarCategorias();
        cargarTabla();
    }

    private void construirUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(C_BG);
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Header
        JLabel lblHeader = new JLabel("🎯  Juegos de Videojuego");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeader.setForeground(C_ACCENT);
        root.add(lblHeader, BorderLayout.NORTH);

        // ── Formulario ────────────────────────────────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(C_PANEL);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER, 1),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        panelForm.setPreferredSize(new Dimension(320, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 4, 4, 4);

        int row = 0;

        // Título
        addLabel(panelForm, "Título *", gbc, row++);
        txtTitulo = campo();
        addField(panelForm, txtTitulo, gbc, row++);

        // Desarrollador
        addLabel(panelForm, "Desarrollador *", gbc, row++);
        txtDesarrollador = campo();
        addField(panelForm, txtDesarrollador, gbc, row++);

        // Año lanzamiento
        addLabel(panelForm, "Año de Lanzamiento *", gbc, row++);
        txtAnio = campo();
        txtAnio.setToolTipText("Ejemplo: 2024");
        addField(panelForm, txtAnio, gbc, row++);

        // Plataforma
        addLabel(panelForm, "Plataforma *", gbc, row++);
        txtPlataforma = campo();
        txtPlataforma.setToolTipText("Ej: PC, PS5, Nintendo Switch");
        addField(panelForm, txtPlataforma, gbc, row++);

        // Precio
        addLabel(panelForm, "Precio (S/.) *", gbc, row++);
        txtPrecio = campo();
        txtPrecio.setToolTipText("Ej: 199.90 — usa 0 para juegos gratuitos");
        addField(panelForm, txtPrecio, gbc, row++);

        // Categoría
        addLabel(panelForm, "Categoría *", gbc, row++);
        cbCategoria = new JComboBox<>();
        cbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbCategoria.setBackground(C_INPUT);
        cbCategoria.setForeground(C_TEXT);
        addField(panelForm, cbCategoria, gbc, row++);

        // CheckBox disponible
        gbc.gridy = row++; gbc.insets = new Insets(10, 4, 6, 4);
        chkDisponible = new JCheckBox("Juego disponible en catálogo");
        chkDisponible.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkDisponible.setForeground(C_TEXT);
        chkDisponible.setBackground(C_PANEL);
        chkDisponible.setSelected(true);
        panelForm.add(chkDisponible, gbc);

        // Botones
        gbc.gridwidth = 1; gbc.insets = new Insets(14, 4, 4, 4);

        btnGuardar    = boton("💾 Guardar",    new Color(99, 102, 241));
        btnActualizar = boton("✏️ Actualizar", new Color(59, 130, 246));
        btnEliminar   = boton("🗑️ Eliminar",   new Color(239, 68, 68));
        btnLimpiar    = boton("✖ Limpiar",     new Color(75, 85, 99));

        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        gbc.gridy = row; gbc.gridx = 0; panelForm.add(btnGuardar,    gbc);
        gbc.gridx = 1;                  panelForm.add(btnActualizar,  gbc);
        gbc.gridy = ++row; gbc.gridx = 0; panelForm.add(btnEliminar, gbc);
        gbc.gridx = 1;                  panelForm.add(btnLimpiar,     gbc);

        root.add(panelForm, BorderLayout.WEST);

        // ── Tabla ─────────────────────────────────────────────────────────
        String[] cols = {"ID", "Título", "Desarrollador", "Año", "Plataforma", "Precio", "Categoría", "Disponible"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        estilizarTabla();

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarFilaSeleccionada();
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(new Color(15, 15, 30));
        scroll.setBorder(BorderFactory.createLineBorder(C_BORDER, 1));
        root.add(scroll, BorderLayout.CENTER);

        // Acciones
        btnGuardar.addActionListener(e    -> accionGuardar());
        btnActualizar.addActionListener(e -> accionActualizar());
        btnEliminar.addActionListener(e   -> accionEliminar());
        btnLimpiar.addActionListener(e    -> limpiarFormulario());

        setContentPane(root);
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    private void accionGuardar() {
        if (!validar()) return;
        Juego j = construirJuego();

        if (juegoCtrl.existeTitulo(j.getTitulo(), -1)) {
            JOptionPane.showMessageDialog(this,
                "Ya existe un juego con ese título.", "Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (juegoCtrl.insertar(j)) {
            JOptionPane.showMessageDialog(this, "✅ Juego guardado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizar() {
        if (idSeleccionado == -1 || !validar()) return;
        Juego j = construirJuego();
        j.setId(idSeleccionado);

        if (juegoCtrl.existeTitulo(j.getTitulo(), j.getId())) {
            JOptionPane.showMessageDialog(this,
                "Otro juego ya tiene ese título.", "Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (juegoCtrl.actualizar(j)) {
            JOptionPane.showMessageDialog(this, "✅ Juego actualizado.");
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
        if (idSeleccionado == -1) return;
        int ok = JOptionPane.showConfirmDialog(this,
            "¿Eliminar el juego seleccionado?",
            "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok != JOptionPane.YES_OPTION) return;

        if (juegoCtrl.eliminar(idSeleccionado)) {
            JOptionPane.showMessageDialog(this, "✅ Juego eliminado.");
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── VALIDACIONES ──────────────────────────────────────────────────────────

    private boolean validar() {
        if (txtTitulo.getText().isBlank()) {
            alerta("El título es obligatorio."); txtTitulo.requestFocus(); return false;
        }
        if (txtTitulo.getText().trim().length() < 2) {
            alerta("El título debe tener al menos 2 caracteres."); return false;
        }
        if (txtDesarrollador.getText().isBlank()) {
            alerta("El desarrollador es obligatorio."); txtDesarrollador.requestFocus(); return false;
        }
        if (txtAnio.getText().isBlank()) {
            alerta("El año de lanzamiento es obligatorio."); txtAnio.requestFocus(); return false;
        }
        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());
            if (anio < 1970 || anio > 2030) {
                alerta("El año debe estar entre 1970 y 2030."); return false;
            }
        } catch (NumberFormatException e) {
            alerta("El año debe ser un número válido (ej: 2024)."); return false;
        }
        if (txtPlataforma.getText().isBlank()) {
            alerta("La plataforma es obligatoria."); txtPlataforma.requestFocus(); return false;
        }
        if (txtPrecio.getText().isBlank()) {
            alerta("El precio es obligatorio (usa 0 para gratuito)."); txtPrecio.requestFocus(); return false;
        }
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio < 0) { alerta("El precio no puede ser negativo."); return false; }
        } catch (NumberFormatException e) {
            alerta("El precio debe ser un número válido (ej: 199.90)."); return false;
        }
        if (cbCategoria.getSelectedItem() == null) {
            alerta("Selecciona una categoría."); return false;
        }
        return true;
    }

    private void alerta(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validación", JOptionPane.WARNING_MESSAGE);
    }

    // ── HELPERS ───────────────────────────────────────────────────────────────

    private Juego construirJuego() {
        Categoria cat = (Categoria) cbCategoria.getSelectedItem();
        return new Juego(
            0,
            txtTitulo.getText().trim(),
            txtDesarrollador.getText().trim(),
            Integer.parseInt(txtAnio.getText().trim()),
            txtPlataforma.getText().trim(),
            Double.parseDouble(txtPrecio.getText().trim()),
            chkDisponible.isSelected(),
            cat.getId()
        );
    }

    private void cargarCategorias() {
        cbCategoria.removeAllItems();
        for (Categoria c : categoriaCtrl.listarActivos()) {
            cbCategoria.addItem(c);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Juego j : juegoCtrl.listarTodos()) {
            modeloTabla.addRow(new Object[]{
                j.getId(), j.getTitulo(), j.getDesarrollador(),
                j.getAnioLanzamiento(), j.getPlataforma(),
                String.format("S/. %.2f", j.getPrecio()),
                j.getCategoriaNombre(),
                j.isDisponible() ? "✅ Sí" : "❌ No"
            });
        }
    }

    private void cargarFilaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        txtTitulo.setText((String) modeloTabla.getValueAt(fila, 1));
        txtDesarrollador.setText((String) modeloTabla.getValueAt(fila, 2));
        txtAnio.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtPlataforma.setText((String) modeloTabla.getValueAt(fila, 4));
        String precioStr = modeloTabla.getValueAt(fila, 5).toString().replace("S/. ", "");
        txtPrecio.setText(precioStr);
        // Seleccionar categoría en ComboBox
        String catNombre = (String) modeloTabla.getValueAt(fila, 6);
        for (int i = 0; i < cbCategoria.getItemCount(); i++) {
            if (cbCategoria.getItemAt(i).getNombre().equals(catNombre)) {
                cbCategoria.setSelectedIndex(i);
                break;
            }
        }
        chkDisponible.setSelected(modeloTabla.getValueAt(fila, 7).toString().contains("Sí"));
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void limpiarFormulario() {
        idSeleccionado = -1;
        txtTitulo.setText(""); txtDesarrollador.setText("");
        txtAnio.setText(""); txtPlataforma.setText(""); txtPrecio.setText("");
        chkDisponible.setSelected(true);
        if (cbCategoria.getItemCount() > 0) cbCategoria.setSelectedIndex(0);
        tabla.clearSelection();
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void estilizarTabla() {
        tabla.setBackground(new Color(15, 15, 30));
        tabla.setForeground(C_TEXT);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(26);
        tabla.setGridColor(C_BORDER);
        tabla.setSelectionBackground(C_ACCENT);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.getTableHeader().setBackground(new Color(25, 25, 50));
        tabla.getTableHeader().setForeground(C_ACCENT);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(35);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(90);
    }

    private void addLabel(JPanel p, String texto, GridBagConstraints gbc, int row) {
        gbc.gridy = row; gbc.gridx = 0; gbc.insets = new Insets(4, 4, 1, 4);
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(new Color(148, 163, 184));
        p.add(l, gbc);
    }

    private void addField(JPanel p, JComponent comp, GridBagConstraints gbc, int row) {
        gbc.gridy = row; gbc.gridx = 0; gbc.insets = new Insets(1, 4, 4, 4);
        p.add(comp, gbc);
    }

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
        tf.setPreferredSize(new Dimension(0, 30));
        return tf;
    }

    private JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 34));
        return btn;
    }
}
