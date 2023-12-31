
delete from public.playlist;
delete from public.playlist_config;
delete from public.recently_played;
delete from public.song;
delete from public.user_verify;

INSERT INTO public.song (song_id,song_name,singer_name,thumnail,number_of_listens,"location",link_song) VALUES
	 ('f79667b1-f9a4-4684-9752-c24200883a90','Như Anh Đã Thấy Em','PhucXP, Freak D','https://localhost:7211/songs/images/26defe0a-6736-4914-a2fe-49080e8e9388.jpg',1,'','https://localhost:7211/songs/lyrics/e571043f-6d63-4193-a45f-d0f83823f4ff.mp3'),
	 ('1282c405-f934-4518-90d0-4273396717ba','Đổi Thay','Hồ Quang Hiếu','https://localhost:7211/songs/images/c3cd34e5-7bd6-4536-bdf3-ca35d86e7980.jpg',3,'','https://localhost:7211/songs/lyrics/403ae803-3651-45af-aae1-ef84a917727e.mp3'),
	 ('c129c109-0cf6-4451-97e0-0a7da948090d','Chúng Ta Dừng Lại Ở Đây Thôi','Nguyễn Đình Vũ','https://localhost:7211/songs/images/c00e4466-84d8-41b3-bcc7-336604f3533a.jpg',1,'','https://localhost:7211/songs/lyrics/56bb35b1-e1b4-43dd-966d-67171de7d010.mp3');
INSERT INTO public.user_verify (user_id,email,password_hash,password_salt,username,avatar) VALUES
	 ('3fa85f64-5717-4562-b3fc-2c963f66afa6','user@example.com',decode('4F7FCED04B4BF0BD53E3D6353C8B0BBB6C1BB21122D7D08C90677E4B9603D239EFC0464E3F7EE213E7F70FAC59BB4F3D2C57F1895E6BAC1791B30D61216B7FC8','hex'),decode('0658A298B4E85D2768ED2922A9CDC0CD07F91A70A4EF815BCC2A9EA1061EE92F6E7D9AD2251790223EE07AC888D5F2CEBB16554DC1A1D18D53F15B767F86BFD803FB6A0D206DE3A0FEF39BE1EECBEAEB820C4F5FBBD720E672AD8A44DA5EA394F3C37EB2C8D88EE93D616AF644173699692168380C8148BD2454E9B034950DB1','hex'),'string','string'),
	 ('ad87c880-86ec-4ab5-9844-2f4148d4cdee','user1@example.com',decode('2C3C125E47432CB054C77268F5B70A1A9C6E50FD56CD4DE07EA28BD61D8CFF3D9F73C331FB370B168C11787C5ADCC2A16186F00ED3300826A79C28D826849857','hex'),decode('3F79DC38B836FE4457B6E151ECC79F44E4EF324E38D2D4DE864BC9C85761314F8DACAC3F36D42F5020D7F13FA55D896A21490D0EF2F9C6899E61DB75EAC19F1A8B6222A08D24DB09DC490703005CBF93B550354803B5038F0CD863C64BE71C3AF5E819FF495BD1B61D8A2A2FD541CF7153D11EA917644A5ACB1DE4E6048F1F20','hex'),'string','string'),
	 ('a2019693-63db-4507-9db9-d5468e5669b5','trongnguyen@gmail.com',decode('4D4EA8581301CD7D2423D19C028D9854266667C6A5D1B026BE743D0D8BC508B8F3479C8539812E005A9FE49F3E86D7876CFD07D0E29A3A0A6FC2FADEAFDDF900','hex'),decode('2635B36278DF9B4271B4A1F008F4C71541A40CD62D4466E2839D15F1FD22C23D5014BFE78D05CEC885D594A11C3AC78910AE4B51DB26F33932DD170A10BE28BB71BAFF81435E3E4A3D7C3F2C93178DD2D35F8E2A2C11AD2B8B8A8FB70C7C25BF752C6F0C4FEFDD528373330971F3F45FFE8F19DE3A500A317E1CC0F8F89D6550','hex'),'trongnguyen','https://localhost:7211/users/1a9471f2-9c72-4242-bba4-5d8fe673daa4.jpg'),
	 ('87b68e98-2ba6-47ae-83ea-932c6eeeb623','dung@gmail.com',decode('B56CF19F62E95547F0C24825EED9864CD81C6763D37E004F74CAE3484604F8F76C45A0A17B7FEE87B13EABF6D35548B440D28022CEA149CEF8C037DEC478534D','hex'),decode('01FE25D104444005F4F97AC09043A0D08416ACA32FE42EA082F803F5509C5F23B00CD44E32F7E12126E05705C919051F26D8F4166DC13960E52003E49EBD6D60F140454108EE5A5C70081AE31FA266D1FC23280B25EA94B3E26945E692866F8D3683618676DED3B8D787F65C150D1E8AC610BC63D12477445A37B60F6768C97A','hex'),'Dung Nguyen','https://localhost:7211/users/997d356a-ac62-4f7d-a7ab-e86717a78ca9.jpg');


INSERT INTO public.playlist (playlist_id,user_id,playlist_name,is_public,playback_mode) VALUES
	 ('2cc3f4d3-15d4-4c71-bdfb-93bc0c688700','3fa85f64-5717-4562-b3fc-2c963f66afa6','string',true,1);

INSERT INTO public.playlist_config (playlist_config_id,playlist_id,song_id) VALUES
	 ('a284c1df-28b8-42a5-a7a7-851ac60e8080','2cc3f4d3-15d4-4c71-bdfb-93bc0c688700','f79667b1-f9a4-4684-9752-c24200883a90');

INSERT INTO public.recently_played (recently_played_id,user_id,song_id,play_time) VALUES
	 ('d116581e-59eb-4f37-8d45-2e77155e1caf','a2019693-63db-4507-9db9-d5468e5669b5','f79667b1-f9a4-4684-9752-c24200883a90','2023-11-26 11:32:46.765358'),
	 ('1948359d-19c4-4401-b1a7-9bc3d323a29c','a2019693-63db-4507-9db9-d5468e5669b5','1282c405-f934-4518-90d0-4273396717ba','2023-11-26 14:06:52.784449'),
	 ('bad98ff9-6b2c-4753-8f9a-e3ca355e68f1','87b68e98-2ba6-47ae-83ea-932c6eeeb623','c129c109-0cf6-4451-97e0-0a7da948090d','2023-11-26 15:18:14.963205');

