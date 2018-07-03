package com.cd.zj.util.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author wbqiang
 * FTP操作工具类     创建时间 20170104
 */
@Component
public class FTPclient {
    
    @Value("${FTP_ADDRESS}")  
    private String ftpHostName; //ftp地址
    @Value("${FTP_PORT}")  
    private int port;  //端口
    @Value("${FTP_USERNAME}")  
    private String userName;  //登录名
    @Value("${FTP_PASSWORD}")  
    private String password;  //密码
	
    private FTPClient ftpClient = new FTPClient();
    private OutputStream os = null;
    private InputStream is = null;
    
    private static int uploadCount;//上传次数计数
    
    
    
    public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}
	private static Logger logger = LoggerFactory.getLogger(FTPclient.class);
    
	
	
    private Pattern pattern = Pattern.compile("\\d{8}");    //8位数字
    
    public FTPclient() {}
    
    
    public FTPclient(String userName,String password,String ftpHostName,int port){
        super();
        this.userName = userName;
        this.password = password;
        this.ftpHostName = ftpHostName;
        this.port = port;
    }
    
     
    
    /**
     *  建立链接
     * 
     */
    private void connect(){
        try {
            logger.info("开始链接...");
            ftpClient.setControlEncoding("utf-8");
            ftpClient.connect(ftpHostName, port);
            int reply = ftpClient.getReplyCode();   //ftp响应码
            if(!FTPReply.isPositiveCompletion(reply)){  //ftp拒绝链接
                logger.error("ftp拒绝链接...");
                System.out.println("ftp拒绝链接...");
                ftpClient.disconnect();
            }
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();       //设置被动模式    通知server端开通端口传输数据
            ftpClient.setBufferSize(256);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
           // ftpClient.setControlEncoding("utf-8");
            logger.info("登录成功！");
        } catch (SocketException e) {
            
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            
            logger.error(e.getMessage(), e);
        }
    }
    /**
     * 退出FTP登录关闭链接   并  关闭输入输出流
     * @param os
     */
    private void close(){
        try {
            if(is!=null){
                is.close();
            }
            if(os!=null){
                os.flush();
                os.close();
            }
            ftpClient.logout();
            logger.info("退出登录！");
            ftpClient.disconnect();
            logger.info("关闭链接！");
        } catch (IOException e) {
            
            logger.error(e.getMessage(), e);
        }
    }
    
    /**
     * 下载文件
     * @param ftpFileName    
     * @param downloadDate
     * @throws IOException 
     */
    public void downloadFiles(String ftpFileName,OutputStream os) throws IOException{
        connect();
        //downloadFileOrDir(ftpFileName);
        downloadFileOrDir(ftpFileName,os);
        //downloadFileByDate(ftpFileName,downloadDate);
        close();
    }
    
    /**
     * 下载指定文件
     * @param ftpFileName
     * @param downloadDate
     */
    @SuppressWarnings("unused")
	private void downloadFileByDate(String ftpFileName,String downloadDate){
        try {
            if(isDir(ftpFileName)){        //文件夹
                String[] names = ftpClient.listNames();
                for(int i=0;i<names.length;i++){
                    System.out.println(names[i] + "--------------");
                    if(pattern.matcher(names[i]).matches()){   //如果是8位数字的文件夹
                        downloadFileByDate(ftpFileName + "/" + downloadDate,downloadDate);  //指定文件夹
                        ftpClient.changeToParentDirectory();
                        break;
                    }
                    if(isDir(names[i])){
                        downloadFileByDate(ftpFileName + "/" + names[i],downloadDate);
                        ftpClient.changeToParentDirectory();
                    }else{
                        is = ftpClient.retrieveFileStream(names[i]);     //取出文件转成输入流 
                        zipByFile(ftpFileName+"/"+names[i],is);                             //压缩文件 
                        //在retrieveFileStream后面加上completePendingCommand，changeWorkingDirectory才能正常输出
                        //而且completePendingCommand一定要在is.close()之后，否则容易程序死掉，坑爹啊；
                        ftpClient.completePendingCommand();  
                        //ftpClient.changeToParentDirectory();
                    }
//                    //测试 
//                    if("04".equals(names[i])){
//                        break;
//                    }
                }
            } else {    //文件
                System.out.println(ftpFileName + "-------------------------");
                is = ftpClient.retrieveFileStream(ftpFileName);
                zipByFile(ftpFileName,is);                             //压缩文件 
                ftpClient.completePendingCommand(); 
                ftpClient.changeToParentDirectory();
            }
            //os.flush();
            logger.info("下载成功！");
        } catch (IOException e) {
            logger.error("下载失败！",e);
        }
    }
    
    
   public long getFileSize(String ftpFileName) {
       connect();
        //获取文件大小
			long size = 0;
			try {
				size = ftpClient.listFiles(ftpFileName)[0].getSize();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			close();
			return size;
    }
    
    
    /**
     * @param ftpFileName
     * @throws IOException 
     */
    private void downloadFileOrDir(String ftpFileName,OutputStream os) throws IOException{
        try {
            if(isDir(ftpFileName)){        //文件夹
                String[] names = ftpClient.listNames();
                for(int i=0;i<names.length;i++){
                    System.out.println(names[i] + "--------------");
                    if(isDir(names[i])){
                        downloadFileOrDir(ftpFileName + "/" + names[i],os);
                        ftpClient.changeToParentDirectory();
                    }else{
                        File loadFile = new File(ftpFileName + File.separator
                                + names[i]);
                        os = new FileOutputStream(loadFile);
                        ftpClient.retrieveFile(names[i], os);
                    }
                }
            } else {    //文件
            //    File file = new File(downloadDate);
             //   os = new FileOutputStream(file);
                
                	
                
            	   ftpClient.setControlEncoding("UTF-8"); // 中文支持  
                   ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
                   ftpClient.enterLocalPassiveMode();//被动模式 
                   ftpClient.retrieveFile(ftpFileName, os);
                   ftpClient.changeToParentDirectory();
                   
                   
            }
            logger.info("下载成功！");
        } catch (IOException e) {
        	logger.error("下载失败！",e);
        	throw e;
        }
    }
    
    
    
    
    
    /**
     * @param ftpFileName
     */
    @SuppressWarnings("unused")
	private void downloadFileOrDir1(String ftpFileName,String downloadDate){
        try {
            if(isDir(ftpFileName)){        //文件夹
                String[] names = ftpClient.listNames();
                for(int i=0;i<names.length;i++){
                    System.out.println(names[i] + "--------------");
                    if(isDir(names[i])){
                        downloadFileOrDir1(ftpFileName + "/" + names[i],downloadDate);
                        ftpClient.changeToParentDirectory();
                    }else{
                        File loadFile = new File(ftpFileName + File.separator
                                + names[i]);
                        os = new FileOutputStream(loadFile);
                        ftpClient.retrieveFile(names[i], os);
                    }
                }
            } else {    //文件
                File file = new File(downloadDate);
                os = new FileOutputStream(file);
                ftpClient.retrieveFile(ftpFileName, os);
                ftpClient.changeToParentDirectory();
            }
            logger.info("下载成功！");
        } catch (IOException e) {
            logger.error("下载失败！",e);
        }
    }
    
    /**
     * 判断是否是目录
     * @param fileName
     * @return
     */
    public boolean isDir(String fileName){
         try {
            // 切换目录，若当前是目录则返回true,否则返回true。
            boolean falg = ftpClient.changeWorkingDirectory(fileName);
            return falg;
        } catch (IOException e) {
            
            logger.error(e.getMessage(), e);
        }
         return false;
    }
    
    /**
     * 压缩文件 
     */
    private void zipByFile(String fileName,InputStream is){
         try {
            // System.out.println(fileName+"==============");
            ((ZipOutputStream) os).putNextEntry(new ZipEntry(fileName));
             // 设置注释  
            //zip.setComment("hello");  
            int temp = 0;  
            while((temp = is.read()) != -1){  
                os.write(temp);  
            }  
            is.close();
            logger.info("压缩成功！");
        } catch (IOException e) {
            
            logger.error(e.getMessage(), e);
            
        } finally{
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                
                e.printStackTrace();
            }
        }
    }
    //创建文件夹
    public void createDir2(String dirname){
        try{
        	ftpClient.makeDirectory(dirname);
            System.out.println("在目标服务器上成功建立了文件夹: " + dirname);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    

	public void uploadFile(String ftpPath,String fileName,InputStream is) throws IOException{
        connect();
        uploadFileByDo(ftpPath,fileName,is);
        close();
     }
	
	public void uploadFilebyRename(String ftpPath,String fileName,InputStream is) throws IOException{
		connect();
		uploadFileRename(ftpPath,fileName,is);
		close();
	}
    
	

    /** 
     * 创建目录(有则切换目录，没有则创建目录) 
     * @param dir 
     * @return 
     */  
    public boolean createDir(String dir){  
        if(StringUtil.isNullOrEmpty(dir))  
            return true;  
        String d;  
        try {  
            //目录编码，解决中文路径问题  
            d = new String(dir.toString().getBytes("GBK"),"iso-8859-1");  
            //尝试切入目录  
            if(ftpClient.changeWorkingDirectory(d))  
                return true;  
            dir = StringUtil.trimStart(dir, "/");  
            dir = StringUtil.trimEnd(dir, "/");  
            String[] arr =  dir.split("/");  
            StringBuffer sbfDir=new StringBuffer();  
            //循环生成子目录  
            for(String s : arr){  
            	sbfDir.setLength(0);
                sbfDir.append(s);  
                sbfDir.append("/");  
                //目录编码，解决中文路径问题  
                d = new String(sbfDir.toString().getBytes("GBK"),"iso-8859-1");  
                //尝试切入目录  
                if(ftpClient.changeWorkingDirectory(d))  
                    continue;  
                if(!ftpClient.makeDirectory(d)){  
                    System.out.println("[失败]ftp创建目录："+sbfDir.toString());  
                    return false;  
                }else {  
                	System.out.println("[成功]创建ftp目录："+sbfDir.toString());  
                	ftpClient.changeWorkingDirectory(d);
                }
            }  
            //将目录切换至指定路径  
            return ftpClient.changeWorkingDirectory(getFatherPath(dir));  
        } catch (Exception e) {  
        	
            e.printStackTrace();  
            return false;
        }  
    }  
      
    private String getFatherPath(String path) {
    	String gang="";
    	int n=path.split("/").length;//返回的次数
    	for(int i=0;i<n;i++) {
    		gang+="../";
    	}
    	return gang;
    }
    
    /**
     * 上传文件 (非递归)
     * @param ftpPath   FTP服务器保存目录
     * @param fileName  上传到FTP服务器上的文件名
     * @throws IOException 
     */
    private void uploadFileByDo(String ftpPath,String fileName,InputStream is) throws IOException{
      //ftpClient.rename(remote, name);  //ftp修改文件名
    	//这里临时性的解决了第一次ftp上的中文变？？？的问题
    	 if(uploadCount==0)
    		 fileName=new String(fileName.getBytes("UTF-8"),"iso-8859-1");
    	try {
            if(ftpClient.changeWorkingDirectory(ftpPath)){
                ftpClient.storeFile(fileName, is);
            }else{
            	createDir(ftpPath);
            	 if(ftpClient.changeWorkingDirectory(ftpPath)){
            		ftpClient.storeFile(fileName, is);
                 }
            }
            uploadCount++;
            logger.debug("本次服务器上传次数："+uploadCount);
        } catch (IOException e) {
            
            logger.error(e.getMessage(), e);
            throw e;
        } finally{
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 防止上传乱码问题造成的文件名不一致的问题
     * @throws IOException 
     */
    private void rename(String fileName) throws IOException {
	   	 String name = ftpClient.listFiles(fileName)[0].getName();
		 if(!name.equals(fileName)) {
		  boolean result = ftpClient.rename(name, new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
		  logger.debug("文件名不相同:修改文件文件名结果：{}，修改后的文件名：{}",result);
		 }
    }
    
    /**
     * 上传文件 (非递归)（支持改名）
     * @param ftpPath   FTP服务器保存目录
     * @param fileName  上传到FTP服务器上的文件名
     * @throws IOException 
     */
    private void uploadFileRename(String ftpPath,String fileName,InputStream is) throws IOException{
    	try {
            if(ftpClient.changeWorkingDirectory(ftpPath)){
                ftpClient.storeFile(fileName, is);
            }else{
            	createDir(ftpPath);
            	 if(ftpClient.changeWorkingDirectory(ftpPath)){
            		ftpClient.storeFile(fileName, is);
                 }
            }
            rename(fileName);
        } catch (IOException e) {
            
            logger.error(e.getMessage(), e);
            throw e;
        } finally{
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                
                logger.error(e.getMessage(), e);
            }
        }
    }
    /**
     * 上传文件 
     * @param ftpPath   FTP服务器保存目录
     * @param fileName  上传到FTP服务器上的文件名
     * @throws IOException 
     */
    @SuppressWarnings("unused")
	private void uploadFileByDo2(String ftpPath,String fileName,InputStream is){
        try {
            if(ftpClient.changeWorkingDirectory(ftpPath)){
                ftpClient.storeFile(fileName, is);
            }else{
            	createDir(ftpPath);
            	uploadFileByDo(ftpPath,fileName,is);
              //  logger.info("上传FTP服务器路径有误！");
            }
        } catch (IOException e) {
            
            logger.error(e.getMessage(), e);
        } finally{
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                
                logger.error(e.getMessage(), e);
            }
        }
    }
    
    
    
    
/*	  *//**
	 *    * 删除远程FTP文件    *    *
	 *  @param remote    *      远程文件路径   
	 *   * @return
	 *    * @throws IOException    
	 *//*
  public FTPStatus delete(String remote) throws IOException 
  { 
    ftpClient.enterLocalPassiveMode(); 
  
    ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
  
    FTPStatus result = null; 
  
    FTPFile[] files = ftpClient.listFiles(remote); 
    if (files.length == 1) 
    { 
      boolean status = ftpClient.deleteFile(remote); 
      result = status ? FTPStatus.Delete_Remote_Success : FTPStatus.Delete_Remote_Faild; 
    } 
    else
    { 
      result = FTPStatus.Not_Exist_File; 
    } 
    Log.getLogger(this.getClass()).info("FTP服务器文件删除标识："+result); 
    return result; 
  }*/

    /** 
     * 删除远程FTP文件 
     * 
     * @param remote 
     *      远程文件路径 
     * @return 
     * @return 
     * @throws IOException 
     */
    public boolean delete(String remote) throws IOException 
    { 
     // ftpClient.enterLocalPassiveMode(); 
    
      connect();
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
      boolean result; 
    
      FTPFile[] files = ftpClient.listFiles(remote); 
      if (files.length == 1) 
      { 
      //  boolean status = ftpClient.deleteFile(remote); 
        //boolean status = ftpClient.removeDirectory(remote);
        boolean status =ftpClient.deleteFile(remote);
       System.out.println("状态码:"+   ftpClient.getReplyCode());
        result = status; 
      } 
      else
      { 
        result = false; 
      } 
      logger.info("FTP服务器文件删除标识："+result); 
      close();
      return result; 
    } 
    
    
    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getFtpHostName() {
        return ftpHostName;
    }


    public void setFtpHostName(String ftpHostName) {
        this.ftpHostName = ftpHostName;
    }


    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }
    
    public OutputStream getOs() {
        return os;
    }
    public void setOs(OutputStream os) {
        this.os = os;
    }
    /**
     * @param args
     * @throws IOException 
     * @throws UnknownHostException 
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        //downloadFiles();
    	//String userName,String password,String ftpHostName,int port
 /*   	  FTPclient ftpUtil = new FTPclient("csd","123456","192.168.22.70",21);
    	  
    	  FileInputStream fis= new FileInputStream("C:\\Users\\chen.shuodong\\Desktop\\xx.jpg");
    	  ftpUtil.setIs(fis);
    	  ftpUtil.uploadFile("lib", "xx1.jpg");*/
    	  
    	
  	 // FTPclient ftpUtil = new FTPclient("csd","123456","192.168.22.70",21);
    	//File f= new File("C:\\test\\test");
  	  FTPclient ftpUtil = new FTPclient("1","123456","192.168.22.163",21);
  	  ftpUtil.connect();
  	  //ftpUtil.delete("zzws_infection/workbrief/1521428875271/");
  	  
  	 // ftpUtil.setIs(new FileInputStream(f));
  	
  	 // ftpUtil.setOs(new FileOutputStream("C:\\test\\test\\xx2.jpg"));
  	
  	 // ftpUtil.downloadFiles("/lib/xx1.jpg", ftpUtil.getOs());
  	  
  	  System.out.println("iamok");
    	}

}