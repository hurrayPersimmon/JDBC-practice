use viewDB1;
select  * from emp;

create index idx_job on emp(job);
create index idx_job_sal on emp(job,sal);

show index from emp;


SELECT
    INDEX_NAME,
    SEQ_IN_INDEX,
    COLUMN_NAME,
    NON_UNIQUE,
    INDEX_TYPE
FROM INFORMATION_SCHEMA.STATISTICS
WHERE TABLE_NAME = 'emp';

optimize table emp;

show index from emp;
drop index idx_job on emp;

CREATE INDEX idx_ename ON emp(ename);
drop index idx_ename on emp;

explain
select  * from emp where ename = 'scott';