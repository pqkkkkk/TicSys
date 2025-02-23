insert into Role (name) values ('USER');
insert into Role (name) values ('ADMIN');
insert into Role (name) values ('ORGANIZER');
insert into Category values ('Sport');
insert into Category values ('Music');
insert into Category values ('Other');
insert into Category values ('Comedy');
insert into PromotionType(name) values ('Flash'), ('Combo'), ('Voucher');


select * from Category;
select * from users;
select * from role;
select * from RoleOfUser;
select * from event;
select * from Ticket;
select * from TicketOfOrder;
select * from [Order];
select * from Comment;

alter table event alter column description nvarchar(max);

alter table ticket drop constraint [FK__Ticket__type__571DF1D5];
drop table TicketType;
alter table ticket drop column type;
alter table ticket add minQtyInOrder int;
alter table ticket add maxQtyInOrder int;

delete from Ticket where id = 1;
delete from Ticket where id = 2;
delete from Event where id = 17;

delete from [Order] where id = 7;
delete from TicketOfOrder where id = 3;
delete from TicketOfOrder where id = 4;

alter table [order] drop column quantity;


alter table comment add dateCreatedAt date default '2025-02-18';
alter table comment add timeCreatedAt time default '16:00:00';

 INSERT INTO [comment] (content, senderId, eventId, parentId,dateCreatedAt, timeCreatedAt)
values ('Is this event interesting?', 'pqkiet854', 18, null, GETDATE(), GETDATE());

delete from Comment where id = 30;
