package com.squidxtv.msc.downloader;



import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to manager the download of minecraft servers.
 */
public abstract class ServerDownloader {

    List<String> cachedVersion;

    public ServerDownloader() {
        cachedVersion = getNewVersions();
    }


    /**
     * Returns a list of all the versions that are available.
     * @return A list of all the versions that are available.
     */
    public abstract List<String> getNewVersions();


    /**
     * Download File from url to destination
     * @param destination Destination for the file
     * @param version Version of the server
     */
    public abstract void downloadFile(File destination,String version) throws IOException;


    /**
     * Save file from url to destination
     * @param file File to save
     * @param url Url to download from
     * @throws IOException If the file cannot be saved
     */
    protected void saveFileFromURL(File file, String url) throws IOException {
        try (ReadableByteChannel rbc = Channels.newChannel(new URL(url).openStream()); FileOutputStream fos = new FileOutputStream(file)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }


    /**
     * Get text from url
     * @param url Url to get text from
     * @return Text from url
     * @throws IOException If the text cannot be retrieved
     */
    protected String getTextFromURL(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));
    }


    public List<String> getCachedVersion() {
        return cachedVersion;
    }
}
