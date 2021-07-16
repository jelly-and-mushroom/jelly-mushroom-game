package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;
import team.jellymushroom.fullmoon.constant.HttpResponseStatusEnum;

/**
 * Controller通用返回结构
 * 如非特别必要，也应是Controller返回的唯一结构
 * 若返回结构不合理，应调整本类，而非增加新类
 */
@Data
public class HttpResponseEntity {

    /**
     * 响应状态
     * 即ResponseStatus.value
     */
    private String status;

    /**
     * 数据
     */
    private Object data;

    /**
     * 描述信息
     */
    private String msg;

    /**
     * 接到同样为HttpResponseEntity结构的http返回值时，解析status字段的key
     */
    public static final String STATUS_KEY = "status";

    /**
     * 接到同样为HttpResponseEntity结构的http返回值时，解析data字段的key
     */
    public static final String DATA_KEY = "data";

    /**
     * 接到同样为HttpResponseEntity结构的http返回值时，解析msg字段的key
     */
    public static final String MSG_KEY = "msg";

    /**
     * 若某入参无意义，则填入null
     */
    public static HttpResponseEntity success(Object data, String msg) {
        HttpResponseEntity result = new HttpResponseEntity();
        result.status = HttpResponseStatusEnum.SUCCESS.getValue();
        result.data = data;
        result.msg = msg;
        return result;
    }

    /**
     * 若某入参无意义，则填入null
     */
    public static HttpResponseEntity error(String msg) {
        HttpResponseEntity result = new HttpResponseEntity();
        result.status = HttpResponseStatusEnum.ERROR.getValue();
        result.msg = msg;
        return result;
    }
}
