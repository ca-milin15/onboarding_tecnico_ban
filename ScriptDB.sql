create database crediauto;


create table marca (
	id SERIAL,
	nombre varchar(50) not null,
	fecha timestamp not null,
	primary key (id)
	
)

create table vehiculo (
	id SERIAL,
	placa varchar(12) not null,
	modelo int not null,
	numero_chasis varchar(60) not null,
	id_marca SERIAL not null,
	tipo char(3),
	cilindraje int not null,
	avaluo float not null,
	fecha timestamp not null,
	primary key (id),
	constraint fk_id_marca foreign key (id_marca) references marca(id),
	unique(placa)
)
create index idx_modelo on vehiculo(modelo)

create table patio_autos (
	id SERIAL,
	nombre varchar(50) not null,
	direccion varchar(40) not null,
	telefono varchar(20) not null,
	numero_punto_venta varchar(20) not null,
	fecha timestamp not null,
	primary key (id)
)

create table ejecutivo (
	id SERIAL,
	identificacion varchar(20) not null,
	nombres varchar(20) not null,
	apellidos varchar(20) not null,
	direccion varchar(40) not null,
	telefono varchar(20) not null,
	celular varchar(20) not null,
	id_patio SERIAL not null,
	edad char(2) not null,
	fecha timestamp not null,
	primary key (id),
	constraint fk_id_patio foreign key (id_patio) references patio_autos(id)
)

create table cliente (
	id SERIAL,
	identificacion varchar(20) not null,
	nombres varchar(20) not null,
	edad char(2) not null,
	fecha_nacimiento timestamp not null,
	apellidos varchar(20) not null,
	direccion varchar(40) not null,
	telefono varchar(20) not null,
	estado_civil char(2) not null,
	identificacion_conyugue varchar(20) not null,
	nombre_conyugue varchar(50) not null,
	sujeto_credito char(1) not null,
	fecha timestamp not null,
	primary key (id)
)

create table asignacion_cliente (
	id SERIAL,
	id_cliente SERIAL not null,
	id_patio SERIAL not null,
	fecha timestamp not null,
	primary key (id, id_cliente, id_patio),
	constraint fk_id_cliente foreign key (id_cliente) references cliente(id),
	constraint fk_id_patio foreign key (id_patio) references patio_autos(id)
)

create table solicitud_credito (
	id SERIAL,
	fecha timestamp not null,
	id_cliente SERIAL not null,
	id_vehiculo SERIAL not null,
	meses_plazo SERIAL not null,
	cuotas SERIAL not null,
	id_ejecutivo SERIAL not null,
	observacion varchar(300),
	estado char(3) not null,
	primary key (id),
	constraint fk_id_cliente foreign key (id_cliente) references cliente(id),
	constraint fk_id_vehiculo foreign key (id_vehiculo) references vehiculo(id),
	constraint fk_id_ejecutivo foreign key (id_ejecutivo) references ejecutivo(id)
)