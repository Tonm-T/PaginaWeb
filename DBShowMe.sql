
 CREATE TABLE Categorias(
Id INT NOT NULL IDENTITY(1,1),
Nombre VARCHAR (50) NOT NULL,
Clasificacion VARCHAR (50) NOT NULL,
PRIMARY KEY (Id)
);
GO
CREATE TABLE Comentarios(
Id INT NOT NULL IDENTITY(1,1),
Contenido VARCHAR (200) NOT NULL,
PRIMARY KEY (Id)
);
GO
CREATE TABLE Blogs(
Id INT NOT NULL IDENTITY(1,1),
Autor  varchar(100) not null,
IdCategorias INT NOT NULL,
IdComentarios INT NOT NULL,
Nombre VARCHAR (100) NOT NULL,
Descripcion VARCHAR (200) NOT NULL,
Contenido VARCHAR (4000) NOT NULL,
FechaCreacion VARCHAR (20) NOT NULL,
ImagenDescripcion VARCHAR (500) NOT NULL,
ImagenContenido VARCHAR (500) NOT NULL,
FOREIGN KEY (IdCategorias) REFERENCES Categorias (Id),
FOREIGN KEY (IdComentarios) REFERENCES Comentarios (Id),
PRIMARY KEY (Id)
);
GO
CREATE TABLE Historietas(
Id INT NOT NULL IDENTITY(1,1),
Autor varchar (100) not null,
IdCategorias INT NOT NULL,
Nombre VARCHAR (100) NOT NULL,
Descripcion VARCHAR (200) NOT NULL,
Precio VARCHAR (100) NOT NULL,
PrecioAnterior VARCHAR (100),
Link VARCHAR (200) NOT NULL,
Editorial VARCHAR (50) NOT NULL,
Edicion INT NOT NULL,
FechaPublicacion VARCHAR (10) NOT NULL,
CantidadPag INT NOT NULL,
Imagen VARCHAR (100) NOT NULL,
FOREIGN KEY (IdCategorias) REFERENCES Categorias (Id),
PRIMARY KEY (Id)
);
GO
create table Roles(
Id int not null identity(1,1),
Nombre varchar(30) not null,
primary key(Id)
);
GO
create table Usuarios(
Id int not null identity (1,1),
RolId int not null,
Nombre varchar(30) not null,
Apellido varchar(30) not null,
[Login] varchar(25) not null,
[Password] char(50) not null,
Estatus tinyint not null,
FechaRegistro datetime not null,
Primary Key(Id),
foreign key(RolId )references Roles(Id),
);
