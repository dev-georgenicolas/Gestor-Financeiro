package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.TextField;
import java.awt.Label;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import model.Usuario;
import DAO.UsuarioDAO;

public class JLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Button logarButton;
	private JPasswordField senhaTextField;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				JLogin frame = new JLogin();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public JLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(54, 52, 95));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Label userLabel = new Label("E-mail");
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

		TextField userTextField = new TextField();
		userTextField.setBounds(100, 130, 300, 25);
		contentPane.add(userTextField);

		senhaTextField = new JPasswordField();
		senhaTextField.setBounds(100, 180, 300, 25);
		contentPane.add(senhaTextField);

		logarButton = new Button("Logar");
		logarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = userTextField.getText();
				String senha = new String(senhaTextField.getPassword());

				if (email.isEmpty() || senha.isEmpty()) {
					JOptionPane.showMessageDialog(logarButton, "Preencha todos os campos.", "Erro", JOptionPane.WARNING_MESSAGE);
					return;
				}

				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario usuario = usuarioDAO.autenticarUsuario(email, senha);

				if (usuario != null) {
					JOptionPane.showMessageDialog(logarButton, "Bem-vindo, " + usuario.getNome() + "!");
					System.out.println("Id do Usuário: "+usuario.getId());

					JPrincipal jPrincipal;
					try {
						jPrincipal = new JPrincipal(usuario.getId());
						jPrincipal.setVisible(true);
						jPrincipal.setLocationRelativeTo(null);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					

					dispose();
				} else {
					JOptionPane.showMessageDialog(logarButton, "Credenciais inválidas.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		logarButton.setFont(new Font("Arial", Font.BOLD, 16));
		logarButton.setBounds(113, 240, 87, 29);
		contentPane.add(logarButton);

		Button cadastroButton = new Button("Cadastrar-se");
		cadastroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JCadastro jCadastro = new JCadastro();
				jCadastro.setVisible(true);
				jCadastro.setLocationRelativeTo(null);
			}
		});
		cadastroButton.setFont(new Font("Arial", Font.BOLD, 16));
		cadastroButton.setBounds(238, 240, 124, 29);
		contentPane.add(cadastroButton);

		JLabel loginTitulo = new JLabel("Login");
		loginTitulo.setVerticalAlignment(SwingConstants.TOP);
		loginTitulo.setFont(new Font("Bahnschrift", Font.BOLD, 42));
		loginTitulo.setForeground(new Color(255, 255, 255));
		loginTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		loginTitulo.setBounds(150, 50, 180, 45);
		contentPane.add(loginTitulo);
	}

}
