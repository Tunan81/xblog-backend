package team.ik.utils;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommentModerationUtil {

    private static final String MODERATION_URL = "http://47.109.104.147:9102/comment/moderation";

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentModerationRequest {
        private JsonArray comments;
        private String mode;
    }

    @Data
    public static class CommentModerationResponse {
        private double costSeconds;
        private JsonObject results;
    }

    public CommentModerationResponse moderateComments(CommentModerationRequest request) {
        Gson gson = new Gson();

        // 将请求对象转换为 JSON 字符串
        String jsonString = gson.toJson(request);

        // 发送请求并获取响应
        String responseJson = HttpRequest.post(MODERATION_URL)
                .header(Header.CONTENT_TYPE, "application/json")
                .body(jsonString)
                .timeout(20000)
                .execute().body();

        // 解析响应
        return gson.fromJson(responseJson, CommentModerationResponse.class);
    }
}
