package net.daw.alist.utils;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class Utils {

    public static Blob pathToImage(String path) throws IOException, SQLException {
        if (path == null) return null;
        Resource image = new ClassPathResource(path);
        Blob blob = BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
        return new SerialBlob(blob);
    }

}
