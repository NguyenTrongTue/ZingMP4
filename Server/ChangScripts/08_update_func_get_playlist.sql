drop function if exists public.func_get_playlist_by_id(p_playlist_id uuid);

CREATE OR REPLACE FUNCTION public.func_get_playlist_by_id(p_playlist_id uuid)
 RETURNS TABLE(playlist_id uuid, user_id uuid, username text, playlist_name text, playback_mode integer, is_public boolean, song_id uuid, song_name text, singer_name text, thumnail text, number_of_listens integer, location text, link_song text)
 LANGUAGE plpgsql
AS $function$
	begin
		drop table if exists tmp_playlist_user;
		create temporary table tmp_playlist_user as
		select p.playlist_id,
            p.user_id,
            uv.username,
            p.playlist_name,
            p.playback_mode,
            p.is_public  
        FROM
            public.playlist p
        join
        	public.user_verify uv on uv.user_id = p.user_id
        WHERE
            p.playlist_id = p_playlist_id;
           
		RETURN QUERY
        SELECT
            p.playlist_id,
            p.user_id,
            p.username,
            p.playlist_name,
            p.playback_mode,
            p.is_public,
            s.song_id,
            s.song_name,
            s.singer_name,
            s.thumnail,
            s.number_of_listens,
            s.location,
            s.link_song
        FROM
            tmp_playlist_user p
        left JOIN
            public.playlist_config pc ON p.playlist_id = pc.playlist_id
        left JOIN
            public.song s ON pc.song_id = s.song_id   
        WHERE
            p.playlist_id = p_playlist_id;
    END;
$function$
;
