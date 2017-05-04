package br.com.agrofauna.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="usuario_empresa")
@NamedQueries({
	@NamedQuery(name="UsuarioEmpresa.findAll", query="SELECT e FROM UsuarioEmpresa e"),
	@NamedQuery(name="UsuarioEmpresa.existe", query="SELECT e FROM UsuarioEmpresa e WHERE e.usuario.idUsuario=:idUsuario AND e.empresa.idPessoa=:idPessoa"),
	@NamedQuery(name="UsuarioEmpresa.limparUsuarioEmpresa", query="DELETE FROM UsuarioEmpresa e WHERE e.usuario.idUsuario=:idUsuario")
})
public class UsuarioEmpresa implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idUsuarioEmpresa;
	private Empresa empresa;
	private Usuario usuario;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario_empresa")
	public Long getIdUsuarioEmpresa() {
		return idUsuarioEmpresa;
	}
	public void setIdUsuarioEmpresa(Long idUsuarioEmpresa) {
		this.idUsuarioEmpresa = idUsuarioEmpresa;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuarioEmpresa == null) ? 0 : idUsuarioEmpresa.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioEmpresa other = (UsuarioEmpresa) obj;
		if (idUsuarioEmpresa == null) {
			if (other.idUsuarioEmpresa != null)
				return false;
		} else if (!idUsuarioEmpresa.equals(other.idUsuarioEmpresa))
			return false;
		return true;
	}	
}
