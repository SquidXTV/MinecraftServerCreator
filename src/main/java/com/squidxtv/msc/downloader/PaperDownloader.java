package com.squidxtv.msc.downloader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

/**
 * This class is used to download the paper servers.
 */
public class PaperDownloader extends ServerDownloader{


    final String base = "https://api.papermc.io/v2/projects/paper/";

    @Override
    public List<String> getNewVersions() {
        try {
            String text = getTextFromURL(base);
            JSONObject object = new JSONObject(text);
            //Return the list of versions
            return object.getJSONArray("versions")
                    .toList()
                    .stream()
                    .map(Object::toString)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public void downloadFile(File destination, String version) throws IOException {
        saveFileFromURL(destination,getURLFile(version));
    }

    /**
     * Get the url for the version of the server.
     * @param version Version of the server.
     * @return Url for the server download
     */
    private String getURLFile(String version) throws IOException {
        String url = base+version+"/builds/";
        String text = getTextFromURL(url);

        // Get the latest build
        JSONObject object = new JSONObject(text).getJSONArray("builds")
                .toList()
                .stream()
                .map(JSONObject::new)
                .max((o1, o2) -> {
                    int i1 = o1.getInt("build");
                    int i2 = o2.getInt("build");
                    return Math.max(i1, i2);
                })
                .orElse(null);
        if(object == null)
            return null;

        //bulild number
        int build = object.getInt("build");

        //download name
        String download = object.getJSONObject("downloads").getJSONObject("application").getString("name");


        return url+build+"/downloads/"+download;
    }


}
