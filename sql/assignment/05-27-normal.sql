use sqldb;
show columns from buytbl;

-- sqldb 데이터베이스에서 다음 조건을 처리하세요.
select * 
from buytbl b join usertbl u on b.userID = u.userID;

-- 앞의 결과에서 userID가 'JYP'인 데이터만 출력하세요.
select * 
from buytbl b join usertbl u on b.userID = u.userID
where b.userID = 'JYP';

-- sqldb 데이터베이스에서 다음 조건을 처리하세요.
select u.userID, name, prodName, addr, concat(mobile1, mobile2) '연락처'
from usertbl u left join buytbl b on u.userID = b.userID 
order by userID;

-- sqldb의 사용자를 모두 조회하되 전화가 없는 사람은 제외하고 출력하세요.
select * from usertbl where mobile1 is not null;

-- sqldb의 사용자를 모두 조회하되 전화가 없는 사람만 출력하세요.
select * from usertbl where mobile1 is null;