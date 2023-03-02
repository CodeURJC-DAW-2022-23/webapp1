package net.daw.alist.utils;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.sql.Blob;

public class Utils {

    public static Blob pathToImage(String path) throws IOException {
        Resource image = new ClassPathResource(path);
        return BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
    }

}
