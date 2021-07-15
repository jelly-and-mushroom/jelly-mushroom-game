package team.jellymushroom.fullmoon.entity.http;

import lombok.Data;

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
    private Object msg;

    /**
     * 若某入参无意义，则填入null
     */
    public static HttpResponseEntity success(Object data, Object msg) {
        HttpResponseEntity result = new HttpResponseEntity();
        result.status = ResponseStatus.SUCCESS.getValue();
        result.data = data;
        result.msg = msg;
        return result;
    }

    /**
     * 若某入参无意义，则填入null
     */
    public static HttpResponseEntity error(Object msg) {
        HttpResponseEntity result = new HttpResponseEntity();
        result.status = ResponseStatus.ERROR.getValue();
        result.msg = msg;
        return result;
    }
}

/**
 * HttpResponseEntity用到的表征状态的枚举值
 */
enum ResponseStatus {

    SUCCESS("success"),
    ERROR("error");

    private String value;

    ResponseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
