CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop table if exists liked_song_config;

create table liked_song_config(
	liked_song_config_id uuid primary key not null,
	song_id uuid,
	user_id uuid,
	CONSTRAINT liked_song_config_song_id_fkey FOREIGN KEY (song_id) REFERENCES public.song(song_id),
	CONSTRAINT liked_song_config_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_verify(user_id)
);






