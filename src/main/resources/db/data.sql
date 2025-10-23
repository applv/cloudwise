
insert into institution_type(name, priority)
values ('root', 0),
       ('organization', 1),
       ('school', 2);

insert into institution(name, type_id)
select 'root',          (select id from institution_type where name = 'root')         union all
select 'EduCloudwise',  (select id from institution_type where name = 'organization') union all
select 'Cloudwise',     (select id from institution_type where name = 'organization') union all
select 'Cloud College', (select id from institution_type where name = 'school')       union all
select 'Sun School',    (select id from institution_type where name = 'school')       union all
select 'The Rainbow',   (select id from institution_type where name = 'school');

update institution
set school_parent_id = (select id from institution where name = 'EduCloudwise')
where name = 'Cloud College';

update institution
set school_parent_id = (select id from institution where name = 'Cloudwise')
where name = 'Sun School';

update institution
set school_parent_id = (select id from institution where name = 'Cloudwise')
where name = 'The Rainbow';

insert into application(app_key, name, url, institution_id)
select 'a1', 'Gmail',                 'www.gmail.com',                         (select id from institution where name = 'root')          union all
select 'a2', 'Agenda',                'www.google.com/agenda',                 (select id from institution where name = 'root')          union all
select 'a3', 'Math4You',              'www.math4you.com',                      (select id from institution where name = 'root')          union all
select 'a4', 'Biology Naturally',     'www.studyapps.com/biology-naturally',   (select id from institution where name = 'root')          union all
select 'a2', 'Calendar',              'www.google.com/agenda',                 (select id from institution where name = 'EduCloudwise')  union all
select 'a5', 'EduCloudwise Intranet', 'www.educloudwise.com/intranet',         (select id from institution where name = 'EduCloudwise')  union all
select 'a1', 'Email',                 'www.outlook.com/mail',                  (select id from institution where name = 'Cloud College') union all
select 'a2', 'Agenda',                'www.outlook.com/agenda',                (select id from institution where name = 'Cloud College') union all
select 'a6', 'School Site',           'www.cloudcollege.com',                  (select id from institution where name = 'Cloud College') union all
select 'a7', 'School Site',           'www.sunschool.com',                     (select id from institution where name = 'Sun School')    union all
select 'a5', 'Intranet',              'www.educloudwise.com/intranet-rainbow', (select id from institution where name = 'The Rainbow');

insert into users(name, school_id)
select 'John',  (select id from institution where name = 'Cloud College') union all
select 'Mary',  (select id from institution where name = 'Sun School')   union all
select 'Peter', (select id from institution where name = 'The Rainbow');
