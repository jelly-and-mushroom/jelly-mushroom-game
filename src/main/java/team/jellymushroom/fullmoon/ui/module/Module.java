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
     * 从外部看，模块的横坐标
     */
    Integer oX;

    /**
     * 从外部看，模块的纵坐标
     */
    Integer oY;

    /**
     * 从外部看，模块的宽
     */
    Integer oWidth;

    /**
     * 从外部看，模块的高
     */
    Integer oHeight;

    /**
     * 模块实际有效区域的横坐标
     */
    Integer iX;
    
    /**
     * 模块实际有效区域的纵坐标
     */
    Integer iY;
    
    /**
     * 模块实际有效区域的宽
     */
    Integer iWidth;
    
    /**
     * 模块实际有效区域的高
     */
    Integer iHeight;

    /**
     * 留白
     */
    Integer padding;

    /**
     * int, 边框图片宽度
     */
    private static final Integer EDGING_WIDTH = 7;

    /**
     * @param resource 绘制所需资源
     * @param oX Integer, 从外部看，模块的横坐标
     * @param oY Integer, 从外部看，模块的纵坐标
     * @param oWidth Integer, 从外部看，模块的宽
     * @param oHeight Integer, 从外部看，模块的高
     * @param padding Integer, 留白
     */
    Module(UIResourceEntity resource, Integer oX, Integer oY, Integer oWidth, Integer oHeight, Integer padding) {
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
