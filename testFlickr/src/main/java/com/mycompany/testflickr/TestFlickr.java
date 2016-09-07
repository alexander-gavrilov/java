/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testflickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.flickr4java.flickr.uploader.Uploader;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.scribe.model.Token;
import org.scribe.model.Verifier;

/**
 *
 * @author gavrilov_a
 */
public class TestFlickr {
    static Logger logger =  Logger.getLogger(TestFlickr.class);
    private static Flickr flickr;
    private static Auth auth;

    public static void auth() throws FlickrException, IOException, URISyntaxException {
        REST rest = new REST();
        rest.setProxy("192.168.15.240", 3128);
        flickr = new Flickr("021cb6473f5e35fd3fb995aea1b797f3", "1d88f92b59a1b825", rest);

        Flickr.debugStream = false;
        AuthInterface authInterface = flickr.getAuthInterface();
        Scanner scanner = new Scanner(System.in);
        Token token = authInterface.getRequestToken();
        System.out.println("token: " + token);
        String url = authInterface.getAuthorizationUrl(token, Permission.WRITE);
        System.out.println("Follow this URL to authorise yourself on Flickr");

        if (Desktop.isDesktopSupported()) {
            // Windows
            Desktop.getDesktop().browse(new URI(url));
        } else {
            // Ubuntu
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("/usr/bin/firefox -new-window " + url);
        }
        System.out.println(url);
        System.out.println("Paste in the token it gives you:");
        System.out.print(">>");
        String tokenKey = scanner.nextLine();
        Token requestToken = authInterface.getAccessToken(token, new Verifier(tokenKey));
        System.out.println("Authentication success");

        auth = authInterface.checkToken(requestToken);

        // This token can be used until the user revokes it.
        System.out.println("Token: " + requestToken.getToken());
        System.out.println("nsid: " + auth.getUser().getId());
        System.out.println("Realname: " + auth.getUser().getRealName());
        System.out.println("Username: " + auth.getUser().getUsername());
        System.out.println("Permission: " + auth.getPermission().getType());
        flickr.setAuth(auth);

    }

    public static void uploadPhoto(String filePath) throws FlickrException {
        Uploader uploader = flickr.getUploader();
        File file = new File(filePath);
        
        UploadMetaData metaData = new UploadMetaData();
        metaData.setContentType(Flickr.CONTENTTYPE_PHOTO);
        metaData.setSafetyLevel(Flickr.SAFETYLEVEL_SAFE);
        metaData.setFilename(file.getName());
        RequestContext.getRequestContext().setAuth(auth);
        uploader.upload(file, metaData);
        logger.info("File "+file.getAbsolutePath()+" uploaded" );
    }

    public static void main(String[] args) {
        try {
            TestFlickr.auth();
            if(flickr != null){
                TestFlickr.uploadPhoto(args[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
