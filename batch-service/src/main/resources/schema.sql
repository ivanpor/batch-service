create table configuracion
(
   respuesta smallint not null,
   fecha timestamp not null
);

create table articulo_sigrid
(
   codigo varchar(50) not null,
   descripcion varchar(255) not null,
   accion smallint not null,
   primary key(codigo)
);

create table articulo
(
   codigo varchar(50) not null,
   descripcion varchar(255) not null,
);