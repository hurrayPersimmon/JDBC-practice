drop database if exists tabledb;
create database tabledb;
use tabledb;

create table usertbl (
    userID char(8) NOT NULL primary key,
    name varchar(10) not null,
    birthyear int not null ,
    addr char(2) not null ,
    mobile1 char(3),
    mobile2 char(8),
    height tinyint unsigned,
    mDate date
);

create table buytbl(
    num int not null auto_increment primary key ,
    userID char(8) not null,
    prodName char(6) not null,
    groupName char(4),
    price int not null ,
    amount smallint not null,
    foreign key (userID) references usertbl(userID)
);

insert into usertbl values
    ('LSG', '이승기', 1987, '서울', '011', '11111111', 182, '2008-8-8'),
    ('KBS', '김범수', 1979, '경남', '011', '22222222', 173, '2012-4-4'),
    ('KKH', '김경호', 1971, '전남', '019', '33333333', 177, '2007-7-7');

# usertbl에 없는 key가 있어서 error 발생.
INSERT INTO buytbl (userID, prodName, groupName, price, amount)
VALUES
    ('KBS', '운동화', NULL, 30, 2),
    ('KBS', '노트북', '전자', 1000, 1),
    ('KBS', '모니터', '전자', 200, 1);

drop table buytbl;
drop table usertbl;

create table usertbl (
    userID char(8) not null primary key,
    name varchar(10) not null ,
    birthyear int
);

create table prodTbl (
    prodCode char(3) not null,
    prodID char(4) not null ,
    prodDate date not null ,
    proCur char(10),
    primary key (prodCode, prodID)

);

create or replace view user_buy as
    select u.userID, name, prodName, addr, concat(mobile1, mobile2) '연락처' from usertbl u
    join buytbl b on u.userID = b.userID;

select * from user_buy;
select * from user_buy where userID = 'KBS';
