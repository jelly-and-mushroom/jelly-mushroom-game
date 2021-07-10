package team.jellymushroom.fullmoon.ui.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.jellymushroom.fullmoon.util.SourceDataGetUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 抽象模块：包含模块的基本信息
 */
public abstract class Module {

    /**
     * BufferedImage, 边框图片
     */
    private static BufferedImage IMAGE_EDGING;

    /**
     * boolean, true--开启窗口内部区域的遮蔽效果,false--关闭窗口内部区域的遮蔽效果
     */
    private static boolean EDG_SHELTER;

    /**
     * int, 边框宽度，单位为px
     */
    public static final int EDGING_LENGTH = 7;

    private static final Logger LOGGER = LoggerFactory.getLogger(Module.class);

    /**
     * int, 从外部看，模块的横坐标
     */
    int oX;

    /**
     * int, 从外部看，模块的纵坐标
     */
    int oY;

    /**
     * int, 从外部看，模块的宽
     */
    int oWidth;

    /**
     * int, 从外部看，模块的高
     */
    int oHeight;

    /**
     * int, 模块实际有效区域的横坐标
     */
    int iX;
    
    /**
     * int, 模块实际有效区域的纵坐标
     */
    int iY;
    
    /**
     * int, 模块实际有效区域的宽
     */
    int iWidth;
    
    /**
     * int, 模块实际有效区域的高
     */
    int iHeight;

    /**
     * int, 留白
     */
    int padding;

    /**
     * BufferedImage, 边框图片
     */
    private BufferedImage imageEdging;

    /**
     * int, 边框图片宽度，单位为px
     */
    private int edgingLength;

    static {
        try {
            // IMAGE_EDGING
            String edgingPath = "/Users/reimuwang/temp/material/images/windows/0.png";
            Module.IMAGE_EDGING = SourceDataGetUtil.loadBufferedImage(edgingPath);
            // EDG_SHELTER
            String edgShelter = "0";
            if ("1".equals(edgShelter))
                Module.EDG_SHELTER = true;
            else if (!"0".equals(edgShelter)) {
                LOGGER.error("view.frame.module.edgShelter illegal,shutdown");
                System.exit(0);
            }
        } catch (Exception e) {
            LOGGER.error("fail to init Module static", e);
            System.exit(0);
        }
    }

    /**
     * 构造函数
     * @param oX int, 从外部看，模块的横坐标
     * @param oY int, 从外部看，模块的纵坐标
     * @param oWidth int, 从外部看，模块的宽
     * @param oHeight int, 从外部看，模块的高
     * @param padding int, 留白
     */
    Module(int oX, int oY, int oWidth, int oHeight, int padding) {
        this.oX = oX;
        this.oY = oY;
        this.oWidth = oWidth;
        this.oHeight = oHeight;
        this.padding = padding;
        this.imageEdging = Module.IMAGE_EDGING;
        this.edgingLength = Module.EDGING_LENGTH;
        this.iX = this.oX + this.edgingLength + this.padding;
        this.iY = this.oY + this.edgingLength + this.padding;
        this.iWidth = this.oWidth - 2 * (this.edgingLength + this.padding);
        this.iHeight = this.oHeight - 2 * (this.edgingLength + this.padding);
    }

    /**
     * 绘制边框
     */
    void drawWindow(Graphics g) {
        int windowWidth = this.imageEdging.getWidth();
        int windowHeight = this.imageEdging.getHeight();
        // 左上
        g.drawImage(this.imageEdging, this.oX, this.oY, this.oX + this.edgingLength, this.oY + this.edgingLength, 0, 0, this.edgingLength, this.edgingLength, null);
        // 中上
        g.drawImage(this.imageEdging, this.oX + this.edgingLength, this.oY, this.oX + this.oWidth - this.edgingLength, this.oY + this.edgingLength, this.edgingLength, 0, windowWidth - this.edgingLength, this.edgingLength, null);
        // 右上
        g.drawImage(this.imageEdging, this.oX + this.oWidth - this.edgingLength, this.oY, this.oX + this.oWidth, this.oY + this.edgingLength, windowWidth - this.edgingLength, 0, windowWidth, this.edgingLength, null);
        // 左中
        g.drawImage(this.imageEdging, this.oX, this.oY + this.edgingLength, this.oX + this.edgingLength, this.oY + this.oHeight - this.edgingLength, 0, this.edgingLength, this.edgingLength, windowHeight - this.edgingLength, null);
        // 中
        if (Module.EDG_SHELTER)
            g.drawImage(this.imageEdging, this.oX + this.edgingLength, this.oY + this.edgingLength, this.oX + this.oWidth - this.edgingLength, this.oY + this.oHeight - this.edgingLength, this.edgingLength, this.edgingLength, windowWidth - this.edgingLength, windowHeight - this.edgingLength, null);
        // 右中
        g.drawImage(this.imageEdging, this.oX + this.oWidth - this.edgingLength, this.oY + this.edgingLength, this.oX + this.oWidth, this.oY + this.oHeight - this.edgingLength, windowWidth - this.edgingLength, this.edgingLength, windowWidth, windowHeight - this.edgingLength, null);
        // 左下
        g.drawImage(this.imageEdging, this.oX, this.oY + this.oHeight - this.edgingLength, this.oX + this.edgingLength, this.oY + this.oHeight, 0, windowHeight - this.edgingLength, this.edgingLength, windowHeight, null);
        // 中下
        g.drawImage(this.imageEdging, this.oX + this.edgingLength, this.oY + this.oHeight - this.edgingLength, this.oX + this.oWidth - this.edgingLength, this.oY + this.oHeight, this.edgingLength, windowHeight - this.edgingLength, windowWidth - this.edgingLength, windowHeight, null);
        // 右下
        g.drawImage(this.imageEdging, this.oX + this.oWidth - this.edgingLength, this.oY + this.oHeight - this.edgingLength, this.oX + this.oWidth, this.oY + this.oHeight, windowWidth - this.edgingLength, windowHeight - this.edgingLength, windowWidth, windowHeight, null);
    }

    /**
     * 绘制模块
     */
    abstract void draw(Graphics g);
}
