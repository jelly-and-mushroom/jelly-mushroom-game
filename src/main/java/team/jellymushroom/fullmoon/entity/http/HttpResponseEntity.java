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
     * 构建一个成功的响应
     * 若某入参无意义，则填入null
     *
     * @param data 数据
     * @param msg 描述信息
     * @return 构建出的成功的响应
     */
    public static HttpResponseEntity success(Object data, Object msg) {
        HttpResponseEntity result = new HttpResponseEntity();
        result.status = ResponseStatus.SUCCESS.getValue();
        result.data = data;
        result.msg = msg;
        return result;
    }

    /**
     * 构建一个失败的响应
     *
     * @param msg 失败描述信息，若没有则传入null
     * @return 构建出的成功的响应
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

    SUCCESS("success", "成功"),
    ERROR("error", "失败");

    /**
     * 状态值
     */
    private String value;

    /**
     * 状态描述
     */
    private String description;

    /**
     * @param value 状态值
     * @param description 状态描述
     */
    ResponseStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
