package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import DAO.CategoriaDAO;
import DAO.HistoricoTransacoesDAO;

public class HistoricoTransacoes extends AbstractTableModel {

	private static final String[] colunas = { "ID", "Valor", "Data", "Categoria", "Descrição", "Tipo" };
	private ArrayList<Transacao> transacoes;

	public HistoricoTransacoes(int idUsuario) {
		HistoricoTransacoesDAO dao = new HistoricoTransacoesDAO();
		this.transacoes = dao.buscarHistorico(idUsuario);
	}

	@Override
	public int getRowCount() {
		return transacoes.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Transacao transacao = transacoes.get(rowIndex);
		CategoriaDAO categoriaDAO = new CategoriaDAO();

		if (columnIndex == 0) {
			return transacao.getId();
		} else if (columnIndex == 1) {
			return transacao.getValor();
		} else if (columnIndex == 2) {
			return transacao.getData();
		} else if (columnIndex == 3) {
			try {
				if (transacao.getIdCategoria() != 0) {
					return categoriaDAO.getCategoriaById(transacao.getIdCategoria()).getNome();
				} else {
					return "Outros";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "Erro ao carregar categoria";
			}

		} else if (columnIndex == 4) {
			return transacao.getDescricao();
		} else if (columnIndex == 5) {
			return transacao.getTipo();
		} else {
			return null;
		}

	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

}
