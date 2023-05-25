-- Criação da tabela "Categorias"
CREATE TABLE Categorias (
    ID SERIAL PRIMARY KEY,
    Nome VARCHAR(50)
);

-- Criação da tabela "Menu"
CREATE TABLE Menu (
    ID SERIAL PRIMARY KEY,
    Name VARCHAR(100),
    Price NUMERIC(10, 2),
    Category_ID INT,
    FOREIGN KEY (Category_ID) REFERENCES Categorias(ID)
);

-- Inserção de categorias na tabela "Categorias"
INSERT INTO Categorias (Nome) VALUES ('Bebidas');
INSERT INTO Categorias (Nome) VALUES ('Entradas');
INSERT INTO Categorias (Nome) VALUES ('Pratos Principais');
INSERT INTO Categorias (Nome) VALUES ('Sobremesas');

-- Inserção de itens de menu na tabela "Menu"
INSERT INTO Menu (Name, Price, Category_ID) VALUES ('Coca-Cola', 3.50, 1);
INSERT INTO Menu (Name, Price, Category_ID) VALUES ('Água Mineral', 2.00, 1);
INSERT INTO Menu (Name, Price, Category_ID) VALUES ('Pão de Alho', 7.50, 2);
INSERT INTO Menu (Name, Price, Category_ID) VALUES ('Camarão à Baiana', 25.90, 3);
INSERT INTO Menu (Name, Price, Category_ID) VALUES ('Bife à Parmegiana', 29.90, 3);
INSERT INTO Menu (Name, Price, Category_ID) VALUES ('Mousse de Chocolate', 9.00, 4);
