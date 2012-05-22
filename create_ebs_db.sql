drop database IF EXISTS ebs;
create database ebs;
use ebs;

#create tables
create table sales_orders (id int, is_open boolean);
create table sales_order_items(sales_order_id int, item_id int, qty int);
create table delivery_records(id int PRIMARY KEY AUTO_INCREMENT, delivery_date varchar(10), weight varchar(10), customer_name varchar(30), address varchar(50));

#seed data
insert into sales_orders VALUES(1, true);
insert into sales_orders VALUES(2, true);
insert into sales_orders VALUES(3, true);
insert into sales_orders VALUES(4, true);
insert into sales_orders VALUES(5, true);
		
insert into sales_order_items VALUES (1, 9, 10);
insert into sales_order_items VALUES (1, 8, 20);
insert into sales_order_items VALUES (1, 7, 30);

insert into sales_order_items VALUES (2, 5, 10);
insert into sales_order_items VALUES (2, 17, 20);
insert into sales_order_items VALUES (2, 13, 30);
	
insert into sales_order_items VALUES (3, 12, 10);
insert into sales_order_items VALUES (3, 18, 20);
insert into sales_order_items VALUES (3, 19, 30);
	
insert into sales_order_items VALUES (4, 9, 10);
insert into sales_order_items VALUES (4, 8, 20);
insert into sales_order_items VALUES (4, 7, 30);
	
insert into sales_order_items VALUES (5, 17, 10);
insert into sales_order_items VALUES (5, 13, 20);
insert into sales_order_items VALUES (5, 12, 30);