package cn.brightasdream.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.brightasdream.authorization.annotation.*;
import cn.brightasdream.authorization.types.Constants;
import cn.brightasdream.common.IdAndPersonSchema;
import cn.brightasdream.common.IndexConstructor;
import cn.brightasdream.common.JsonFileResolver;
import cn.brightasdream.common.TokenSchemaManager;
import cn.brightasdream.dao.FileDao;
import cn.brightasdream.model.FileModel;
import cn.brightasdream.model.ResBody;
@RestController
@CrossOrigin
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private FileDao fileDao;
    
    @Autowired
    private TokenSchemaManager manager;
    @Autowired
    private IdAndPersonSchema schema;
    @Autowired
    private IndexConstructor constructor;
    @Authorization
    @RequestMapping("/file")
    public ResBody upload(@RequestParam("file") MultipartFile file,HttpServletRequest request){
        // 返回类体对象
        ResBody resBody = new ResBody();
        if(file.isEmpty()){
            resBody.setStatus(Constants.FAIL);
            resBody.setContent("上传失败！请上传文件");
            return resBody;
        }
        
        String fileName = file.getOriginalFilename();
        String filePath = request.getSession().getServletContext().getRealPath("uploadFile/");
        File path = new File(filePath);
        if(!path.exists()){
            path.mkdir();
        }
        File dest = new File(filePath+fileName);

        try {
            file.transferTo(dest);
            // 将文件信息写到数据库中
            FileModel fileModel = new FileModel();
            fileModel.setUsername(request.getParameter("username"));
            fileModel.setFilesize(file.getSize());
            fileModel.setFilename(file.getOriginalFilename());
            fileModel.setFiletype(file.getContentType());
            fileModel.setUploadTime(request.getParameter("uploadtime"));
            String savename = UUID.randomUUID().toString().replaceAll("-", "");//随机生成不重复的savename
            fileModel.setSavename(savename);
            fileModel.setDestination(filePath+fileName);
            
            //写入数据库
            fileDao.addFile(fileModel);

            // 构建索引并持久化
            createAndStoreIndex(filePath+fileName,filePath+savename+".schs");

            resBody.setStatus(Constants.SUCCESS);
            resBody.setContent(fileModel);
            return resBody;
        }  catch (IOException e) {
            resBody.setStatus(Constants.FAIL);
            resBody.setContent("上传失败或者构建索引失败"+e.toString());
            return resBody;
        }
    }

    // @Authorization
    // @RequestMapping("/getJson")
    // public Object getJson(HttpServletRequest request){
    //     JsonFileResolver jsonFileResolver = JsonFileResolver.getInstance();
    //     JSONArray array = jsonFileResolver.getJsonObjFromFile("F:\\学习资料\\大三上课程\\信息系统检索\\homework1\\test.json");

    //     // 构建索引表
    //     constructor.constructFromJSONArray(array);
        
    //     // 将对象持久化
    //     // String filePath = request.getSession().getServletContext().getRealPath("uploadFile/");
    //     try {
    //         File file = new File("F:\\学习资料\\大三上课程\\信息系统检索\\homework1\\" + "test.schs");
    //         if(!file.exists()) file.createNewFile();
    //         ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
    //         outputStream.writeObject(manager);
    //         outputStream.writeObject(schema);
    //         outputStream.close();
    //     } catch (FileNotFoundException e) {
    //         e.printStackTrace();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return e.toString();
	// 	}
    //     return manager.getTokenSchema();
        
    // }


    private void createAndStoreIndex(String source,String target) throws IOException {
        // 将创建索引并将对象持久化
        JsonFileResolver jsonFileResolver = JsonFileResolver.getInstance();
        JSONArray array = jsonFileResolver.getJsonObjFromFile(source);

        // 构建索引表
        constructor.constructFromJSONArray(array);
        
        // 将对象持久化
        // String filePath = request.getSession().getServletContext().getRealPath("uploadFile/");
        
        File file1 = new File(target);
        if(!file1.exists()) file1.createNewFile();
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file1));
        outputStream.writeObject(manager);
        outputStream.writeObject(schema);
        outputStream.close();
        
    }

    @Authorization
    @RequestMapping("/getAllFile")
    public ResBody getAllUploadedFile(String username){
        ResBody resBody = new ResBody();
        fileDao.getAllFile(username, resBody);
        return resBody;
    }

    @Authorization
    @RequestMapping("/getInvertIndex")
    public ResBody getInvertIndex(String path){
        ResBody resBody = new ResBody();

        TokenSchemaManager manager;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(path));
            // 从本地文件中加载索引表
            manager = (TokenSchemaManager) input.readObject();
            input.close();
            resBody.setStatus(Constants.SUCCESS);
            resBody.setContent(manager.getTokenSchema());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resBody;
    }
}