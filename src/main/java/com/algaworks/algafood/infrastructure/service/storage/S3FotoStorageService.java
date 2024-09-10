package com.algaworks.algafood.infrastructure.service.storage;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class S3FotoStorageService //implements FotoStorageService {
{
//	@Autowired
//	private AmazonS3 amazonS3;
//
//	@Autowired
//	private StorageProperties storageProperties;
//
//	@Override
//	public void armazenar(NovaFoto novaFoto) {
//		try {
//			var caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
//			var objectMetaData = new ObjectMetadata();
//			objectMetaData.setContentType(novaFoto.getContentType());
//			objectMetaData.setContentLength(novaFoto.getTamanho());
//
//			var putObjectRequest = new PutObjectRequest(
//					storageProperties.getS3().getBucket(),
//					caminhoArquivo,
//					novaFoto.getInputStream(),
//					objectMetaData)
//				.withCannedAcl(CannedAccessControlList.PublicRead);
//
//			amazonS3.putObject(putObjectRequest);
//		} catch (Exception e) {
//			throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e.getCause());
//		}
//	}
//
//	@Override
//	public void excluir(FotoProduto foto) {
//		try {
//			var caminhoArquivo = getCaminhoArquivo(foto.getNomeArquivo());
//			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
//					storageProperties.getS3().getBucket(),
//					caminhoArquivo
//			);
//			amazonS3.deleteObject(deleteObjectRequest);
//
//		} catch (Exception e) {
//			throw new StorageException("Não foi possivel excluir o arquivo", e.getCause());
//		}
//	}
//
//	@Override
//	public FotoRecuperada recuperar(String nomeArquivo) {
//		FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
//				.url(amazonS3.getUrl(
//						storageProperties.getS3().getBucket(),
//						getCaminhoArquivo(nomeArquivo))
//						.toString())
//				.build();
//		return fotoRecuperada;
//	}
//
//	private String getCaminhoArquivo(String nomeArquivo) {
//		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
//	}
}
