use sqldb;
create table stdtbl (
    stdName varchar(10) not null primary key,
    addr char(4) not null

);

create table clubtbl(
    clubName varchar(10) not null primary key,
    roomNo char(4) not null
);

create table stdclubtbl(
    num int auto_increment not null primary key,
    stdName varchar(10) not null,
    clubName varchar(10) not null,
    foreign key (stdName) references stdtbl(stdName),
    foreign key (clubName) references  clubtbl(clubName)
 );

INSERT INTO stdtbl
VALUES
('김범수', '경남'),
('성시경', '서울'),
('조용필', '경기'),
('은지원', '경북'),
('바비킴', '서울');

INSERT INTO clubtbl
VALUES
('수영', '101호'),
('바둑', '102호'),
('축구', '103호'),
('봉사', '104호');

INSERT INTO stdclubtbl
VALUES
(NULL, '김범수', '바둑'),
(NULL, '김범수', '축구'),
(NULL, '조용필', '축구'),
(NULL, '은지원', '축구'),
(NULL, '은지원', '봉사'),
(NULL, '바비킴', '봉사');


select s.stdName '학생', addr '지역', c.clubName '가입한동아리', roomNo '동아리방' from stdtbl s
    join stdclubtbl s2 on s.stdName = s2.stdName
    join clubtbl c on c.clubName = s2.clubName;

select s.clubName, roomNo, s.stdName, addr from clubtbl
join stdclubtbl s on clubtbl.clubName = s.clubName
join stdtbl s2 on s.stdName = s2.stdName;


USE sqldb;

select * from emptbl;

select *
from empTbl;

drop table empTbl;
CREATE TABLE empTbl (
    emp CHAR(3),
    manager CHAR(3),
    empTel VARCHAR(8)
);

INSERT INTO empTbl VALUES ('나사장', NULL, '0000');
INSERT INTO empTbl VALUES ('김재무', '나사장', '2222');
INSERT INTO empTbl VALUES ('김부장', '김재무', '2222-1');
INSERT INTO empTbl VALUES ('이부장', '김재무', '2222-2');
INSERT INTO empTbl VALUES ('우대리', '이부장', '2222-2-1');
INSERT INTO empTbl VALUES ('지사원', '이부장', '2222-2-2');
INSERT INTO empTbl VALUES ('이영업', '나사장', '1111');
INSERT INTO empTbl VALUES ('한과장', '이영업', '1111-1');
INSERT INTO empTbl VALUES ('최정보', '나사장', '3333');
INSERT INTO empTbl VALUES ('윤차장', '최정보', '3333-1');
INSERT INTO empTbl VALUES ('이주임', '윤차장', '3333-1-1');

select e.emp '부하직원', e.manager '직속상관', eT.empTel '직속상관연락처' from empTbl e
join empTbl eT on e.manager = eT.emp;

use employees;

select e.emp_no, first_name, last_name, title from employees e
join titles t on e.emp_no = t.emp_no
where to_date = '9999-01-01';

select e.emp_no, birth_date, first_name, last_name, gender, hire_date, title, salary
from employees e
join titles t on e.emp_no = t.emp_no
join salaries s on e.emp_no = s.emp_no
where s.to_date = '9999-01-01';

select e.emp_no, first_name, last_name, dept_name from employees e
join dept_emp d on e.emp_no = d.emp_no
join departments dp on d.dept_no = dp.dept_no
where d.to_date = '9999-01-01'
order by emp_no;

select dp.dept_no, dept_name, count(*) from dept_emp d
join departments dp on dp.dept_no = d.dept_no
where d.to_date = '9999-01-01'
group by dp.dept_no
order by dp.dept_no;

select e.emp_no, first_name, last_name, dept_name, from_date, to_date from employees e
join dept_emp de on e.emp_no = de.emp_no
join departments dp on de.dept_no = dp.dept_no
where de.emp_no = 10209;