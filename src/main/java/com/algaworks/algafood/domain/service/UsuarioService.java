package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.SenhaAtualIncorretaException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.modelVO.SenhaVO;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Usuario> findAll(){
		return usuarioRepository.findAll();
	}

	public Usuario findById(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	@Transactional
	public Usuario save(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário com o e-mail %s", usuario.getEmail()));
		}
		usuario.setSenha(encryptSenha(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public Usuario updateSenha(Long id, SenhaVO senhaVO) {
		Usuario usuario = findById(id);
		if(!passwordEncoder.matches(senhaVO.getSenhaAtual(), usuario.getSenha())) {
			throw new SenhaAtualIncorretaException("Senha atual incorreta");
		}else {
			usuario.setId(id);
			usuario.setSenha(senhaVO.getNovaSenha());
			return save(usuario);
		}		
	}
	
	@Transactional
	public void deleteById(Long id) {		
			findById(id);
			usuarioRepository.deleteById(id);		
	}

	public String encryptSenha(String senha) {
		return passwordEncoder.encode(senha);
	}

}
