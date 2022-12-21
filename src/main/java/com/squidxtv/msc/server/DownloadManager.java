package com.squidxtv.msc.server;

import com.squidxtv.msc.Main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadManager {

    public static void downloadBuildTools() throws IOException {
        // check if git and correct java version is available, BuildTools will automatically download and install Git
        final Path path = Path.of(Main.getTools().toUri()).resolve("BuildTools.jar");
        if(Files.exists(path)) {
            System.out.println("exists");
            return;
        }
        final URL url = new URL("https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar");
        try(ReadableByteChannel byteChannel = Channels.newChannel(url.openStream()); FileOutputStream fos = new FileOutputStream(path.toFile())) {
            fos.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
        }
    }

}
