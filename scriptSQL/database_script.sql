-- Crear una base de datos
CREATE DATABASE qatest;

-- Usar la base de datos creada
USE qatest;

-- Crear tabla Aplicativo
CREATE TABLE Aplicativo (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    Nombre VARCHAR(255)
);

-- Crear tabla Version
CREATE TABLE Version (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    AplicativoID INT,
    NumeroVersion VARCHAR(20),
    FOREIGN KEY (AplicativoID) REFERENCES Aplicativo(ID)
);

-- Crear tabla ResultadosPrueba
CREATE TABLE ResultadosPrueba (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    VersionID INT,
    CicloPrueba VARCHAR(50),
    CasosPruebaEjecutados INT,
    PorcentajeCasosExitosos DECIMAL(5, 2),
    TiempoPromedioEjecucion DECIMAL(10, 2),
    ErroresEncontrados INT,
    TasaFallos DECIMAL(5, 2),
    FOREIGN KEY (VersionID) REFERENCES Version(ID)
);