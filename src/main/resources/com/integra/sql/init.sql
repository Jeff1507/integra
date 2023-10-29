CREATE TABLE projeto(
    id int not null AUTO_INCREMENT,
    nome varchar(255) not null,
    area_atuacao varchar(255) not null,
    descricao text not null,
    PRIMARY KEY(id)
    );
CREATE TABLE solucao(
    id int not null AUTO_INCREMENT,
    titulo varchar(255) not null,
    descricao text not null,
    PRIMARY KEY(id)
);
CREATE TABLE projeto_solucao(
    id int not null AUTO_INCREMENT,
    projeto_id int not null,
    solucao_id int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(projeto_id) REFERENCES projeto(id),
    FOREIGN KEY(solucao_id) REFERENCES solucao(id));
CREATE TABLE empresa(
    id int not null AUTO_INCREMENT,
    nome varchar(255) not null,
    telefone varchar(255) not null,
    email varchar(255) not null,
    senha varchar(255) not null,
    PRIMARY KEY(id)
    );