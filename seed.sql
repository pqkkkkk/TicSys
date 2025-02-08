insert into Role (name) values ('USER');
insert into Role (name) values ('ADMIN');
insert into Role (name) values ('ORGANIZER');
insert into Category values ('Sport');
insert into Category values ('Music');
insert into Category values ('Other');
insert into Category values ('Comedy');

insert into TicketType values ('A'),
('B'), ('C'), ('D'), ('E');
select * from Category;
select * from users;
select * from role;
select * from RoleOfUser;
select * from event;
select * from Ticket;

alter table event alter column description nvarchar(max);

alter table ticket drop constraint [FK__Ticket__type__571DF1D5];
drop table TicketType;
alter table ticket drop column type;
alter table ticket add minQtyInOrder int;
alter table ticket add maxQtyInOrder int;

delete from Ticket where id = 1;
delete from Ticket where id = 2;
delete from Event where id = 17;
