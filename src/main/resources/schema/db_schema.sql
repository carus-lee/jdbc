drop table member if exists cascade;
create table member (
                        member_id varchar(10),
                        money integer not null default 0,
                        primary key (member_id)
);

---- 트랜잭션
set autocommit true;
delete from member;
insert into member(member_id, money) values ('memberA',10000);
insert into member(member_id, money) values ('memberB',10000);

set autocommit false;
update member set money=10000 - 2000 where member_id = 'memberA';
update member set money=10000 + 2000 where member_id = 'memberB';
commit;

select * from member;

---- DB Lock
set autocommit true;
delete from member;
insert into member(member_id, money) values ('memberA',10000);
-- 1. 세션1 - memberA 500원 업뎃 - lock 획득
set autocommit false;
update member set money=500 where member_id = 'memberA';
-- 2. 세션2 - memberA 1000원 업뎃
SET LOCK_TIMEOUT 60000;
set autocommit false;
update member set money=1000 where member_id = 'memberA'
-- 3. 세션1 - lock 반납
commit;
-- 4. 세션2 - 세션1이 commit하여 lock 획득 (2의 update 처리됨)
-- 5. 세션2 - lock 반납
commit;

---- DB Lock - 조회시점에 Lock 획득하기
set autocommit true;
delete from member;
insert into member(member_id, money) values ('memberA',10000)
-- 1. 세션1 - 트랜잭션 종료(commit or rollback)때 까지 lock 보유
set autocommit false;
select * from member where member_id='memberA' for update;
-- 2. 세션2 - Lock 획득까지 대기
set autocommit false;
update member set money=500 where member_id = 'memberA';
-- 3. 세션1 - lock 반납
commit;
-- 4. 세션2 - lock 반납
commit;
