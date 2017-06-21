package com.xuzz.fitutils;
/**
 * 图形验证码工具类
 */

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ValidateCodeUtil {
    Random random = new Random();
    char[] code = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    // 设置图片的宽、高
    int width = 80;
    int height = 30;

    public String getRandomString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 4; i++) { // 生成六个字符
            buffer.append(code[random.nextInt(code.length)]);
        }
        return buffer.toString();
    }

    public Color getRandomColor() { // 得到随机颜色
        return new Color(random.nextInt(255), random.nextInt(255),
                random.nextInt(255));
    }

    public Color getReverseColor(Color c) { // 得到颜色的反色
        return new Color(255 - c.getRed(), 255 - c.getGreen(),
                255 - c.getBlue());
    }

    public void writeCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置背景
        Color fcolor = getReverseColor(getRandomColor());
        // 创建一个彩色图片
        BufferedImage bimage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        // 创建绘图对象
        Graphics2D g = bimage.createGraphics();
        // 字体样式为宋体,加粗，20磅
        g.setFont(new Font("宋体", Font.BOLD, 20));
        // 先画出背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 再画出前景色
        g.setColor(fcolor);
        //g.drawRect(0, 0, width - 1, height - 1); 
        // 绘制随机字符
        String verifyCode = getRandomString();
        request.getSession(true).setAttribute("verifyCode", verifyCode);
        g.drawString(verifyCode, 20, 22);
        // 画出干扰
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        ServletOutputStream outstream;
        outstream = response.getOutputStream();
//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outstream);
//		encoder.encode(bimage);
        ImageIO.write(bimage, "jpg", outstream);
        outstream.flush();
    }

    /**
     * 获取验证码
     *
     * @param verifyCode 验证码
     * @param request
     * @param response
     * @throws IOException
     */
    public void writeCode(String verifyCode, HttpServletResponse response) throws IOException {
        // 设置背景
        Color fcolor = getReverseColor(getRandomColor());
        // 创建一个彩色图片
        BufferedImage bimage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        // 创建绘图对象
        Graphics2D g = bimage.createGraphics();
        // 字体样式为宋体,加粗，20磅
        g.setFont(new Font("宋体", Font.BOLD, 20));
        // 先画出背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 再画出前景色
        g.setColor(fcolor);
        //g.drawRect(0, 0, width - 1, height - 1);
        // 绘制随机字符
        g.drawString(verifyCode, 20, 22);
        // 画出干扰
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        ServletOutputStream outstream;
        outstream = response.getOutputStream();
//      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outstream);
//      encoder.encode(bimage);
        ImageIO.write(bimage, "jpg", outstream);
        outstream.flush();
    }
}
