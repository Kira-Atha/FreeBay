CREATE TABLE UserInfo(
	IdUser INTEGER PRIMARY KEY,
	Latitude TEXT,
	Longitude TEXT,
	Password TEXT,
	Pseudo TEXT
)

CREATE TABLE Item(
	IdItem INTEGER PRIMARY KEY,
	Name TEXT,
	Price NUMERIC,
	Description TEXT,
	Type TEXT
)

CREATE TABLE Photo_Item(
	IdPhoto INTEGER PRIMARY KEY,
	IdItem INTEGER,
	FOREIGN KEY(IdItem) REFERENCES Item(IdItem)
)

CREATE TABLE List_Tracked_Items(
	IdList INTEGER PRIMARY KEY,
	IdUser Integer,
	FOREIGN KEY(IdUser) REFERENCES UserInfo(IdUser)
)

CREATE TABLE Choice_List(
	IdChoiceList INTEGER PRIMARY KEY,
	IdItem INTEGER,
	IdList INTEGER,
	FOREIGN KEY(IdItem) REFERENCES Item(IdItem),
	FOREIGN KEY(IdList) REFERENCES List_Tracked_Items(IdList)
)