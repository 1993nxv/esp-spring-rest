use algafoodapi;

CREATE TABLE restaurante_usuario_responsavel (
    restaurante_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY (restaurante_id, usuario_id),
    FOREIGN KEY (restaurante_id) REFERENCES restaurante(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

