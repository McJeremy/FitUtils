package com.xuzz.fitutils.qiniu;

/**
 * Created by win10 on 2016/11/7.
 */


import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.IOException;

/**
 * @author Baih
 * @version V1.0
 * @Title: UploadDemo
 * @Package com.hxtx.offcial.util.qiniu
 * @Description: 七牛云存储测试类
 * @date
 */
public class UploadDemo
{
    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "zvgAqiH95zR3E4XyXzIOHD2jcBp9KknvLnPA3grt";
    String SECRET_KEY = "zN8F_bHtZNr4kkEH-E5oCZ0G51Fm-Z1OGsnoODE3";
    //要上传的空间
    String bucketname = "static";
    //上传到七牛后保存的文件名
    String key = "upload//2016/11/08/1478574637970.png";
    //上传文件的路径
    String FilePath = "C:/Users/win10/Pictures/Saved Pictures/不知道花名.jpeg";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken()
    {
        //return auth.uploadToken(bucketname);
        return auth.uploadToken(bucketname, key, 3600, new StringMap()
                .put("callbackUrl", "http://localhost:8080")
                .put("callbackBody", "filename=$(fname)&filesize=$(fsize)"));
    }

    // 覆盖上传
    public String getCoverUpToken()
    {
        //<bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
        return auth.uploadToken(bucketname, key);
    }

    public void upload() throws IOException
    {
        try
        {
            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, getCoverUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e)
        {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try
            {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1)
            {
                //ignore
                e1.printStackTrace();
            }
        }
    }

    /**
     * 分段上传文件（断点）
     *
     * @throws IOException
     */
    public void subsectionUpload() throws IOException
    {
        //设置断点记录文件保存在指定文件夹或的File对象
        String recordPath = "/upload/test";
        //实例化recorder对象
        Recorder recorder = new FileRecorder(recordPath);
        //实例化上传对象，并且传入一个recorder对象
        UploadManager uploadManager = new UploadManager(recorder);

        try
        {
            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, getUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e)
        {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try
            {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1)
            {
                //ignore
            }
        }
    }

    /**
     * 文件下载
     */
    public void download(String URL)
    {
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(URL,3600);
        System.out.println(downloadRUL);
    }

    public static void main(String args[]) throws IOException
    {
//        new UploadDemo().upload();
//        new UploadDemo().subsectionUpload();
//        new UploadDemo().download("http://og43bqa82.bkt.clouddn.com/upload//2016/11/08/1478574637970.png");
    }
}