create table CONVITE_REGISTRO
(
  id           NUMBER(19) not null,
  ate          TIMESTAMP(6),
  comecando_em TIMESTAMP(6),
  convite_id   VARCHAR2(255 CHAR),
  descricao    VARCHAR2(255 CHAR),
  hospede_id   VARCHAR2(255 CHAR)
);

alter table CONVITE_REGISTRO
  add primary key (ID);

create table HOSPEDE
(
  id         NUMBER(19) not null,
  ativo      NUMBER(1),
  descricao  VARCHAR2(255 CHAR),
  hospede_id VARCHAR2(255 CHAR),
  nome       VARCHAR2(255 CHAR)
);

alter table HOSPEDE
  add primary key (ID);

create table CONVITES_REGISTRO
(
  id_hospede          NUMBER(19) not null,
  id_convite_registro NUMBER(19) not null
);

alter table CONVITES_REGISTRO
  add primary key (ID_HOSPEDE, ID_CONVITE_REGISTRO);
alter table CONVITES_REGISTRO
  add constraint AK_CONVITES_REGISTRO unique (ID_CONVITE_REGISTRO);
alter table CONVITES_REGISTRO
  add constraint FK_HOSPEDE#CONVITES_REGISTRO foreign key (ID_HOSPEDE)
  references HOSPEDE (ID);
alter table CONVITES_REGISTRO
  add constraint FK_CONVITES_REGISTRO#CONVITES foreign key (ID_CONVITE_REGISTRO)
  references CONVITE_REGISTRO (ID);

create table GRUPO
(
  id         NUMBER(19) not null,
  descricao  VARCHAR2(255 CHAR),
  hospede_id VARCHAR2(255 CHAR),
  nome       VARCHAR2(255 CHAR) not null,
  versao     NUMBER(10)
)
;
alter table GRUPO
  add primary key (ID);

create table GRUPO_MEMBROS
(
  grupo_id   NUMBER(19) not null,
  hospede_id VARCHAR2(255 CHAR),
  nome       VARCHAR2(255 CHAR) not null,
  tipo       VARCHAR2(255 CHAR)
);

alter table GRUPO_MEMBROS
  add primary key (GRUPO_ID, NOME);
alter table GRUPO_MEMBROS
  add constraint FK_2DGHP5DLXGOBI18P8F5O4AKJR foreign key (GRUPO_ID)
  references GRUPO (ID);

create table PAPEL
(
  id                  NUMBER(19) not null,
  descricao           VARCHAR2(255 CHAR),
  hospede_id          VARCHAR2(255 CHAR),
  nome                VARCHAR2(255 CHAR) not null,
  suporta_aninhamento NUMBER(1),
  versao              NUMBER(10),
  id_grupo            NUMBER(19)
);

alter table PAPEL
  add primary key (ID);
alter table PAPEL
  add constraint FK_PUJJF59DNFJYNQ4PQBFHHEFKN foreign key (ID_GRUPO)
  references GRUPO (ID);

create table PESSOA
(
  id         NUMBER(19) not null,
  hospede_id VARCHAR2(255 CHAR),
  email      VARCHAR2(255 CHAR),
  nome       VARCHAR2(255 CHAR),
  usuario_id NUMBER(19)
);

alter table PESSOA
  add primary key (ID);

create table USUARIO
(
  id          NUMBER(19) not null,
  data_fim    TIMESTAMP(6),
  data_inicio TIMESTAMP(6),
  habilitada  NUMBER(1),
  hospede_id  VARCHAR2(255 CHAR),
  senha       VARCHAR2(255 CHAR),
  username    VARCHAR2(255 CHAR),
  pessoa_id   NUMBER(19)
)
;

alter table USUARIO
  add primary key (ID);
  
alter table PESSOA
  add constraint FK_7UETHYM0TXUD36GIEJAV1TLQS foreign key (USUARIO_ID)
  references USUARIO (ID);

alter table USUARIO
  add constraint FK_6B5G5SVCJO77DX9CSGTA5Y7XK foreign key (PESSOA_ID)
  references PESSOA (ID);
