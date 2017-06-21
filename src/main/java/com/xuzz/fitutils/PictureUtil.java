package com.xuzz.fitutils;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Baih
 * @version V1.0
 * @Title: PictureUtil
 * @Package com.hxtx.core.utils
 * @Description: 图像处理的工具类
 * @date 2016/12/21 14:52
 */
public class PictureUtil
{
    /**
     * @param sizeContext 添加水印文字
     * @param request 请求流对象
     * @param request 响应流对象
     * */
    public static void createMarkSize(String sizeContext, HttpServletRequest request, HttpServletResponse response)   {
        try
        {
            String path=request.getRealPath(request.getServletPath());
            FileInputStream in=new FileInputStream("C:/Users/win10/Pictures/Saved Pictures/桌面.png");
            Image src=ImageIO.read(in);
            int w=src.getWidth(null);
            int h=src.getHeight(null);
            BufferedImage img=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);//构建画板
            Graphics g=img.getGraphics();//得到画笔
            g.drawImage(src,0,0,w,h,null);//把源图片写入画板
            g.setColor(Color.red);
            g.drawString(sizeContext,10,5);  // 添加文字
            g.dispose();//生成图片
            JPEGImageEncoder e=JPEGCodec.createJPEGEncoder(response.getOutputStream());
            e.encode(img);
            response.getOutputStream().close();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    /**
     * @param sizeContext 添加水印文字
     * @param primaryPath 原图完整图片名
     * @param targetPath 要保存的完整图片名
     */
    public static void createMarkSize(String sizeContext, String primaryPath, String targetPath)   {
        try
        {
            FileInputStream in=new FileInputStream(primaryPath);
            Image src=ImageIO.read(in);
            int w=src.getWidth(null);
            int h=src.getHeight(null);
            BufferedImage img=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);//构建画板
            //Font 对象（加粗）
            Font fsib30 = new Font("宋体", Font.BOLD, 16);
//          （加粗，倾斜）
//          Font fsib30 = new Font("宋体", Font.BOLD + Font.ITALIC, 16);
            Graphics g=img.getGraphics();//得到画笔
            g.setFont(fsib30);//添加Font
            g.drawImage(src,0,0,w,h,null);//把源图片写入画板
            g.setColor(Color.white);
            g.drawString(sizeContext,w-240,h-40);  // 添加文字
            g.dispose();//生成图片
            OutputStream outputStream = new FileOutputStream(new File(targetPath));

            JPEGImageEncoder e=JPEGCodec.createJPEGEncoder(outputStream);
            e.encode(img);
            outputStream.close();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    /**
     * @param localPath 添加水印LOGO路径
     * @param request 请求流对象
     * @param request 响应流对象
     **/
    public static void createMarkLogo(String localPath, HttpServletRequest request, HttpServletResponse response)   {
        try
        {
            FileInputStream file=new FileInputStream(localPath);
            Image fimg=ImageIO.read(file);
            int fw=fimg.getWidth(null);
            int fh=fimg.getHeight(null);
            String path=request.getRealPath(request.getServletPath());
            FileInputStream in=new FileInputStream(path);
            Image src=ImageIO.read(in);
            int w=src.getWidth(null);
            int h=src.getHeight(null);
            BufferedImage img=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);//构建画板
            Graphics g=img.getGraphics();//得到画笔
            g.drawImage(src,0,0,w,h,null);//把原图片写入画板
            g.drawImage(fimg,w-20,h-15,fw,fh,null);//把水印图片写入画板
            g.dispose();//生成图片
            JPEGImageEncoder e=JPEGCodec.createJPEGEncoder(response.getOutputStream());
            e.encode(img);
            response.getOutputStream().close();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }

    }

    /**
     * @param localPath 添加水印LOGO路径
     * @param primaryPath 原图完整图片名
     * @param targetPath 要保存的完整图片名
     */
    public static void createMarkLogo(String localPath, String primaryPath, String targetPath)   {
        try
        {
            FileInputStream file=new FileInputStream(localPath);
            Image fimg=ImageIO.read(file);
            int fw=fimg.getWidth(null);
            int fh=fimg.getHeight(null);
            FileInputStream in=new FileInputStream(primaryPath);
            Image src=ImageIO.read(in);
            int w=src.getWidth(null);
            int h=src.getHeight(null);
            BufferedImage img=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);//构建画板
            Graphics g=img.getGraphics();//得到画笔
            g.drawImage(src,0,0,w,h,null);//把原图片写入画板
            g.drawImage(fimg,w-246,h-100,fw,fh,null);//把水印图片写入画板
            g.dispose();//生成图片
            OutputStream outputStream = new FileOutputStream(new File(targetPath));

            JPEGImageEncoder e=JPEGCodec.createJPEGEncoder(outputStream);
            e.encode(img);
            outputStream.close();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }

    }

    /**
     * @param localPath 添加水印图片路径
     * @param request 请求流对象
     * @param request 响应流对象
     * @param width   水印图片的宽度
     * @param height  水印图片的长度
     **/
    public static void createMarkLogo(String localPath, HttpServletRequest request, HttpServletResponse response, int width, int height)   {
        try
        {
            FileInputStream file=new FileInputStream(localPath);
            Image fimg=ImageIO.read(file);
            int fw=fimg.getWidth(null);
            int fh=fimg.getHeight(null);
            String path=request.getRealPath(request.getServletPath());
            FileInputStream in=new FileInputStream(path);
            Image src=ImageIO.read(in);
            int w=src.getWidth(null);//w为你过滤图片的宽度
            int h=src.getHeight(null);//h为你过滤图片的长度
            BufferedImage img=new BufferedImage(w+width,h+height,BufferedImage.TYPE_INT_RGB);//构建画板(画板的宽度为两个图片之和)
            Graphics g=img.getGraphics();//得到画笔
            g.drawImage(src,0,0,w,h,null);//把原图片写入画板
            g.drawImage(fimg,width,height,fw,fh,null);//把水印图片写入画板
            g.dispose();//生成图片
            JPEGImageEncoder e=JPEGCodec.createJPEGEncoder(response.getOutputStream());
            e.encode(img);
            response.getOutputStream().close();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }
}