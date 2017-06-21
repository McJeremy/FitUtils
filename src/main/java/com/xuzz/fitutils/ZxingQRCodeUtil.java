package com.xuzz.fitutils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author Baih
 * @version V1.0
 * @Title: ZxingQRCodeUtil
 * @Package com.hxtx.core.utils
 * @Description: Zxing二维码处理
 * @date 2017/1/13 11:08
 */
public class ZxingQRCodeUtil {
    // 图片宽度的一般
    private static final int IMAGE_WIDTH = 80;
    private static final int IMAGE_HEIGHT = 80;
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
    private static final int FRAME_WIDTH = 2;

    // 二维码写码器
    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

    /**
     * 通过网络地址获取InputStream
     *
     * @param Url 网络地址
     * @return
     */
    public static InputStream getUrlInputStream(String Url) {
        URL url = null;
        InputStream iputstrea = null;
        try {
            url = new URL(Url);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
            uc.connect();
            iputstrea = uc.getInputStream();
        } catch (IOException e) {
            return null;
        }
        return iputstrea;
    }

    /**
     * @param content       二维码显示的文本
     * @param width         二维码的宽度
     * @param height        二维码的高度
     * @param srcImagePath  中间嵌套的图片
     * @param destImagePath 二维码生成的地址
     */
    public static void encode(String content, int width, int height,
                              String srcImagePath, String destImagePath) {
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, width, height, srcImagePath),
                    "jpg", new File(destImagePath));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content      二维码显示的文本
     * @param width        二维码的宽度
     * @param height       二维码的高度
     * @param srcImagePath 中间嵌套的图片
     * @param imgSuffix    二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param response     Http响应
     */
    public static void encodeOut(String content, int width, int height, String srcImagePath, String imgSuffix, HttpServletResponse response) throws IOException {
        OutputStream outStream = response.getOutputStream();
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, width, height, srcImagePath),
                    imgSuffix, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        outStream.flush();
        outStream.close();
    }

    /**
     * @param content         二维码显示的文本
     * @param width           二维码的宽度
     * @param height          二维码的高度
     * @param srcImagePathUrl 中间嵌套图片的http地址
     * @param imgSuffix       二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param response        Http响应
     */
    public static void encodeOutHttp(String content, int width, int height, String srcImagePathUrl, String imgSuffix, HttpServletResponse response) throws IOException {
        OutputStream outStream = response.getOutputStream();
        try {
            File file = new File(srcImagePathUrl);
            FileInputStream inputStream = new FileInputStream(file);
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, width, height, inputStream),
                    imgSuffix, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        outStream.flush();
        outStream.close();
    }

    /**
     * @param content       二维码显示的文本
     * @param width         二维码的宽度
     * @param height        二维码的高度
     * @param srcImagePath  中间嵌套的图片
     * @param imgSuffix     二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param destImagePath 二维码生成的地址
     */
    public static void encodeOut(String content, int width, int height, String srcImagePath, String imgSuffix, String destImagePath) {
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, width, height, srcImagePath),
                    imgSuffix, new File(destImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content       二维码显示的文本
     * @param srcImagePath  中间嵌套的图片
     * @param imgSuffix     二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param destImagePath 二维码生成的地址
     */
    public static void encodeOut6x(String content, String srcImagePath, String imgSuffix, String destImagePath) {
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, 600, 600, srcImagePath),
                    imgSuffix, new File(destImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content       二维码显示的文本
     * @param srcImagePath  中间嵌套的图片
     * @param imgSuffix     二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param destImagePath 二维码生成的地址
     */
    public static void encodeOut4x(String content, String srcImagePath, String imgSuffix, String destImagePath) {
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, 400, 400, srcImagePath),
                    imgSuffix, new File(destImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content         二维码显示的文本
     * @param srcImagePathUrl 中间嵌套图片的http地址
     * @param imgSuffix       二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param destImagePath   二维码生成的地址
     */
    public static void encodeOutFile4x(String content, String srcImagePathUrl, String imgSuffix, String destImagePath) {
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, 400, 400, getUrlInputStream(srcImagePathUrl)),
                    imgSuffix, new File(destImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content       二维码显示的文本
     * @param inputStream   中间嵌套的图片
     * @param imgSuffix     二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param destImagePath 二维码生成的地址
     */
    public static void encodeOut4x(String content, InputStream inputStream, String imgSuffix, String destImagePath) {
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, 400, 400, inputStream),
                    imgSuffix, new File(destImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content     二维码内容
     * @param inputStream 中间嵌套的图片
     * @param imgSuffix   二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param response    Http响应
     */
    public static void encodeOutHttp4x(String content, InputStream inputStream, String imgSuffix, HttpServletResponse response) throws IOException {
        OutputStream outStream = response.getOutputStream();
        try {
            ImageIO.write(genBarcode(content, 400, 400, inputStream),
                    imgSuffix, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        outStream.flush();
        outStream.close();
    }

    /**
     * @param content         二维码显示的文本
     * @param srcImagePathUrl 中间嵌套图片的http地址
     * @param imgSuffix       二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param response        Http响应
     */
    public static void encodeOutHttp4x(String content, String srcImagePathUrl, String imgSuffix, HttpServletResponse response) throws IOException {
        OutputStream outStream = response.getOutputStream();
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, 400, 400, getUrlInputStream(srcImagePathUrl)),
                    imgSuffix, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        outStream.flush();
        outStream.close();
    }

    /**
     * @param content         二维码显示的文本
     * @param srcImagePathUrl 中间嵌套图片的http地址
     * @param imgSuffix       二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param response        Http响应
     */
    public static void download4XQRCode(String content, String srcImagePathUrl, String imgSuffix, HttpServletResponse response) throws IOException {
        setResponseHeader(response, new Date().getTime() + "", imgSuffix);
        OutputStream outStream = response.getOutputStream();
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, 400, 400, getUrlInputStream(srcImagePathUrl)),
                    imgSuffix, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        outStream.flush();
        outStream.close();
    }


    /**
     * 设置响应头
     *
     * @param response
     */
    public static void setResponseHeader(HttpServletResponse response, String fileName, String imgSuffix) {
        String charset = response.getCharacterEncoding();
        response.setContentType("application/octet-stream;charset=" + charset);
        try {
            fileName = new String(fileName.getBytes("utf-8"), charset);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + URLEncoder.encode("." + imgSuffix, charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content       二维码显示的文本
     * @param srcImagePath  中间嵌套的图片
     * @param imgSuffix     二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param destImagePath 二维码生成的地址
     */
    public static void encodeOut2x(String content, String srcImagePath, String imgSuffix, String destImagePath) {
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, 200, 200, srcImagePath),
                    imgSuffix, new File(destImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到BufferedImage
     *
     * @param content      二维码显示的文本
     * @param width        二维码的宽度
     * @param height       二维码的高度
     * @param srcImagePath 中间嵌套的图片
     * @return
     * @throws WriterException
     * @throws IOException
     */
    private static BufferedImage genBarcode(String content, int width,
                                            int height, String srcImagePath) throws WriterException,
            IOException {
        // 读取源图像
        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,
                IMAGE_HEIGHT, false);

        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int i = 0; i < scaleImage.getWidth(); i++) {
            for (int j = 0; j < scaleImage.getHeight(); j++) {
                srcPixels[i][j] = scaleImage.getRGB(i, j);
            }
        }

        java.util.Hashtable hint = new java.util.Hashtable();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成二维码
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
                width, height, hint);

        // 二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];

        // System.out.println(matrix.getHeight());
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                // 读取图片
                if (x > halfW - IMAGE_HALF_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH
                        && y < halfH + IMAGE_HALF_WIDTH) {
                    pixels[y * width + x] = srcPixels[x - halfW
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                }
                // 在图片四周形成边框
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        - IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                    pixels[y * width + x] = 0xfffffff;
                } else {
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
                            : 0xfffffff;
                }
            }
        }

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);

        return image;
    }

    /**
     * 得到BufferedImage
     *
     * @param content     二维码显示的文本
     * @param width       二维码的宽度
     * @param height      二维码的高度
     * @param inputStream 中间嵌套的输入流
     * @return
     * @throws WriterException
     * @throws IOException
     */
    private static BufferedImage genBarcode(String content, int width,
                                            int height, InputStream inputStream) throws WriterException,
            IOException {
        // 读取源图像
        BufferedImage scaleImage = scale(inputStream, IMAGE_WIDTH,
                IMAGE_HEIGHT, false);

        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int i = 0; i < scaleImage.getWidth(); i++) {
            for (int j = 0; j < scaleImage.getHeight(); j++) {
                srcPixels[i][j] = scaleImage.getRGB(i, j);
            }
        }

        java.util.Hashtable hint = new java.util.Hashtable();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成二维码
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
                width, height, hint);

        // 二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];

        // System.out.println(matrix.getHeight());
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                // 读取图片
                if (x > halfW - IMAGE_HALF_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH
                        && y < halfH + IMAGE_HALF_WIDTH) {
                    pixels[y * width + x] = srcPixels[x - halfW
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                }
                // 在图片四周形成边框
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        - IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                    pixels[y * width + x] = 0xfffffff;
                } else {
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
                            : 0xfffffff;
                }
            }
        }

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);

        return image;
    }

    /**
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
     *
     * @param srcImageFile 源文件地址
     * @param height       目标高度
     * @param width        目标宽度
     * @param hasFiller    比例不对时是否需要补白：true为补白; false为不补白;
     * @throws IOException
     */
    private static BufferedImage scale(String srcImageFile, int height,
                                       int width, boolean hasFiller) throws IOException {
        double ratio = 0.0; // 缩放比例
        File file = new File(srcImageFile);
        BufferedImage srcImage = ImageIO.read(file);
        Image destImage = srcImage.getScaledInstance(width, height,
                BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
            if (srcImage.getHeight() > srcImage.getWidth()) {
                ratio = (new Integer(height)).doubleValue()
                        / srcImage.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue()
                        / srcImage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform
                    .getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        }
        if (hasFiller) {// 补白
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null))
                graphic.drawImage(destImage, 0, (height - destImage
                                .getHeight(null)) / 2, destImage.getWidth(null),
                        destImage.getHeight(null), Color.white, null);
            else
                graphic.drawImage(destImage,
                        (width - destImage.getWidth(null)) / 2, 0, destImage
                                .getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            graphic.dispose();
            destImage = image;
        }
        return toBufferedImage(destImage);
    }

    /**
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
     *
     * @param inputStream 源文件输入流
     * @param height      目标高度
     * @param width       目标宽度
     * @param hasFiller   比例不对时是否需要补白：true为补白; false为不补白;
     * @throws IOException
     */
    private static BufferedImage scale(InputStream inputStream, int height,
                                       int width, boolean hasFiller) throws IOException {
        double ratio = 0.0; // 缩放比例
        BufferedImage srcImage = ImageIO.read(inputStream);
        Image destImage = srcImage.getScaledInstance(width, height,
                BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
            if (srcImage.getHeight() > srcImage.getWidth()) {
                ratio = (new Integer(height)).doubleValue()
                        / srcImage.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue()
                        / srcImage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform
                    .getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        }
        if (hasFiller) {// 补白
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null))
                graphic.drawImage(destImage, 0, (height - destImage
                                .getHeight(null)) / 2, destImage.getWidth(null),
                        destImage.getHeight(null), Color.white, null);
            else
                graphic.drawImage(destImage,
                        (width - destImage.getWidth(null)) / 2, 0, destImage
                                .getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            graphic.dispose();
            destImage = image;
        }
        return toBufferedImage(destImage);
    }

    /**
     * Image转BufferedImage
     *
     * @param image
     * @return
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
           /* if (hasAlpha) {
             transparency = Transparency.BITMASK;
             }*/

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
            /*if (hasAlpha) {
             type = BufferedImage.TYPE_INT_ARGB;
             }*/
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }


    /**
     * @param content         二维码显示的文本
     * @param srcImagePathUrl 中间嵌套图片的http地址
     * @param imgSuffix       二维码图片后缀（BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif）
     * @param response        Http响应
     */
    public static void encodeOutHttp4x(String content, String srcImagePathUrl, String imgSuffix, int width, int height, boolean Scaling, boolean hasFiller, HttpServletResponse response) throws IOException {
        OutputStream outStream = response.getOutputStream();
        try {
            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
            ImageIO.write(genBarcode(content, width, height, getUrlInputStream(srcImagePathUrl), Scaling, hasFiller),
                    imgSuffix, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        outStream.flush();
        outStream.close();
    }

    /**
     * 得到BufferedImage
     *
     * @param content     二维码显示的文本
     * @param width       二维码的宽度
     * @param height      二维码的高度
     * @param inputStream 中间嵌套的输入流
     * @return
     * @throws WriterException
     * @throws IOException
     */
    private static BufferedImage genBarcode(String content, int width, int height, InputStream inputStream, boolean Scaling, boolean hasFiller) throws WriterException,
            IOException {
        // 读取源图像
        BufferedImage scaleImage = scale(inputStream, IMAGE_WIDTH, IMAGE_HEIGHT, Scaling, hasFiller);

        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int i = 0; i < scaleImage.getWidth(); i++) {
            for (int j = 0; j < scaleImage.getHeight(); j++) {
                srcPixels[i][j] = scaleImage.getRGB(i, j);
            }
        }

        java.util.Hashtable hint = new java.util.Hashtable();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成二维码
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
                width, height, hint);

        // 二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];

        // System.out.println(matrix.getHeight());
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                // 读取图片
                if (x > halfW - IMAGE_HALF_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH
                        && y < halfH + IMAGE_HALF_WIDTH) {
                    pixels[y * width + x] = srcPixels[x - halfW
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                }
                // 在图片四周形成边框
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        - IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                    pixels[y * width + x] = 0xfffffff;
                } else {
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
                            : 0xfffffff;
                }
            }
        }

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);

        return image;
    }


    /**
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
     *
     * @param inputStream 源文件输入流
     * @param height      目标高度
     * @param width       目标宽度
     * @param hasFiller   比例不对时是否需要补白：true为补白; false为不补白;
     * @throws IOException
     */
    private static BufferedImage scale(InputStream inputStream, int height, int width, boolean Scaling, boolean hasFiller) throws IOException {
        double ratio = 0.0; // 缩放比例
        BufferedImage srcImage = ImageIO.read(inputStream);
        Image destImage = srcImage.getScaledInstance(width, height,
                BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if (!Scaling) {
            if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
                if (srcImage.getHeight() > srcImage.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / srcImage.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue()
                            / srcImage.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform
                        .getScaleInstance(ratio, ratio), null);
                destImage = op.filter(srcImage, null);
            }
        }
        if (hasFiller) {// 补白
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null))
                graphic.drawImage(destImage, 0, (height - destImage
                                .getHeight(null)) / 2, destImage.getWidth(null),
                        destImage.getHeight(null), Color.white, null);
            else
                graphic.drawImage(destImage,
                        (width - destImage.getWidth(null)) / 2, 0, destImage
                                .getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            graphic.dispose();
            destImage = image;
        }
        return toBufferedImage(destImage);
    }

}