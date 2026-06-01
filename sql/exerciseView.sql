create database viewDB1;
use viewDB1;

# 결론적으로 replace를 진행하면 수정과 동일.
create or replace view sales_emp_view as
select empno, ename, job, deptno from emp
where deptno = (select deptno from dept where dname = 'SALES');

select * from sales_emp_view;

SELECT empno, ename, job, deptno
FROM EMP
WHERE deptno = (SELECT deptno FROM DEPT WHERE dname = 'SALES');

select empno, ename, sal, dname, loc
from emp e join dept d on e.deptno = d.deptno;

create or replace view joinview as
select empno, ename, sal, dname, loc
from emp e join dept d on e.deptno = d.deptno;

select empno, ename, sal, dname, grade from joinview j
    join salgrade s on j.sal between s.losal and s.hisal;

create or replace view joinview2 as
select empno, ename, sal, dname, grade from joinview j
join salgrade s on j.sal between s.losal and s.hisal;


-- EMP, DEPT, SALGRADE 테이블을 조인하여 직원의 부서명과 급여 등급을 포함하는 복합 뷰
CREATE VIEW emp_dept_salgrade_view AS
SELECT E.empno, E.ename, E.job, D.dname AS department, E.sal, S.grade AS salary_grade
FROM EMP E
JOIN DEPT D ON E.deptno = D.deptno
JOIN SALGRADE S ON E.sal BETWEEN S.losal AND S.hisal;

CREATE USER 'read_user'@'localhost' IDENTIFIED BY '1234';
GRANT SELECT ON emp_dept_salgrade_view TO 'read_user'@'localhost';
revoke SELECT ON emp_dept_salgrade_view FROM 'read_user'@'localhost';

show full tables;
show full tables in viewDB1 where table_type = 'VIEW';
drop view joinview;

CREATE or replace VIEW emp_high_sal_view AS
    SELECT empno, ename, job, sal, deptno FROM emp
WHERE sal > 1500 WITH CHECK OPTION;

SELECT * FROM emp_high_sal_view;

# [HY000][1369] CHECK OPTION failed 'viewdb1.emp_high_sal_view'
# UPDATE emp_high_sal_view SET sal = 700 WHERE empno = 7499;
UPDATE emp_high_sal_view SET sal = 3450 WHERE empno = 7499;
select sal from emp where empno = 7499;

CREATE OR REPLACE VIEW emp_dept_salgrade_view AS
SELECT e.empno, e.ename, e.job, e.sal, d.dname AS department, s.grade AS salary_grade
FROM emp e JOIN dept d ON e.deptno = d.deptno
JOIN salgrade s ON e.sal BETWEEN s.losal AND s.hisal
WHERE e.sal > 1500
WITH CHECK OPTION;