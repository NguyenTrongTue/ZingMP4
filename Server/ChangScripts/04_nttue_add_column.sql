alter table song add if not exists liked integer;

update public.song set liked = 0;