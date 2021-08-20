package com.com.myblog.model;



import java.io.Serializable;

import lombok.Data;

/*{
         "id": 5,
         "title": "제목5",
         "content": "내용5",
         "user": {
                "id": 2,
                "username": "cos",
                "password": "1234",
                "email": "cos@nate.com",
                "created": "2021-26-11T09:26:05",
                "updated": "2021-26-11T09:26:05"
         },
         "created": "2021-26-11T09:26:05",
         "updated": "2021-26-11T09:26:05"
},*/

@Data
public class Post implements Serializable {

    private static final String TAG = "Post";

    private int id;
    private String title;
    private String content;
    private User user;
    private String created;
    private String updated;
}
