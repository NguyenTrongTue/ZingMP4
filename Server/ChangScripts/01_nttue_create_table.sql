DROP TABLE public.playlist;

CREATE TABLE public.playlist (
	playlist_id uuid NOT NULL,
	user_id uuid NOT NULL,
	playlist_name text NULL,
	is_public bool NULL,
	playback_mode int4 NULL,
	CONSTRAINT playlist_pkey PRIMARY KEY (playlist_id),
	CONSTRAINT pk_playlist_user_verify FOREIGN KEY (user_id) REFERENCES public.user_verify(user_id),
	CONSTRAINT playlist_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_verify(user_id)
);

DROP TABLE public.playlist_config;

CREATE TABLE public.playlist_config (
	playlist_config_id uuid NOT NULL,
	playlist_id uuid NULL,
	song_id uuid NULL,
	CONSTRAINT playlist_config_pkey PRIMARY KEY (playlist_config_id)
);

DROP TABLE public.recently_played;

CREATE TABLE public.recently_played (
	recently_played_id uuid NOT NULL,
	user_id uuid NULL,
	song_id uuid NULL,
	play_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT recently_played_pkey PRIMARY KEY (recently_played_id),
	CONSTRAINT recently_played_song_id_fkey FOREIGN KEY (song_id) REFERENCES public.song(song_id),
	CONSTRAINT recently_played_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_verify(user_id)
);

DROP TABLE public.song;

CREATE TABLE public.song (
	song_id uuid NOT NULL,
	song_name text NOT NULL,
	singer_name text NOT NULL,
	thumnail text NOT NULL,
	number_of_listens int4 NOT NULL DEFAULT 0,
	"location" text NULL,
	link_song text NULL,
	CONSTRAINT song_pkey PRIMARY KEY (song_id)
);

DROP TABLE public.user_verify;

CREATE TABLE public.user_verify (
	user_id uuid NOT NULL,
	email text NOT NULL,
	password_hash bytea NULL,
	password_salt bytea NULL,
	username text NULL,
	avatar text NULL,
	CONSTRAINT user_verify_pkey PRIMARY KEY (user_id)
);