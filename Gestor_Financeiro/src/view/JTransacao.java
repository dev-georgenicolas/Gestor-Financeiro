package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.CategoriaDAO;
import DAO.TransacaoDAO;
import model.Categoria;
import model.Transacao;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class JTransacao extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField tf_valor;
	private JTextField tf_descricao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JTransacao frame = new JTransacao(1);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JTransacao(int idUsuario) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 401, 301);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(54, 52, 95));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_geral = new JPanel();
		panel_geral.setBounds(10, 11, 365, 240);
		contentPane.add(panel_geral);
		panel_geral.setLayout(null);

		JLabel lblTitulo = new JLabel("Nova Transação");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(85, 11, 120, 15);
		panel_geral.add(lblTitulo);

		JPanel categoriaPanel = new JPanel();
		categoriaPanel.setBounds(119, 36, 178, 73);
		panel_geral.add(categoriaPanel);
		categoriaPanel.setVisible(false);
		categoriaPanel.setLayout(null);

		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(68, 7, 55, 14);
		lblCategoria.setFont(new Font("Tahoma", Font.BOLD, 11));
		categoriaPanel.add(lblCategoria);

		JComboBox comboBox_categorias = new JComboBox();
		comboBox_categorias.setBounds(34, 32, 119, 20);
		categoriaPanel.add(comboBox_categorias);

		CategoriaDAO categoriaDAO = new CategoriaDAO();
		List<Categoria> categorias = categoriaDAO.listarCategorias();
		DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();
		for (Categoria categoria : categorias) {
			model.addElement(categoria);
		}
		comboBox_categorias.setModel(model);

		JRadioButton rdbtnReceita = new JRadioButton("Receita");
		rdbtnReceita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoriaPanel.setVisible(false);

			}
		});

		buttonGroup.add(rdbtnReceita);
		rdbtnReceita.setBounds(21, 61, 109, 15);
		panel_geral.add(rdbtnReceita);

		JRadioButton rdbtnDespesa = new JRadioButton("Despesa");
		rdbtnDespesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoriaPanel.setVisible(true);
			}
		});

		buttonGroup.add(rdbtnDespesa);
		rdbtnDespesa.setBounds(21, 79, 109, 15);
		panel_geral.add(rdbtnDespesa);

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTipo.setBounds(37, 38, 30, 14);
		panel_geral.add(lblTipo);

		tf_valor = new JTextField();
		tf_valor.setBounds(101, 124, 86, 20);
		panel_geral.add(tf_valor);
		tf_valor.setColumns(10);

		JLabel lblValor = new JLabel("Valor:   R$");
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblValor.setBounds(31, 127, 60, 14);
		panel_geral.add(lblValor);

		JButton btnConfirmar = new JButton("Confirmar");

		btnConfirmar.addActionListener(e -> {
			try {
				double valor = Double.parseDouble(tf_valor.getText());
				String descricao = tf_descricao.getText();
				Transacao transacao = new Transacao();

				if (rdbtnDespesa.isSelected()) {
					Categoria categoriaSelecionada = (Categoria) comboBox_categorias.getSelectedItem();
					if (categoriaSelecionada != null && valor > categoriaSelecionada.getValor_limite()) {
						JOptionPane.showMessageDialog(this, "Erro: Valor excede o limite da categoria!", "Erro",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					transacao.setTipo("Despesa");
					transacao.setIdCategoria(categoriaSelecionada.getId());
				} else if (rdbtnReceita.isSelected()) {
					transacao.setTipo("Receita");
					transacao.setIdCategoria(0);
				} else {
					JOptionPane.showMessageDialog(this, "Erro: Selecione o tipo de transação!", "Erro",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				transacao.setValor(valor);
				transacao.setDescricao(descricao);
				transacao.setId_usuario(idUsuario);

				TransacaoDAO transacaoDAO = new TransacaoDAO();
				transacaoDAO.createTransacao(transacao);

				JOptionPane.showMessageDialog(this, "Transação registrada com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
				dispose();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Erro: Insira um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnConfirmar.setBounds(80, 206, 102, 23);
		panel_geral.add(btnConfirmar);

		JButton btnCancelar = new JButton("Cancelar");

		btnCancelar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JTransacao.this.dispose();
			}
		});

		btnCancelar.setBounds(208, 206, 89, 23);
		panel_geral.add(btnCancelar);

		JLabel lblNewLabel = new JLabel("Descrição:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(29, 158, 58, 14);
		panel_geral.add(lblNewLabel);

		tf_descricao = new JTextField();
		tf_descricao.setBounds(101, 155, 222, 20);
		panel_geral.add(tf_descricao);
		tf_descricao.setColumns(10);

	}

}
