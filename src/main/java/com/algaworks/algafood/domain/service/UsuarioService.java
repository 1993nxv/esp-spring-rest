package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Usuario> findAll(){
		return usuarioRepository.findAll();
	}

	public Usuario findById(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	@Transactional
	public Usuario save(Usuario usuario) {	
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public Usuario updatePartially(Long id, Usuario usuario) {
		findById(id);
		usuario.setId(id);
		return save(usuario);
	}
	
	@Transactional
	public void deleteById(Long id) {		
			findById(id);
			usuarioRepository.deleteById(id);		
	}

}
