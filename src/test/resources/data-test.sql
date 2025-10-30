insert into institution_type(id, name)
values (1000, 'root'),
       (2000, 'organization'),
       (3000, 'school');

insert into institution(id, name, type_id, school_parent_id)
values (1000, 'root',          1000, null),
       (2000, 'EduCloudwise',  2000, null),
       (3000, 'Cloudwise',     2000, null),
       (4000, 'Cloud College', 3000, 2000),
       (5000, 'Sun School',    3000, 3000),
       (6000, 'The Rainbow',   3000, 3000);

insert into application(id, app_key, name, url, institution_id)
values (1000,  'a1000', 'Gmail',                 'www.gmail.com',                         1000),
       (2000,  'a2000', 'Agenda',                'www.google.com/agenda',                 1000),
       (3000,  'a3000', 'Math4You',              'www.math4you.com',                      1000),
       (4000,  'a4000', 'Biology Naturally',     'www.studyapps.com/biology-naturally',   1000),
       (5000,  'a2000', 'Calendar',              'www.google.com/agenda',                 2000),
       (6000,  'a5000', 'EduCloudwise Intranet', 'www.educloudwise.com/intranet',         2000),
       (7000,  'a1000', 'Email',                 'www.outlook.com/mail',                  4000),
       (8000,  'a2000', 'Agenda',                'www.outlook.com/agenda',                4000),
       (9000,  'a6000', 'School Site',           'www.cloudcollege.com',                  4000),
       (10000, 'a7000', 'School Site',           'www.sunschool.com',                     5000),
       (11000, 'a5000', 'Intranet',              'www.educloudwise.com/intranet-rainbow', 6000);

insert into users(id, name, school_id)
values (1000, 'John',  4000),
       (2000, 'Mary',  5000),
       (3000, 'Peter', 6000);
