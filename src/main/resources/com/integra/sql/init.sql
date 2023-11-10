CREATE TABLE IF NOT EXISTS empresa(
    id int not null AUTO_INCREMENT,
    nome varchar(255) not null,
    email varchar(255) not null,
    senha varchar(255) not null,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS projeto(
    id int not null AUTO_INCREMENT,
    empresa_id int not null,
    nome varchar(255) not null,
    area_atuacao varchar(255) not null,
    descricao text not null,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY(empresa_id) REFERENCES empresa(id)
);

CREATE TABLE IF NOT EXISTS estudante(
    id int not null AUTO_INCREMENT,
    nome varchar(255) not null,
    email varchar(255) not null,
    senha varchar(255) not null,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS solucao(
    id int not null AUTO_INCREMENT,
    estudante_id int NOT NULL,
    projeto_id int NOT NULL,
    nome varchar(255) not null,
    descricao text not null,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY(estudante_id) REFERENCES estudante(id),
    FOREIGN KEY(projeto_id) REFERENCES projeto(id)
);