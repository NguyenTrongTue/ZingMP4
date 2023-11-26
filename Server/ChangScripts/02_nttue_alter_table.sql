CREATE OR REPLACE FUNCTION public.func_get_recently_played(p_user_id uuid)
	RETURNS TABLE(song_id uuid, song_name text, singer_name text, thumnail text, number_of_listens integer, location text, link_song text)
	LANGUAGE plpgsql
AS $function$
declare queryText text;
	BEGIN
 queryText = 'select 
					s.song_id,
					s.song_name,
					s.singer_name,
					s.thumnail,
					s.number_of_listens,
					s.location,
					s.link_song
					from public.song s
					inner join public.recently_played rp on rp.song_id = s.song_id 
					where rp.user_id = ' || ''' || p_user_id ||  ''' ||
					' order by rp.play_time desc' || ';';
	raise notice '%', queryText;

	return query execute queryText;
	
	END;
$function$
;
