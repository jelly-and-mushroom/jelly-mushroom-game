package team.jellymushroom.fullmoon.ui.module;

import team.jellymushroom.fullmoon.entity.ui.UIResourceEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 抽象模块：包含模块的基本信息
 */
public abstract class Module {

    /**
     * 所需资源
     */
    UIResourceEntity resource;

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
     * int, 边框图片宽度
     */
    private static final Integer EDGING_WIDTH = 7;

    /**
     * @param resource 绘制所需资源
     * @param oX int, 从外部看，模块的横坐标
     * @param oY int, 从外部看，模块的纵坐标
     * @param oWidth int, 从外部看，模块的宽
     * @param oHeight int, 从外部看，模块的高
     * @param padding int, 留白
     */
    Module(UIResourceEntity resource, int oX, int oY, int oWidth, int oHeight, int padding) {
        this.resource = resource;
        this.oX = oX;
        this.oY = oY;
        this.oWidth = oWidth;
        this.oHeight = oHeight;
        this.padding = padding;
        this.iX = this.oX + EDGING_WIDTH + this.padding;
        this.iY = this.oY + EDGING_WIDTH + this.padding;
        this.iWidth = this.oWidth - 2 * (EDGING_WIDTH + this.padding);
        this.iHeight = this.oHeight - 2 * (EDGING_WIDTH + this.padding);
    }

    /**
     * 绘制边框
     */
    void drawWindow(Graphics g) {
        BufferedImage edgingImg = this.resource.getEdgingImg();
        int windowWidth = edgingImg.getWidth();
        int windowHeight = edgingImg.getHeight();
        // 左上
        g.drawImage(edgingImg, this.oX, this.oY, this.oX + EDGING_WIDTH, this.oY + EDGING_WIDTH, 0, 0, EDGING_WIDTH, EDGING_WIDTH, null);
        // 中上
        g.drawImage(edgingImg, this.oX + EDGING_WIDTH, this.oY, this.oX + this.oWidth - EDGING_WIDTH, this.oY + EDGING_WIDTH, EDGING_WIDTH, 0, windowWidth - EDGING_WIDTH, EDGING_WIDTH, null);
        // 右上
        g.drawImage(edgingImg, this.oX + this.oWidth - EDGING_WIDTH, this.oY, this.oX + this.oWidth, this.oY + EDGING_WIDTH, windowWidth - EDGING_WIDTH, 0, windowWidth, EDGING_WIDTH, null);
        // 左中
        g.drawImage(edgingImg, this.oX, this.oY + EDGING_WIDTH, this.oX + EDGING_WIDTH, this.oY + this.oHeight - EDGING_WIDTH, 0, EDGING_WIDTH, EDGING_WIDTH, windowHeight - EDGING_WIDTH, null);
        // 右中
        g.drawImage(edgingImg, this.oX + this.oWidth - EDGING_WIDTH, this.oY + EDGING_WIDTH, this.oX + this.oWidth, this.oY + this.oHeight - EDGING_WIDTH, windowWidth - EDGING_WIDTH, EDGING_WIDTH, windowWidth, windowHeight - EDGING_WIDTH, null);
        // 左下
        g.drawImage(edgingImg, this.oX, this.oY + this.oHeight - EDGING_WIDTH, this.oX + EDGING_WIDTH, this.oY + this.oHeight, 0, windowHeight - EDGING_WIDTH, EDGING_WIDTH, windowHeight, null);
        // 中下
        g.drawImage(edgingImg, this.oX + EDGING_WIDTH, this.oY + this.oHeight - EDGING_WIDTH, this.oX + this.oWidth - EDGING_WIDTH, this.oY + this.oHeight, EDGING_WIDTH, windowHeight - EDGING_WIDTH, windowWidth - EDGING_WIDTH, windowHeight, null);
        // 右下
        g.drawImage(edgingImg, this.oX + this.oWidth - EDGING_WIDTH, this.oY + this.oHeight - EDGING_WIDTH, this.oX + this.oWidth, this.oY + this.oHeight, windowWidth - EDGING_WIDTH, windowHeight - EDGING_WIDTH, windowWidth, windowHeight, null);
    }

    /**
     * 绘制模块
     */
    abstract void draw(Graphics g);
}
