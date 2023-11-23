-- create table user_verify
drop table if exists user_verify;
CREATE TABLE user_verify (
    user_id uuid PRIMARY KEY,
    email text NOT NULL,
    password_hash text,
    password_salt text,
    username text,
    avatar text 
)

-- create table song
drop table if exists song;
CREATE TABLE song (
    song_id uuid PRIMARY KEY,
    song_name text NOT NULL,
    singer_name text NOT NULL,
    thumnail text NOT NULL,
    number_of_listens INT NOT NULL DEFAULT 0,
         text
);

-- create table playlist
drop table if exists playlist;
CREATE TABLE playlist (
    playlist_id uuid PRIMARY KEY,
    user_id uuid NOT NULL,
    song_id uuid NOT NULL,
    playlist_name text,
    playlist_image text,
    FOREIGN KEY (user_id) REFERENCES user_verify(user_id),
    FOREIGN KEY (song_id) REFERENCES song(song_id)
);
