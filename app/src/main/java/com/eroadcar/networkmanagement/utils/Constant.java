package com.eroadcar.networkmanagement.utils;

public class Constant {
    //    APP类型
    public final static String APPTYPE = "android";
    //   正式地址
    public final static String HTTP = "http://116.236.115.124:9090/";
    //     登录
    public final static String login = HTTP +
            "/workdir/ernet/ernet/index.php/Shop4S/Public/login";
    //    获取身份类型
    public final static String getRoles = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Public/getRoles ";
    //   获取统计信息
    public final static String reqStatisticsForManager = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/BossCenter/reqStatisticsForManager";
    //    获取员工列表
    public final static String getUsers = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Users/getUsers";
    //    修改员工信息
    public final static String modifyUser = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Users/modifyUser";
    //    提交意见
    public final static String sendSuggest = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/BossCenter/sendSuggest";
    //    更新
    public final static String getApkInfo = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/ApkManager/getApkInfo";

    //    添加员工
    public final static String addUser = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Users/addUser";
    // 分配权限
    public final static String distributeRole = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/Users/distributeRole";
    // 获取验证码接口
    public final static String getVerificateCode = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/getVerificateCode";
    // 修改密码接口
    public final static String modifyPassword = HTTP
            + "workdir/ernet/ernet/index.php/Shop4S/SellCar/modifyPassword";
    // 登出
    public final static String logout = HTTP
            + "/workdir/ernet/ernet/index.php/Shop4S/public/logout";
}
