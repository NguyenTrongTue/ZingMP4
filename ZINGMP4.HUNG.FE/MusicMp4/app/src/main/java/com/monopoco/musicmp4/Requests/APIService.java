package com.monopoco.musicmp4.Requests;

public class APIService {
    private static String base_url = "http://10.0.2.2:5109/api/";
    public static DataService getService(){
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
