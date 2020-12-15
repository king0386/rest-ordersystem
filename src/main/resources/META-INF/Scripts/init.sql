--insert into SECURITY_ROLE values(1, 'ADMIN_ROLE');
--insert into SECURITY_ROLE values(2, 'USER_ROLE');
--INSERT INTO SECURITY_USER VALUES (1, 'PBKDF2WithHmacSHA256:2048:pZaejuhYU1EEhqdI1O9D9oR+gJQW1lRwAYWg4s6FULQ=:4r54JlZPpPmbyu9DYFkMwMLxvZc4ltg6G9XUvHWjqNs=', 'admin', NULL);
--INSERT INTO SECURITY_USER_SECURITY_ROLE values(1,1);
--INSERT INTO SECURITY_USER VALUES (2, 'PBKDF2WithHmacSHA256:2048:pZaejuhYU1EEhqdI1O9D9oR+gJQW1lRwAYWg4s6FULQ=:4r54JlZPpPmbyu9DYFkMwMLxvZc4ltg6G9XUvHWjqNs=', 'user', NULL);
--INSERT INTO SECURITY_USER_SECURITY_ROLE values(2,2);

insert into CUST_ADDR values(1, 'B', 'city1', 'country1', NOW(), 'P1S 1A1', 'state1', 'street1', NOW(), 0, null);
insert into CUST_ADDR values(2, 'S', 'city2', 'country2', NOW(), 'P2S 2A2', 'state2', 'street2', NOW(), 0, null);
insert into CUST_ADDR values(3, 'B', 'city3', 'country3', NOW(), 'P3S 3A3', 'state3', 'street3', NOW(), 0, null);
insert into CUST_ADDR values(4, 'S', 'city4', 'country4', NOW(), 'P4S 4A4', 'state4', 'street4', NOW(), 0, null);

insert into CUSTOMER values(1, NOW(), 'email1.com', 'John1', 'Doe1', '111.111.1111', NOW(), 0, 1, 2);
insert into CUSTOMER values(2, NOW(), 'email2.com', 'John2', 'Doe2', '222.222.2222', NOW(), 0, 3, 4);
insert into CUSTOMER values(3, NOW(), 'email3.com', 'John3', 'Doe3', '333.333.3333', NOW(), 0, 1, 2);

insert into ORDER_TBL values(1, NOW(), 'book1', NOW(), 0, 1)
insert into ORDER_TBL values(2, NOW(), 'book2', NOW(), 0, 1)
insert into ORDER_TBL values(3, NOW(), 'book3', NOW(), 0, 1)
insert into ORDER_TBL values(4, NOW(), 'grocery1', NOW(), 0, 2)
insert into ORDER_TBL values(5, NOW(), 'grocery2', NOW(), 0, 2)