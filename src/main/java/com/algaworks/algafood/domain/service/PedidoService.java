package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class PedidoService {
	
	private static final String MSG_PEDIDO_EM_USO = "Pedido com id:%d não pode ser removido, pois está em uso.";
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	
	public List<Pedido> findAll(){	
		return pedidoRepository.findAll();
	}
	
	public Pedido findById(Long id){		
			return pedidoRepository.findById(id)
					.orElseThrow(() -> new PedidoNaoEncontradoException(id));	
	}
//	
//	public Set<Permissao> findPermissoes(Long grupoId) {
//		Grupo grupo = findById(grupoId);
//		return grupo.getPermissoes();
//	}
//	
//	@Transactional
//	public Set<Permissao> adicionaPermissao(Long grupoId, Long permissaoId) {
//		Grupo grupo = findById(grupoId);
//		Permissao permissao = permissaoService.findById(permissaoId);
//		grupo.getPermissoes().add(permissao);
//		return grupo.getPermissoes();
//	}
//	
//	@Transactional
//	public void removePermissao(Long grupoId, Long permissaoId) {
//		Grupo grupo = findById(grupoId);
//		Permissao permissao = permissaoService.findById(permissaoId);
//		grupo.getPermissoes().remove(permissao);
//	}
//	
//	@Transactional
//	public Grupo save(Grupo Grupo){	
//		return grupoRepository.save(Grupo);
//	}
//	
//	@Transactional
//	public Grupo updatePartially(Long id, Grupo grupo) {
//		findById(id);
//		grupo.setId(id);
//		return save(grupo);
//	}
//	
//	@Transactional
//	public void deleteById(Long id) {
//		grupoRepository.findById(id);
//		grupoRepository.flush();
//		try {
//			grupoRepository.deleteById(id);
//		} catch (DataIntegrityViolationException e) {
//			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
//		}	
//	}

}
	

