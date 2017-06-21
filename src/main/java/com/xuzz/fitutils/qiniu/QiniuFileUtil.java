package com.xuzz.fitutils.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author Baih
 * @version V1.0
 * @Title: QiniuFileUtil
 * @Package com.hxtx.offcial.util.qiniu
 * @Description: 七牛云存储处理工具类
 * @date 2016-11-7 15：26：00
 */

public class QiniuFileUtil
{
/*
    //  密钥配置
//    private static Auth auth = Auth.create(SysConfig.QN_ACCESS_KEY, SysConfig.QN_SECRET_KEY);
    private static Auth auth = Auth.create("","");
    private static String to_bucketname="static";
    //  创建上传对象
    private static UploadManager uploadManager = new UploadManager();

    //  获取连接服务器的Token--默认空间
    public static String getUpToken()
    {
        return auth.uploadToken(to_bucketname);
    }

    */
/**
     * 覆盖上传获取连接服务器的Token--默认空间
     *
     * @param key 文件对象的key(文件全名)
     * @return 七牛云的Token
     *//*

    public static String getCoverUpToken(String key)
    {
        return auth.uploadToken(to_bucketname, key);
    }

    */
/**
     * 获取连接服务器的Token--指定存储空间
     *
     * @param bucketname 存储空间名
     * @return七牛云的Token
     *//*

    public static String getUpToken(String bucketname)
    {
        return auth.uploadToken(bucketname);
    }

    */
/**
     * 覆盖上传获取连接服务器的Token
     *
     * @param bucketname 存储空间名
     * @param key        文件对象的key(文件全名)
     * @return 七牛云的Token
     *//*

    public static String getCoverUpToken(String bucketname, String key)
    {
        return auth.uploadToken(bucketname, key);
    }

    */
/**
     * 上传文件到七牛云（Http上传）-- 默认空间
     *
     * @param request   HttpServletRequest请求对象
     * @param prevFix   文件保存的前缀
     * @param inputName 文件的元素名（key）
     * @return 文件存储的相对路径
     * @throws IOException
     *//*

    public static String upload(HttpServletRequest request, String prevFix, String inputName) throws IOException
    {
        StringBuffer objectKey = new StringBuffer();
        try
        {
//          获取文件信息
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(inputName);
            if(null==file || file.isEmpty())
            {
                return "";
            }
//          获取上传的文件名
            String realFileName = file.getOriginalFilename();
//          获取文件存储名
            objectKey.append("updatepath");
            objectKey.append(prevFix);
            objectKey.append("/");
            objectKey.append(DateUtil.getCurMilli());
            objectKey.append(realFileName.substring(realFileName.lastIndexOf(".")));
//          调用put方法上传
            Response res = uploadManager.put(file.getBytes(), objectKey.toString(), getUpToken());
//          打印返回的信息
        } catch (QiniuException e)
        {
            Response r = e.response;
            try
            {r.bodyString();
            } catch (QiniuException e1)
            {
//              ignore
                e1.printStackTrace();
            }
//          出现异常清空路径（便于调用者逻辑处理）
            objectKey = new StringBuffer();
        }
        return objectKey.toString();
    }

    */
/**
     * 上传文件到七牛云（Http上传）-- 默认空间
     *
     * @param multipartFile 文件对象
     * @param prevFix   文件保存的前缀
     * @return 文件存储的相对路径
     * @throws IOException
     *//*

    public static String uploadDoApp(MultipartFile multipartFile, String prevFix) throws IOException
    {
        StringBuffer objectKey = new StringBuffer();
        try
        {
            if(null==multipartFile || multipartFile.isEmpty())
            {
                return "";
            }
//          获取上传的文件名
            String realFileName = multipartFile.getOriginalFilename();
//          获取文件存储名
            objectKey.append(SysConfig.to_upPath);
            objectKey.append(prevFix);
            objectKey.append("/");
            objectKey.append(DateUtil.getCurMilli());
            objectKey.append(realFileName.substring(realFileName.lastIndexOf(".")));
//          调用put方法上传
            Response res = uploadManager.put(multipartFile.getBytes(), objectKey.toString(), getUpToken());
//          打印返回的信息
        } catch (QiniuException e)
        {
            Response r = e.response;
//          请求失败时打印的异常的信息
            try
            {
//              响应的文本信息
                r.bodyString();
            } catch (QiniuException e1)
            {
//              ignore
                e1.printStackTrace();
            }
//          出现异常清空路径（便于调用者逻辑处理）
            objectKey = new StringBuffer();
        }
        return objectKey.toString();
    }

    */
/**
     * 上传文件到七牛云（Http上传）-- 指定空间
     *
     * @param request    HttpServletRequest请求对象
     * @param prevFix    文件保存的前缀
     * @param inputName  文件的元素名（key）
     * @param bucketname 存储空间名
     * @return 文件存储的相对路径
     * @throws IOException
     *//*

    public static String upload(HttpServletRequest request, String prevFix, String inputName, String bucketname) throws IOException
    {
        StringBuffer objectKey = new StringBuffer();
        try
        {
//          获取文件信息
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(inputName);
            if(null==file || file.isEmpty())
            {
                return "";
            }
//          获取上传的文件名
            String realFileName = file.getOriginalFilename();
//          获取文件存储名
            objectKey.append(SysConfig.to_upPath);
            objectKey.append(prevFix);
            objectKey.append("/");
            objectKey.append(DateUtil.getCurMilli());
            objectKey.append(realFileName.substring(realFileName.lastIndexOf(".")));
//          调用put方法上传
            Response res = uploadManager.put(file.getBytes(), objectKey.toString(), getUpToken(bucketname));
//          打印返回的信息
            Log4j.info(res.bodyString());
        } catch (QiniuException e)
        {
            Response r = e.response;
//          请求失败时打印的异常的信息
            Log4j.info(r.toString());
            try
            {
//              响应的文本信息
                Log4j.info(r.bodyString());
            } catch (QiniuException e1)
            {
//              ignore
                e1.printStackTrace();
            }
//          出现异常清空路径（便于调用者逻辑处理）
            objectKey = new StringBuffer();
        }
        return objectKey.toString();
    }

    */
/**
     * 覆盖上传文件到七牛云（Http上传）-- 默认空间
     *
     * @param request   HttpServletRequest请求对象
     * @param objectKey 文件对象的Key
     * @param inputName 文件的元素名（key）
     * @return 文件存储的相对路径
     * @throws IOException
     *//*

    public static String coverUpload(HttpServletRequest request, String objectKey, String inputName) throws IOException
    {
        try
        {
//          获取文件信息
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(inputName);

            if(null==file || file.isEmpty())
            {
                Log4j.info("上次图片失败，图片为空");
                return "";
            }

//          获取上传的文件名
            String realFileName = file.getOriginalFilename();
//          调用put方法上传
            Response res = uploadManager.put(file.getBytes(), objectKey, getCoverUpToken(objectKey));
//          打印返回的信息
            Log4j.info(res.bodyString());
//          返回路径
            return objectKey;
        } catch (QiniuException e)
        {
            Response r = e.response;
//          请求失败时打印的异常的信息
            Log4j.info(r.toString());
            try
            {
//              响应的文本信息
                Log4j.info(r.bodyString());
            } catch (QiniuException e1)
            {
//              ignore
                e1.printStackTrace();
            }
//          出现异常清空路径（便于调用者逻辑处理）
            return null;
        }
    }

    */
/**
     * 覆盖上传文件到七牛云（Http上传）-- 指定空间
     *
     * @param request    HttpServletRequest请求对象
     * @param objectKey  文件对象的Key
     * @param inputName  文件的元素名（key）
     * @param bucketname 存储空间名
     * @return 文件存储的相对路径
     * @throws IOException
     *//*

    public static String coverUpload(HttpServletRequest request, String objectKey, String inputName, String bucketname) throws IOException
    {
        try
        {
//          获取文件信息
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(inputName);
            if(null==file || file.isEmpty())
            {
                Log4j.info("上次图片失败，图片为空");
                return "";
            }
//          调用put方法上传
            Response res = uploadManager.put(file.getBytes(), objectKey, getCoverUpToken(SysConfig.to_bucketname, objectKey));
//          打印返回的信息
            Log4j.info(res.bodyString());
//          返回路径
            return objectKey;
        } catch (QiniuException e)
        {
            Response r = e.response;
//          请求失败时打印的异常的信息
            Log4j.info(r.toString());
            try
            {
//              响应的文本信息
                Log4j.info(r.bodyString());
            } catch (QiniuException e1)
            {
//              ignore
                e1.printStackTrace();
            }
//          出现异常清空路径（便于调用者逻辑处理）
            return null;
        }
    }

    */
/**
     * 七牛云存储删除文件--默认空间
     *
     * @param objectKey 文件对象的key
     * @return 执行结果
     *//*

    public static boolean delete(String objectKey)
    {
        try
        {
            //实例化一个BucketManager对象
            BucketManager bucketManager = new BucketManager(auth);
            //调用delete方法移动文件
            bucketManager.delete(SysConfig.to_bucketname, objectKey);
            return true;
        } catch (QiniuException e)
        {
            //捕获异常信息
            Response r = e.response;
            Log4j.error(r.toString());
        }
        return false;
    }

    */
/**
     * 七牛云存储删除文件--指明空间
     *
     * @param objectKey 文件对象的key
     * @param bucketname 存储空间名
     * @return 执行结果
     *//*

    public static boolean delete(String objectKey, String bucketname)
    {
        try
        {
            //实例化一个BucketManager对象
            BucketManager bucketManager = new BucketManager(auth);
            //调用delete方法移动文件
            bucketManager.delete(bucketname, objectKey);
            return true;
        } catch (QiniuException e)
        {
            //捕获异常信息
            Response r = e.response;
            Log4j.error(r.toString());
        }
        return false;
    }

    */
/**
     * 上传文件到七牛云（本地上传）
     *
     * @param fileUrl 本地路径
     * @param prevFix 文件保存的前缀
     * @return 文件存储的相对路径
     * @throws IOException
     *//*

    public static String upload(String fileUrl, String prevFix) throws IOException
    {
        StringBuffer objectKey = new StringBuffer();
        try
        {
//          获取文件信息
            File file = new File(fileUrl);
//          获取上传的文件名
            String realFileName = file.getName();
//          获取文件存储名
            objectKey.append(SysConfig.to_upPath);
            objectKey.append(prevFix);
            objectKey.append("/");
            objectKey.append(DateUtil.getCurMilli());
            objectKey.append(realFileName.substring(realFileName.lastIndexOf(".")));
//          调用put方法上传
            Response res = uploadManager.put(file, objectKey.toString(), getUpToken());
//          打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e)
        {
            Response r = e.response;
//          请求失败时打印的异常的信息
            System.out.println(r.toString());
            try
            {
//              响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1)
            {
//              ignore
                e1.printStackTrace();
            }
//          出现异常清空路径（便于调用者逻辑处理）
            objectKey = new StringBuffer();
        }
        return objectKey.toString();
    }
    */
}
