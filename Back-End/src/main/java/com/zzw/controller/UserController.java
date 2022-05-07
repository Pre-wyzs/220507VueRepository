package com.zzw.controller;

import com.zzw.entity.User;
import com.zzw.service.UserService;
import com.zzw.utils.Result;
import com.zzw.utils.jwttoken.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController  //1、因为是前后端分离的项目，所以只把json数据返回给前端
@RequestMapping("/api/admin")
public class UserController {

    @Autowired
    private UserService userService;

    //交叉查询的总接口
    @GetMapping("/general")
    @CrossOrigin
    public Result<List<User>> generalSelectInterface(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String power,
            @RequestParam(required = false) String nameSure,
            @RequestParam(required = false) String nameFuzzy,
            @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(required = false,defaultValue = "5") Integer pageSize
    ){

        //最开始先对传进来的参数进行判断
        if(id == null){  //因为restful风格中，参数是必须要传递的，所以只能给一个标志位，如果是-1的话就设置该参数为null...
//            System.out.println("id没传");
//            id = null;
        }
        if(power.compareTo("") == 0){
//            System.out.println("power没传");
            power = null;
        }
        if(nameSure.compareTo("") == 0){
//            System.out.println("nameSure没传");
            nameSure = null;
        }
        if(nameFuzzy.compareTo("") == 0){
//            System.out.println("nameFuzzy没传");
            nameFuzzy = null;
        }
//        if(pageNum == -1){
//            System.out.println("页码没传");
//            pageNum = null;
//        }
//        if(pageSize == -1){
//            pageSize = null;
//            System.out.println("页面大小没传");
//        }

        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("power",power);
        map.put("nameSure",nameSure);
        map.put("nameFuzzy",nameFuzzy);
        //获取总的记录数
        Integer rowNum = userService.generalSelectInterfaceNum(map);

        //如果前端没有把指定的页码传递过来，就默认它为一
//        if(pageNum == null){
//            pageNum = 1;
//        }
//        if(pageSize == null){
//            pageSize = 5;
//        }

        map.put("startIndex",(pageNum - 1)*pageSize);
        map.put("pageSize",pageSize);
        //传给前端总的页数
        Integer totalPages;
        if(rowNum%pageSize == 0){
            totalPages = rowNum/pageSize;
        }else {
            totalPages = rowNum/pageSize + 1;
        }
//        System.out.println("总页数" + totalPages);
        return Result.success(userService.generalSelectInterface(map),totalPages);
    }



    //添加一个用户
    @PostMapping("/add")  //2、post方法提交才会调用该方法
    @CrossOrigin  //*配置该方法返回的数据允许跨域请求
    public Result addUser(@RequestBody User user){  //3、前后端分离项目要把前端传来的json数据转换为User对象
        //userService.addUser(user);
//        System.out.println(user);

        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = user.getToken();
        //token解析
        Claims claims = JwtUtils.tokenParse(token);
//        System.out.println(claims);

        if(claims.get("power").toString().compareTo("0") == 0){
            userService.addUser(user);
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    //更新一个用户
    @PutMapping("/update")
    @CrossOrigin
    public Result updateUser(@RequestBody User user) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = user.getToken();
        //token解析
        Claims claims = JwtUtils.tokenParse(token);


        String power = claims.get("power").toString();
        String id = claims.get("id").toString();

        /** 如果是超级管理员权限的话就随便更新就行了捏*/
        if(power.compareTo("0") == 0){
            userService.updateUser(user);
            return Result.success();
            /** 如果是普通管理员权限的话捏，那它就只能修改普通用户了捏*/
        }else if(power.compareTo("1") == 0){
            User user0 = userService.selectUserById(user.getId());
            if(user0.getPower().compareTo("2") == 0 && user.getPower().compareTo("2") == 0){//防止普通管理把用户的权限改高了
                userService.updateUser(user);
                return Result.success();
            }else{
                return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
            }
        }else{  /**如果是普通用户的话他就只能修改它自己了捏**/
            if(user.getId().toString().compareTo(id) == 0 && user.getPower().compareTo("2") == 0){ //防止把权限改高了
                userService.updateUser(user);
                return Result.success();
            }
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    //删除一个用户
    @PostMapping("/del")  //restful风格的参数传递
    @CrossOrigin
    public Result deleteUser(@RequestBody User user) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = user.getToken();
        //token解析
        Claims claims = JwtUtils.tokenParse(token);
//        System.out.println(claims);

        if(claims.get("power").toString().compareTo("0") == 0){
            userService.deleteUser(user.getId());
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息

    }

    //4、查询用户：这时候后端就需要给前端返回一个json数据了
    @GetMapping("/id/{id}")
    @CrossOrigin
    public Result<User> selectUserById(@PathVariable Integer id) {
        return Result.success(userService.selectUserById(id),1);  //因为只会查出一个人，所以默认的总页数就是1了
    }

    //5、分页查询所有用户
    @GetMapping("/all")
    @CrossOrigin
    public Result<List<User>> selectUsers(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                          @RequestParam(required = false,defaultValue = "5") Integer pageSize
                                          ){

        Integer totalPages;
        Map<String,Object> map = new HashMap<>();
        //给他们传一个空的map就行了
        if(userService.selectLength(map)%pageSize!=0){
            totalPages = userService.selectLength(map)/pageSize + 1;
        }else {
            totalPages = userService.selectLength(map)/pageSize;
        }

        return Result.success(userService.selectUsers(pageNum,pageSize),totalPages);
    }

    //6、分页查询所有power用户
    @GetMapping("/power/{power}")
    @CrossOrigin
    public Result<List<User>> selectByPower(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                            @RequestParam(required = false,defaultValue = "5")Integer pageSize,
    @PathVariable String power){

        Integer totalPages;
        Map<String,Object> map = new HashMap<>();
        map.put("power",power);
        if(userService.selectLength(map)%pageSize!=0){
            totalPages = userService.selectLength(map)/pageSize + 1;
        }else {
            totalPages = userService.selectLength(map)/pageSize;
        }

        return Result.success(userService.selectByPower(power,pageNum,pageSize),totalPages);

    }

    //7、精确的用户名查询
    @GetMapping("/nameSure/{name}")
    @CrossOrigin
    public Result<User> selectUserByName(@PathVariable String name) {
        return Result.success(userService.selectUserByName(name),1);
    }


    //8、分页模糊查询
    @GetMapping("/name/{name}")
    @CrossOrigin
    public Result<List<User>> getUsersByName(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                            @RequestParam(required = false,defaultValue = "5")Integer pageSize,
                                            @PathVariable String name){

        Integer totalPages;
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        if(userService.selectLength(map)%pageSize!=0){
            totalPages = userService.selectLength(map)/pageSize + 1;
        }else {
            totalPages = userService.selectLength(map)/pageSize;
        }

        return Result.success(userService.getUsersByName(name,pageNum,pageSize),totalPages);

    }



}
