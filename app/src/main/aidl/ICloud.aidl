// ICloud.aidl

interface ICloud {
    // 天气信息
    void onWeatherInfo(String json);

    // 配置信息
    void onPanelInfo(String json);

    /**
    * 日志上报信息
    */
    void onLogInfo(String json);

    /**
    * 云端资源值下发
    * @param res 资源值
    * @param subscribeDid 为屏的did时处理屏自己的业
    * 如：调节屏的亮度为40%  来自云端的数据 {"data":{"14.11.85":"40"},"did":"lumi1.54ef44316c8d","subscribeDid":"lumi1.54ef44316c8d"}
    * -> res: 14.11.85 ,value: 40, did: lumi1.54ef44316c8d， subscribeDid: lumi1.54ef44316c8d
    */
    void onResDownload(String res, String value, String did, String subscribeDid);
}