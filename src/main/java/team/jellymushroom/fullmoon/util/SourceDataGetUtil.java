package team.jellymushroom.fullmoon.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

/**
 * 静态工具类，将各种素材数据加载入内存
 */
public class SourceDataGetUtil {

    /**
     * 本类为静态工具类，不得创建实例
     */
    private SourceDataGetUtil() {}

    /**
     * 加载文件
     * @param path String, 相对根目录的路径
     * @return File, 文件
     * @throws URISyntaxException
     * @throws FileNotFoundException
     */
    public static File loadFile(String path) throws URISyntaxException, FileNotFoundException {
        SourceDataGetUtil.check(path);
        URL url = SourceDataGetUtil.loadURL(path);
        return new File(url.toURI());
    }

    /**
     * 加载图片
     * @param path String, 相对根目录的路径
     * @return BufferedImage, 图片
     * @throws IOException
     */
    public static BufferedImage loadBufferedImage(String path) throws IOException {
//        SourceDataGetUtil.check(path);
//        URL url = SourceDataGetUtil.loadURL(path);
        return ImageIO.read(new File(path));
    }

    /**
     * 加载URL
     * @param path String, 相对根目录的路径
     * @return URL
     * @throws FileNotFoundException
     */
    public static URL loadURL(String path) throws FileNotFoundException {
        SourceDataGetUtil.check(path);
        URL url = SourceDataGetUtil.class.getClassLoader().getResource(path);
        if (null == url)
            throw new FileNotFoundException("get none url by path=" + path); 
        return url;
    }

    /**
     * 检查穿入路径是否合法
     * @param path String, 路径
     */
    private static void check(String path) {
        if (StringUtils.isBlank(path))
            throw new NullPointerException("path is blank");
    }
}
