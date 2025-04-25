package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Usuario;
import DAO.UsuarioDAO;

public class JCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField senhaTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				JCadastro frame = new JCadastro();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JCadastro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(54, 52, 95));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Label userLabel = new Label("Usuário");
		userLabel.setForeground(Color.WHITE);
		userLabel.setFont(new Font("Arial", Font.BOLD, 14));
		userLabel.setAlignment(Label.CENTER);
		userLabel.setBounds(40, 130, 56, 22);
		contentPane.add(userLabel);

		Label senhaLabel = new Label("Senha");
		senhaLabel.setAlignment(Label.CENTER);
		senhaLabel.setForeground(Color.WHITE);
		senhaLabel.setFont(new Font("Arial", Font.BOLD, 14));
		senhaLabel.setBounds(40, 180, 56, 22);
		contentPane.add(senhaLabel);

		Label emailLabel = new Label("Email");
		emailLabel.setForeground(Color.WHITE);
		emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
		emailLabel.setAlignment(Label.CENTER);
		emailLabel.setBounds(40, 80, 56, 22);
		contentPane.add(emailLabel);

		TextField userTextField = new TextField();
		userTextField.setBounds(100, 130, 300, 25);
		contentPane.add(userTextField);

		TextField emailTextField = new TextField();
		emailTextField.setBounds(100, 80, 300, 25);
		contentPane.add(emailTextField);

		senhaTextField = new JPasswordField();
		senhaTextField.setBounds(100, 180, 300, 25);
		contentPane.add(senhaTextField);

		Button cadastroButton = new Button("Cadastrar-se");
		cadastroButton.setFont(new Font("Arial", Font.BOLD, 16));
		cadastroButton.setBounds(175, 250, 124, 29);
		cadastroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = userTextField.getText();
				String email = emailTextField.getText();
				String senha = new String(senhaTextField.getPassword());

				if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
					JOptionPane.showMessageDialog(cadastroButton, "Preencha todos os campos.", "Erro",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario usuario = new Usuario(0, nome, 0.0, email, senha);

				if (usuarioDAO.autenticarUsuario(email, senha) != null) {
					JOptionPane.showMessageDialog(cadastroButton, "Este e-mail já está registrado.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				} else {
					usuarioDAO.createUsuario(usuario);
					JOptionPane.showMessageDialog(cadastroButton, "Cadastro realizado com sucesso!");
					dispose();
					JLogin jLogin = new JLogin();
					jLogin.setVisible(true);
					jLogin.setLocationRelativeTo(null);
				}
			}
		});
		contentPane.add(cadastroButton);

		JLabel cadastroTitulo = new JLabel("Cadastro");
		cadastroTitulo.setVerticalAlignment(SwingConstants.TOP);
		cadastroTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		cadastroTitulo.setFont(new Font("Calibri", Font.BOLD, 42));
		cadastroTitulo.setForeground(new Color(255, 255, 255));
		cadastroTitulo.setBounds(150, 20, 180, 45);
		contentPane.add(cadastroTitulo);
	}
}