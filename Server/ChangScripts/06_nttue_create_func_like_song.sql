drop function if exists public.func_like_song(p_song_id uuid, p_user_id uuid);

CREATE OR REPLACE FUNCTION public.func_like_song(p_song_id uuid, p_user_id uuid)
  RETURNS TABLE(tmp_song_id uuid, song_name text, singer_name text, thumbnail text, number_of_listens integer, location text, link_song text, tmp_liked integer, is_liked bool)
  LANGUAGE plpgsql
AS $function$
DECLARE
  v_is_liked integer := 0;
  v_tmp_song_id uuid;
BEGIN
  v_tmp_song_id := p_song_id;

  SELECT COUNT(1) INTO v_is_liked FROM public.liked_song_config lsc WHERE lsc.song_id = v_tmp_song_id;

  IF v_is_liked > 0 THEN
    DELETE FROM public.liked_song_config lsc WHERE lsc.song_id = v_tmp_song_id AND user_id = p_user_id;
   
    UPDATE public.song SET liked = liked - 1 WHERE song_id = v_tmp_song_id;
  ELSE
    INSERT INTO public.liked_song_config(liked_song_config_id, song_id, user_id)
    VALUES(uuid_generate_v4(), v_tmp_song_id, p_user_id);
   
    UPDATE public.song SET liked = liked + 1 WHERE song_id = v_tmp_song_id;
  END IF;

  DROP TABLE IF EXISTS tmp_result;
  CREATE TEMPORARY TABLE tmp_result
  (
    tmp_song_id uuid,
    song_name text, 
    singer_name text,
    thumbnail text, 
    number_of_listens integer,
    location text,
    link_song text, 
    tmp_liked integer,
    is_liked bool
  );
  INSERT INTO tmp_result
    SELECT 
    s.song_id as tmp_song_id,
    s.song_name,
    s.singer_name,
    s.thumnail,
    s.number_of_listens
, s."location", s.link_song, s.liked as tmp_liked  ,
      CASE 
        WHEN v_is_liked > 0 THEN true
        ELSE false 
      END AS is_liked
    FROM public.song s
    WHERE s.song_id = p_song_id;

  RETURN QUERY SELECT * FROM tmp_result;
END;
$function$;



drop function public.func_get_song_by_filter(p_take integer, p_skip integer, p_filter text);

CREATE OR REPLACE FUNCTION public.func_get_song_by_filter(p_take integer, p_skip integer, p_filter text)
 RETURNS TABLE(song_id uuid, song_name text, singer_name text, thumnail text, number_of_listens integer, location text, link_song text, liked integer)
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
$function$
;

drop function if exists public.func_get_recently_played(p_user_id uuid);

CREATE OR REPLACE FUNCTION public.func_get_recently_played(p_user_id uuid)
 RETURNS TABLE(song_id uuid, song_name text, singer_name text, thumnail text, number_of_listens integer, location text, link_song text, liked integer)
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
                        s.link_song,
						s.liked
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


drop function if exists public.func_get_playlist_by_id(p_playlist_id uuid);

CREATE OR REPLACE FUNCTION public.func_get_playlist_by_id(p_playlist_id uuid)
 RETURNS TABLE(playlist_id uuid, user_id uuid, username text, playlist_name text, playback_mode integer, is_public boolean, song_id uuid, song_name text, singer_name text, thumnail text, number_of_listens integer, location text, link_song text, liked integer)
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
            s.link_song,
            s.liked 
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


