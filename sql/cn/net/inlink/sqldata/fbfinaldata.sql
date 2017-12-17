prompt Importing table fb_statistics_final...
set feedback off
set define off
insert into fb_statistics_final (LOCATIONCODE, DEPARTMENT, FLAG, QTY)
values ('ZA1', '组立三科', 'DownloadASSY3', 140781);

insert into fb_statistics_final (LOCATIONCODE, DEPARTMENT, FLAG, QTY)
values ('ZB1', '制造二科', 'DownloadMAKE2', 2339788);

insert into fb_statistics_final (LOCATIONCODE, DEPARTMENT, FLAG, QTY)
values ('合计', '制造二科', 'DownloadMAKE2', 2339788);

insert into fb_statistics_final (LOCATIONCODE, DEPARTMENT, FLAG, QTY)
values ('合计', '组立三科', 'DownloadASSY3', 140781);

prompt Done.
