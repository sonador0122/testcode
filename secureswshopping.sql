create database secureswdb character set utf8 collate utf8_general_ci;

grant select, insert, update, delete, create, drop on secureswdb.* to 'securesw'@'localhost' identified by 'shopping';
grant select, insert, update, delete, create, drop on secureswdb .* to 'securesw'@'%' identified by 'shopping';


use secureswdb;

create table tbl_idx_sequence (
	sequence_name varchar(10) not null,
	next_value int not null,
	primary key (sequence_name)
);

insert into tbl_idx_sequence values ('board', 0);
insert into tbl_idx_sequence values ('comment', 0);

create table member_tbl (
	idx int not null auto_increment,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	email varchar(255) not null,
	password varchar(50) not null,
	address varchar(255) not null,
	postcode int not null,
	created_date datetime not null,
	last_date datetime not null,
	authority varchar(20) not null default 'ROLE_USER',
	image_path varchar(50) default 'default.jpg',
	primary key (idx),
	unique index (email)
);


CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);

create table board_tbl (
	idx int not null auto_increment,
	group_idx int not null,
	sequence_idx char(12) not null,
	title varchar(100) not null,
	content longtext not null,
	posting_date datetime not null,
	read_count int not null,
	comment_count int not null,
	file_count int not null,
	products_count int not null,
	member_idx int not null,
	member_email varchar(50) not null,
	separator_name varchar(20) not null,
	file_idx int,
	total_price int not null,
	image_path varchar(50),
	primary key (idx),
	index (sequence_idx),
	foreign key(member_idx) references member_tbl(idx),
	foreign key(member_email) references member_tbl(email)
);


create table comment_tbl(
	idx int not null auto_increment,
	group_idx int not null,
	sequence_idx char(12) not null,
	content text not null,
	posting_date datetime not null,
	member_idx int not null,
	member_email varchar(50) not null,
	board_idx int not null,
	separator_name varchar(20) not null,
	primary key (idx),
	index (sequence_idx),
	foreign key(board_idx) references board_tbl(idx) on delete cascade,
	foreign key(member_idx) references member_tbl(idx),
	foreign key(member_email) references member_tbl(email)
);

create table products_tbl (
	idx int not null auto_increment,
	name char(100) not null,
	size char(20) not null,
	material char(50) not null,
	component char(20) not null,
	options text not null,
	manufacturer char(50) not null,
	madein char(50) not null,
	description text not null,
	price int not null,
	stock int not null,
	member_idx int not null,
	member_email varchar(50) not null,
	created_date datetime not null,
	primary key (idx),
	foreign key(member_idx) references member_tbl(idx),
	foreign key(member_email) references member_tbl(email)
);

create table board_products_tbl (
	board_idx int not null,
	products_idx int not null,
	foreign key(board_idx) references board_tbl(idx),
	foreign key(products_idx) references products_tbl(idx)
);

create table orders_tbl(
	idx int not null auto_increment,
	order_date datetime not null,
	order_now varchar(50) not null,
	member_email varchar(255) not null,
	member_name varchar(50) not null,
	address varchar(255) not null,
	postcode int not null,
	receiver varchar(50) not null,
	board_idx int not null,
	total_price int not null,
	quantity int not null,
	primary key (idx)
);

create table giftlist_tbl(
	idx int not null auto_increment,
	board_idx int not null,
	member_email varchar(255) not null,
	primary key (idx),
	foreign key(member_email) references member_tbl(email) on delete cascade,
	foreign key(board_idx) references board_tbl(idx)
);

create table cart_tbl(
	idx int not null auto_increment,
	board_idx int not null,
	member_email varchar(255) not null,
	quantity int not null,
	primary key (idx),
	foreign key(member_email) references member_tbl(email) on delete cascade,
	foreign key(board_idx) references board_tbl(idx)
);

create table file_tbl(
	idx int not null auto_increment,
	realname varchar(255) not null,
	name varchar(255) not null,
	ext varchar(4) not null,
	uploader varchar(255) not null,
	board_idx int null,
	primary key (idx)
);