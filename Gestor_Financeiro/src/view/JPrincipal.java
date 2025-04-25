package view;

import java.awt.EventQueue;

import model.HistoricoTransacoes;
import model.Usuario;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.TransacaoDAO;
import DAO.UsuarioDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class JPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	
	private JLabel lbl_valorSaldo;
	private JLabel lbl_valorReceitas;
	private JLabel lbl_valorDespesas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JPrincipal frame = new JPrincipal(1);
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
	 * @throws SQLException 
	 */
	public JPrincipal(int idUsuario) throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(54, 52, 95));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_geral = new JPanel();
		panel_geral.setBounds(54, 96, 583, 330);
		contentPane.add(panel_geral);
		panel_geral.setLayout(null);

		JLabel lblVGTitulo = new JLabel("Visão Geral");
		lblVGTitulo.setBounds(195, 6, 100, 17);
		lblVGTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblVGTitulo.setVerticalAlignment(SwingConstants.TOP);
		lblVGTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		panel_geral.add(lblVGTitulo);

		JLabel lblSaldo = new JLabel("Saldo");
		lblSaldo.setBackground(new Color(0, 0, 0));
		lblSaldo.setBounds(39, 31, 88, 26);
		panel_geral.add(lblSaldo);
		lblSaldo.setFont(new Font("Arial", Font.BOLD, 22));
		lblSaldo.setForeground(new Color(0, 0, 0));
		lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaldo.setVerticalAlignment(SwingConstants.TOP);

		JLabel lblRSSaldo = new JLabel("R$");
		lblRSSaldo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblRSSaldo.setBounds(26, 68, 32, 26);
		panel_geral.add(lblRSSaldo);

		JLabel lblHistorico = new JLabel("Histórico de Transações");
		lblHistorico.setHorizontalAlignment(SwingConstants.CENTER);
		lblHistorico.setBounds(122, 120, 142, 14);
		panel_geral.add(lblHistorico);

		JButton bt_Add = new JButton("Adicionar");
		bt_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTransacao jtrans = new JTransacao(idUsuario);
				jtrans.setVisible(true);
				jtrans.setLocationRelativeTo(null);
			}
		});
		bt_Add.setBounds(456, 163, 100, 23);
		panel_geral.add(bt_Add);

		JButton bt_Delete = new JButton("Remover");

		bt_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = table.getSelectedRow();

				if (linhaSelecionada == -1) {
					JOptionPane.showMessageDialog(null, "Por favor, selecione uma transação para remover.", "Erro",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				int confirmacao = JOptionPane.showConfirmDialog(null,
						"Tem certeza de que deseja remover esta transação?", "Confirmação", JOptionPane.YES_NO_OPTION);

				if (confirmacao == JOptionPane.YES_OPTION) {

					int idTransacao = (int) table.getValueAt(linhaSelecionada, 0);

					try {
						TransacaoDAO dao = new TransacaoDAO();
						dao.deleteTransacao(idTransacao, idUsuario);
						JOptionPane.showMessageDialog(null, "Transação removida com sucesso.");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Erro ao remover a transação: " + ex.getMessage(), "Erro",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		bt_Delete.setBounds(456, 197, 100, 23);
		panel_geral.add(bt_Delete);

		JButton bt_Update = new JButton("Atualizar");
		bt_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					atualizarTabela(idUsuario);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		bt_Update.setBounds(456, 231, 100, 23);
		panel_geral.add(bt_Update);

		JLabel lblReceitas = new JLabel("Receitas");
		lblReceitas.setFont(new Font("Arial", Font.BOLD, 14));
		lblReceitas.setBounds(287, 55, 61, 14);
		panel_geral.add(lblReceitas);

		JLabel lblDespesas = new JLabel("Despesas");
		lblDespesas.setFont(new Font("Arial", Font.BOLD, 14));
		lblDespesas.setBounds(423, 55, 69, 14);
		panel_geral.add(lblDespesas);

		JLabel lblRSReceitas = new JLabel("+ R$");
		lblRSReceitas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRSReceitas.setBounds(259, 72, 32, 26);
		panel_geral.add(lblRSReceitas);

		JLabel lblRSDespesas = new JLabel("- R$");
		lblRSDespesas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRSDespesas.setBounds(399, 72, 32, 26);
		panel_geral.add(lblRSDespesas);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 134, 418, 171);
		panel_geral.add(scrollPane);

		table = new JTable();
		table.setBackground(new Color(255, 255, 255));
		table.setModel(new HistoricoTransacoes(idUsuario));
		scrollPane.setViewportView(table);

		TransacaoDAO transDAO = new TransacaoDAO();
		UsuarioDAO userDAO = new UsuarioDAO();
		

		double valor_despesas = transDAO.getDespesas(idUsuario);
		double valor_receitas = transDAO.getReceitas(idUsuario);
		double saldo_usuario = transDAO.getSaldo(idUsuario);
		
		userDAO.atualizarSaldo(idUsuario, saldo_usuario);

		lbl_valorSaldo = new JLabel(String.valueOf(saldo_usuario)); 
		lbl_valorSaldo.setFont(new Font("Arial", Font.PLAIN, 24));
		lbl_valorSaldo.setBounds(59, 69, 150, 29);
		panel_geral.add(lbl_valorSaldo);

		lbl_valorReceitas = new JLabel(String.valueOf(valor_receitas));
		lbl_valorReceitas.setFont(new Font("Arial", Font.PLAIN, 14));
		lbl_valorReceitas.setBounds(294, 73, 95, 26);
		panel_geral.add(lbl_valorReceitas);

		lbl_valorDespesas = new JLabel(String.valueOf(valor_despesas));
		lbl_valorDespesas.setFont(new Font("Arial", Font.PLAIN, 14));
		lbl_valorDespesas.setBounds(430, 73, 95, 26);
		panel_geral.add(lbl_valorDespesas);

		JLabel lblTitulo = new JLabel("GRANA FLOW");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Dialog", Font.BOLD, 26));
		lblTitulo.setForeground(new Color(255, 255, 255));
		lblTitulo.setBounds(250, 40, 200, 45);
		contentPane.add(lblTitulo);
	}

	public void atualizarTabela(int idUsuario) throws SQLException {
		HistoricoTransacoes modelo = new HistoricoTransacoes(idUsuario);
		table.setModel(modelo);
		
		TransacaoDAO transDAO = new TransacaoDAO();
		UsuarioDAO userDAO = new UsuarioDAO();
		
		
		double valor_despesas = transDAO.getDespesas(idUsuario);
		double valor_receitas = transDAO.getReceitas(idUsuario);
		double saldo_usuario = transDAO.getSaldo(idUsuario);

		lbl_valorSaldo.setText(String.valueOf(saldo_usuario));
		lbl_valorReceitas.setText(String.valueOf(valor_receitas));
		lbl_valorDespesas.setText(String.valueOf(valor_despesas));

		System.out.println("Tabela Atualizada Com Sucesso!");
	}
}
