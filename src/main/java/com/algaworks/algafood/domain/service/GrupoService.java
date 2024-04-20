package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class GrupoService {
	
	private static final String MSG_GRUPO_EM_USO = "Grupo com id:%d não pode ser removidO, pois está em uso.";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PermissaoService permissaoService;
	
	public List<Grupo> findAll(){	
		return grupoRepository.findAll();
	}
	
	public Grupo findById(Long id){		
			return grupoRepository.findById(id)
					.orElseThrow(() -> new GrupoNaoEncontradoException(id));	
	}
	
	public Set<Permissao> findPermissoes(Long grupoId) {
		Grupo grupo = findById(grupoId);
		return grupo.getPermissoes();
	}
	
	@Transactional
	public Set<Permissao> adicionaPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = findById(grupoId);
		Permissao permissao = permissaoService.findById(permissaoId);
		grupo.getPermissoes().add(permissao);
		return grupo.getPermissoes();
	}
	
	@Transactional
	public void removePermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = findById(grupoId);
		Permissao permissao = permissaoService.findById(permissaoId);
		grupo.getPermissoes().remove(permissao);
	}
	
	@Transactional
	public Grupo save(Grupo Grupo){	
		return grupoRepository.save(Grupo);
	}
	
	@Transactional
	public Grupo updatePartially(Long id, Grupo grupo) {
		findById(id);
		grupo.setId(id);
		return save(grupo);
	}
	
	@Transactional
	public void deleteById(Long id) {
		grupoRepository.findById(id);
		grupoRepository.flush();
		try {
			grupoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
		}	
	}

}
	

