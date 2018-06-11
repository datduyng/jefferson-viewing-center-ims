
start transaction;

-- drop tables
drop table if exists PortfolioAsset;
drop table if exists Portfolio;
drop table if exists EmailAddress;
drop table if exists Person; 
drop table if exists Asset;

-- create tables
create table Asset(
	assetId int not null primary key auto_increment, 
    assetCode varchar(100) not null, 
	assetType varchar(10) not null,
    label varchar(100) not null, 
    apr double,
    baseRateOfReturn double,
    quatDiv double, 
    omegaMeasure double,
    totValue double,
    betaMeasure double, 
    sharePrice double,
    stockSymbol varchar(100)
) engine=InnoDB,collate=latin1_general_cs;

create table Person(
	personId int not null primary key auto_increment, 
    personCode varchar(100) not null,
    lastName varchar(100) not null,
    firstName varchar(100) not null,
    perAssAnnFee double,
    commRate double,
    secIdCode varchar(100), 
    personType varchar(10),
    street varchar(100) not null,
    city varchar(100) not null,
    state varchar(100) not null,
    zipCode varchar(100) not null,
    country varchar(100) not null
) engine=InnoDB,collate=latin1_general_cs;

create table EmailAddress(
	emailAddressId int not null primary key auto_increment,
    personId int,
    address varchar(100) not null,
    foreign key (personId) references Person(personId) on delete cascade,
    constraint uniquePair unique index(personId, address)
) engine=InnoDB,collate=latin1_general_cs;

create table Portfolio(
	portfolioId int not null primary key auto_increment,
    portfolioCode varchar(100) not null,
    brokerId int,
    benId int,
    ownId int, 
    foreign key (brokerId) references Person(personId) on delete cascade,
    foreign key (benId) references Person(personId) on delete cascade,
    foreign key (ownId) references Person(personId) on delete cascade
) engine=InnoDB,collate=latin1_general_cs;

create table PortfolioAsset(
	portfolioAssetId int not null primary key auto_increment,
    portfolioId int,
    assetId int, 
    portAssetValue double default 0,
    numShares double,
	perStake double,
    foreign key (portfolioId) references Portfolio(portfolioId) on delete cascade,
    foreign key (assetId) references Asset(assetId) on delete cascade
) engine=InnoDB,collate=latin1_general_cs;

-- add Assets (9 - Dep,Stocks,PrivInv)
insert into Asset (assetCode, assetType, label, apr) values ('87-4679555', 'D', 'Roth IRA', 9.12); 
insert into Asset (assetCode, assetType, label, apr) values ('53-8110569', 'D', 'Money Market Savings', 4.72);
insert into Asset (assetCode, assetType, label, apr) values ('45-0474625', 'D', 'Savings Account', 7.55);

insert into Asset (assetCode, assetType, label, quatDiv, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice) values ('23-5692788', 'S', 'Devpulse', 2690.35, 1.59, 2.05, 'VA', 151.52);
insert into Asset (assetCode, assetType, label, quatDiv, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice) values ('18-1654632', 'S', 'Dynazzy', 4037.11, 9.47, 1.18, 'OH', 784.13);
insert into Asset (assetCode, assetType, label, quatDiv, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice) values ('47-5136938', 'S', 'Skynoodle', 3548.2, 8.49, 7.81, 'TX', 964.56);

insert into Asset (assetCode, assetType, label, quatDiv, baseRateOfReturn, omegaMeasure, totValue) values ('93-6497980', 'P', 'Browselab', 535.94, 0.62, 9.65, 39654.59);
insert into Asset (assetCode, assetType, label, quatDiv, baseRateOfReturn, omegaMeasure, totValue) values ('70-9734310', 'P', 'Thoughtbeat', 4836.89, 7.51, 6.4, 10740.57);
insert into Asset (assetCode, assetType, label, quatDiv, baseRateOfReturn, omegaMeasure, totValue) values ('53-1067620', 'P', 'Voonte', 4510.77, 6.97, 5.12, 33322.47);

-- add people (5)
insert into Person (personCode, personType, secIdCode, lastName, firstName, street, city, state, zipCode, country) values ('51079-866', 'E', 'sec003', 'Turner', 'Judith', '2 Morning Circle', 'Brooklyn', 'New York', '77563', 'United States'); 
insert into Person (personCode, lastName, firstName, street, city, state, zipCode, country) values ('51885-7125','Hart', 'Edward', '4 Buhler Plaza', 'Huntington', 'West Virginia', '57724', 'United States'); 
insert into Person (personCode, lastName, firstName, street, city, state, zipCode, country) values ('24559-080', 'Jenkins', 'Donald', '1372 Butterfield Terrace', 'Utica', 'New York', '76575', 'United States'); 
insert into Person (personCode, lastName, firstName, street, city, state, zipCode, country) values ('0703-5075', 'Warren', 'Lillian', '53 Dottie Way', 'Saint Louis', 'Missouri', '61244', 'United States'); 
insert into Person (personCode, personType, secIdCode, lastName, firstName, street, city, state, zipCode, country) values ('0268-0925', 'J', 'sec009', 'Robertson', 'Katherine', '0703 Warner Drive', 'Detroit', 'Michigan', '51313', 'United States'); 

-- add email addresses
insert into EmailAddress (personId, address) values (1, 'jturner0@loc.gov');
insert into EmailAddress (personId, address) values (2, 'ehart1@shinystat.com');
insert into EmailAddress (personId, address) values (3, 'djenkins2@4shared.com');
insert into EmailAddress (personId, address) values (5, 'krobertson4@163.com');

-- add portfolios
insert into Portfolio (portfolioCode, ownId, brokerId) values ('PT155', 2, 1); 
insert into Portfolio (portfolioCode, ownId, brokerId, benId) values ('PF001', 1, 5, 4); 
insert into Portfolio (portfolioCode, ownId, brokerId, benId) values ('PD456', 3, 5, 1); 

-- add assets to portfolios
insert into PortfolioAsset (portfolioId, assetId, portAssetValue) values ( 1, 1, 10500);
insert into PortfolioAsset (portfolioId, assetId, numShares, portAssetValue) values ( 1, 4, 100, 15152.00);
insert into PortfolioAsset (portfolioId, assetId, perStake, portAssetValue) values ( 1, 7, 1.0, 39654.49);
insert into PortfolioAsset (portfolioId, assetId, portAssetValue) values ( 2, 2, 1200);
insert into PortfolioAsset (portfolioId, assetId, numShares, portAssetValue) values ( 2, 5, 25.5, 19995.32);
insert into PortfolioAsset (portfolioId, assetId, perStake, portAssetValue) values ( 2, 8, 0.95, 10203.54);
insert into PortfolioAsset (portfolioId, assetId, portAssetValue) values ( 3, 3, 95000.00);
insert into PortfolioAsset (portfolioId, assetId, numShares, portAssetValue) values ( 3, 5, 43, 33717.59);
insert into PortfolioAsset (portfolioId, assetId, perStake, portAssetValue) values ( 3, 9, 0.10, 3332.25);

commit;