set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;
delete from pedido;
delete from item_pedido;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Brasileira');
insert into cozinha (id, nome) values (4, 'Chinesa');

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, false, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, false);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, false);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (1, 'Coca-cola', 'Bebida', 3.5, true, 1);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (2, 'Prato Principal', 'Comida', 15.5, true, 1);

insert into grupo (id, nome) values (1, "Gerente");
insert into grupo (id, nome) values (2, "Vendedor");
insert into grupo (id, nome) values (3, "Atendente");
insert into grupo (id, nome) values (4, "Auxiliar");

insert into grupo_permissao(grupo_id, permissao_id) values (1, 1);
insert into grupo_permissao(grupo_id, permissao_id) values (1, 2);
insert into grupo_permissao(grupo_id, permissao_id) values (2, 1);
insert into grupo_permissao(grupo_id, permissao_id) values (2, 2);
insert into grupo_permissao(grupo_id, permissao_id) values (3, 2);

insert into usuario (id, nome, email, senha, data_cadastro) values (1, 'Wellington Delmondes', 'delmondes@email.com', '123', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (2, 'Bruna Franco', 'bruna@email.com', '123', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (3, 'Sandramar Delmondes', 'sandramar@email.com', '123', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (4, 'Lourrana Delmondes', 'lourrana@email.com', '123', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1);
insert into usuario_grupo (usuario_id, grupo_id) values (1, 2);
insert into usuario_grupo (usuario_id, grupo_id) values (2, 2);
insert into usuario_grupo (usuario_id, grupo_id) values (3, 3);
insert into usuario_grupo (usuario_id, grupo_id) values (4, 4);


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, sub_total, taxa_frete, valor_total)
values (1, uuid(), 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CRIADO', utc_timestamp, 34.50, 10, 44.50);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 3.50, 3.50, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 15.50, 31.00, 'Menos picante, por favor');


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, sub_total, taxa_frete, valor_total)
values (2, uuid(), 1, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CRIADO', utc_timestamp, 15.50, 10, 25.50);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 2, 1, 15.50, 15.50, 'Ao ponto');
