CREATE DATABASE FreeBay

USE [FreeBay]
GO

USE [FreeBay]
GO

/****** Object:  Table [dbo].[Item]    Script Date: 16/08/2022 13:20:33 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Item](
	[IdItem] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Type] [nvarchar](50) NOT NULL,
	[Price] [money] NOT NULL,
	[Description] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_Item] PRIMARY KEY CLUSTERED 
(
	[IdItem] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

USE [FreeBay]
GO

/****** Object:  Table [dbo].[User]    Script Date: 16/08/2022 13:20:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[User](
	[IdUser] [int] IDENTITY(1,1) NOT NULL,
	[Pseudo] [varchar](50) NOT NULL,
	[Password] [varchar](50) NOT NULL,
	[Longitude] [varchar](50) NOT NULL,
	[Latitude] [varchar](50) NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[IdUser] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [FreeBay]
GO

/****** Object:  Table [dbo].[Photo_Item]    Script Date: 16/08/2022 13:21:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Photo_Item](
	[IdPhoto] [int] IDENTITY(1,1) NOT NULL,
	[IdItem] [int] NOT NULL,
 CONSTRAINT [PK_Photo_Item] PRIMARY KEY CLUSTERED 
(
	[IdPhoto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Photo_Item]  WITH CHECK ADD  CONSTRAINT [FK_Photo_Item_Item] FOREIGN KEY([IdItem])
REFERENCES [dbo].[Item] ([IdItem])
GO

ALTER TABLE [dbo].[Photo_Item] CHECK CONSTRAINT [FK_Photo_Item_Item]
GO

USE [FreeBay]
GO

/****** Object:  Table [dbo].[List_Tracked_Items]    Script Date: 16/08/2022 13:21:15 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[List_Tracked_Items](
	[IdList] [int] IDENTITY(1,1) NOT NULL,
	[IdUser] [int] NOT NULL,
 CONSTRAINT [PK_List_Tracked_Items] PRIMARY KEY CLUSTERED 
(
	[IdList] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[List_Tracked_Items]  WITH CHECK ADD  CONSTRAINT [FK_List_Tracked_Items_User] FOREIGN KEY([IdUser])
REFERENCES [dbo].[User] ([IdUser])
GO

ALTER TABLE [dbo].[List_Tracked_Items] CHECK CONSTRAINT [FK_List_Tracked_Items_User]
GO


USE [FreeBay]
GO

/****** Object:  Table [dbo].[Choice_List]    Script Date: 16/08/2022 13:21:25 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Choice_List](
	[IdChoiceListe] [int] IDENTITY(1,1) NOT NULL,
	[IdList] [int] NOT NULL,
	[IdItem] [int] NOT NULL,
 CONSTRAINT [PK_Choice_List] PRIMARY KEY CLUSTERED 
(
	[IdChoiceListe] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Choice_List]  WITH CHECK ADD  CONSTRAINT [FK_Choice_List_Item] FOREIGN KEY([IdItem])
REFERENCES [dbo].[Item] ([IdItem])
GO

ALTER TABLE [dbo].[Choice_List] CHECK CONSTRAINT [FK_Choice_List_Item]
GO

ALTER TABLE [dbo].[Choice_List]  WITH CHECK ADD  CONSTRAINT [FK_Choice_List_List_Tracked_Items] FOREIGN KEY([IdList])
REFERENCES [dbo].[List_Tracked_Items] ([IdList])
GO

ALTER TABLE [dbo].[Choice_List] CHECK CONSTRAINT [FK_Choice_List_List_Tracked_Items]
GO
