package com.zsm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author : ZSM
 * Time :  2024/06/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Integer id;
    private String title;
    private String desc;
    private String photoPath;
    private String content;
    private String createTime;
    private String updateTime;
    private String type;

    public boolean check() {
        return title != null && desc != null && content != null && type != null;
    }
}
