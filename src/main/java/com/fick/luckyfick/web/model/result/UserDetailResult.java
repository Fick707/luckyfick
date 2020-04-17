package com.fick.luckyfick.web.model.result;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @name: UserDetailResult
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/17
 **/
@Data
@ToString
public class UserDetailResult implements Serializable {
    private static final long serialVersionUID = 9215517051933649021L;

    private String name = "Figo";

    private String avatar = "https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png";

    private String userId = "000001";

    private String signature = "飞机的飞";

    private String title = "CTO";

    private String group = "ruos.tech";

    private String country = "China";

    private String address = "Chongqing";

    private String phone = "18516240550";


}
