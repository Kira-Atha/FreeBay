CREATE DATABASE local;

CREATE TABLE local.UserInfo(
	IdUser INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	Latitude TEXT,
	Longitude TEXT,
	Password TEXT,
	Pseudo TEXT
);
CREATE TABLE local.Item(
	IdItem INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	Name TEXT,
	Price NUMERIC,
	Description TEXT,
	IdUser INTEGER,
	FOREIGN KEY(IdUser) REFERENCES UserInfo(IdUser),
	Type TEXT
);

CREATE TABLE local.Photo_Item(
	IdPhoto INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	IdItem INTEGER,
	Photo BLOB,
	FOREIGN KEY(IdItem) REFERENCES Item(IdItem)
);

CREATE TABLE local.List_Tracked_Items(
	IdList INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	IdUser Integer,
	FOREIGN KEY(IdUser) REFERENCES UserInfo(IdUser)
);

CREATE TABLE local.Choice_List(
	IdChoiceList INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	IdItem INTEGER,
	IdList INTEGER,
	FOREIGN KEY(IdItem) REFERENCES Item(IdItem),
	FOREIGN KEY(IdList) REFERENCES List_Tracked_Items(IdList)
);