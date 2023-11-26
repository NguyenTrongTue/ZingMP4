drop function if exists public.func_get_playlist_by_id(p_playlist_id uuid);

CREATE OR REPLACE FUNCTION public.func_get_playlist_by_id(p_playlist_id uuid)
 RETURNS TABLE(playlist_id uuid, user_id uuid, username text, playlist_name text, playback_mode integer, is_public boolean, song_id uuid, song_name text, singer_name text, thumnail text, number_of_listens integer, location text, link_song text)
 LANGUAGE plpgsql
AS $function$
	BEGIN
		RETURN QUERY
        SELECT
            p.playlist_id,
            p.user_id,
            uv.username,
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
            public.playlist p
        JOIN
            public.playlist_config pc ON p.playlist_id = pc.playlist_id
        JOIN
            public.song s ON pc.song_id = s.song_id
        join
        	public.user_verify uv on uv.user_id = p.user_id
        WHERE
            p.playlist_id = p_playlist_id;
    END;
$function$
;

drop function if exists public.func_get_recently_played(p_user_id uuid);
CREATE OR REPLACE FUNCTION public.func_get_recently_played(p_user_id uuid)
 RETURNS TABLE(song_id uuid, song_name text, singer_name text, thumnail text, number_of_listens integer, location text, link_song text)
 LANGUAGE plpgsql
AS $function$
DECLARE
    queryText text;
BEGIN
    queryText := 'SELECT 
                        s.song_id,
                        s.song_name,
                        s.singer_name,
                        s.thumnail,
                        s.number_of_listens,
                        s.location,
                        s.link_song
                  FROM 
                        public.song s
                        INNER JOIN public.recently_played rp ON rp.song_id = s.song_id 
                  WHERE 
                        rp.user_id = ' || quote_literal(p_user_id) ||
                  ' ORDER BY 
                        rp.play_time DESC;';

    RAISE NOTICE '%', queryText;

    RETURN QUERY EXECUTE queryText;
END;
$function$
;

drop function if exists public.func_get_song_by_filter(p_take integer, p_skip integer, p_filter text);

CREATE OR REPLACE FUNCTION public.func_get_song_by_filter(p_take integer, p_skip integer, p_filter text)
 RETURNS TABLE(song_id uuid, song_name text, singer_name text, thumnail text, number_of_listens integer, location text, link_song text)
 LANGUAGE plpgsql
AS $function$
DECLARE
    queryText text;
    p_offset integer := p_take * (p_skip - 1);
    whereText text;
BEGIN
    whereText := 'song_name ILIKE ''%' || p_filter || '%'' OR singer_name ILIKE ''%' || p_filter || '%''';
    queryText := 'SELECT * FROM public.song WHERE ' || whereText || ' LIMIT ' || p_take || ' OFFSET ' || p_offset;

	RAISE NOTICE '%', queryText;
    
RETURN QUERY EXECUTE queryText;
END;
$function$;

drop function if exists public.func_update_rencently_played(recently_played_id uuid, p_song_id uuid, p_user_id uuid, p_play_time timestamp without time zone);
CREATE OR REPLACE FUNCTION public.func_update_rencently_played(recently_played_id uuid, p_song_id uuid, p_user_id uuid, p_play_time timestamp without time zone)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
declare is_song_exists int := 0;
BEGIN
		update public.song set number_of_listens = number_of_listens + 1 where song_id = p_song_id;
	
	select count(1) into is_song_exists from recently_played where song_id = p_song_id;

	if is_song_exists = 0 then
		insert into recently_played (recently_played_id, song_id, user_id, play_time) values (recently_played_id, p_song_id, p_user_id, p_play_time);
	else 
		update recently_played set play_time =  p_play_time where song_id = p_song_id and user_id = p_user_id;
	end if;
	return true;
	END;
$function$
;

