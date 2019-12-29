USE GradesAdministrator
GO

CREATE VIEW vw_students
AS
	SELECT * FROM Student
Go

CREATE PROCEDURE usp_execute_insertions
AS
	INSERT INTO Student(nume,prenume,profesor,grupa) 
	VALUES 
		('Csiki','Delia','Teo','222'),
		('Prator','Mihaela','Teo','322'),
		('Pintea','Oana','Ioana','323'),
		('Crisan','Bianca','Teo','222'),
		('Cotocea','Eliza','Teo','222'),
		('Michelle','Leona','Ioana','922'),
		('Swann','Patrick','Ioana','922'),
		('Grecu','Ioana','Ioana','323');
GO

CREATE PROCEDURE usp_delete_insertions
AS
	DELETE FROM Student
GO

CREATE PROCEDURE usp_drop_tables
AS
	DROP TABLE Student
GO


CREATE PROCEDURE usp_initialize_tables
AS 
	DROP TABLE IF EXISTS Student;

	CREATE TABLE Student 
	(
		id int NOT NULL IDENTITY(1,1),
		nume nvarchar(100),
		prenume nvarchar(255),
		profesor nvarchar(255),
		grupa nvarchar(100)
	)

	EXEC usp_execute_insertions
GO

EXEC usp_drop_tables
EXEC usp_initialize_tables
SELECT * FROM vw_students